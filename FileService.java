import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private final Path filePath;

    public FileService() {
        this("students.csv");
    }

    public FileService(String fileName) {
        this.filePath = Paths.get(fileName);
    }

    public List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return students;
        }

        int lineNumber = 0;
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;
                try {
                    List<String> fields = parseCsvLine(line);
                    if (fields.size() != 4) throw new IllegalArgumentException("Expected 4 CSV fields");
                    int id = Integer.parseInt(fields.get(0).trim());
                    String name = fields.get(1);
                    int age = Integer.parseInt(fields.get(2).trim());
                    double marks = Double.parseDouble(fields.get(3).trim());
                    students.add(new Student(id, name, age, marks));
                } catch (RuntimeException ex) {
                    System.err.println("Skipping invalid record at " + filePath + ":" + lineNumber
                            + " (" + ex.getMessage() + ")");
                }
            }
        } catch (IOException ex) {
            System.err.println("Could not read " + filePath + ": " + ex.getMessage());
        }
        return students;
    }

    // Rewrites the entire file through a temporary file, then replaces the old file.
    public boolean saveStudents(List<Student> students) {
        Path absolute = filePath.toAbsolutePath();
        Path parent = absolute.getParent();
        Path temp = null;
        try {
            if (parent != null) Files.createDirectories(parent);
            temp = Files.createTempFile(parent, "students-", ".tmp");
            try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
                for (Student student : students) {
                    writer.write(toCsv(student));
                    writer.newLine();
                }
            }

            try {
                Files.move(temp, absolute, StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(temp, absolute, StandardCopyOption.REPLACE_EXISTING);
            }
            return true;
        } catch (IOException ex) {
            System.err.println("Could not save student records: " + ex.getMessage());
            if (temp != null) {
                try { Files.deleteIfExists(temp); } catch (IOException ignored) { }
            }
            return false;
        }
    }

    private String toCsv(Student student) {
        return student.getId() + "," + quote(student.getName()) + ","
                + student.getAge() + "," + student.getMarks();
    }

    private String quote(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    // Supports quoted CSV fields, commas and escaped double quotes.
    private List<String> parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean quoted = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (quoted) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        field.append('"');
                        i++;
                    } else {
                        quoted = false;
                    }
                } else {
                    field.append(c);
                }
            } else if (c == ',' ) {
                fields.add(field.toString());
                field.setLength(0);
            } else if (c == '"' && field.length() == 0) {
                quoted = true;
            } else {
                field.append(c);
            }
        }
        if (quoted) throw new IllegalArgumentException("Unclosed quoted CSV field");
        fields.add(field.toString());
        return fields;
    }
}

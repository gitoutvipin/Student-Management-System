import java.io.*;
import java.util.ArrayList;

public class FileService {

    private static final String FILE_NAME = "students.txt";

    public void saveStudent(Student student) {

        try (BufferedWriter buffer =
                new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            buffer.write(student.toString());
            buffer.newLine();

         //   System.out.println("Student saved successfully.");

        } catch (IOException e) {
            System.out.println("Error writing data: " + e.getMessage());
        }
    }

    public ArrayList<Student> loadStudents() {

        ArrayList<Student> students = new ArrayList<>();

        try (BufferedReader reader =
                new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",", -1);

                int id = Integer.parseInt(data[0].trim());
                String name = data[1].trim();
                int age = Integer.parseInt(data[2].trim());
                double marks = Double.parseDouble(data[3].trim());

                students.add(new Student(id, name, age, marks));
            }

        } catch (FileNotFoundException e) {
            System.out.println("No existing student records.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid student data: " + e.getMessage());
        }

        return students;
    }

    public void deleteStudentById(int id) {

    ArrayList<String> updatedLines = new ArrayList<>();

    try (BufferedReader reader =
             new BufferedReader(new FileReader(FILE_NAME))) {

        String line;

        while ((line = reader.readLine()) != null) {

            String[] data = line.split(",", -1);

            int studentId = Integer.parseInt(data[0].trim());

            // Keep all records except matching ID
            if (studentId != id) {
                updatedLines.add(line);
            }
        }

    } catch (IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
        return;
    }

    try (BufferedWriter writer =
             new BufferedWriter(new FileWriter(FILE_NAME, false))) {

        for (String line : updatedLines) {
            writer.write(line);
            writer.newLine();
        }


    } catch (IOException e) {
        System.out.println("Error updating file: " + e.getMessage());
    }
}
    public void updateStudentById(int id, String name, int age, double marks) {

    ArrayList<String> updatedLines = new ArrayList<>();

    boolean found = false;

    try (BufferedReader reader =
             new BufferedReader(new FileReader(FILE_NAME))) {

        String line;

        while ((line = reader.readLine()) != null) {

            String[] data = line.split(",", -1);

            int studentId = Integer.parseInt(data[0].trim());

            if (studentId == id) {

                // Replace existing student record
                updatedLines.add(
                    id + "," + name + "," + age + "," + marks
                );

                found = true;

            } else {

                // Keep other student records unchanged
                updatedLines.add(line);
            }
        }

    } catch (IOException | NumberFormatException e) {
        System.out.println("Error reading file: " + e.getMessage());
        return;
    }

    if (!found) {
        System.out.println("Student ID not found in file!");
        return;
    }

    try (BufferedWriter writer =
             new BufferedWriter(new FileWriter(FILE_NAME, false))) {

        for (String record : updatedLines) {
            writer.write(record);
            writer.newLine();
        }

    //    System.out.println("Student file updated successfully.");

    } catch (IOException e) {
        System.out.println("Error updating file: " + e.getMessage());
    }
}
}

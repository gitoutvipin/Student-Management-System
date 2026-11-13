import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> students;
    private final FileService fileService;

    public StudentService() {
        this.fileService = new FileService();
        this.students = new ArrayList<>(fileService.loadStudents());
    }

    public boolean addStudent(Student student) {
        if (searchStudentById(student.getId()) != null) {
            System.out.println("Student ID already exists!");
            return false;
        }

        List<Student> updated = new ArrayList<>(students);
        updated.add(student);
        if (!fileService.saveStudents(updated)) {
            System.out.println("Student was not added because saving failed.");
            return false;
        }
        students.add(student);
        System.out.println("Student added successfully.");
        return true;
    }

    public void viewStudentsDetails() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("===== STUDENT LIST =====");
        for (Student student : students) student.viewStudent();
    }

    public Student searchStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) return student;
        }
        return null;
    }

    public boolean removeStudentById(int id) {
        Student existing = searchStudentById(id);
        if (existing == null) {
            System.out.println("Student not found!");
            return false;
        }

        List<Student> updated = new ArrayList<>(students);
        updated.remove(existing);
        if (!fileService.saveStudents(updated)) {
            System.out.println("Student was not removed because saving failed.");
            return false;
        }
        students.remove(existing);
        System.out.println("Student removed successfully.");
        return true;
    }

    public boolean updateStudentById(int id, String name, int age, double marks) {
        Student existing = searchStudentById(id);
        if (existing == null) {
            System.out.println("Student not found!");
            return false;
        }

        Student replacement;
        try {
            replacement = new Student(id, name, age, marks);
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid student details: " + ex.getMessage());
            return false;
        }

        List<Student> updated = new ArrayList<>(students);
        int index = updated.indexOf(existing);
        updated.set(index, replacement);
        if (!fileService.saveStudents(updated)) {
            System.out.println("Student was not updated because saving failed.");
            return false;
        }
        students.set(index, replacement);
        System.out.println("Student updated successfully.");
        return true;
    }

    public void exit() {
        System.out.println("Exiting the program. Goodbye!");
    }
}

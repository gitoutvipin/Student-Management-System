
import java.util.ArrayList;

public class StudentService {

    ArrayList<Student> students = new ArrayList<>();
    FileService fileser = new FileService();

    // Load existing records when application starts
    public StudentService() {
        students = fileser.loadStudents();
    }

    // Add student
    public void addStudent(Student student) {

        // Prevent duplicate IDs
        if (searchStudentById(student.getId()) != null) {
            System.out.println("Student ID already exists!");
            return;
        }

        students.add(student);

        fileser.saveStudent(student);

        System.out.println("Student added successfully.");
    }

    // View all students
    public void viewStudentsDetails() {

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("\n===== STUDENT LIST =====");
         
        for (Student student : students) {
            student.viewStudent();
        }
    }

    // Search student
    public Student searchStudentById(int id) {

        for (Student student : students) {

            if (student.getId() == id) {
                return student;
            }
        }

        return null;
    }

    // Delete student
    public void removeStudentById(int id) {

        Student studentToRemove = searchStudentById(id);

        if (studentToRemove == null) {
            System.out.println("Student not found!");
            return;
        }

        students.remove(studentToRemove);

        // Rewrite file after deletion
        fileser.deleteStudentById(id);
        fileser.loadStudents();
   

        System.out.println("Student removed successfully.");
    }

    // Update student
    public void updateStudentById(
            int id, String name, int age, double marks) {

        Student studentToUpdate = searchStudentById(id);

        if (studentToUpdate == null) {
            System.out.println("Student not found!");
            return;
        }

        studentToUpdate.setName(name);
        studentToUpdate.setAge(age);
        studentToUpdate.setMarks(marks);

        // Rewrite file after updating
        fileser.deleteStudentById(id);

        fileser.saveStudent(studentToUpdate);

        System.out.println("Student updated successfully.");
    }

    public void exit() {
        System.out.println("Exiting the program. Goodbye!");
        System.exit(0);
    }
}

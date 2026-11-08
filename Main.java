import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        while (true) {
            System.out.println("###### Student Management System ######");
            System.out.println("1. Add Student");
            System.out.println("2. View Student Details");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Remove Student by ID");
            System.out.println("5. Update Student");
            System.out.println("6. Exit");
         //   System.out.println("3. Enter Age");
          //  System.out.println("4. Enter Marks");
            //System.out.print(". Exit: ");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter Id: ");
                    int id = scanner.nextInt();
                    System.out.print("Enter Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();
                    System.out.print("Enter Marks: ");
                    double marks = scanner.nextDouble();
                    Student student = new Student(id, name, age, marks);
                    studentService.addStudent(student);
                    //System.out.println("Student added successfully.");
                    

                    break;
                case 2:
                    
                  //  StudentService studentService = new StudentService();
                
                    studentService.viewStudentsDetails();
  

                    break;
                case 3:
                    System.out.print("Enter Student ID to search: ");
                    int searchId = scanner.nextInt();
                    Student foundStudent = studentService.searchStudentById(searchId);
                    if (foundStudent != null) {
                        System.out.println("Student found:");
                        foundStudent.viewStudent();
                    } else {
                        System.out.println("Student not found!");
                    }

                    break;
                case 4:
                    System.out.print("Enter Student ID to remove: ");
                    int removeId = scanner.nextInt();
                    studentService.removeStudentById(removeId);

                    break;

                case 5:
                    System.out.print("Enter Student ID to update: ");
                    int updateId = scanner.nextInt();
                    if (studentService.searchStudentById(updateId) == null) {
                        System.out.println("Student not found!");
                        break;
                    }
                    // System.out.println("What you want to update:");
                    // switch (scanner.nextInt()) {
                    //     case 1:
                    //         System.out.print("Enter new Name: ");
                    //         String newName = scanner.next();
                    //         studentService.updateStudentById(updateId, newName, -1, -1);
                    //         break;
                    //     case 2:
                    //         System.out.print("Enter new Age: ");
                    //         int newAge = scanner.nextInt();
                    //         studentService.updateStudentById(updateId, null, newAge, -1);
                    //         break;
                    //     case 3:
                    //         System.out.print("Enter new Marks: ");
                    //         double newMarks = scanner.nextDouble();
                    //         studentService.updateStudentById(updateId, null, -1, newMarks);
                    //         break;
                    //     default:
                    //         System.out.println("Invalid option. Please try again.");
                    // }
                    System.out.print("Enter new Name: ");

                    String newName = scanner.next();
                    System.out.print("Enter new Age: ");
                    int newAge = scanner.nextInt();
                    System.out.print("Enter new Marks: ");
                    double newMarks = scanner.nextDouble();
                    studentService.updateStudentById(updateId, newName, newAge, newMarks);

                    break;
                case 6:
                    studentService.exit();
                default:
                    System.out.println("Invalid option. Please try again.");
            }
}
    }
}
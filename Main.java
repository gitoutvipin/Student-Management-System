import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1:
                    addStudent(studentService);
                    break;
                case 2:
                    studentService.viewStudentsDetails();
                    break;
                case 3:
                    int searchId = readInt("Enter Student ID to search: ");
                    Student found = studentService.searchStudentById(searchId);
                    if (found == null) System.out.println("Student not found!");
                    else found.viewStudent();
                    break;
                case 4:
                    studentService.removeStudentById(readInt("Enter Student ID to remove: "));
                    break;
                case 5:
                    updateStudent(studentService);
                    break;
                case 6:
                    studentService.exit();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-6.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("###### Student Management System ######");
        System.out.println("1. Add Student");
        System.out.println("2. View Student Details");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Remove Student by ID");
        System.out.println("5. Update Student");
        System.out.println("6. Exit");
    }

    private static void addStudent(StudentService service) {
        int id = readInt("Enter ID: ");
        String name = readNonEmpty("Enter Name: ");
        int age = readInt("Enter Age: ");
        double marks = readDouble("Enter Marks (0-100): ");
        try {
            service.addStudent(new Student(id, name, age, marks));
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid student details: " + ex.getMessage());
        }
    }

    private static void updateStudent(StudentService service) {
        int id = readInt("Enter Student ID to update: ");
        if (service.searchStudentById(id) == null) {
            System.out.println("Student not found!");
            return;
        }
        String name = readNonEmpty("Enter new Name: ");
        int age = readInt("Enter new Age: ");
        double marks = readDouble("Enter new Marks (0-100): ");
        service.updateStudentById(id, name, age, marks);
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("This field cannot be empty.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                double number = Double.parseDouble(value);
                if (Double.isFinite(number)) return number;
            } catch (NumberFormatException ignored) { }
            System.out.println("Please enter a valid number.");
        }
    }
}

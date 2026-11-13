import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int id;
    private String name;
    private int age;
    private double marks;

    public Student(int id, String name, int age, double marks) {
        validate(id, name, age, marks);
        this.id = id;
        this.name = name.trim();
        this.age = age;
        this.marks = marks;
    }

    private static void validate(int id, String name, int age, double marks) {
        if (id <= 0) throw new IllegalArgumentException("ID must be greater than zero.");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        if (age < 1 || age > 120) throw new IllegalArgumentException("Age must be between 1 and 120.");
        if (!Double.isFinite(marks) || marks < 0 || marks > 100)
            throw new IllegalArgumentException("Marks must be between 0 and 100.");
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getMarks() { return marks; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name.trim();
    }

    public void setAge(int age) {
        if (age < 1 || age > 120) throw new IllegalArgumentException("Age must be between 1 and 120.");
        this.age = age;
    }

    public void setMarks(double marks) {
        if (!Double.isFinite(marks) || marks < 0 || marks > 100)
            throw new IllegalArgumentException("Marks must be between 0 and 100.");
        this.marks = marks;
    }

    public void viewStudent() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Marks: " + marks);
        System.out.println("-----------------------------");
    }
}

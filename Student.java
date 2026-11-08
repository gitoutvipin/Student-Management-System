import java.io.Serializable;
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int age;
    private double marks;
    public Student(int id, String name, int age, double marks){
        this.id = id;
        this.name = name;
        this.age = age;
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public int setAge(int age) {
        this.age = age;
        return age;
    }

    public double setMarks(double marks) {
        this.marks = marks;
        return marks;
    }

     @Override
     public String toString(){
         return id+","+name+","+age+","+marks;
     }
    void viewStudent(){
        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Marks: " + marks);
        System.out.println("-----------------------------");
    }
}
import java.util.Scanner;

class Student {
    private String firstName, lastName;

    Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String concatenate() {
        String fName = capitalize(firstName);
        String lName = capitalize(lastName);
        return fName + " " + lName;
    }

    private String capitalize(String name) {

        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String getFirstName() {
        return (firstName);
    }

    public String getLastName() {
        return (lastName);
    }
}

public class Name {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter first name : ");
        String fname = scan.nextLine();
        System.out.println("Enter last name : ");
        String lname = scan.nextLine();
        Student s = new Student(fname, lname);
        System.out.println("First Name : " + s.getFirstName());
        System.out.println("Last Name : " + s.getLastName());
        String fullName = s.concatenate();
        System.out.println("Full Name : " + fullName);
    }
}
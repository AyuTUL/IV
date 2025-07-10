package collectionframework;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Student {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<StudentList> students = new ArrayList<>();
        char choice;
        do {
            StudentList student = new StudentList();
            student.inputDetails();
            students.add(student);
            System.out.println("Add another student ? (Y/N) :");
            choice = scan.next().toUpperCase().charAt(0);
        } while (choice == 'Y');
        System.out.println("Student Details : ");
        for(StudentList student: students){
            student.outputDetails();
        }  
        System.out.println("Enter index of student record to be deleted : ");
        int delete=scan.nextInt();
        students.remove(delete);
        System.out.println("Student Details after deletion : ");
        for(StudentList student: students){
            student.outputDetails();
        }
        System.out.println("Enter index of student record to be updated : ");
        int updata=scan.nextInt();
        students.get(updata).inputDetails();
        System.out.println("Student Details after update: ");
        for(StudentList student: students){
            student.outputDetails();
        }
    }
}

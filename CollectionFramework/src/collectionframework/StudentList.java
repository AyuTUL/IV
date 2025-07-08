/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collectionframework;
import java.util.*;
/**
 *
 * @author user
 */
public class StudentList extends Student{
    private String name,address;
    private int roll;
    public void inputDetails(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Enter name : ");
        name=scan.next();
        System.out.println("Enter roll : ");
        roll=scan.nextInt();
        System.out.println("Enter address : ");
        address=scan.next();        
    }
    public void outputDetails(){
        System.out.println("Student Details:");
        System.out.println("Name : "+name);
        System.out.println("Roll : "+roll);
        System.out.println("Address : "+address);
    }
}

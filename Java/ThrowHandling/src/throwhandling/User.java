/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package throwhandling;

import java.util.Scanner;

public class User {

    private String fname, lname;
    private String age;

    public String getAge() {
        return age;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    @Override
    public String toString() {
        return "User{" + "fname=" + fname + ", lname=" + lname + ", age=" + age + '}';
    }
    
    

    public void inputDetails() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter user details :");
        System.out.println("Enter first name : ");
        fname = scan.next();
        System.out.println("Enter last name : ");
        lname = scan.next();
        System.out.println("Enter age : ");
        age = scan.next();

    }

    public void outputDetails() {
        System.out.println("User Details : ");
        System.out.println("First Name : " + fname);
        System.out.println("Last Name : " + lname);
        System.out.println("Age : " + age);
    }
}

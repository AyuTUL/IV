/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package throwhandling;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ThrowHandling {

    public static void inputValidate(User u) {
        try {
            Integer.parseInt(String.valueOf(u.getAge()));
            String stringPattern="[a-zA-Z]+";
            Pattern stringRegex=Pattern.compile(stringPattern);
            Matcher stringMatcher=stringRegex.matcher(u.getFname());
            if(stringMatcher.matches())
                System.out.println("Invalid input for first name");
        } catch (AgeNoNo e) {
            throw new AgeNoNo("Invalid input for age");
        }
    }

    public static void main(String[] args) {
        User u = new User();
        u.inputDetails();
        inputValidate(u);
        u.outputDetails();
    }

}

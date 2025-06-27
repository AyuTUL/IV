import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter 1st number : ");
        int a = scan.nextInt();
        System.out.println("Enter 2nd number : ");
        int b = scan.nextInt();
        int sum = a + b;
        System.out.println("Sum of " + a + " & " + b + " = " + sum);
    }
}
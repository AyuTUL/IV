import java.util.Scanner;

public class Factorial {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter integer : ");
        int n = scan.nextInt();
        if (n < 0)
            System.out.println("Factorial of negative number doesn't exist");
        else {
            int f = 1, a = n;
            while (a > 1) {
                f *= a;
                a--;
            }
            System.out.println("Factorial of " + n + " = " + f);
        }

    }
}

import java.util.Scanner;

public class Function {

    public static void areaOfRectangle() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter length of rectangle : ");
        float length = scan.nextFloat();
        System.out.println("Enter breadth of rectangle : ");
        float breadth = scan.nextFloat();
        float area = length * breadth;
        System.out.println("Area of Rectangle (" + length + "x" + breadth + ") = " + area);
    }

    public static double areaOfTriangle() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter base of triangle : ");
        double base = scan.nextDouble();
        System.out.println("Enter height of triangle : ");

        double height = scan.nextDouble();
        double area = 0.5 * base * height;
        return (area);
    }

    public static void areaOfSquare(int length) {
        int area = length * length;
        System.out.println("Area of Square (" + length + ") = " + area);
    }

    public static float perimeterOfRectangle(float length, float breadth) {
        float perimeter = 2 * (length + breadth);
        return (perimeter);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        areaOfRectangle();

        double triangleArea = areaOfTriangle();
        System.out.println("Area of Triangle = " + triangleArea);
        System.out.println("Enter length of square : ");

        int squareLength = scan.nextInt();
        areaOfSquare(squareLength);

        System.out.println("Enter length of rectangle : ");
        float rectangleLength = scan.nextFloat();
        System.out.println("Enter breadth of rectangle : ");
        float rectangleBreadth = scan.nextFloat();
        float rectanglePerimeter = perimeterOfRectangle(rectangleLength, rectangleBreadth);
        System.out.println("Perimeter of Rectangle = " + rectanglePerimeter);
    }

}
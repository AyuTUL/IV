/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package trycatch;

/**
 *
 * @author user
 */
public class TryCatch {

    public static void main(String[] args) {
        try {
            int array[] = {1, 2, 3};
            System.out.println("Array = " + array[3]);
            System.out.println("Division = " + 10 / 0);
            String s = null;
            System.out.println("Uppercase = " + s.toUpperCase());
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (ArithmeticException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        System.out.println("Exception handled");
    }
}

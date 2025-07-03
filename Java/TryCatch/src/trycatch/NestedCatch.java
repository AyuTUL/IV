/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trycatch;

/**
 *
 * @author user
 */
public class NestedCatch {

    public static void main(String[] args) {
        try {
            int a = 7 / 0;
            System.out.println("a");
            try {
                String str = null;
                System.out.println(str);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        System.out.println("Exception handled");
    }

}

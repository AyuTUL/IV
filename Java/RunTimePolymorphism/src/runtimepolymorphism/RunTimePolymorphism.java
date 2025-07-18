/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package runtimepolymorphism;

/**
 *
 * @author pro series
 */
public class RunTimePolymorphism {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Bank bank;
        bank = new Global(3000.0);
        System.out.println("Amount in Global = " + bank.calculateInterest());
        bank = new MLBB(4000.0);
        System.out.println("Amount in Global = " + bank.calculateInterest());
        bank = new NMB(5000.0);
        System.out.println("Amount in Global = " + bank.calculateInterest());
    }

}

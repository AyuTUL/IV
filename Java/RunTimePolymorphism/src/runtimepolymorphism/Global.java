/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runtimepolymorphism;

/**
 *
 * @author pro series
 */
class Global implements Bank {

    double rate, amount;

    Global(double amount) {
        this.amount = amount;
    }

    @Override
    public void getRate() {
        rate = 5.5;
    }

    @Override
    public double calculateInterest() {
        return ((rate / 100) * amount);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package collectionframework;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author user
 */
public class CollectionFramework {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        for (int i = 0; i < numbers.size(); i++) {
            System.out.println(numbers.get(i));
        }
        System.out.println("\n");
        for (Integer num : numbers) {
            System.out.println(num);
        }
        System.out.println("\n");
        Iterator<Integer> iterators = numbers.iterator();
        while (iterators.hasNext()) {
            System.out.println(iterators.next());
        }
        numbers.set(0,31);
        System.out.println("\n");
        for (Integer num : numbers) {
            System.out.println(num);
        }
        int index=numbers.indexOf(2);
        System.out.println("Index of 2 = "+index);
        numbers.remove(1); //passing index
        System.out.println("\n");
        for (Integer num : numbers) {
            System.out.println(num);
        }
        numbers.remove(new Integer(3)); //passing object
        System.out.println("\n");
        for (Integer num : numbers) {
            System.out.println(num);
        }
        List<Integer> numbero = new LinkedList<>();
        //array list for insert update not really delete
        //linked list for insert delete update
    }

}

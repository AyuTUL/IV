/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collectionframework;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class SetImplementation {
    public static void main(String[] args) {
        //set ->hashset,linkedhashsest,tresset
        //cant access element index wise, doesnt sotre duplicate
        Set<String> fruits =new HashSet<>();
        fruits.add("Orange");
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Mango");
        fruits.add("Apple");
        System.out.println("\n");
        for(String fruit: fruits)
        {
            System.out.println(fruit);
        }
        fruits.remove("Orange");
        System.out.println("\n");
        for(String fruit: fruits)
        {
            System.out.println(fruit);
        }
        Set<String> fruitos =new LinkedHashSet<>();
        //linkedhash maintains insertion order
        fruitos.add("Orange");
        fruitos.add("Apple");
        fruitos.add("Banana");
        fruitos.add("Mango");
        fruitos.add("Apple");
        System.out.println("\n");
        for(String fruit: fruitos)
        {
            System.out.println(fruit);
        }
        fruitos.remove("Orange");
        System.out.println("\n");
        for(String fruit: fruitos)
        {
            System.out.println(fruit);
        }
         Set<String> fruitoes =new TreeSet<>();
        //treehash sorts
        fruitoes.add("Orange");
        fruitoes.add("Apple");
        fruitoes.add("Banana");
        fruitoes.add("Mango");
        fruitoes.add("Apple");
        System.out.println("\n");
        for(String fruit: fruitoes)
        {
            System.out.println(fruit);
        }
        fruitoes.remove("Orange");
        System.out.println("\n");
        for(String fruit: fruitoes)
        {
            System.out.println(fruit);
        }
    }
}

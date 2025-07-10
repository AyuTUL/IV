/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collectionframework;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author user
 */
public class MapImplementation {
    public static void main(String[] args) {
        Map<Integer,String> hashMap=new HashMap<>();
        hashMap.put(7,"Ronaldo");
        hashMap.put(11,"Bale");
        hashMap.put(1,"Courtois");
        hashMap.put(9,"Benzema");
        hashMap.put(9,"KarimBenzema");
        System.out.println(hashMap.get(9));
        hashMap.remove(9);
        System.out.println(hashMap.get(9));
        for(Map.Entry<Integer,String> m:hashMap.entrySet())
        {
            System.out.println(m.getKey());
            System.out.println(m.getValue());
            System.out.println("-----------");
        }
        Map<Integer,String> TreeMap=new TreeMap<>();
        TreeMap.put(7,"Ronaldo");
        TreeMap.put(11,"Bale");
        TreeMap.put(1,"Courtois");
        TreeMap.put(9,"Benzema");
        TreeMap.put(9,"KarimBenzema");
        System.out.println(TreeMap.get(9));
        TreeMap.remove(9);
        System.out.println(TreeMap.get(9));
        for(Map.Entry<Integer,String> m:TreeMap.entrySet())
        {
            System.out.println(m.getKey());
            System.out.println(m.getValue());
            System.out.println("-----------");
        }
        System.out.println(TreeMap.keySet());
        System.out.println(TreeMap.values());
        //linkedMap also same
    }
}

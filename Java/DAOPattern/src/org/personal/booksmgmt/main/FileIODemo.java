/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.personal.booksmgmt.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author nischalshaky
 */
public class FileIODemo {

    public static void main(String[] args) {
        fisfosDemo();
        bisbosDemo();
        readerWriterDemo();
    }

    private static void readerWriterDemo() {
        try {
            FileWriter fw = new FileWriter("hello.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("hello world");
            bw.write("\n");
            bw.write("hello world");
            bw.write("\n");
            bw.write("hello world");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileReader fr = new FileReader("hello.txt");
            BufferedReader br = new BufferedReader(fr);
            String byteData;
            while ((byteData = br.readLine()) != null) {
                System.out.println(byteData); // Convert byte to char for text output
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void fisfosDemo() {
        try {
            FileOutputStream fos = new FileOutputStream("abc.txt");
            fos.write(67);
            fos.write(68);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fis = new FileInputStream("abc.txt");
            int byteData;
            while ((byteData = fis.read()) != -1) {
                System.out.println((char) byteData); // Convert byte to char for text output
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void bisbosDemo() {
        try {
            FileOutputStream fos = new FileOutputStream("abc.txt");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(67);
            bos.write(68);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fis = new FileInputStream("abc.txt");
            BufferedInputStream bis = new BufferedInputStream(fis);
            int byteData;
            while ((byteData = bis.read()) != -1) {
                System.out.println((char) byteData); // Convert byte to char for text output
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

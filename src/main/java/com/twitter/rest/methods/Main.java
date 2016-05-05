package com.twitter.rest.methods;

import javax.swing.JFrame;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.apple.eio.FileManager.getResource;

/*
 * Displays License splash page and calls main gui
 */
public class Main
{
    JFrame licensePopup = new JFrame();
    public static Map<Integer, String> angry = new HashMap<Integer, String>();//ArrayList for angry words
    public static Map<Integer, String> happy = new HashMap<Integer, String>();//ArrayList for happy words
    public void run()
    {
        //License Splash Page
//        JOptionPane.showMessageDialog(licensePopup, "MyTwitterScraper is free software: you can redistribute it and/or modify\n it " +
//                "under the terms of the GNU General Public License as published by\n the Free Software Foundation, either version 3 " +
//                "of the License, or\n any later version.\n\nThis program is distributed in the hope that it will be useful,\nbut without any " +
//                "warranty; without even the implied warranty of\nmerchantability or fitness for a particular purpose. See the\n" +
//                "GNU General Public License for more details at: \nhttp://www.gnu.org/licenses\n\n.");
        buildAngryWordList();
        buildHappyWordList();
        MainGui mainGui = new MainGui();
        mainGui.getGui().dispose();
        mainGui.buildGui();

    }

    public void buildAngryWordList()
    {
        try {


          //  URL fstream = Main.class.getResource("/angryWords.txt");
            File file = new File(System.getenv("ROOT")+"angryWords.txt");
            System.out.println("CLASSPATH: "+System.getenv("ROOT"));

            BufferedReader bufferedReader  = new BufferedReader(new FileReader(file));

            int j =0;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                angry.put(j,line.trim().toLowerCase());
                j++;
            }
            //fstream.close();
            System.out.println("Number of Angry Words: "+angry.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildHappyWordList()
    {
        try {
            File file = new File(System.getenv("ROOT")+"happyWords.txt");
            System.out.println(file.getAbsolutePath());
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int j =0;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                happy.put(j,line.trim().toLowerCase());
                j++;
            }
            fileReader.close();
            System.out.println("Number of Happy Words: "+happy.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package com.twitter.rest.methods;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
/*
 * Saves data and closes program
 */
public class EndingListener implements ActionListener
{
    String filePath = "";
    public void actionPerformed(ActionEvent e)
    {
        JFileChooser fileChooser = new JFileChooser(filePath);//opens dialog box for selecting save location
        //JFileChooser config
        fileChooser.setDialogTitle("Select File Name and Save Location");
        fileChooser.setDialogTitle("Select File Name and Save Location");
        int fc = fileChooser.showOpenDialog(null);//opens file dialog box to select file save location
        if(fc == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile(); //gets pathway for file
            filePath = file.getAbsolutePath() + ".csv";//returns selected file pathway as string to be used in creating csv
        }
        try
        {
            CSVFileCreator file = new CSVFileCreator(SearchListener.data, filePath);//create csv writer
            file.makeFile();//write to file
        }
        catch(Exception exc)
        {
            System.out.println("Error creating CSVFile...");
        }
        JOptionPane.showMessageDialog(null,  "CSV File Successfully Created. Program Will Now End.");
        System.exit(0);
    }
}
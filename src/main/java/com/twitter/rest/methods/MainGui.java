package com.twitter.rest.methods;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;

/*
 * initializes all gui elements. Also sets buttons and action listeners. Launches com.twitter.rest.methods.SearchListener from this class.
 */
public class MainGui
{
    public static PrintStream standardOut;
    public static JTextField searchTerms = new JTextField(20);//search query input field object
    public static JTextField Mood = new JTextField(10);//tweet mood
    static int rows = 40, col = 110; //dims for jtextarea (output field) --> 140 char == max tweet length
    public static JTextArea jta = new JTextArea("", rows, col);//output box object
    public static JScrollPane jsp = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    public static CustomOutputStream cos = new CustomOutputStream(MainGui.jta);//redirects console output to scrollpane on UI
    public static PrintStream printStream = new PrintStream(cos);//new printstream for jtextarea
    JPanel topPanel = new JPanel();//top container of UI - contains file name input box, search terms input box, and action buttons
    JPanel centerPanel = new JPanel();//center container of UI - contains scroll pane for output
    JPanel bottomPanel = new JPanel();// bottom container of UI

    FlowLayoutJFrame gui = new FlowLayoutJFrame();//gui layout
    //swing gui config

    //windows labels:
    JLabel totalTweets = new JLabel("   Mood: ");
    JLabel info = new JLabel(" Enter Search Terms: ");
    JLabel fileNamePrompt = new JLabel(" Session File Name: ");

    //Action Buttons
    JButton endButton = new JButton("Save & Close");
    public static JButton searchButton = new JButton("Capture Twitter Stream");
    JButton configure = new JButton("OAuth Config");

    //spacer
    JLabel spacer = new JLabel("       ");//spacer

    public void buildGui()
    {
        EndingListener endingEar = new EndingListener();
        SearchListener searchEar = new SearchListener();//searchListener constructor
        ConfigurationManager configurationEar = new ConfigurationManager();

        jsp.setBounds(0, 0, 1000, 750);//jscrollpane dims
        jta.setOpaque(true);
        jta.setBackground(Color.black);
        jta.setForeground(Color.green);
        searchTerms.setOpaque(true);
        searchTerms.setBackground(Color.black);
        searchTerms.setForeground(Color.green);
        Mood.setBackground(Color.black);
        Mood.setForeground(Color.green);
        topPanel.setBackground(Color.DARK_GRAY);
        centerPanel.setBackground(Color.DARK_GRAY);
        bottomPanel.setBackground(Color.DARK_GRAY);

        //window labels
        totalTweets.setForeground(Color.white);
        info.setForeground(Color.white);
        fileNamePrompt.setForeground(Color.white);
        gui.setSize(1336,768);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//gui default buttons
        gui.getContentPane().setBackground(Color.darkGray);

        //buttons
        endButton.addActionListener(endingEar);
        searchButton.addActionListener(searchEar);
        configure.addActionListener(configurationEar);

        //gui initialization
        topPanel.add(info);
        topPanel.add(searchTerms);
        topPanel.add(spacer);
        topPanel.add(searchButton);
        topPanel.add(endButton);
        topPanel.add(totalTweets);
        topPanel.add(Mood);
        topPanel.add(spacer);
        topPanel.add(configure);
        centerPanel.add(jsp);
        gui.add(topPanel);
        gui.add(centerPanel);

        gui.setVisible(false);
    }

    public FlowLayoutJFrame getGui(){

    return this.gui;
}
    public static void clickSearchButton(){

        searchButton.doClick();
    }
}

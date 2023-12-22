
package pl.polsl.view;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pl.polsl.classes.ArgValues;
import pl.polsl.classes.ErrorHandler;
import pl.polsl.classes.Results;
import pl.polsl.model.Model;

/**
 * Displays the output of the Alcohol Effects application.
 * Handles printing results to the console and writing to a text file.
 * @author nimo
 */
public class View {    
    private final ArgValues argValues;
    private final ErrorHandler errorHandler;
    
    private JFrame frame;
    
    private JPanel input_panel;
    private JPanel output_panel;
    
    private JLabel input_label;
    private JLabel output_label;
    
    private JButton run_button;
    private JButton input_button;
    private JButton output_button;
    private JTextField input_file_textfield;
    private JTextField output_file_textfield;
    
    private JScrollPane console_scrollpane;
    private JTextArea console_output;
    
    private Model modelAccess;
    
    public View(ArgValues argValues, ErrorHandler errorHandler) 
    {
        this.argValues = argValues;
        this.errorHandler = errorHandler;
        
        prepareGUI();
    }
    
    public void setModel(Model model)
    {
        this.modelAccess = model;
    }
    
    /**
    * Writes the results to a text file with a timestamped filename.
    * If an error occurs during file writing, logs an error message.
    * @param results The results to be written to the text file.
    */
    public void printToTxt(Results results) {
        
        String finalFilename = String.format("%s %s.txt", argValues.arg_output_path, new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date()));

        try (PrintWriter writer = new PrintWriter(finalFilename)) 
        {
            writer.printf(results.toString());
        } 
        catch (IOException ex) 
        {
            errorHandler.addError(103, "Error writing to the file: " + ex.getMessage());
        }
    }
    
    /**
     * Prints the results to the console.
     * @param results The results to be printed.
     */
//    public void outputToConsole(Results results) {
//        System.out.println("========================\n        Resoults\n========================");
//        System.out.printf(results.toString());
//    }
//    
    /**
    * Prints any errors stored in the error handler to the standard error stream.
    */
    public void viewErrors()
    {
        if(errorHandler.count() > 0)
            System.err.println(errorHandler.popAllAsString());
    }
    
    /**
    * Handles user interaction when there are no or insufficient command-line arguments.
    * Prompts the user to provide input for missing arguments.
    * Reads input from the console to set input paths and switches.
    */
//    public void handleNoArguments() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("""
//                           The arguments are 
//                           \t-i [filename]
//                           \t-o [filename]
//                           \t-c (output to console)
//                           Please provide the following:
//                           """);
//
//        if (argValues.arg_input_path.equals("")) {
//            System.out.print("Input (relative/absolute path and filename)\n -i: ");
//            argValues.arg_input_path = scanner.nextLine();
//        }
//
//        if (argValues.arg_output_path.equals("")) {
//            System.out.print("Output (relative/absolute path and filename)\n -o: ");
//            argValues.arg_output_path = scanner.nextLine();
//        }
//
//        System.out.print("Switches (write the letters)\n: ");
//        String input = scanner.nextLine();
//        if (input.toLowerCase().contains("c")) {
//            argValues.arg_to_console = true;
//        }
//    }
    
    private String chooseFileOpen() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }
    
    private String chooseFileSave() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return "";
    }
    
    private void buttonsActions()
    {
        input_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file_path = chooseFileOpen();
                argValues.arg_input_path = file_path;
                input_file_textfield.setText(file_path);
            }
        });

        output_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file_path = chooseFileOpen();
                argValues.arg_output_path = file_path;
                output_file_textfield.setText(file_path);
            }
        });

        run_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                modelAccess.loadFromCSV();
                viewErrors();

                Results results = modelAccess.calculateAll();
                viewErrors();

//                if(argValues.arg_to_console)
//                    outputToConsole( results );

                if(!"".equals(argValues.arg_output_path))
                    printToTxt( results );  
    
                viewErrors();
                
                console_output.setText( results.toString() );
            }
        });
    }
    
    private void prepareGUI() {
        
        frame = new JFrame("Maths Effects Alcohol");

        // Initialize labels
        input_label = new JLabel("INPUT FILE");
        output_label = new JLabel("OUTPUT TO FILE");

        // buttons
        input_button = new JButton("Select Input File");
        output_button = new JButton("Select Output File");
        run_button = new JButton("RUN");
        
        buttonsActions();

        // textfields
        input_file_textfield = new JTextField(argValues.arg_input_path);
        output_file_textfield = new JTextField(argValues.arg_output_path);

        // panels
        input_panel = new JPanel();
        output_panel = new JPanel();

        // console
        console_output = new JTextArea();
        console_scrollpane = new JScrollPane(console_output);
        
        
        
        

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 500));
        frame.setLayout(new GridLayout(6, 1, 5, 5));
        frame.setResizable(true);

        // Add components to the frame
        frame.add(input_label);
        input_panel.setLayout(new GridLayout(1, 2, 5, 5));
        input_panel.add(input_file_textfield);
        input_panel.add(input_button);
        frame.add(input_panel);

        frame.add(output_label);
        output_panel.setLayout(new GridLayout(1, 2, 5, 5));
        output_panel.add(output_file_textfield);
        output_panel.add(output_button);
        frame.add(output_panel);

        frame.add(run_button);

        console_scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame.add(console_scrollpane);

        frame.setVisible(true);
    }
}

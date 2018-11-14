/**
 * @Author  Will Kang   Group 40
 *
 * This class is used to show mainly error that user might make.  It also 
 * shows Read Me file that contains people who made the game and direction to
 * compile, play and rules of the game.
 */




package GUI;
import Logic.*;
import GUI.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Dictator extends JFrame{
    public static final String NL = "\r\n"; //newline charachter for this OS
    private static File ErrorLog;           //Text file that will collect error log
    private JTextArea ta;                   //Varaible for JTextArea
	 private JLabel title;	                //Variable for JLabel
	
	/**
	 *This construtor creates a window name "About Frame IT".  This constructor
	 *decides what size the window is going to be, size of the text area, and it also
	 *adds scroll function to the window.
	 */
	
    public Dictator(){
		
		setLayout(new BorderLayout());                            //Sets layout of the window
		setSize(1300,550);                                        //Sets size of the window
		setVisible(false);                                        //Makes the window visible
		JPanel titpanel = new JPanel(new FlowLayout());           //Creates JPanel named "titpanel"
		JPanel tapanel = new JPanel(new FlowLayout());            //Creates JPanel named "tapanel"
		ta = new JTextArea(70, 125);                              //Sets the size of JTextArea named ta
		title = new JLabel("About Frame It");                     //Sets title of the window as "About Frame It"
		add(tapanel, BorderLayout.CENTER);                        //Adds tapanel to the center
		add(titpanel, BorderLayout.NORTH);                        //Adds titpanel to the north
		
		JScrollPane scrollPane = new JScrollPane(ta);             //Creates JScrollPane named scrollPane
		titpanel.add(title);                                      //Sets title to the titlepanel
		tapanel.add(scrollPane);                                  //Adds scroll function to tapanel

		ta.setEditable(false);                                    //Makes the ta not editable
		ta.setVisible(true);                                      //Makes the ta visible
    }
    
    /**
	 * sets the abstract pathfile of the score history txt to the provided file path.
	 *
	 * @param	arg_path	the string pathway to be con verted to abstract pathway of the new score history file.
	 * @thows	FileNotFoundException fnfe
	 */
	 
	public static void setFile(String arg_path){
		ErrorLog = new File(arg_path);		//attempt to set abstract file path
	}
    
    /**
	 * This method loops through each element in the Scores array
	 * checks that it isnt null, then writes it to a new line of the file.
	 * does not write a newline character for the final element.
	 * each line is formated according to formatRankLine(Str).
	 *
	 * @throws	FileNotFoundException fnfe
	 * @throws IOException ioe
	 */
	 
	public static void writeErrors(String arg){
		try{
			FileWriter fou = new FileWriter(ErrorLog, true);						//open file output stream in  append mode
			fou.write(arg);										//write the element to a new line of the output file
			fou.close();														// close file
		}
		catch(NullPointerException e0){
			try{
				setFile("ErrorLog.txt");
				FileWriter fou = new FileWriter(ErrorLog, true);						//open file output stream in  append mode
				fou.write(arg);										//write the element to a new line of the output file
				fou.close(); 														// close file
			}
			catch(IOException | NullPointerException e2){									//if new file cannot be created
				System.exit(-1);                                    //Exist system with error message
			}
		}
		catch(FileNotFoundException e1){
			try{
				setFile("ErrorLog.txt");
				FileWriter fou = new FileWriter(ErrorLog, true);						//open file output stream in  append mode
				fou.write(arg);										//write the element to a new line of the output file
				fou.close(); 														// close file
			}
			catch(IOException | NullPointerException e2){									//if new file cannot be created
				System.exit(-1);                                    //Exist system with error message
			}
		}
		catch(IOException e3){
			try{
				setFile("ErrorLog.txt");
				FileWriter fou = new FileWriter(ErrorLog, true);						//open file output stream in  append mode
				fou.write(arg);										//write the element to a new line of the output file
				fou.close(); 														// close file
			}
			catch(IOException | NullPointerException e1){									//if new file cannot be created
				System.exit(-1);                                    //Exist system with error message
			}                                      //Exit system with error message
		}
	}
	
	/*
	 * this method updates the text area  with the provided String
	 * @param	arg		the string to append to the text area
	 */
    
    public void redisplay(String arg){
		ta.append(arg);
		ta.append("\n--------------------------------\n");
    }
    
	/*
	 *
	 */
    public void redisplay(File argfile){
		String current = "";	// the string value of the current line
		
		try{
			FileReader fin = new FileReader(argfile); //the argfile containing the best times
			BufferedReader buff = new BufferedReader(fin); //prep to read the lines as strings
			
			while(current != null){
			//System.out.println(current);
			current = buff.readLine(); //read the line
			if(current == null){
				break;
			}
			ta.append(current + NL);
			}
			buff.close();
			fin.close(); // close argfile
		}
		catch(FileNotFoundException e){ //raise exception if file isn't there when input stream is created
			ta.append(e.getMessage());
		}
		catch(IOException e){ //raise exception if an error occurs with stream
			ta.append(e.getMessage());
		}
		ta.append("\n--------------------------------\n");
    }
	
	public void redisplay(){
		String current = "";	// the string value of the current line
		
		try{
			FileReader fin = new FileReader(ErrorLog); //the argfile containing the best times
			BufferedReader buff = new BufferedReader(fin); //prep to read the lines as strings
			
			while(current != null){
			//System.out.println(current);
			current = buff.readLine(); //read the line
			if(current == null){
				break;
			}
			ta.append(current + NL);
			}
			buff.close();
			fin.close(); // close argfile
		}
		catch(FileNotFoundException e){ //raise exception if file isn't there when input stream is created
			ta.append(e.getMessage());
		}
		catch(IOException e){ //raise exception if an error occurs with stream
			ta.append(e.getMessage());
		}
		ta.append("\n--------------------------------\n");
    }
}

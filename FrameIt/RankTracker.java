/**
 * @Author	Justin Desrochers, Group 40
 * @Version	3.7 04,10,2017
 *
 * Semester project, FrameIt.Logic
 * <p>
 * This class contains methods to interpret and edit the the Best_Times.txt
 * which will contain the fastest completion times  in order
 * this class is static so that only one instance of the file can be opened at once.
 */
 
package Logic;

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;

public class RankTracker{

	//constants
	public static final String NL = "\r\n"; //newline charachter for this OS
	public static final String DOT = ".";	//period string
	public static final String DEFAULT_RANK = "99:99:99, DEFAULT VALUE"; //default value of rank
	public static final int MAX_LENGTH = 30;							//the min and max value of every rank line	
	
	//class variabes
	private static String[] Scores;			//the array of all rank lines
	private static File file;				//the abstract pathway of the score history txt
	private static String lastScore;		//the Score of the most recent winner
	
	
   /**
    * This method reads each line in the best times file
	 * checks taht line isn't null and adds them to the ArrayList temp.
	 * temp is then converted to the array Scores.
	 *
	 * @throws	FileNotFoundException rse0
	 * @throws IOException rse1
    */
     
	public static void readScores() throws FileNotFoundException, IOException{
		ArrayList<String> temp = new ArrayList<String>();	//the temporary ArrayList of lines in order of fastent time to slowest
		String current = " ";								// the string value of the current line, initialised to not be null
		
		try{
			FileReader fin = new FileReader(file); 			//the file containing the best times
			BufferedReader buff = new BufferedReader(fin);  //prep to read the lines as strings

			while(current != null){			//if not at the end of the file
				current = buff.readLine();  //read the line
				if(current == null){		//if line is blank
					break;					//skip that line
				}
				temp.add(current);			//add line to the ArrayList
			}
			fin.close(); 					// close file
		}
		catch(FileNotFoundException rse0){  //raise exception if file isn't there when input stream is created
			throw rse0;
		}
		catch(IOException rse1){ 			//raise exception if an error occurs with stream
			throw rse1;
		}
		finally{ 							//no matter what, initialise scores array to avoid NullPointerExceptions
			Scores = new String[temp.size()];				//create Scores array of appropriate size
			temp.toArray(Scores);							//move values to Scores
		}
	}
	
	
	/**
	 * This method takes the new rank as a string, 
	 * checks if newRank is null
	 * and adds newRank to a new index of Scores
	 * it then sorts the array, according to natural sort order
	 * ordering it from  fastest time to slowest.
	 *
	 * @param	newtime 	The score to be added.
	 */
	 
	public static void addToScores(String newRank){
		if(newRank != null){									//if null, do nothing
			String[] temp = new String[getLengthScores() + 1];	//create a new array with an empty element
			temp[temp.length -1] = newRank;						//add the new time to the array at the last index
			
			for(int i = 0; i < getLengthScores(); i++)			//loop through scores
			{
				temp[i] = getScores(i);							//copy over previous values
			}
			
			Arrays.sort(temp);									//rerank the Scores
			setScores(temp);									//set Scores to the new array
		}
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
	 
	public static void writeScores() throws FileNotFoundException, IOException{
		String current = " ";												//initialise iteration variable
		try{
			FileWriter fou = new FileWriter(file);						//open file output stream
			for(int index = 0; index < Scores.length; index++){	//loop through the array
				if(current != null){											//check if there is a value at that index to avoid NullPointerException
					current = formatRankLine(getScores(index));
					fou.write(current);										//write the element to a new line of the output file
					
					if(index < (Scores.length -1)){						//if not the last line of the file
						fou.write(NL);											//write a new line of the output file
					}
				}
			}
			fou.close(); 														// close file
		}
		catch(FileNotFoundException fnfe){
			throw fnfe;
		}
		catch(IOException ioe){
			throw ioe;
		}
	}
	
	
	/**
	 * This method overloads updateRanks()
	 * This method calls readScores() to read the the file and create the Scores array
	 * then calls addToScores() to update the array with the most recent rank
	 * then call writeScores() to write each rank to the file.
	 *
	 * @param	arg_score	the String version of the rank to be added.
	 * @throws	FileNotFoundException fnfe
	 * @throws	IOException ioe
	 */
	 
	public static void updateRanks(String arg_score) throws FileNotFoundException, IOException{
		try{
			readScores();										//read score history txt
			addToScores(formatRankLine(arg_score));	//add the new score
			writeScores();										//rewrite the score history txt
		}
		catch(FileNotFoundException fnfe){
			throw fnfe;
		}
		catch(IOException ioe){
			throw ioe;
		}
		finally{
			setLastScore(formatRankLine(arg_score));	//set the lastScore variable to the new score
		}
	}
	
	
	/**
	 * This method calls readScores() to read the file and create the Scores array
	 * then call writeScores() to write each rank to the file.
	 *
	 * @throws	FileNotFoundException fnfe
	 * @throws	IOException ioe
	 */
	 
	public static void updateRanks() throws FileNotFoundException, IOException{
		try{
			readScores();
			writeScores();
		}
		catch(FileNotFoundException fnfe){
			throw fnfe;
		}
		catch(IOException ioe){
			throw ioe;
		}
	}
	
	
	/**
	 * This method overloads formatRankLine(Str)
	 * This method takes a players name and thier final time
	 * and converts them into a single string in the propwer format for the rank file.
	 * uses formatRankLine(Str) to ensure rank is exactly MAX_LENGTH
	 * if either arg is null, returns DEFAULT_RANK.
	 *
	 * @param	name	the String of the players name
	 * @param	time	the string version the players final time
	 * @return			a string in form (<time> , <name> <...> <newline>)
	 */
	 
	public static String formatRankLine(String name, String time){
		String temp = DEFAULT_RANK;				//return default rank if arg is null
		if((name != null) && (time != null)){	//if both arguments have values
			temp = (time + ", " + name);			//concatenate
			temp = formatRankLine(temp);			//make rank the correct length
		}
		return temp;
	}
	
	
	/**
	 * This method takes a rank (player name and final time)
	 * and converts it into a single string in the propwer format for the rank file
	 * it also ensures that the length of the string is equal to the MAX_LENGTH
	 * by adding DOTS until it is the proper length.
	 * if arg is null, returns DEFAULT_RANK.
	 *
	 * @param	arg		the String to be converted.
	 * @return	arg		a string of proper length
	 */
	 
	public static String formatRankLine(String arg){
		String temp = DEFAULT_RANK;			//return default rank if arg is null
		if(arg != null){
			temp = arg;
		}
		while(temp.length() < MAX_LENGTH){	//add DOTS until equal to MAX_LENGTH
			temp = temp + DOT;
		}
		return temp;
	}
	
	
	/**
	 * Getter method for the Scores array, returns a copy of the entire array.
	 *
	 * @return	copy	the copy of the Scores array.
	 */
	 
	public static String[] getScores(){
		String[] copy = new String[getLengthScores()];	//create an empty array of the same size
		for(int i = 0; i < getLengthScores(); i++)		//loop through the array
		{
			copy[i] = Scores[i];									//copy the value at each index
		}
		return copy;												//return the copy
	}
	
	
	/**
	 * Overloads getScores()
	 * gets the value of a single element of Scores at the specified index.
	 *
	 * @param	index	the index correspondoing to the value to be returned.
	 * @return	Scores[index]	the value at the specified index.
	 */
	 
	public static String getScores(int index){
		return Scores[index];
	}
	
	
	/**
	 * Setter method for the Scores array
	 * sets the Scores array to a copy of the provided array.
	 *
	 * @param	newScores	the array for Scores to be set to.
	 */
	 
	public static void setScores(String[] newScores){	//create an empty array of the same size
		Scores = new String[newScores.length];
		for(int i = 0; i < newScores.length; i++)			//loop through the array
		{
			Scores[i] = newScores[i];							//copy the value at each index to Scores
		}
	}
	
	
	/**
	 * Overloads setScores(Str[])
	 * sets a single element of Scores at the provided index to the provided value.
	 *
	 * @parm	index	the position of the element ot be set.
	 * @param	val		the value the element is set to.
	 */
	 
	public static void setScores(int index, String val){
		Scores[index] = val;
	}
	
	
	/**
	 * Getter method for the length of the Scores array.
	 *
	 * @return		the length of the Scores array.
	 */
	 
	public static int getLengthScores(){
		return Scores.length;
	}
	
	
	/**
	 * sets the abstract pathfile of the score history txt to the provided file path.
	 *
	 * @param	arg_path	the string pathway to be con verted to abstract pathway of the new score history file.
	 * @thows	FileNotFoundException fnfe
	 */
	 
	public static void setRankFile(String arg_path) throws FileNotFoundException{
		try{
			file = new File(arg_path);		//attempt to set abstract file path
		}
		catch(NullPointerException npe){//throws exception if path doesn't exist
			throw npe;
		}
	}
	
	
	/**
	 * static setter method for the class variable lastScore.
	 * if arg isn't null, sets lastScore to the provided argument.
	 * arg will be formatted to be in proper rank format
	 *
	 * @parm		arg	the rank to format and set
	 */
	 
	public static void setLastScore(String arg){
		if (arg != null){
			lastScore = formatRankLine(arg);
		}
	}
	
	
	/**
	 * Getter method for the class variable lastScore
	 *
	 * @return		the length of the Scores array.
	 */
	 
	public static String getLastScore(){
		return lastScore;
	}
}
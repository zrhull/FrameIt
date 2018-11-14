/**
 * @Author	Zachary Hull 10109756 Group 40
 *
 * This class is used to save and load the current state of the game.
 * To do this saveTheGame will be called once per map to save and loadSavedGame
 * once per map in orger to get saved maps.
 *
 * need to somehow return the map, prevent privacy, probably use mapismap in map parent
 */
 
 
package Logic;
import java.util.Arrays;
import java.io.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;

public class SaveTracker{

	private static Integer[][] toMap = new Integer[5][5];
	private static String thisLine = "aString";	//the current line in the document
	private static String name;		//name being saved
	private static int linesSaved = 0;
	private static int linesLoaded = 0;	//number of lines already been loaded, used to count to the next line to load
	private static int arraySize;
	private static final String NEWLINE = "\r\n"; //new line for different systems
	private static String savedTime;
	private static boolean gameGoodToLoad = true;	//variable used to determine if a full game has been saved then it can be loaded 
	
	
	/**
	 * This method saves the current state of any one board, to do this it take the 2d map given,
	 * the players name, the size of their map (in 3 or 5) and a boolean false for the first map
	 * and true for the last two in order to append to the end of the file or replace everything.
	 * It opens the the file Saves.txt and then puts it into a filewritter with the bool. It then 
	 * writes the name and prints the map line by line.
	 *
	 * @param	map	This 2d map will be the map to be saved.
	 * @param	aName	This is the name the map will be saved under.
	 * @param	size	The size of the array being saved in either 3 or 5.
	 * @param	ifGood	The bool to appaend or replace the array into the save file.
	 * @throws	FileNotFoundException
	 * @throws	IOException
	 */
	
	public static void saveTheGame(Integer[][] map, String aName, int size, boolean ifGood) 
													throws FileNotFoundException, IOException{
		arraySize = size;
		try{
			if (map[0][0] != null){		//if you try to save without generating the maps nothing will happen
				File saves = new File("Saves.txt");		//creates a new file in saves not made yet
				FileWriter openedSaves = new FileWriter(saves, ifGood);
				if(aName == null){	//prevent exception where saveing without a name
					openedSaves.write("NoName" + NEWLINE);
				}else{
					openedSaves.write(aName + NEWLINE);
				}
				for (int y = 0; y < arraySize; y++){
					for (int x = 0; x < arraySize; x++){
						openedSaves.write(Integer.toString(map[y][x]));	//prints each row of the board at a time
					}
					openedSaves.write(NEWLINE);
				}
				openedSaves.close();
			}
		}
		catch(FileNotFoundException rse0){
			throw new FileNotFoundException("ERROR! SaveTracker, saveTheGame(), Could not find save file.");
		}
		catch(IOException rse1){
			throw new IOException("ERROR! SaveTracker, saveTheGame(), An error occurred while saving game.");
		}
	}
	
	
	/**
	 * This method is used to save the time of the current play into the save file.
	 *
	 * @param	time	The string time being saved
	 */
	
	public static void saveTime(String time) throws FileNotFoundException, IOException{
		try{
			File saves = new File("Saves.txt");
			FileWriter openedSaves = new FileWriter(saves, true);
			openedSaves.write(time + NEWLINE);
			openedSaves.close();
		}
		catch(FileNotFoundException rse0){
			throw new FileNotFoundException("ERROR! SaveTracker, saveTime(), Could not find save file.");
		}
		catch(IOException rse1){
			throw new IOException("ERROR! SaveTracker, saveTime(), An error occurred while saving game.");
		}
	}
	
	
	/**
	 * This method is used load the time saved from the save file and return its String.
	 * 
	 * @return	thisLine	The time string being returned.
	 */
	
	public static String loadTime() throws FileNotFoundException, IOException{
		try{
			File saves = new File("Saves.txt");
			FileReader openedSaves = new FileReader(saves);
			BufferedReader buffed = new BufferedReader(openedSaves);
			for (int i=0; i <= linesLoaded; i++){
				thisLine = buffed.readLine();		//reads lines until the last line then prints time on the next line
			}
			openedSaves.close();
		}
		catch(FileNotFoundException rse0){
			throw new FileNotFoundException("ERROR! SaveTracker, loadTime(), Could not load save file.");
		}
		catch(IOException rse1){
			throw new IOException("ERROR! SaveTracker, loadTime(), An error occurred while loading game.");
		}
		linesLoaded = 0;	//full game has been loaded, retrun lines loaded to 0 so we can save another file and load again
		return thisLine.substring(thisLine.length() -8, thisLine.length());	//returns the time string
	}
	
	
	/**
	 * This method is used to load a saved game file. To do this it needs only 1 parameter
	 * for the size of the array being loaded. It will open the file, buffer it for reading
	 * and then read the number of lines that have already been loaded. This is where the 
	 * lines loaded variable is used. Once the given number of lines have been loaded it will
	 * then read the name and save that to the name variable. From here on it will begin
	 * counting loaded lines and start rebuilding the saved map. Finally it will return
	 * the name of the player on that map.
	 *
	 * @param	size	The integer size of the array in either 3 or 5.
	 * @return	name	The name of the player of the grid being returned.
	 * @throws	FileNotFoundException
	 * @throws	IOException
	 */
	
	public static String loadSavedGame(int size) throws FileNotFoundException, IOException{
		arraySize = size;
		try{
			File saves = new File("Saves.txt");
			FileReader openedSaves = new FileReader(saves);
			BufferedReader buffed = new BufferedReader(openedSaves);
			linesLoaded += 1;										 //need to say we load this next line before we actually 
			for (int y = 0; y < linesLoaded; y++){					 //do it, so it counts the name before the array
				name = buffed.readLine();		//read lines until the last one already loaded, next line is the name of the next map
			}
			for (int y = 0; y < arraySize; y++){
				thisLine = buffed.readLine();	//loading each line of the current map
				linesLoaded +=1;				//increment per line loaded
				for (int x = 0; x < arraySize; x++){
					toMap[y][x] = Integer.parseInt(thisLine.substring(x, x+1)); //saved as string, must parse to put into array
				}
			}
			buffed.close();
		}
		catch(FileNotFoundException rse0){
			throw new FileNotFoundException("ERROR! SaveTracker, loadSavedGame(), Could not load save file.");
		}
		catch(IOException rse1){
			throw new IOException("ERROR! SaveTracker, loadSavedGame(), An error occurred while loading game.");
		}
		return name;
	}
	
	
	/**
	 * This method is used to get the map that was just called to be loaded right
	 * before this method was called and reurn the map this class is containing.
	 * This method copies the map it contains and sends that as a return value.
	 *
	 * @return	map	This is an array to be returned to the caller.
	 */
	
	public static Integer [][] getMap(){
		Integer[][] map = new Integer[5][5];
		for (int i = 0; i< arraySize; i++){
			for (int x = 0; x< arraySize; x++){
				map[i][x] = toMap[i][x];		//copy the map and return the current map being held
			}
		}
		return map;
	}
	
	
	/**
	 * This method returns a boolean value to tell the driver if a game has been 
	 * loaded or not.
	 */
	
	public static boolean loadOkay(){
		if (linesLoaded < 16){	//saved file always has 16 lines, if all lines have been loaded then 
			return true;		//you can no longer attemp to load a file until another has been saved
		}else{
			return false;
		}
	}
}
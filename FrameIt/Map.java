/**
 * @Author Zachary Hull 10109756 Group  40
 * @version 3.5
 * 
 * Frame it Map class
 * Semester project 
 * Text based version
 * <p>
 * This class is the parent class for two other classes designed to 
 * generate their grids depending on the given map size.
 */

package Logic;
import Logic.*;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


 /**
 * Frame it Map class. Text based version.
 * <p>
 * This class is used to create two arrays and fill them with colors. 
 * Methods include generate.
 */


public abstract class Map{
	
	private boolean colorPicked = false;
	private int blank_x;
	private int blank_y;
	private int ranNum;
	private int mapSize;
	private int randomInt;		//the range of numbers to be randomly chosen, if 7 its with blank and a 5x5, if 6 its without blank and a 3x3
	private final int BLANK = 6;
	private Integer[][] aMap = new Integer[5][5];
	private Integer[] counts = new Integer[7];		//counts of number of each color, six colors and 1 blank
	private static final int BIGARRAYSIZE = 5;
	private static final int SMALLARRAYSIZE = 3;
   
   
   /**
    * Constructor
    *
    * @param    aMapSize    This int is used to decide how big the map is.
    */

	public Map(Integer aMapSize){
		this.mapSize = aMapSize;
		if (mapSize == BIGARRAYSIZE){	
			this.randomInt = 7;
		}else{
			this.randomInt = 6; 
		}
	}
	
	
	/**
	 *	Copy constructor used mainly for the second player to get the same map.
	 *	
	 * @param	mapToCopy	This is a Map object with the map needing to be copied.
	 */
	 
	public Map(Map mapToCopy){
		this(mapToCopy.getMapSize());
		mapIsMap(mapToCopy.getFirstMap());
	}
	
	
	/**
	 * This method takes an array as a parameter called map and is used to make this instances
	 * values of its array the same as the received map. To do this it goes through 2 for loops
	 * (one nested) in order to derive the location of the map and make each individual value
	 * equal. If the value is blank then it must set the values of blank_x and blank_y the same
	 * as its location (which is needed for the update method).
	 *
	 * @param	map	an array of 5x5 values
	 */
	
	public void mapIsMap(Integer[][] map){
		if (map[0][0] != null){
			for (int y = 0; y < getMapSize(); y++){
				for (int x = 0; x < getMapSize(); x++){
					if (map[y][x] == BLANK){
						blank_x = x;    //assigning blank's raw location
						blank_y = y;
					}
					aMap[y][x] = map[y][x];  //assigning the maps values
				}
			}
		}
	}
	
	
	/**
	 * Method used to return the map array of the class.
	 *
	 * @return	aMap	the 5x5 array this instance is using
	 */ 
	
	public Integer [][] getFirstMap(){
		Integer[][] map = new Integer[getMapSize()][getMapSize()];
		for (int y = 0; y < getMapSize(); y++){
			for (int x = 0; x < getMapSize(); x++){
				map[y][x] = aMap[y][x];  //assigning the maps values
			}
		}
		return map;
	}
	
	
	/**
	 * Method used to return the x coordinate of the blank space.
	 *
	 * @return	blank_x	The x coordinate of the blank space
	 */ 
	
	public int getABlank_x(){
		return blank_x;
	}
	
	
	/**
	 * Method used to return the y coordinate of the blank space.
	 *
	 * @return	blank_y	The y coordinate of the blank space
	 */
	
	public int getABlank_y(){
		return blank_y;
	}
	
	
	/**
	 * Method used to return the size of the map being used.
	 *
	 * @return	mapSize	The length and hight of the map being used
	 */
	
	public int getMapSize(){
		return mapSize;
	}
	
	
	/**
	 * This method is used to make the classes map filled with zeros to prevent a nullPointerException
	 * from being called. It also makes the counts list all zeros.
	 */
		
	public void makelistzero(){
		for (int y = 0; y < mapSize; y++){
			for (int x = 0; x < mapSize; x++){
				aMap[y][x] = 0;
			}
		}
		for (int y = 0; y < 7; y++){
			counts[y] = 0;
		}
	}


	/**
	 * This method uses two for loops (one nested) to one at a time be used as the x and y coordinates
	 * of the map array. It then uses a while loop to get a random color each iteration until a valid color
	 * has been chosen. To choose the color the while loop picks a random number from 0 to 6, if the number
	 * is 6 that location is now the blank space and its total number of blanks goes up to one. Now if the
	 * random number is 6 it will not create another blank space because there is already one created. If
	 * the random number is any other and their total amount of blocks is less than 4 it will assign that
	 * location with that color. There are a total of 25 blocks with 24 colors and 1 empty. There are 6 
	 * colors and a max total of 4 blocks per color.
	 */
	 
	public void generate(){
		makelistzero();		//empties the array
		Random rn = new Random();
		for (int y = 0; y < mapSize; y++){
			for (int x = 0; x < mapSize; x++){
				colorPicked = false;
				while (!colorPicked){			//must keep getting new numbers until an acceptable number is chosen
					ranNum = rn.nextInt(randomInt);   //picks a random number in range 6 or 7 (6 without blank, 7 with)
					if (ranNum == 6){   //if num picked is 6 then that spot is blank
						if (counts[ranNum] == 0){
							aMap[y][x] = BLANK;
							blank_x = x;
							blank_y = y;
							counts[ranNum] += 1;
							colorPicked = true;
						}
					}else if (counts[ranNum] < 4){  //adds a color to the map if it has less than 4 at the moment
						aMap[y][x] = ranNum;
						counts[ranNum] += 1;		//counts increase if color is chosen
						colorPicked = true;
					}
				}
			}
		}
	}
}
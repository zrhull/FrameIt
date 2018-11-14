/**
 * @Author Zachary Hull 10109756 Group  40
 * @version 3.3
 * 
 * Frame it PlayMap class
 * Semester project 
 * Text based version
 * <p>
 * This class is used to create a 5x5 array, fill it with colors and display the 5x5 array. 
 * It also will take an array and make a copy of it to be used as another object of this class.
 * Methods  generate, updateMap, display, mapIsMap.
 */


package Logic;
import Logic.*;
import java.util.Arrays;
import java.util.List;


 /**
 * Frame it PlayMap class. Text based version.
 * <p>
 * This class is used to create a 5x5 array, fill it with colors and display the 5x5 array. 
 * It also will take an array and make a copy of it to be used as another object of this class.
 * Methods include generate, updateMap, display, mapIsMap.
 */

public class PlayMap extends Map{
   
	private int blank_x;
	private int blank_y;
	private final int BLANK = 6;
	private Integer[][] fullMap = new Integer[5][5];
	
	
	/**
     * Constructor used because 2 instances of this class are required.
     */

	public PlayMap(int aMapSize){
		 super(aMapSize);
	}
	
	public PlayMap(PlayMap mapToCopy){
		super(mapToCopy);
	}
	
	
	/**
	 * This method is used to get the map made in the super class and the x and y coords for the 
	 * blank space. This methods takes no parameters and returns nothing.
	 */
	 
	public void fillAll(){
		blank_x = getABlank_x();
		blank_y = getABlank_y();
		fullMap = getFirstMap();
	}
	
	
	/**
	 * Method used to return the map array of the class.
	 *
	 * @return	fullMap	the 5x5 array this instance is using.
	 */ 
	
	public Integer [][] getMap(){
		mapIsMap(fullMap);
		Integer[][] map = getFirstMap();	
		return map;
	}
	
	
	/**
	 * Method used to set this instances map to the map given using mapIsMap in the parent Map class
	 */
	
	public void setMap(Integer[][] map){
		super.mapIsMap(map);
	}
	
	
	/**
	 * This method receives one parameter that it uses to updateMap the movement of the blank space
	 * on the array. To do this it checks what direction the arg contains then it moves the color
	 * in the direction it is moving to the blank space. It then updateMaps the raw location of the
	 * blank and changes the old colors location to blank. Essentially swapping their two
	 * locations. It the arg sent is somehow not a direction it prints out invalid input.
	 *
	 * @param	arg    The string direction chosen by the user.
	 * @throws	ArrayIndexOutOfBoundsException
	 */
	
	public void updateMap(String arg){
		try{
			if (arg.equals("Up")){
				fullMap[blank_y][blank_x] = fullMap[blank_y-1][blank_x];
				blank_y -= 1;
				fullMap[blank_y][blank_x] = BLANK;
			}else if (arg.equals("Down")){
				fullMap[blank_y][blank_x] = fullMap[blank_y+1][blank_x];
				blank_y += 1;
				fullMap[blank_y][blank_x] = BLANK;
			}else if (arg.equals("Left")){
				fullMap[blank_y][blank_x] = fullMap[blank_y][blank_x-1];
				blank_x -= 1;
				fullMap[blank_y][blank_x] = BLANK;
			}else if (arg.equals("Right")){
				fullMap[blank_y][blank_x] = fullMap[blank_y][blank_x+1];
				blank_x += 1;
				fullMap[blank_y][blank_x] = BLANK;
			}else{
				System.out.println("Not a valid input!");
			}
		}catch(ArrayIndexOutOfBoundsException aiobe){
			throw (aiobe);
		}
	}
}

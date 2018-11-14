/** 
 * @Author	Tevin Kandamby, Modified by Zachary Hull,  Group 40
 *
 * Solution class for Frame It
 * this class is is the child of map, being the 3x3 solution map.
 * This class is used to hold and pass the map, as well as check to see if the game has been won.
 */
 

package Logic;
import Logic.*;
import java.util.Arrays;
import java.util.List;


	/**
	 * This class is used to create a 3x3 map and to determine if either of the players
	 * has won the game.
	 */

public class Solution extends Map{
	
	private boolean gameWon = false;
	private Integer[][] smallMap = new Integer[3][3];
	
	
	/**
	 * Constructor for this class.
	 *
	 * @param	aMapSize	Just the map size for this class
	 */
	 
	public Solution(int aMapSize){
		 super(aMapSize);
	}
	
	
	/**
	 * Method used to get the map from the parent and send it back to this class.
	 */
	 
	public void fillAll(){
		smallMap = getFirstMap();
	}

	
	/**
	 * Method used to return the map array of the class.
	 *
	 * @return	smallMap	the 3x3 array this instance is using
	 */ 
	
	public Integer [][] getMap(){
		mapIsMap(smallMap);
		Integer[][] map = getFirstMap();
		return map;
	}
	
	
	/**
	 * This class is used to set the solutions map to a map given, used when loading a game.
	 */
	
	public void setMap(Integer[][] map){
		super.mapIsMap(map);
	}
	
	
		/**
		 * The win condition for the game. Checks to see if the middle 3x3 of the players map
		 * is the exact same as the 3x3 solution map. It does this by checking to make sure each
		 * individual tile is equal from solution to players map.
		 * 
		 * @param 	aMap	A 5x5 array map, coming from the player or the computer player.
		 * @return	gameWon	The boole variable that shows if the game has been won.
		 */
			
	public boolean winCon(Integer[][] aMap){
      
		if((smallMap[0][0] == aMap[1][1]) &&
			(smallMap[0][1] == aMap[1][2]) &&
			(smallMap[0][2] == aMap[1][3]) &&
			(smallMap[1][0] == aMap[2][1]) &&
			(smallMap[1][1] == aMap[2][2]) &&
			(smallMap[1][2] == aMap[2][3]) &&
			(smallMap[2][0] == aMap[3][1]) &&
			(smallMap[2][1] == aMap[3][2]) &&
			(smallMap[2][2] == aMap[3][3])) {
			gameWon = true;
			return gameWon;
		}else{
			return gameWon;
		}
	}
}
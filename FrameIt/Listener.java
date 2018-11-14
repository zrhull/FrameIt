/**
 * @Author	JC Sicat ID 30024897 Group 40
 *  
 * This class is used for listning to player input
 * it implements KeyListener to read key strokes form the user
 * and to do that, this class will add a textField to a panel to implement the listener.
 */
 
 
package GUI;
import Logic.*;
import GUI.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;


public class Listener extends GameGUI implements KeyListener{

    //class variables
    private PlayMap p1Map;     //The map of Player 1
    private PlayMap p2Map;     //The map of Player 2
    private Solution solution;     //The map of Solution board
    private Clock clock;     //The timer
    
    //constants
    private int count = 0;     //The variable count initialized to 0
    public static final int BIGARRAYSIZE = 5;     //The size of players' array set to 5
    public static final int SMALLARRAYSIZE = 3;     //The size of solution's array set to 3
	
    /**
     * This constructor takes a panel as an argument to be used in the Frame that is used.
     */
    
    public Listener(){
		super();     //It creates a constructor of GameGUI
		TextField textField = getTField();     //Open text field
		textField.addKeyListener(this);     //Read key typed in the text field
		p1Map = new PlayMap(BIGARRAYSIZE);     //Open Player 1's array and set it to variable p1Map
		p2Map = new PlayMap(BIGARRAYSIZE);     //Open Player 2's array and set it to variable p2Map
		solution = new Solution(SMALLARRAYSIZE);     //Open Solution's array and set it to variable solution
    }
    
    
    /**
     * This method is used to setup the game, to set it up it creates the maps using the generate
     * and fillmap methods from the instances created before.
     */
    
    public void setupGame(){
		p1Map.generate();			 			                                        //Makes maps
		p1Map.fillAll();                                                                //Fills Player 1's map
		Integer[][] fullMap = p1Map.getFirstMap();                                      //Getting the player map
		p2Map.mapIsMap(fullMap);                                                        //Makes Player 2's map equal to Player 1's map
		p2Map.fillAll();                                                                //Fills Player 2's map
		solution.generate();                                                            //Makes map for Soultion
		solution.fillAll();                                                             //Fills soultion map
		Integer[][] smallMap = solution.getFirstMap();	                                //Getting the solution map
		updateBoards(p1Map,p2Map,solution);			                                    //Set up boards in gui
		updatePlayerLabels(GameGUI.getPlayer1(), GameGUI.getPlayer2());                 //Update the name for each player
        Clock.setLastTime("00:00:00");                                                  //The initial time is set to 00:00:00
    }
	
    /**
     *This method is used to setup the saved game when it is loaded.  It creates the map based on
     *the where players saved the game.
     */
    
	public void setupGameLoad(){
		p1Map.fillAll();                                                                //Fills Player 1's map
		p2Map.fillAll();                                                                //Fills Player 2's map
		solution.fillAll();                                                             //Fills Solution's map
		Integer[][] smallMap = solution.getFirstMap();			                        //Getting the solution map
		updateBoards(p1Map,p2Map,solution);			                                    //Set up boards in gui
		updatePlayerLabels(GameGUI.getPlayer1(), GameGUI.getPlayer2());                 //Set up each players' name in gui
    }
    
    
    /**
     * This method is used to ask for an array of an instance of either of the map classes.
     * To do this it uses the array size given to determine which array it is.
     *
     * @param	arraySize	The size of the array needed.
     */
    
    public Integer[][] getAMap(int arraySize){
    	Integer[][] tempMap = new Integer[5][5];                        //Getting the players' map
    	if (arraySize == BIGARRAYSIZE && count == 0){                   //Check if its Players' array
    		count +=1;					                                //Count used to determine if p1 map or p2 map(0 = p1, 1 = p2)
    		tempMap = p1Map.getMap();                                   //Set tempMap as Player 1's map
    	}else if (arraySize == BIGARRAYSIZE && count == 1){             //Check if its Players' array
    		tempMap = p2Map.getMap();                                   //Set tempMap as Player 2's map
    		count = 0;                                                  //Count is set to 0 once the tempMap is set to Player 2's map
    	}else{                                                          //Comes here if the array size is not BIGARRAYSIZE
    		tempMap = solution.getMap();                                //Set tempMap as Solution's map
    	}
    	return tempMap;                                                 //Return the value tempMap once it it set to either Player 1, Player 2, or Solution
    }
    
    
    /**
     * This method is used to set a map given to be the same as the map of a instance of either
     * of the map classes. To do this it checks the array size and the count to see if it is
     * the first map or the second map given and then consequently calls the setMap method in
     * the map class.
     *
     * @param	map			The array to be used to set the instances array.
     * @param	arraySize	The size of the array given.
     */
    
    public void setAMap(Integer[][] map, int arraySize){
    	if (arraySize == BIGARRAYSIZE && count == 0){                   //Check if its Players' array
    		count +=1;                                                  //Count used to determine if p1 map or p2 map(0 = p1, 1 = p2)
    		p1Map.setMap(map);                                          //Sets Player 1's map
    	}else if (arraySize == BIGARRAYSIZE && count == 1){             //Check if its Players' array
    		p2Map.setMap(map);                                          //Sets Player 2's map
    		count = 0;                                                  //Count is set to 0
    	}else{                                                          //Comes here if the arraySize is not BIGARRAYSIZE
    		solution.setMap(map);                                       //Sets Soultion map
    	}
    } 
    
    public void keyTyped(KeyEvent e){}                      //This method is not used but it is needed to make the method keyPressed work
    public void keyReleased(KeyEvent e){}                   //This method is not used but it is needed to make the method keyPressed work
    
    
    /**
     * This method is the main game method, for human players.
     * when called it starts the main game loop and displays
     * the players map, gets the current x and y coords for the blank space, sends those coords
     * to the direction method to get an input from the player, then lastly updates the array.
     * This loop will continue until the win condition in target returns true.
     */
    
    @Override
    public void keyPressed(KeyEvent e){
		try{                                                   
			int keyCode = e.getKeyCode();                                                 //Set method getKeyCode to integer variable keyCode
			if (keyCode == KeyEvent.VK_W){                                                //Check if keyCode is equal to key command "W"
				makeMove(GameGUI.getPlayer1(), "Up");                                     //If keyCode is equal to key command "W", Player 1's blank moves "UP"
				
			}else if (keyCode == KeyEvent.VK_S){                                          //Check if keyCode is equal to key command "S"
				makeMove(GameGUI.getPlayer1(), "Down");                                   //If keyCode is equal to key command "S", Player 2's blank moves "DOWN"
				
			}else if (keyCode == KeyEvent.VK_A){                                          //Check if keyCode is equal to key command "A"
				makeMove(GameGUI.getPlayer1(), "Left");                                   //If keyCode is equal to key command "A", Player 1's blank moves "LEFT"
                
			}else if (keyCode == KeyEvent.VK_D){                                          //Check if keyCode is equal to key command "D"
				makeMove(GameGUI.getPlayer1(), "Right");                                  //If keyCode is equal to key command "D", Player 1's blank moves "RIGHT"
			}
		}catch (ArrayIndexOutOfBoundsException aiobe) {                                   //Check if Player 1 tries to move blank out of the board and catch ArrayIndexOutOfException
			setCommentator1(GameGUI.getPlayer1() + ": Out of Bounds");                    //If Player 1 tries to move blank out of the board, it prints "Player 1: Out of Bounds" in gui 
			
		}try{
			int keyCode = e.getKeyCode();                                                 //Set method getKeyCode to integer variable keyCode
			if (keyCode == KeyEvent.VK_UP){                                               //Check if keyCode is equal to key command Arrow Key "UP"
				makeMove(GameGUI.getPlayer2(), "Up");                                     //If keyCode is equal to key command Arrow Key "UP", Player 2's blank moves "UP"

			}else if (keyCode == KeyEvent.VK_DOWN){                                       //Check if keyCode is equal to key command Arrow Key "DOWN"
				makeMove(GameGUI.getPlayer2(), "Down");                                   //If keyCode is equal to key command Arrow Key "DOWN", Player 2's blank moves "DOWN"
				
			}else if (keyCode == KeyEvent.VK_LEFT){                                       //Check if keyCode is equal to key command Arrow Key "LEFT"
				makeMove(GameGUI.getPlayer2(), "Left");                                   //If keyCode is equal to key command Arrow Key "LEFT", Player 2's blank moves "LEFT"

			}else if (keyCode == KeyEvent.VK_RIGHT){                                      //Check if keyCode is equal to key command Arrow Key "RIGHT"
				makeMove(GameGUI.getPlayer2(), "Right");                                  //If keyCode is equal to key command Arrow Key "RIGHT", Player 2's blank moves "RIGHT"
			}
		}
        catch (ArrayIndexOutOfBoundsException aiobe) {                                    //Check if Player 2 tries to move blank out of the board and catch ArrayIndexOutOfException
			setCommentator2(GameGUI.getPlayer2() + ": Out of Bounds");	                  //If Player 2 tries to move blank out of the board, it prints "Player 20: Out of Bounds" in gui 
		}
    }
    
    /**
     *This method is used to update each time players makes move and show direction each players moved in gui.
     *
     *@param   pname   The string value used to get name of each player 
     *@param   move    The string value used to get which direction each player moved
     */

	public void makeMove(String pname, String move){
		if (getInputAllowed()){                                                    //Checks if input is allowed by Player 1
			if (pname.equals(GameGUI.getPlayer1())){                               //Checks if pname is equal to Player 1's name 
				p1Map.updateMap(move);                                             //Update the map according to the direction Player 1 moved
				setCommentator1(pname + ": " + move);                              //Shows in gui which player moved which direction
			}
			else if (pname.equals(GameGUI.getPlayer2())){                          //Checks if pname is equal to Player 2's name 
				p2Map.updateMap(move);                                             //Update the map according to the direction Player 2 moved
				setCommentator2(pname + ": " + move);                              //Shows in gui which player moved which direction
			}
			
			updateBoards(p1Map,p2Map);                                             //Updates maps for both Player 1 and Player 2
			
			if((solution.winCon(p1Map.getMap())) ||                                                            //Checks if either Player 1 or Player 2 finished the game by comparing their map to the win condition
				(solution.winCon(p2Map.getMap()))  ){
				Clock.trigger = false;                                                                         //If either Player 1 or Player 2 finished the game, the timer is stopped by setting trigger value to false
				inputOff();                                                                                    //if either Player 1 or Player 2 finished the game, the input is turned off and they are not allowed to make anymore move
				setCommentator1(pname + " Wins!");                                                             //Shows who is winner on gui
				try{
					RankTracker.updateRanks(RankTracker.formatRankLine(pname, Clock.getLastTime()));           //Records winner's name and time to the RankTracker
				}
				catch(FileNotFoundException fnfe0){                                                            //raise exception if file isn't there when input stream is created
					//System.out.println("ERROR! IOException");
				}
				catch(IOException ioe1){                                                                       //raise exception if an error occurs with stream
					//System.out.println("ERROR! IOException");
				}
			}
		}
	}
}

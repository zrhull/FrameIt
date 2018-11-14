/**
 * @Author Justin Desrochers, Group 40
 *
 * This class takes the data stored in Map objects 
 * and translates them to coloured tiles on GUI board.
 * translates both solutipon and playmap objects of Map.
 */


package GUI;
import Logic.*;
import GUI.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class BoardButtons extends JFrame{

	private JButton[] tiles;						//array of button tiles
	private Integer[] tileMap = new Integer[25];	//array of colours for buttons
	
	
	/**
	 * Constructor initialises tiles to a JButton array of length 25
	 * creates 25 blank JButtons with no action, then adds them to the provide panel
	 * sets provided panel to grid layout of appropriate size.
	 *
	 * @param	p	the panel to add the buttons to, should be in grid layout
	 */
	 
	public BoardButtons(Panel p){
		tiles = new JButton[25];				//set size to 25 (5x5)
		p.setLayout(new GridLayout(5, 5));		//set layout to 5x5 grid
		for (int i = 0; i <25; i++){			//loop through tiles[]
			tiles[i] = new JButton();			//intialise each button to blank with no action
			p.add(tiles[i]);					//add each button to panel
		}
	}
	
	
	/**
	 * Constructor, initialises tiles to a JButton array of length size^2
	 * creates the appropriate numbe of blank JButtons(either 25 or 9) with no action, 
	 * then adds them to the provide panel
	 * sets provided panel to grid layout of appropriate size
	 *
	 * @param	p	the panel to add the buttons to, should be in grid layout
	 * @param	size	the size of the grid to be creted either 5x5 or 3x3
	 */
	 
	public BoardButtons(Panel p, int bSize){
		p.setLayout(new GridLayout(bSize, bSize));	//set layout to grid of apropriate size
		
		if(bSize == 5){
			tiles = new JButton[25];				//set size to 25 (5x5)
			for (int i = 0; i <25; i++){			//loop through tiles[]
				tiles[i] = new JButton();			//intialise each button to blank with no action
				p.add(tiles[i]);					//add each button to panel
			}
		}
		else if(bSize == 3){
			tiles = new JButton[9];
			for (int i = 0; i <9; i++){				//loop through tiles[]
				tiles[i] = new JButton();			//intialise each button to blank with no action
				p.add(tiles[i]);					//add each button to panel
			}
		}
	}
	
	
	/**
	 * This method takes the values from the tileMap instance and sets the corresponding button to that colour.
	 */
	 
	public void colorButtons(){
		for(int i = 0; i < tiles.length; i++){		//loop through tiles[]
			if (tileMap[i] == 0){						//value is enumerable 0
				tiles[i].setBackground(Color.RED);		//set button background to corresponding colour
			}else if (tileMap[i] == 1){
				tiles[i].setBackground(Color.ORANGE);
			}else if (tileMap[i] == 2){
				tiles[i].setBackground(Color.YELLOW);
			}else if (tileMap[i] == 3){
				tiles[i].setBackground(Color.GREEN);
			}else if (tileMap[i] == 4){
				tiles[i].setBackground(Color.BLUE);
			}else if (tileMap[i] == 5){
				tiles[i].setBackground(Color.MAGENTA);
			}else if (tileMap[i] == 6){
				tiles[i].setBackground(Color.BLACK);
			}else{										//if isnt an enum value
				tiles[i].setBackground(Color.PINK);		//set button background to error colour
				tiles[i].setText("NO VAL!");				//SET BUTTON TEXT TO ERROR MESSAGE "NO VALUE"
			}
		}
	}


	/**
	 * Setter method for the tile map.
	 * Tile map is a single dimension integer array version of the array provided by Map.
	 *
	 * @param	loc_map		the map to set tileMap to
	 */
	 
	public void setTileMap(Integer[] loc_map){
		for (int i = 0; i < loc_map.length; i++){	//loop through array
			tileMap[i] = loc_map[i];				//copy each value
		}
	}
	
	
	/**
	 * For playmap object
	 * This method takes a 2d String array and flattens it into a singleDimension.
	 * the array is looped through top to bottom, left to right,
	 * ensuring a consistent index pairing between the arrays. 
	 *
	 * @param	loc_map		A PlayMap with a grid to flatten.
	 * @return	map1d		A one dimensional version of the array in provided Map.
	 */
	 
	public Integer[] singleDimension(PlayMap loc_map){
		int i = 0;												//Initialise tiles[] index
		Integer[][] map2d = loc_map.getMap();					//get 2dimensional array from PlayMap object
		Integer[] map1d = new Integer[25];						//initialise one dimensional array to  mapsize^2
		for (int y = 0; y < loc_map.getMapSize(); y++){			//loop through both dimensions of array
			for (int x = 0; x < loc_map.getMapSize(); x++){
				map1d[i] = map2d[y][x];  						//assigning the maps values to 1d array
				i += 1; 						 				//increment the index
			}
		}
		return map1d;
	}
	
	
	/**
	 * overloaded for solution object.
	 * This method takes a 2d int array and flattens it into a singleDimension.
	 * the array is looped through top to bottom, left to right,
	 * ensuring a consistent index pairing between the arrays. 
	 *
	 * @param	map		a Solution(3x3) with a grid to flatten.
	 * @return	map1d	a one dimensional version of the array in provided Map.
	 */
	 
	public Integer[] singleDimension(Solution loc_map){
		int i = 0;												//Initialise tiles[] index
		Integer[][] map2d = loc_map.getMap();					//get 2dimensional array from PlayMap object
		Integer[] map1d = new Integer[9];						//initialise one dimensional array to mapsize^2
		for (int y = 0; y < loc_map.getMapSize(); y++){			//loop through both dimensions of array
			for (int x = 0; x < loc_map.getMapSize(); x++){
				map1d[i] = map2d[y][x];  						//assigning the maps values to 1d array
				i += 1; 						 				//increment the index
			}
		}
		return map1d;
	}
	
	
	/**
	 * This method updates the tile map and tiles based on provided playmap.
	 *
	 * @param	map		The playmap object to based changes on.
	 */
	 
	public void update(PlayMap map){		
		setTileMap(singleDimension(map));	//create the new 1d map, set tilmap[] to that map
		colorButtons();						//recolour the buttons
	}
	
	
	/**
	 * Overloaded for solution objects.
	 * This method updates the tile map and tiles based on provided solution.
	 *
	 * @param	map		The solution object to based changes on.
	 */
	 
	public void update(Solution map){
		setTileMap(singleDimension(map));	//create the new 1d map, set tilmap[] to that map
		colorButtons();						//recolour the buttons
	}	
}
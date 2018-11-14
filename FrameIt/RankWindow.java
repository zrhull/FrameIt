/**
 * @Author	Justin Desrochers Group 40
 *
 *This class contains a constructor that displays the ranking window.
 *It also contains methods that set up and updates the ranking window.
 */

package GUI;

import GUI.*;
import Logic.RankTracker;

import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
*This class opens window to display Scores to players.
*/

public class RankWindow extends JFrame{
	public static final String NL = "\r\n"; //newline charachter for this OS
	private JButton[] rows;						//the array of buttons which contain each line
	private Font rankFont;						//the text font used in this window
	private static final int SIZE = 10;		//the number of scores displayed (in this case top ten))
	public static final String DEFAULT_RANK = "99:99:99, DEFAULT VALUE";
	
	/**
	*This constructor creates the ranking window and displays it.
	*/
	public RankWindow(){
		
		setLayout(new GridLayout(SIZE +1, 1));
		setSize(1500,1000);
		setVisible(true);
		
		rankFont = new Font("Courier", Font.BOLD, 24);
		
		rows = new JButton[SIZE +1];
		
		for(int index = 0; index < SIZE +1; index++){
			rows[index] = new JButton();
			add(rows[index]);
			rows[index].setOpaque(true);
			rows[index].setForeground(Color.RED);
			rows[index].setFont(rankFont);
		}
	}
	
	/**
	 *This method updates the rank of the players everytime new players finishes the game.
	 *It also throws FileNotFoundException and IOException.
	 */
	
	public void updateRanks() throws FileNotFoundException, IOException{
		String temp = "";
		try{
			RankTracker.updateRanks();
			for(int index = 0; index < SIZE; index++){
				temp = Integer.toString(index +1) + "...." + RankTracker.getScores(index);
				rows[index].setText(temp);
			}
		}
		catch(FileNotFoundException e){
			throw new FileNotFoundException("ERROR! RankWindow, updateRanks(), File could not be found");
		}
		catch(IOException e){
			throw new IOException("ERROR! RankWindow, updateRanks(), Error occurred reading file");
		}
		finally{
			String newRank = RankTracker.getLastScore();
			if(newRank != null){
				rows[rows.length -1].setText("Your Score:  " + newRank);
			}
		}
	}
	
	/**
	 *This method takes string value arg_path as an argument to set up the ranking window.
	 *It also throws FileNotFoundException and IOEception.
	 *
	 *@param   arg_path   The string pathway to be con verted to abstract pathway of the new score history file.
	 */
	
	public static void setUp(String arg_path) throws FileNotFoundException, IOException{
		try{
			RankTracker.setRankFile(arg_path);
			RankTracker.updateRanks();
		}
		catch(FileNotFoundException e){
			throw new FileNotFoundException("ERROR! RankWindow, setUp(), File could not be found");
		}
		catch(IOException e){
			throw new IOException("ERROR! RankWindow, setUp(), Error occurred reading file");
		}
	}
}
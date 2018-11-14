/**
 * @Author Group 40
 * 
 * This class is the menu pop-up window that runs the game
 * class contains main method used to start the game program
 * this class also catches all errors and displays them through the dictator class
 */
 
package Start;
import GUI.*;
import Logic.*;

import java.io.*;
import java.util.Arrays;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Driver extends JFrame implements ActionListener{
	//constants
    public static final int BIGARRAYSIZE = 5;
    public static final int SMALLARRAYSIZE = 3;
	
    //buttons
    private Button playButton;
    private Button loadButton;
    private Button saveButton;
    private Button highScores;
	private Button abtGame;
	//textfield
    private TextField player1Name;
    private TextField player2Name;
	//windows
    private static Listener listener = new Listener();
	private Dictator dictator;
	//misc
    private Integer[][] map = new Integer[5][5];
	 private boolean gameLoaded = false;
    private boolean gameplay = false;

    /*
    * constructor
    * creates a window container with 2 textfiled for the player names
	 * and 4 buttons to go to different portions of the game
	 * <p>
	 *	Play:		starts the game if both textfields are filled out with different names
	 *				pressing this button while the game is in progress will produce no action
	 * 			before game starts, can be pushed multiple times to generate new game states
	 * <p>
	 * Save:		save the current state of an ongoing game if the game is in progress
	 * <p>
	 * Load:		loads the saved game state if one exist
	 *				pressing this button while the game is in progress will produce no action
	 * <p>
	 * HighScores:		opens the RankWindow which displays the top ten Scores ever
	 *						window also displays most recent Score if a game has been completed this run through
	 * 					button can be pressed to open the window at any time
     */
    
    public Driver(){
    	
    dictator = new Dictator();

	//set up the Menu window
		setLayout(new GridLayout(7,1));
		setSize(500,250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	//create the menu title
		Label title = new Label("Frame It");
		title.setForeground(Color.BLACK);	
		
	//create and label the textfields
		player1Name = new TextField(21);
		player1Name.setVisible(true);
		
		player2Name = new TextField(21);
		player2Name.setVisible(true);
		
		Label player1 = new Label("Player 1: ");
		Label player2 = new Label("Player 2: ");
	
	//create the buttons with text labels	
		playButton = new Button("Play");
		loadButton = new Button("Load");
		saveButton = new Button("Save");
		highScores = new Button("Highscores");
		abtGame = new Button("About Game");
	
	//add ActionListener to each button	
		playButton.addActionListener(this);
		loadButton.addActionListener(this);
		saveButton.addActionListener(this);
		highScores.addActionListener(this);
		abtGame.addActionListener(this);
	

	//create containers, add thier components
		Panel panel1 = new Panel(new FlowLayout());		//container for the title label
		panel1.add(title);

		Panel panel2 = new Panel(new GridLayout(1,2));	//layout contains player 1 textfield and label
		panel2.add(player1);
		panel2.add(player1Name);
		
		Panel panel3 = new Panel(new GridLayout(1,2));	//layout contains player 2 textfield and label
		panel3.add(player2);
		panel3.add(player2Name);

		Panel panel4 = new Panel(new FlowLayout());		//container for the Play button
		panel4.add(playButton);
		
		Panel panel5 = new Panel(new FlowLayout());		//container for the Load button
		panel5.add(loadButton);
		
		Panel panel6 = new Panel(new FlowLayout());		//container for the Save button
		panel6.add(saveButton);
		
		Panel panel7 = new Panel(new GridLayout(1,2));		//container for the High Scores button and about game button
		panel7.add(highScores);
		panel7.add(abtGame);
		
	//add containers to menu window
		add(panel1);
		add(panel2);
		add(panel3);
		add(panel4);
		add(panel5);
		add(panel6);
		add(panel7);
	
	//initial set up for the RankWindow
		try{
			RankWindow.setUp("Best_Times.txt");
			File file = new File("README.md");
			Dictator.setFile("ErrorLog.txt");
			dictator.redisplay(file);
		}
		catch(FileNotFoundException e){ //raise exception if file isn't there when input stream is created
			dictator.writeErrors(e.getMessage());
			dictator.redisplay();
		}
		catch(IOException e){ //raise exception if an error occurs with stream
			dictator.writeErrors(e.getMessage());
			dictator.redisplay();
		}
    }
    
    /**
	 * Event method for the menu, checks to see if the button has been pressed and if it is allowed to be pressed again
	 */
	 
    public void actionPerformed(ActionEvent e){
    	
		//play the game
		if (e.getActionCommand().equals("Play") && GameGUI.getStartPressed() == false){
            playButton();		//play the game if game not currently being played acording to the GameGUI start button
			
		//save the game
		}else if (e.getActionCommand().equals("Save") && gameplay == true){
			saveButton();		//save the current game if the game is currently being played according to this menu's play button
			
		//load the game	
		}else if (e.getActionCommand().equals("Load") && SaveTracker.loadOkay()){
            loadButton();		//load saved game file if a full game has been saved
        
		//get all the scores	
		}else if (e.getActionCommand().equals("Highscores")){
            highScores();
		}
		else if (e.getActionCommand().equals("About Game")){
		    abtGameMethod();
		}

	}

	
	/*
	 *
	 */
	public void abtGameMethod(){
	    dictator.setVisible(true);
    }
    
	/**
	 * Method to use if the play button has been pressed and was allowed to be pressed.
	 * Sets gameplay to true allowing save button to be used, and makes sure the players 
	 * names are diferent. Then it choses to setup a new game or setup a loaded game 
	 * depending on if the load button has been pressed and the game successfully loaded.
	 * Finally in the main GameGUI it allows the start button to be pressed.
	 */
    
    public void playButton(){
        gameplay = true;
		GameGUI.setPlayer1(player1Name.getText());
        GameGUI.setPlayer2(player2Name.getText());
        if(!(GameGUI.getPlayer1().equals(GameGUI.getPlayer2()))){	//if names are the same ask again
            if (!gameLoaded){
                listener.setupGame();		//setup a new game that has not been saved
			}else{
                gameLoaded = false;			//game has now been loaded so must wait for load to be pressed again to load the game
                listener.setupGameLoad();	//setup a loaded game
			}
			GameGUI.startOn();
		}
		else{
			dictator.redisplay("Please enter 2 different names for player 1 and player 2");
		}
    }
    
	
	/**
	 * Save buttons method used to save each map and then the last time the game was at. 
	 */
	
    public void saveButton(){
        try{
            SaveTracker.saveTheGame(listener.getAMap(BIGARRAYSIZE), GameGUI.getPlayer1(), BIGARRAYSIZE, false);
            SaveTracker.saveTheGame(listener.getAMap(SMALLARRAYSIZE), "Solution", SMALLARRAYSIZE, true);
			SaveTracker.saveTheGame(listener.getAMap(BIGARRAYSIZE), GameGUI.getPlayer2(), BIGARRAYSIZE, true);
			SaveTracker.saveTime(Clock.getLastTime());		//saves each map and then the time
		}
		catch(FileNotFoundException e){ //raise exception if file isn't there when input stream is created
			dictator.writeErrors(e.getMessage());
			dictator.redisplay();
		}
		catch(IOException e){ //raise exception if an error occurs with stream
			dictator.writeErrors(e.getMessage());
			dictator.redisplay();
		}
    }
	
	
	/**
	 * Load button used to create the envionment as the old saved game. It starts by determining if 
	 * the save button was used befor a game had been generated or before save was ever used. To load the game 
	 * it calls saveTracker and individually loads the the name and assigns it to the TextField then loads the map 
	 * with a seperate method. For the solution generation the returned name is not needed so its just ingnored.
	 * lastly it calls for the saved time, sets the clock to that time, and then sets gameLoaded to true, allowing
	 * setup in listner to call the setupGameLoad.
	 */
    
    public void loadButton(){
        try{
			File fileToCheck = new File("Saves.txt");
			FileReader fileCheck = new FileReader(fileToCheck);
			BufferedReader fileReady = new BufferedReader(fileCheck);
            String lineRead = fileReady.readLine();
			if (fileReady.readLine() != null && lineRead != "00:00:00\n"){	//needs to determin if there is a save file
			
				player1Name.setText(SaveTracker.loadSavedGame(BIGARRAYSIZE));	//calls for player 1 name, while generating the map
				GameGUI.setPlayer1(player1Name.getText());	//assigns name
				map = SaveTracker.getMap();					//gets the map
				listener.setAMap(map, BIGARRAYSIZE);		//assigns the map
				
				String solution = SaveTracker.loadSavedGame(SMALLARRAYSIZE);	//solution map
				map = SaveTracker.getMap();
				listener.setAMap(map, SMALLARRAYSIZE);
				
				player2Name.setText(SaveTracker.loadSavedGame(BIGARRAYSIZE));	//player 2 map / name
				GameGUI.setPlayer2(player2Name.getText());
				map = SaveTracker.getMap();
				listener.setAMap(map, BIGARRAYSIZE);
				listener.updatePlayerLabels(GameGUI.getPlayer1(), GameGUI.getPlayer2());	//updates the names
				
				String s=SaveTracker.loadTime();		//calls for the time
				Clock.setLastTime(s);			//sets the time in clock
				gameLoaded = true;			//allows play button to call setupGameLoad
			}
			fileReady.close();
		}
        catch(FileNotFoundException e){ //raise exception if file isn't there when input stream is created
			dictator.writeErrors(e.getMessage());
			dictator.redisplay();
		}
		catch(IOException e){ //raise exception if an error occurs with stream
			dictator.writeErrors(e.getMessage());
			dictator.redisplay();
		}
    }
    
    /*
     * this method loads and displays the RankWindow containing the topten Scores and most recent score
     * method constructs and updates the RankWindow object win
     */
    public void highScores(){
        try{
			RankWindow win = new RankWindow();
			win.updateRanks();
		}
		catch(FileNotFoundException e){ //raise exception if file isn't there when input stream is created
			dictator.writeErrors(e.getMessage());
			dictator.redisplay();
		}
		catch(IOException e){ //raise exception if an error occurs with stream
			dictator.writeErrors(e.getMessage());
			dictator.redisplay();
		}
    }
    
	/*
	 * main(), constructs driver object
	 */
	public static void main(String[] args){
		Driver gameMenu = new Driver();
	}
}

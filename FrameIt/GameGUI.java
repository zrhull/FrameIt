/**
 * @Author JC Sicat Group 40
 * @Version 1.4
 *
 * Frame It GameGUI class
 * Semester Project
 */
 
package GUI;
import Logic.*;
import GUI.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


 /**
  * Frame It GameGUI class.
  * <p>
  * This class creates the game window and sets up the layout of entire board, instances,
  * and GUI.
  */

public class GameGUI extends JFrame implements ActionListener{
	private static String player1;
    private static String player2;    
    private static Button startButton;
    private static boolean startPressed = false;
    
    private Clock clock;
    private BoardButtons p1Board;
    private BoardButtons p2Board;
    private TextField textField;
    private BoardButtons sBoard;
    private boolean inputAllowed;
    private Label commentator1;
    private Label commentator2;
    private Label player1Name;
	private Label player2Name;

    
    /**
     * This constructor initializes the layout and instances.  It adds the panels for main layout.
     * Initializes the Boards for the players and the solution.  Adds all the instances in the GUI, 
     * and set's the properties of the GUI.  Sets dimension for size for Player 1 and Player 2.  It 
     * also sets the background color for both players.
     */
    
    public GameGUI(){
		
		//Initializes the Layout and instances
		player1Name = new Label();
		player2Name = new Label();
		commentator1 = new Label();
		commentator2 = new Label();
		startButton = new Button("Start");
		startButton.addActionListener(this);
		textField = new TextField(50);
		setLayout(new BorderLayout());
		setSize(1000, 500);
		Panel clockPanel = new Panel(new FlowLayout());
		clock = new Clock(clockPanel);
		inputAllowed = false;

		//Adds the Panels for main layout
		Panel p1 = new Panel(new GridLayout(5,5));
		add(p1, BorderLayout.WEST);
		Panel p2 = new Panel(new GridLayout(5,5));
		add(p2, BorderLayout.EAST);
		Panel north = new Panel(new GridLayout(1,3));
		add(north, BorderLayout.NORTH);
		Panel south = new Panel(new GridLayout(1,2));
		add(south, BorderLayout.SOUTH);
		Panel center = new Panel(new FlowLayout());
		add(center, BorderLayout.CENTER);
		Panel centerBoard = new Panel(new GridLayout(3,3));
		Panel centerLabels = new Panel(new GridLayout(4,1));

		//Initializes the Boards for the players and the solution
		p1Board = new BoardButtons(p1);		 
		p2Board = new BoardButtons(p2);
		sBoard = new BoardButtons(centerBoard, 3);		 

		//Adds all the instances in the GUI, and set's the properties of the GUI
		center.add(centerBoard);
		center.add(centerLabels);
		centerLabels.add(clockPanel);
		centerLabels.add(startButton);
		centerLabels.add(commentator1);
		centerLabels.add(commentator2);
		center.setPreferredSize(new Dimension(100,100));
		center.setBackground(Color.LIGHT_GRAY);
		centerBoard.setPreferredSize(new Dimension(175,175));
		north.add(player1Name);
		north.add(textField);
		north.add(player2Name);
        startButton.setEnabled(false);
        
        //Sets dimension for size for Player 1 and Player 2.  It also sets the 
        //background color for both players.
		p1.setPreferredSize(new Dimension(400,400));
		p1.setBackground(Color.MAGENTA);
		p2.setPreferredSize(new Dimension(400,400));
		p2.setBackground(Color.CYAN);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		textField.setBackground(Color.BLACK);
		textField.setForeground(Color.BLACK);
    }
    
    
    /**
     * This methode updates the boards whenever Player 1 and Player 2
     * makes move.
     *
     * @param   p1   This updates the Player 1 board.
     * @param   p2   This updates the Player 2 board.
     */
    
    public void updateBoards(PlayMap p1, PlayMap p2){
		p1Board.update(p1);
		p2Board.update(p2);
    }
    
    
    /**
     * This methode initiate the boards for Player 1 , Player 2 and 
     * soulution board.
     *
     * @param   p1   This sets board for Player 1.
     * @param   p2   This sets board for Player 2.
     * @param   s    This sets board for soultion board.
     */
    
    public void updateBoards(PlayMap p1, PlayMap p2, Solution s){
		p1Board.update(p1);
		p2Board.update(p2);
		sBoard.update(s);
    }
    
    
    /**
     * This methode starts the clock when the start button is clicked.
     * When the start button is clicked, this methode will allow the
     * players to make their blank move.
     *
     * @param   e    This ActionEvent decides what happens when the start button is clicked.
     */
    
    public void actionPerformed(ActionEvent e){
		try{
		if (e.getActionCommand().equals("Start")){
			clock.startTime();
			startButton.setVisible(false);
			textField.requestFocusInWindow();
			inputOn();
            startPressed = true;
		}
		}catch(NullPointerException npe){
			System.out.println("What now?");
		}
    }
    
    
    /**
     * This methode returns the textField.
     *
     * @return   textField   Returns the textField.
     */
    
    public TextField getTField(){
		return textField;    
    }
       
       
    /**
     * This methode allows the players to input.
     *
     * @return   inputAllowed   Returns the inputAllowed.
     */
    
    public boolean getInputAllowed(){
		return inputAllowed;
    }    


    /**
     * This methode turns off player input.
     */
    
    public void inputOff(){
		inputAllowed = false;
    }
    
    
    /**
     * This methode turns on player input.
     */

    public void inputOn(){
		inputAllowed = true;
    }
    
    
    /**
     * This getter methode gets Player 1.
     *
     * @return   player1   Returns player1.
     */

    public static String getPlayer1(){
		return player1;
    }
    
    
    /**
     * This setter methode sets Player 1.
     *
     * @param   arg   This takes string type arg.
     */

    public static void setPlayer1(String arg){
		player1 = arg;
    }
    
    
    /**
     * This getter methode gets Player 2.
     *
     * @return   player2   Returns player2.
     */

    public static String getPlayer2(){
		return player2;
    }
    
    
    /**
     * This setter methode sets Player 2.
     *
     * @param   arg   This takes string type arg.
     */

    public static void setPlayer2(String arg){
		player2 = arg;
		
    }


    /**
     * This setter methode sets commantator1.
     *
     * @param   arg   This takes string type arg.
     */

    public void setCommentator1(String arg){
		commentator1.setText(arg);
    }
    
    
    /**
     * This setter methode sets commantator2.
     *
     * @param   arg   This takes string type arg.
     */
    
    public void setCommentator2(String arg){
		commentator2.setText(arg);	
	}
	
	public void updatePlayerLabels(String n1, String n2){
		player1Name.setText("(Player 1) " + n1);
		player2Name.setText("(Player 2) " + n2);
	}
    
    public static void startOff(){
        startButton.setEnabled(false);
    }
    
    public static void startOn(){
        startButton.setEnabled(true);
    }
    
    public static boolean getStartPressed(){
        return startPressed;
    }
    
    
}
/**
 * @author     Seung Kang
 * @version    %I%, %G%
 * @since      1.0
 *
 * This is the clock class that will create
 * a timer based on seconds.
 * It is a simple timer with only start button available.
 * You need to click the start button to start the timer but
 * to stop it, you will have to finish the Frame It game.
 * However, for now the timer will continue to move even if the 
 * game is finished.
 * When the game is finished the main class FrameIt will set the
 * instance varable "trigger" to false and the timer will stop.
 * Something to keep in mind is that, if "Start" button is pressed
 * again while the timer is going, it will start the another timer.
 * <p>
 * This code was created based on Winston Lievsay's YouTube video
 * "Java GUI Tutorial 29 - Timer program (Part 1 of 2)" and
 * "Java GUI Tutorial 30 - Timer program (Part 2 of 2)".
 * The address to following videos are
 * "https://www.youtube.com/watch?v=gs5aMzlLLts" and 
 * "https://www.youtube.com/watch?v=4HQDH2r9hqo".
 */

package GUI;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;


public class Clock extends JFrame {
    
    /**
     * Setting the variables that is used in this code.
     */
     
    private JLabel timerLabel; 						// Label for timer.
    private int counter; 								// Counter that will count the time.
    private Timer timer;
    public static boolean trigger = true; 				// Variable that determines when the timer will stop.
	TimeClass tc;
	private static String lastTime;
    private int some = 0;
	
	
    /**
     * In this constructor it adds the label 
     * "Standby" in center.
     *
     * @param p     takes the argument Panel p.  
     */
    
    public Clock(Panel p) {
		setLastTime("00:00:00");
		timerLabel = new JLabel("Time: 00:00", SwingConstants.CENTER);
		p.add(timerLabel); 					// add "Standby" label at center.
	 }
    
    
    /**
     * The variable "count" is initialized as 0 so when the
     * timer starts, it will start from 0 seconds.
     */
    
    public void startTime(){
		int count = 0;
		timerLabel.setText("Time: " + count);
		tc = new TimeClass(count);
		timer = new Timer(10, tc);
		timer.start(); 						// starts the timer.	
    }

	
		/**
         *Getter methode to get the amount of time Winner took to finish the game.
         *It will return that value so it can be used in ranking system.
         */
         
		public static String getLastTime() {
			return lastTime;
		}
		
		/**
		*Setter methode to set the string parameter arg to lastTime.
		*
		*@param   arg   This string parameter sets the value of lastTime.
		*/
		
		public static void setLastTime(String arg){
			lastTime = arg;
		}
		
		/**
		*This methode takes string value lastTime and extract the value equal to minute.
		*After it extracts that value it turns it into integer value and return it.
		*
		*@return   itM   This is integer type of lastTime minute.
		*/
		
		int calMin(){
			String tM = lastTime.substring(0,2);
			int itM = Integer.parseInt(tM);
			return itM;
		}
		
		/**
		*This methode takes string value lastTime and extract the value equal to second.
		*After it extracts that value it turns it into integer value and return it.
		*
		*@return   itS   This is integer type of lastTime second.
		*/
		
		int calSec(){
			String tS = lastTime.substring(3,5);
			int itS = Integer.parseInt(tS);
			return itS;
		}
		
		/**
		*This methode takes string value lastTime and extract the value equal to centi second.
		*After it extracts that value it turns it into integer value and return it.
		*
		*@return   itM   This is integer type of lastTime centi second.
		*/
		
		int calCen(){
			String tB = lastTime.substring(6,8);
			int itB = Integer.parseInt(tB);
			return itB;
		}

    /**
     * This class will increment the variable "counter" by 1 every centi second.
     * The timer will keep going untill the variable "trigger"
     * set to false.
     */
	
    public class TimeClass implements ActionListener {

		int counter;		//Centisecond	
		int counterS;       //Second
		int counterM;       //Minute

    	public TimeClass(int counter) {
			counter = calCen();		//centisecond	
			counterS = calSec();       //Second
			counterM = calMin();          //Minute
    	}
		
		/**
		*This methode will increment the counter by 1 every centi second.  It will start
		*incrementing when the player press "Start" button.  When the player finishes the
		*game, the timer will stop.
		*
		*@param   tc   This argument is action event that will trigger time timer to go off.
		*/
	
	 	public void actionPerformed(ActionEvent tc) {
	    	counter++; 							// increment counter by 1 every second.
	    	String tempS = String.format("%02d", counterM) +  ":" + String.format("%02d", counterS);
			String temp = String.format("%02d", counterM) +  ":" + String.format("%02d", counterS) + ":" + String.format("%02d", counter);
			if(trigger){
				setLastTime(temp);
				timerLabel.setText("Time: " + tempS); // shows current timer.
				if (counter > 99) { //changed to equal to so not counting to 100
					counterS = counterS + 1;
					counter = 0;
					if (counterS > 59) { //changed to equal to so not counting to 60
						counterM = counterM + 1;
						counterS = 0;
					}
				}
				else if (counter == 100){}
			}
			else if (!trigger){
				timer.stop();
				setLastTime("Time: " + tempS);
				timerLabel.setText(getLastTime());
			}
		}
    }
}

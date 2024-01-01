package hw2;
import static api.BallType.RED;
import static api.BallType.WHITE;
import static api.BallType.YELLOW;
import static api.PlayerPosition.PLAYER_A;
import static api.PlayerPosition.PLAYER_B;

import api.BallType;
import api.PlayerPosition;

/**
 * Class that models the game of three-cushion billiards.
 * 
 * @author Cade Bradford
 */
public class ThreeCushion {
	/** 
	 * Stores the number of cushion impacts 
	 **/
	private int cushionImpacts;
	/**
	 * Stores the current inning
	 **/
	private int inning;
	/**
	 * Stores player b's score
	 **/
	private int playerBscore;
	/**
	 * Stores player a's score
	 **/
	private int playerAscore;
	/**
	 * Stores the points required to win
	 **/
	private int pointsToWin;
	/**
	 * Stores the winner of the lag
	 **/
	private PlayerPosition lagWinner;
	/**
	 * Stores the current player up
	 **/
	private PlayerPosition playerUp;
	/**
	 * Stores player A's cue ball
	 **/
	private BallType playerAcue;
	/**
	 * Stores player B's cue ball
	 **/
	private BallType playerBcue;
	/**
	 * Stores if one of the players objects has been hit yet
	 **/
	private boolean objectA ;
	/**
	 * Stores if one of the players objects have been hit yet
	 **/
	private boolean objectB ;
	/**
	 * Stores if the inning has started
	 **/
	private boolean inningStarted;
	/**
	 * Stores if the shot has started
	 **/
	private boolean shotStarted;
	/**
	 * Stores if it is a break shot or not
	 **/
	private boolean isBreakShot;
	/**
	 * Stores if you can switch players or not
	 **/
	private boolean ableSwitch=true;
	/**
	 * Stores if there has been a foul on the play
	 **/
	private boolean foul;
	/**
	 * Stores if the player completed a bank shot
	 **/
	private boolean bankShot;
	/**
	 * Stores if it is possible for the player to get a bank shot
	 **/
	private boolean possibleBank;
	/**
	 * Stores if the game is over or not
	 **/
	private boolean gameOver;
	/**
	 * @return 
	 * returns if the shot has been started yet
	 **/
	public boolean isShotStarted() {
		return shotStarted;
	}
	/**
	 * Sets the first player and the cue ball
	 * @param self break
	 * inputes if the lag winner want to start or not
	 * @param
	 * inputes what cue ball the lag winner chooses
	 **/
	public void lagWinnerChooses(boolean selfBreak, BallType cueBall) {
		if (lagWinner == PLAYER_A){
			if (cueBall == WHITE) {
				playerAcue = WHITE;
				playerBcue = YELLOW;
			}
			else {
				playerAcue = YELLOW;
				playerBcue = WHITE;
			}
			if (selfBreak == true) {
				playerUp = PLAYER_A;
			}
			else {
				playerUp = PLAYER_B;
			}
			isBreakShot = true;
		}
		else if (lagWinner == PLAYER_B) {
			if (cueBall == WHITE) {
				playerAcue = YELLOW;
				playerBcue = WHITE;
			}
			else {
				playerAcue = WHITE;
				playerBcue = YELLOW;
			}
			if (selfBreak == true) {
				playerUp = PLAYER_B;
			}
			else {
				playerUp = PLAYER_A;
			}
			isBreakShot = true;
		}
		lagWinner = null;
		
		
	}
	/**
	 * Indicates the cue stick has struck the given ball.
	 * @param' color
	 * takes in the ball that was hit
	 **/
	public void cueStickStrike(BallType color) {
		bankShot = false;
		foul = false;
		if (!gameOver) {
		if (playerUp == PLAYER_A) {
			if (playerAcue == color) {
				inningStarted = true;
				shotStarted = true;
			}
			else {
				foul();
			}
		}
		else if (playerUp == PLAYER_B) {
			if (playerBcue == color) {
				inningStarted = true;
				shotStarted = true;
			}
			else {
				foul();
			}
		}
		}
	}
	/**
	 * Contructor creates a new game of three-cushion billiards with a given lag winner and the predetermined number of points required to win the game.
	 * @param lagWinner1
	 * takes in the lag winner
	 * @param pointsToWin1
	 * sets the points to win in this game
	 */
	public ThreeCushion(PlayerPosition lagWinner1, int pointsToWin1) {
		inning = 1;
		pointsToWin = pointsToWin1;
		lagWinner = lagWinner1;
	}
	
	/**
	 * @return
	 * returns if there was a bank shot made
	 */
	public boolean isBankShot() {
		return bankShot;
	}
	/**
	 * @return
	 * returns the cue ball of the player that is currently up
	 */
	public BallType getCueBall() {
		if (playerUp == PLAYER_A)
			return playerAcue;
		else if (playerUp == PLAYER_B) {
			return playerBcue;
		}
		return null;
	}
	/**
	 * @return
	 * returns if this shot is a break shot
	 */
	public boolean isBreakShot() {
		return isBreakShot;
	}
	/**
	 * Indicates a ball has been hit
	 * @param color
	 * input what color of ball has been hit by cue
	 */
	public void cueBallStrike(BallType color) {
		if (playerAcue!=null&&!foul) {
			/**
			 * makes sure if it is a break shot that the user hits the red ball first
			 */
		if (isBreakShot) {
			if (color == RED) {
				if (cushionImpacts>=1) {
				foul();
			}
			
				objectA = true;
			}
			else {
				foul();
			}
			isBreakShot = false;
				}
		else {
			if (!(objectA) && !(objectB)) {
				if (cushionImpacts >=3) {
					possibleBank = true;
				}
				if (color == RED) {
					objectA = true;
				}
				else {
					objectB = true;
				}
				
			}
			else {
				/**
				 * makes sure the cushion has been impacted three times before counting the point
				 */
				if (cushionImpacts >= 3) {
					if (color == RED) {
						objectA = true;
					}
					else {
						objectB = true;
					}
					if (objectB && objectA) {
					if (possibleBank) {
						bankShot= true;
					}
					if (playerUp== PLAYER_A) {
						playerAscore++;
						ableSwitch = false;
						if (playerAscore >= pointsToWin) {
							gameOver = true;
							inningStarted = false;
						}
					}
					else {
						playerBscore++;
						ableSwitch = false;
						if (playerBscore >= pointsToWin) {
							inningStarted = false;
							gameOver = true;
						}
					}
					}
					}
				
				}
					
			}
		}
	}
	/**
	 * Indicates the cue has hit a cushion
	 */
	public void cueBallImpactCushion() {
		cushionImpacts++;
	}
	/**
	 * ends the shot and resets the table
	 * also switches players if not already done
	 */
	public void endShot() {
		isBreakShot = false;
		objectA= false;
		objectB=false;
		shotStarted = false;
		possibleBank= false;
		
		cushionImpacts= 0;
		if (!(foul)&& ableSwitch&&playerAcue!=null) {
			inning++;
			if (playerUp == PLAYER_A) {
				playerUp = PLAYER_B;
				}
			else {
			playerUp = PLAYER_A;
			}
			inningStarted = false;
			
		}
		ableSwitch = true;
		}
	
	/**
	 * indicates a foul has been made and switches innings and stops player from getting more points
	 */
	public void foul() {
		if (!(playerAcue==null)&&!gameOver){
		if (playerUp == PLAYER_A) {
				playerUp = PLAYER_B;
				
				}
		else {
			playerUp = PLAYER_A;
			}
			foul = true;
			inning++;
			inningStarted = false;
		}
	}
	/**
	 * @return
	 * returns the inning
	 */
	public int getInning() {
		return inning;
	}
	/**
	 * @return
	 * returns play b's score
	 */
	public int getPlayerBScore() {
		return playerBscore;
	}
	/*
	 * @return
	 * returns player a's score
	 */

	public int getPlayerAScore() {
		return playerAscore;
	}
	/*
	 * @return if the game is over or not
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	/**
	 * @return
	 * returns if the inning has started
	 */
	
	public boolean isInningStarted() {
		return inningStarted;
	}
	/**
	 * @return
	 * returns what player is up in that inning
	 */
	public PlayerPosition getInningPlayer() {
		return playerUp;
	}
	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * <p>
	 * <tt>Player A*: X Player B: Y, Inning: Z</tt>
	 * <p>
	 * The asterisks next to the player's name indicates which player is at the
	 * table this inning. The number after the player's name is their score. Z is
	 * the inning number. Other messages will appear at the end of the string.
	 * 
	 * @return one-line string representation of the game state
	 */
	public String toString() {
		String fmt = "Player A%s: %d, Player B%s: %d, Inning: %d %s%s";
		String playerATurn = "";
		String playerBTurn = "";
		String inningStatus = "";
		String gameStatus = "";
		if (getInningPlayer() == PLAYER_A) {
			playerATurn = "*";
		} else if (getInningPlayer() == PLAYER_B) {
			playerBTurn = "*";
		}
		if (isInningStarted()) {
			inningStatus = "started";
		} else {
			inningStatus = "not started";
		}
		if (isGameOver()) {
			gameStatus = ", game result final";
		}
		return String.format(fmt, playerATurn, getPlayerAScore(), playerBTurn, getPlayerBScore(), getInning(), inningStatus, gameStatus);
	}
	}
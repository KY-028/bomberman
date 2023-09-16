/* This is the character class
 * It contains all about the character
 * Such as amount of lives, score, speed, and various score trackings
 */
public class Character {

	private int lives;
	private boolean shield = false;
	private int score;
	private int speed;
	private int storespeed;
	private int numbombs = 1;
	private int bombpower = 1;
	public String currenttool = "bomb";
	private int toolcount;
	private int charnum;
	private int bombsset;
	private int propsset;
	private int blocksdestroyed;

	public Character(int numbombs, int bombpower) {
		this.numbombs = numbombs;
		this.bombpower = bombpower;
		speed = 3;
		lives = 1;
		score = 0;
		bombsset = 0;
		propsset = 0;
		blocksdestroyed = 0;
	}

	public Prop setProp(Map map,int x, int y, int time){
		if (currenttool.equals("bomb")) {
			if (map.getMap()[x][y] == 0 || map.getMap()[x][y] == 6) {
				map.getMap()[x][y] = 3;
				//the current game in minutes will change the time it takes for a bomb to explode slightly
				if (time >= 3) { //within the first 2 minutes the bomb takes 150 frames or 2.5 seconds to explode
					return new Prop(this,"bomb",x,y,3,150);
				} else if (time >1) { //within the next 2 minutes the bomb takes 120 frames or 2 seconds to explode
					return new Prop(this,"bomb",x,y,3,120);
				} else if (time == 0) { //within the last minute the bomb takes 90 frames or 1.5 seconds to explode
					return new Prop(this,"bomb",x,y,3,90);
				}
			}
		} else if (currenttool.equals("minebomb")) {
			if (map.getMap()[x][y] == 0) {
				map.getMap()[x][y] = 5;
				decreaseToolCount();
				return new Prop(this,"mineset",x,y,9,0);
			}
		} else if (currenttool.equals("gas")) {
			if (map.getMap()[x][y] == 0) {
				map.getMap()[x][y] = 6;
				decreaseToolCount();
				return new Prop(this,"fog",x,y,8,1800);
			}
		}
		return null;
	}

	//getter
	public int getPower(){
		return bombpower;
	}
	public int getNumBombs(){
		return numbombs;
	}
	public int getSpeed(){
		return speed;
	}
	public boolean getShield(){
		return shield;
	}
	public int getLives(){
		return lives;
	}
	public int getScore(){
		return score;
	}
	public int getCharNum(){
		return charnum;
	}
	public int getBombsSet(){
		return bombsset;
	}
	public int getBlockDestroyed(){
		return blocksdestroyed;
	}
	public int getCurrentTool(){
		if (currenttool.equals("bomb")) {
			return 3;
		} else if (currenttool.equals("minebomb")) {
			return 6;
		} else if (currenttool.equals("gas")) {
			return 7;
		} else {
			return 0;
		}
	}
	public int getPropsSet(){
		return propsset;
	}

	//setter
	public void increaseSpeed(){
		this.speed++;
	}
	public void increaseBomb(){
		this.numbombs++;
	}
	public void increasePower(){
		this.bombpower++;
	}
	public void increaseLife(){
		this.lives++;
	}
	public void setShield(boolean shield){
		this.shield = shield;
	}
	public void addScore(int points) {
		score += points;
	}
	public void setCharNum(int c) {
		charnum = c;
	}
	public void loseLife(){
		lives--;
	}
	public void increaseBombsSet(){
		bombsset++;
	}
	public void increaseBlocksDestroyed(){
		blocksdestroyed++;
	}
	public void increasePropsSet(){
		propsset++;
	}
	public void setCurrentTool(String s){
		currenttool = s;
		toolcount = 2;
	}
	public void decreaseToolCount(){
		toolcount--;
		if (toolcount == 0) {
			currenttool = "bomb";
		}
	}
	public void restoreSpeed(){
			speed = storespeed;
	}
	public void affectedspeed(){
		if (speed!=1) {
			storespeed = speed;
			speed = 1;
		}
	}
	public String toString(){
		return String.format("Character Status: %nLives: %d%nNum Bombs:%d%nPower: %d%nSpeed: %d%nStored Speed: %d", lives,numbombs,bombpower,speed,storespeed);
	}
}

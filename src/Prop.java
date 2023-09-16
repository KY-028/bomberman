/* This is the prop class
 * It contains info like the character that set the prop, the prop name, description, location
 * It implemented the Comparable Interface for sorting the props by its names alphabetically
 */
import java.awt.Rectangle;

public class Prop implements Comparable<Object>{
	
	private Character ch;
	private String name;
	private int row;
	private int column;
	private int imgno;
	private int time;
	private Rectangle loc;
	private String description;
	private Character affectedch = null;
	
	public Prop (Character ch, String name, int r, int c, int imgno, int framecount) {
		row=r;
		column=c;
		this.ch = ch;
		this.name = name;
		this.imgno = imgno;
		loc=new Rectangle(r*50,c*50,50,50);
		if (framecount != 0){
			time = framecount; //get and set
		}
		//Below is only used for props page
		if (this.name.equalsIgnoreCase("bomb")) {
			description="<html>A regular bomb.<BR>By default, it destroys 1 block in all its directions.</html>";
		} else if (this.name.equalsIgnoreCase("speedy shoes")) {
			description="Increases the character's speed by 1";
		} else if (this.name.equalsIgnoreCase("bomb +1")) {
			description="<html>Increase the number of bombs that can be set by <BR>that character by 1</html>";
		} else if (this.name.equalsIgnoreCase("shield")) {
			description="<html>Protects the character from a bomb attack once.<BR>Monitor the character status to see if shield is present.</html>";
		} else if (this.name.equalsIgnoreCase("power")) {
			description="<html>Increase the character's power of the bomb by 1.<BR>(Damages one more block in all directions)</html>";
		} else if (this.name.equalsIgnoreCase("heart")) {
			description="<html>Increase character life by 1!<BR>(A life protects the character from all sorts of attacks)</html>";
		} else if (this.name.equalsIgnoreCase("gas tank")) {
			description="Can set \"fog\" on the map.";
		} else if (this.name.equalsIgnoreCase("fog")) {
			description="<html>If an opponent steps on the fog, their speed<BR> decreases to 1 for 8 seconds. A fog disappears <BR> automatically in 30 seconds</html>";
		} else if (this.name.equalsIgnoreCase("mine bomb")) {
			description="<html>Set minebombs on the map.<BR>If an enemy steps on the mine, they lose a life regardless of shield.</html>";
		} else if (this.name.equalsIgnoreCase("mine location")) {
			description="<html>Indicates the location of a set mine.<BR>You can clear a mine by setting a bomb beside it.</html>";
		}
	}

	//Perform the action according to the character
	//Return: boolean for special props like mineset and gas, Parameter: the character that touched the prop
	public boolean performAction(Character character) {
		if (this.name.equals("speed")) {
			character.increaseSpeed();
		} else if (this.name.equals("bomb+1")) {
			character.increaseBomb();
		} else if (this.name.equals("shield")) {
			character.setShield(true);
		} else if (this.name.equals("power")) {
			character.increasePower();
		} else if (this.name.equals("life")) {
			character.increaseLife();
		} else if (this.name.equals("gas")) {
			character.setCurrentTool("gas");
		} else if (this.name.equals("minebomb")) {
			character.setCurrentTool("minebomb");
		} else if (this.name.equals("mineset")) {
			//if the character that stepped on the mine is the one that set it, it doens't count
			if (ch!=character) {
				character.loseLife();
				return true;
			} else {
				return false;
			}
		} else if (this.name.equals("gas")) {
			character.setCurrentTool("gas");
		} else if (this.name.equals("fog")) {
			//if the character that stepped on the fog is the one that set it, it doens't count
			if (ch!=character) {
				if (affectedch == null) {
					this.setTime(480); //affected time: 8 seconds
					affectedch = character;
					character.affectedspeed();
				}
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	//SORTING BY ALPHABETICAL ORDER!
	public int compareTo(Object o) {
		Prop p = (Prop)o;
		return this.name.compareToIgnoreCase(p.name);
	}
	
	//FINDING THE INDEX OF A PROP
	public boolean equals(Object o) {
		Prop p = (Prop)o;
		return (this.row==p.getRow()) &&(this.column==p.getColumn());
	}
	
	//getter
	public String getName(){
		return name;
	}
	public int getRow(){
		return row;
	}
	public int getColumn(){
		return column;
	}
	public int getImageNo(){
		return imgno;
	}
	public int getTime(){
		return time;
	}
	public Rectangle getRectangle(){
		return loc;
	}
	public Character getChar(){
		return ch;
	}
	public Character getAffectedChar(){
		return affectedch;
	}
	public String getDescription(){
		return description;
	}
	//setter
	public void setChar(Character ch){
		this.ch = ch;
	}
	public void setTime(int time){
		this.time = time;
	}
	public void setName(String s){
		name = s;
	}
	public void setImageNo(int im) {
		imgno = im;
	}
	
	public String toString(){
		return String.format("Prop Info: %nName: %s%nRow: %d%n%Column: %d%nRectangle area: %s", name,row,column,loc.toString());
	}
}

/* This is the Map class
 * It contains all the information about a map!
 * Such as the map 2D array, the wall blocks, destructible wall class and props that are summoned
 */

import java.awt.*;
import java.util.*;

public class Map{
	private int[][]map;
	private int[][]initialmap = new int[13][15];
	private ArrayList<Rectangle> walls = new ArrayList<>();
	private ArrayList<Rectangle> initialwalls;
	private ArrayList<Rectangle> destructible = new ArrayList<>();
	private ArrayList<Rectangle> initialdestructible;
	private HashMap<Rectangle,Character> bombLocs = new HashMap<>();
	private ArrayList<Rectangle> bombWalls = new ArrayList<>();
	private ArrayList<Prop> props = new ArrayList<>();
	private Color mapcolor;

	public Map (int[][] map, ArrayList<Rectangle> walls, ArrayList<Rectangle> destructible, Color mapcolor){
		this.map = map;
		this.walls = walls;
		this.destructible = destructible;
		this.mapcolor = mapcolor;	
		for (int i = 0; i < map.length; i++) { //copy maps
			initialmap[i] = Arrays.copyOf(map[i], map[i].length);
		}
		initialwalls = new ArrayList<>(walls);
		initialdestructible = new ArrayList<>(destructible);
	}

	//1 means walls
	//2 means destructible
	//3 means bombs
	//4 means props
	//5 means minebombs
	//6 means fog
	
	//This method checks the block and removes the block
	//Return: none, Parameter: x,y coordinate and the character that set it
	public void removeBlock(Character ch, int x, int y){
		if (map[x][y] == 2) { //if it's a destructible block
			map[x][y] = 0;
			Rectangle r = new Rectangle(y*50,x*50,50,50); //Find its rectangle and remove from walls
			walls.remove(r);
			destructible.remove(r);
			ch.addScore(10);
			ch.increaseBlocksDestroyed();
			
			//Summon props
			if (newProp()) { //newProp() --> 45% chance for spawning a prop
				SplittableRandom random = new SplittableRandom();
				int newProp = random.nextInt(100);
				if (newProp < 8) { //8%
					map[x][y] = 4;
					props.add(new Prop(null,"shield",x,y,2,0));
				} else if (newProp >=8 && newProp < 33) { //25%
					map[x][y] = 4;
					props.add(new Prop(null,"bomb+1",x,y,0,0));
				} else if (newProp >=33 && newProp < 58) {//25%
					map[x][y] = 4;
					props.add(new Prop(null,"power",x,y,1,0));
				} else if (newProp >=58 && newProp <75){ //17%
					map[x][y] = 4;
					props.add(new Prop(null,"speed",x,y,4,0));
				} else	if (newProp >= 75 && newProp <85) { //10%
					map[x][y] = 4;
					props.add(new Prop(ch, "minebomb",x,y,6,0));
				} else if (newProp >= 85 && newProp <95) { //10%
					map[x][y] = 4;
					props.add(new Prop(ch, "gas",x,y,7,1800));
				} else if (newProp >= 95 && newProp <100) {//5%
					map[x][y] = 4;
					props.add(new Prop(null,"life",x,y,5,0));
				}
			}
		} else if (map[x][y]==3) { //if it's a bomb that was placed (make it 0 so it can place another bomb again)
			map[x][y] = 0;
		} else if (map[x][y]==5) { //if that block is a set mine
			map[x][y] = 0; //destroy that mine
			int index = props.indexOf(new Prop(null,"",x,y,0,0)); //remove the mineset from props arraylist
			props.get(index).setName("");
			props.get(index).setImageNo(10);
		}
	}
	
	//This method restores all map lists, info back to the original so it's good to go if this map was selected again
	//Return: none, Parameter: none
	public void restart(){
		for (int i = 0; i < map.length; i++) {
			map[i] = Arrays.copyOf(initialmap[i], initialmap[i].length);
		}
		walls = new ArrayList<>(initialwalls);
		destructible = new ArrayList<>(initialdestructible);
		props.clear();
		bombWalls.clear();
		bombLocs.clear();
	}

	//a 45% chance of spawning a new prop
	//Return: boolean yes or no for spawn, Parameter: none
	public boolean newProp(){
		SplittableRandom random = new SplittableRandom();
		boolean newProp = random.nextInt(100) < 45;
		if (newProp) {
			return true;
		} else {
			return false;
		}
	}

	//getter
	public int[][] getMap(){
		return map;
	}

	public ArrayList<Rectangle> getWalls(){
		return walls;
	}

	public ArrayList<Rectangle> getDestructible(){
		return destructible;
	}
	
	public ArrayList<Rectangle> getBombWalls(){
		return bombWalls;
	}

	public Color getMapColor(){
		return mapcolor;
	}

	public ArrayList<Prop> getProps(){
		return props;
	}
	
	public HashMap<Rectangle,Character> getBombLocs(){
		return bombLocs;
	}

}
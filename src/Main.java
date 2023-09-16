/* ICS4U Culminating
 * This program is a game that is inspired by Bomb-it on 4399.com
 * Due Date: June 22nd, 2022
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class Main extends JPanel implements ActionListener, Runnable, KeyListener, MouseListener{

	JFrame frame;
	Clip mainmusic, clip1, clip2, clip3, clip4, clip5, clip6, clip7, win, lose;

	//Starting Page
	JButton start,help,exit,sound,music,back,prop;
	boolean musicswitch=true;
	boolean soundswitch=true;
	ImageIcon[] images = new ImageIcon[12];
	static Image[] imgs = new Image[3];

	//How to Play Page
	JLabel mainimage, title, rul1, rul2, rul3, rul4, rul5, rul6;
	ArrayList<Prop> props = new ArrayList<>();

	//Props Related
	static Image[] propimgs = new Image[11];
	JLabel[] proplabel = new JLabel[10];
	JLabel[] propimagelabels = new JLabel[10];
	JLabel[] propdescriptions = new JLabel[10];

	//Characters Related
	Image[][] charimgs = new Image[4][4];
	Rectangle[] recs = new Rectangle[4];
	JLabel instruction,chlabel1,chlabel2,chlabel3,chlabel4,arrow1,arrow2,keyslabel;
	boolean char1selected = false;
	boolean char2selected = false;
	int[] charselects = new int[2];
	Rectangle rect1 = new Rectangle(0, 0, 41, 45);
	Rectangle rect2 = new Rectangle(705, 605, 41, 45);
	Character char1,char2;
	boolean up1, down1, left1, right1, up2, down2, left2, right2;
	int char1bombson = 0;
	int char2bombson = 0;
	int char1imageno = 0;
	int char2imageno = 0;

	
	//Game related
	static HashMap<Integer,Map> gamemaps = new HashMap<Integer,Map> ();
	HashMap<Rectangle,Integer> damagerecs = new HashMap <Rectangle,Integer>();
	static Map map;
	Thread thread;
	int screen = 0;
	JLabel char1life,char1bomb,char1power,char1speed,char1score,char1set,char1destruct,char1propsset,
	char2life,char2bomb,char2power,char2speed,char2score,char2set,char2destruct,char2propsset,winner;
	int screenWidth = 750;
	int screenHeight = 650;
	int FPS = 60;
	boolean gamepaused = false;

	//Timer
	JLabel counterLabel;
	Timer timer;
	int minute,second;

	public Main(){
		
		try {
			//Import all music
			AudioInputStream sound = AudioSystem.getAudioInputStream(getClass().getResource("/music/main.wav"));
			mainmusic = AudioSystem.getClip();
			mainmusic.open(sound);
			sound = AudioSystem.getAudioInputStream(getClass().getResource("/music/1.wav"));
			clip1 = AudioSystem.getClip();
			clip1.open(sound);
			sound = AudioSystem.getAudioInputStream(getClass().getResource("/music/2.wav"));
			clip2 = AudioSystem.getClip();
			clip2.open(sound);
			sound = AudioSystem.getAudioInputStream(getClass().getResource("/music/3.wav"));
			clip3 = AudioSystem.getClip();
			clip3.open(sound);
			sound = AudioSystem.getAudioInputStream(getClass().getResource("/music/4.wav"));
			clip4 = AudioSystem.getClip();
			clip4.open(sound);
			sound = AudioSystem.getAudioInputStream(getClass().getResource("/music/5.wav"));
			clip5 = AudioSystem.getClip();
			clip5.open(sound);
			sound = AudioSystem.getAudioInputStream(getClass().getResource("/music/6.wav"));
			clip6 = AudioSystem.getClip();
			clip6.open(sound);
			sound = AudioSystem.getAudioInputStream(getClass().getResource("/music/7.wav"));
			clip7 = AudioSystem.getClip();
			clip7.open(sound);
			sound = AudioSystem.getAudioInputStream(getClass().getResource("/music/win.wav"));
			win = AudioSystem.getClip();
			win.open(sound);
			sound = AudioSystem.getAudioInputStream(getClass().getResource("/music/lose.wav"));
			lose = AudioSystem.getClip();
			lose.open(sound);
		} catch (Exception e) {
			System.out.println("ERROR");
		}

		//Frame
		frame = new JFrame("Bomberman CN V1.0"); //JFrame title
		setPreferredSize(new Dimension(1200,800)); 
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setBackground (new Color(172,242,255));
		setLayout(null);

		//ImageIcon & Images import
		ImageIcon soundimg = new ImageIcon(getClass().getResource("/volume.png"));
		Image suitablesound = soundimg.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);;
		soundimg = new ImageIcon(suitablesound);
		images[0] = soundimg;
		ImageIcon musicimg = new ImageIcon(getClass().getResource("/music.png"));
		Image suitablemusic = musicimg.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);;
		musicimg = new ImageIcon(suitablemusic);
		images[1] = musicimg;
		ImageIcon nosoundimg = new ImageIcon(getClass().getResource("/no volume.png"));
		Image suitablenosound = nosoundimg.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);;
		nosoundimg = new ImageIcon(suitablenosound);
		images[2] = nosoundimg;
		ImageIcon nomusicimg = new ImageIcon(getClass().getResource("/no music.png"));
		Image suitablenomusic = nomusicimg.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);;
		nomusicimg = new ImageIcon(suitablenomusic);
		images[3] =	nomusicimg;
		ImageIcon ch1img = new ImageIcon(getClass().getResource("/character/ch1.png"));
		Image suitablech1 = ch1img.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;

		ch1img = new ImageIcon(suitablech1);
		images[4] =	ch1img;
		ImageIcon ch2img = new ImageIcon(getClass().getResource("/character/ch2.png"));
		Image suitablech2 = ch2img.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		ch2img = new ImageIcon(suitablech2);
		images[5] =	ch2img;
		ImageIcon ch3img = new ImageIcon(getClass().getResource("/character/ch3.png"));
		Image suitablech3 = ch3img.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		ch3img = new ImageIcon(suitablech3);
		images[6] =	ch3img;
		ImageIcon ch4img = new ImageIcon(getClass().getResource("/character/ch4.png"));
		Image suitablech4 = ch4img.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		ch4img = new ImageIcon(suitablech4);
		images[7] =	ch4img;
		ImageIcon arrow1img = new ImageIcon(getClass().getResource("/arrow1.png"));
		Image suitablearrow1 = arrow1img.getImage().getScaledInstance(90, 120, java.awt.Image.SCALE_SMOOTH);;
		arrow1img = new ImageIcon(suitablearrow1);
		images[8] =	arrow1img;
		ImageIcon arrow2img = new ImageIcon(getClass().getResource("/arrow2.png"));
		Image suitablearrow2 = arrow2img.getImage().getScaledInstance(90, 120, java.awt.Image.SCALE_SMOOTH);;
		arrow2img = new ImageIcon(suitablearrow2);
		images[9] =	arrow2img;
		ImageIcon keys = new ImageIcon(getClass().getResource("/keys.png"));
		Image suitablekeys = keys.getImage().getScaledInstance(891, 306, java.awt.Image.SCALE_SMOOTH);;
		keys = new ImageIcon(suitablekeys);
		images[10] = keys;
		ImageIcon mainscreen = new ImageIcon(getClass().getResource("/Main screen.png"));
		Image suitmainscreen = mainscreen.getImage().getScaledInstance(1000, 750, java.awt.Image.SCALE_SMOOTH);;
		mainscreen = new ImageIcon(suitmainscreen);
		images[11] = mainscreen;

		//Character Images import
		charimgs[0][0] = suitablech1;
		charimgs[1][0] = suitablech2;
		charimgs[2][0] = suitablech3;
		charimgs[3][0] = suitablech4;

		ImageIcon char1left = new ImageIcon(getClass().getResource("/character/ch1.2.png"));
		Image suitablechar1left = char1left.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		charimgs[0][1] = suitablechar1left;
		ImageIcon char1up = new ImageIcon(getClass().getResource("/character/ch1.3.png"));
		Image suitablechar1up = char1up.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		charimgs[0][2] = suitablechar1up;
		ImageIcon char1right = new ImageIcon(getClass().getResource("/character/ch1.4.png"));
		Image suitablechar1right = char1right.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		charimgs[0][3] = suitablechar1right;

		ImageIcon char2left = new ImageIcon(getClass().getResource("/character/ch2.2.png"));
		Image suitablechar2left = char2left.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		charimgs[1][1] = suitablechar2left;
		ImageIcon char2up = new ImageIcon(getClass().getResource("/character/ch2.3.png"));
		Image suitablechar2up = char2up.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		charimgs[1][2] = suitablechar2up;
		ImageIcon char2right = new ImageIcon(getClass().getResource("/character/ch2.4.png"));
		Image suitablechar2right = char2right.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		charimgs[1][3] = suitablechar2right;

		ImageIcon char3left = new ImageIcon(getClass().getResource("/character/ch3.2.png"));
		Image suitablechar3left = char3left.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		charimgs[2][1] = suitablechar3left;
		ImageIcon char3up = new ImageIcon(getClass().getResource("/character/ch3.3.png"));
		Image suitablechar3up = char3up.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		charimgs[2][2] = suitablechar3up;
		ImageIcon char3right = new ImageIcon(getClass().getResource("/character/ch3.4.png"));
		Image suitablechar3right = char3right.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		charimgs[2][3] = suitablechar3right;

		ImageIcon char4left = new ImageIcon(getClass().getResource("/character/ch4.2.png"));
		Image suitablechar4left = char4left.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		charimgs[3][1] = suitablechar4left;
		ImageIcon char4up = new ImageIcon(getClass().getResource("/character/ch4.3.png"));
		Image suitablechar4up = char4up.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		charimgs[3][2] = suitablechar4up;
		ImageIcon char4right = new ImageIcon(getClass().getResource("/character/ch4.4.png"));
		Image suitablechar4right = char4right.getImage().getScaledInstance(182, 200, java.awt.Image.SCALE_SMOOTH);;
		charimgs[3][3] = suitablechar4right;


		//------------------------------------------------------------------------------------------------
		ImageIcon pause = new ImageIcon(getClass().getResource("pause.png"));
		Image pauseimg = pause.getImage().getScaledInstance(100, 40, java.awt.Image.SCALE_SMOOTH);;
		imgs[0] = pauseimg;
		ImageIcon quit = new ImageIcon(getClass().getResource("quit.png"));	
		Image quitimg = quit.getImage().getScaledInstance(100, 40, java.awt.Image.SCALE_SMOOTH);;
		imgs[1] = quitimg;
		//Prop Images Import
		ImageIcon bombp1 = new ImageIcon(getClass().getResource("prop/bomb+1.png"));	
		Image bombp1img = bombp1.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);;
		propimgs[0] = bombp1img;
		props.add(new Prop(null, "Bomb +1", -1, -1, 0, 0));
		ImageIcon power = new ImageIcon(getClass().getResource("prop/power.png"));	
		Image powerimg = power.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);;
		propimgs[1] = powerimg;
		props.add(new Prop(null, "Power", -1, -1, 1, 0));
		ImageIcon shield = new ImageIcon(getClass().getResource("prop/shield.png"));	
		Image shieldimg = shield.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);;
		propimgs[2] = shieldimg;
		props.add(new Prop(null, "Shield", -1, -1, 2, 0));
		ImageIcon bomb = new ImageIcon(getClass().getResource("prop/bomb.png"));	
		Image bombimg = bomb.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);;
		propimgs[3] = bombimg;
		props.add(new Prop(null, "Bomb", -1, -1, 3, 0));
		ImageIcon speed = new ImageIcon(getClass().getResource("prop/speed.png"));	
		Image speedimg = speed.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);;
		propimgs[4] = speedimg;
		props.add(new Prop(null, "Speedy Shoes", -1, -1, 4, 0));
		ImageIcon life = new ImageIcon(getClass().getResource("prop/life.png"));	
		Image lifeimg = life.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);;
		propimgs[5] = lifeimg;
		props.add(new Prop(null, "Heart", -1, -1, 5, 0));
		ImageIcon minebomb = new ImageIcon(getClass().getResource("prop/minebomb.png"));	
		Image minebombimg = minebomb.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);;
		propimgs[6] = minebombimg;
		props.add(new Prop(null, "Mine Bomb", -1, -1, 6, 0));
		ImageIcon gas = new ImageIcon(getClass().getResource("prop/gas.png"));	
		Image gasimg = gas.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);;
		propimgs[7] = gasimg;
		props.add(new Prop(null, "Gas Tank", -1, -1, 7, 0));
		ImageIcon fog = new ImageIcon(getClass().getResource("prop/fog.png"));	
		Image fogimg = fog.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);;
		propimgs[8] = fogimg;
		props.add(new Prop(null, "Fog", -1, -1, 8, 0));
		ImageIcon mineset = new ImageIcon(getClass().getResource("prop/mineset.png"));
		Image minesetimg = mineset.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);;
		propimgs[9] = minesetimg;
		props.add(new Prop(null, "Mine Location", -1, -1, 9, 0));

		//Buttons
		start = new JButton ("Start");
		start.setFont(new Font("SansSerif", Font.PLAIN, 22));
		start.setBounds(950,170,150,50);
		start.setActionCommand ("Start");
		start.addActionListener(this);
		help = new JButton ("How to Play");
		help.setFont(new Font("SansSerif", Font.PLAIN, 20));
		help.setBounds(950,250,150,50);
		help.setActionCommand ("Help");
		help.addActionListener(this);
		prop = new JButton("Prop");
		prop.setFont(new Font("SansSerif", Font.PLAIN, 22));
		prop.setBounds(950,330,150,50);
		prop.setActionCommand ("Prop");
		prop.addActionListener(this);
		exit = new JButton ("Exit");
		exit.setFont(new Font("SansSerif", Font.PLAIN, 22));
		exit.setBounds(950,410,150,50);
		exit.setActionCommand ("Exit");
		exit.addActionListener(this);
		sound = new JButton(soundimg);
		sound.setActionCommand ("Sound");
		sound.addActionListener(this);
		sound.setBounds(920,550,80,80);
		music = new JButton(musicimg);
		music.setBounds(1020,550,80,80);
		music.setActionCommand ("Music");
		music.addActionListener(this);
		back = new JButton("Back");
		back.setActionCommand("Back");
		back.setFont(new Font("SansSerif", Font.PLAIN, 18));
		back.addActionListener(this);
		back.setBounds(1000,600,80,80);


		//Labels displaying character status when playing game
		char1life = new JLabel();
		char1bomb = new JLabel();
		char1power = new JLabel();
		char1speed = new JLabel();
		char1score = new JLabel();
		char2life = new JLabel();
		char2bomb = new JLabel();
		char2power = new JLabel();
		char2speed = new JLabel();
		char2score = new JLabel();

		//Main screen Image
		mainimage = new JLabel(images[11]);
		mainimage.setBounds(0,0,1000,750);

		//Rules
		title = new JLabel("Rules:");
		title.setFont(new Font("Serif", Font.PLAIN, 36));
		title.setBounds(100,50,title.getPreferredSize().width,title.getPreferredSize().height);
		rul1 = new JLabel("1. Move around the screen in empty spaces");
		rul1.setFont(new Font("Serif", Font.PLAIN, 24));
		rul2 = new JLabel("2. Use your bomb to destroy obstacles and enemies");
		rul2.setFont(new Font("Serif", Font.PLAIN, 24));
		rul3 = new JLabel("3. Pick up props to help you! (Visit our props page)");
		rul3.setFont(new Font("Serif", Font.PLAIN, 24));
		rul4 = new JLabel("4. Each game is 5 minutes, if time is up the player with a higher score wins");
		rul4.setFont(new Font("Serif", Font.PLAIN, 24));
		rul5 = new JLabel("5. The last one that stands wins!");
		rul5.setFont(new Font("Serif", Font.PLAIN, 24));
		rul1.setBounds(100,140,rul1.getPreferredSize().width,rul1.getPreferredSize().height);
		rul2.setBounds(100,220,rul2.getPreferredSize().width,rul2.getPreferredSize().height);
		rul3.setBounds(100,300,rul3.getPreferredSize().width,rul3.getPreferredSize().height);
		rul4.setBounds(100,380,rul4.getPreferredSize().width,rul4.getPreferredSize().height);
		rul5.setBounds(100,460,rul5.getPreferredSize().width,rul5.getPreferredSize().height);

		//Add items onto the starting frame
		add(sound);
		add(music);
		add(start);
		add(help);
		add(exit);
		add(prop);
		add(mainimage);
		add(title);
		add(rul1);
		add(rul2);
		add(rul3);
		add(rul4);
		add(rul5);
		add(back);
		title.setVisible(false);
		rul1.setVisible(false);
		rul2.setVisible(false);
		rul3.setVisible(false);
		rul4.setVisible(false);
		rul5.setVisible(false);
		back.setVisible(false);

		//Interface & Prepping to start
		addKeyListener(this);
		setFocusable(true);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		thread = new Thread(this);
		thread.start();
		mainmusic.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand (); //get command from ActionEvent e
		if (command.equals("Exit")) { //if clicked exit button
			System.exit(0); //exits
		} else if (command.equals("Help")) { //Help/How to play page
			screen = 0;
			start.setVisible(false);
			help.setVisible(false);
			exit.setVisible(false);
			sound.setVisible(false);
			music.setVisible(false);
			prop.setVisible(false);
			mainimage.setVisible(false);
			title.setVisible(true);
			rul1.setVisible(true);
			rul2.setVisible(true);
			rul3.setVisible(true);
			rul4.setVisible(true);
			rul5.setVisible(true);
			back.setVisible(true);
		

		} else if (command.equals("Start")) {
			start.setVisible(false);
			help.setVisible(false);
			exit.setVisible(false);
			sound.setVisible(false);
			music.setVisible(false);
			prop.setVisible(false);
			mainimage.setVisible(false);

			//Initiate Character to have 1 bomb and power of 1
			char1  = new Character(1,1);
			char2 = new Character(1,1);

			//Character select screen = 2
			screen = 2;
			instruction = new JLabel("Select your character");
			instruction.setFont(new Font("Serif",Font.BOLD,30));
			instruction.setBounds(460,10,instruction.getPreferredSize().width,instruction.getPreferredSize().height);

			//Four location for arrow heads
			recs[0] = new Rectangle(125,80,90,120);
			recs[1] = new Rectangle(365,80,90,120);
			recs[2] = new Rectangle(605,80,90,120);
			recs[3] = new Rectangle(835,80,90,120);
			arrow1 = new JLabel(images[8]);
			arrow1.setBounds(recs[0]);
			arrow2 = new JLabel(images[9]);
			arrow2.setBounds(recs[1]);
			charselects[0] = 0;
			charselects[1] = 1;

			//Display the 4 characters & Keys for players to press
			chlabel1 = new JLabel(images[4]);
			chlabel1.setBounds(80,210,182,200);
			chlabel2 = new JLabel(images[5]);
			chlabel2.setBounds(320,210,182,200);
			chlabel3 = new JLabel(images[6]);
			chlabel3.setBounds(560,210,182,200);
			chlabel4 = new JLabel(images[7]);
			chlabel4.setBounds(790,210,182,200);
			keyslabel = new JLabel(images[10]);
			keyslabel.setBounds(50,410,846,291);

			add(instruction);
			add(arrow1);
			add(arrow2);
			add(chlabel1);
			add(chlabel2);
			add(chlabel3);
			add(chlabel4);
			add(keyslabel);
			add(back);
			back.setVisible(true);

		} else if (command.equals("Back")) {

			//The back button is used throughout many frames
			backToMain();

		} else if (command.equals("Sound")) {

			soundswitch = !soundswitch; //Once clicked the soundswitch status changes
			//Switch image icon once clicked
			if (soundswitch == false) {
				sound.setVisible(false);
				remove(sound);
				sound = new JButton(images[2]);
				sound.setActionCommand ("Sound");
				sound.addActionListener(this);
				sound.setBounds(920,550,80,80);
				add(sound);
				sound.setVisible(true);
			} else if (soundswitch == true) {
				sound.setVisible(false);
				remove(sound);
				sound = new JButton(images[0]);
				sound.setActionCommand ("Sound");
				sound.addActionListener(this);
				sound.setBounds(920,550,80,80);
				add(sound);
				sound.setVisible(true);
			}

		} else if (command.equals("Music")) {

			musicswitch = !musicswitch; //Once clicked the musicswitch status changes
			//Switch image icon once clicked
			if(musicswitch == false) {
				mainmusic.stop();
			} else if (musicswitch == true){
				reset(mainmusic);
				mainmusic.loop(Clip.LOOP_CONTINUOUSLY);
			}
			if (musicswitch == false) {
				music.setVisible(false);
				remove(music);
				music = new JButton(images[3]);
				music.setBounds(1020,550,80,80);
				music.setActionCommand ("Music");
				music.addActionListener(this);
				add(this.music);
				music.setVisible(true);
			} else if (musicswitch == true) {
				music.setVisible(false);
				remove(music);
				music = new JButton(images[1]);
				music.setBounds(1020,550,80,80);
				music.setActionCommand ("Music");
				music.addActionListener(this);
				add(this.music);
				music.setVisible(true);
			}
		} else if (command.equals("Prop")) { //Prop Page

			//Prop Information Screen = 4
			screen = 4;
			start.setVisible(false);
			help.setVisible(false);
			exit.setVisible(false);
			sound.setVisible(false);
			music.setVisible(false);
			prop.setVisible(false);
			mainimage.setVisible(false);

			//Sort props using name to display in alphabetical order
			Collections.sort(props);
			for (int i = 0; i<props.size();i++) {
				if (i<5) {
					propimagelabels[i] = new JLabel(new ImageIcon(propimgs[props.get(i).getImageNo()]));
					propimagelabels[i].setBounds(100,(i+1)*120-100,100,100);
					proplabel[i] = new JLabel(props.get(i).getName());
					proplabel[i].setBounds(225,(i+1)*120-100,350,50);
					proplabel[i].setFont(new Font("SansSerif", Font.PLAIN, 18));
					propdescriptions[i] = new JLabel(props.get(i).getDescription());
					propdescriptions[i].setBounds(225,(i+1)*120-80,350,100);
					propdescriptions[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
					add(propimagelabels[i]);
					add(proplabel[i]);
					add(propdescriptions[i]);
				} else {
					propimagelabels[i] = new JLabel(new ImageIcon(propimgs[props.get(i).getImageNo()]));
					propimagelabels[i].setBounds(600,(i-4)*120-100,100,100);
					proplabel[i] = new JLabel(props.get(i).getName());
					proplabel[i].setBounds(725,(i-4)*120-100,350,50);
					proplabel[i].setFont(new Font("SansSerif", Font.PLAIN, 18));
					propdescriptions[i] = new JLabel(props.get(i).getDescription());
					propdescriptions[i].setBounds(725,(i-4)*120-80,350,100);
					propdescriptions[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
					add(propimagelabels[i]);
					add(proplabel[i]);
					add(propdescriptions[i]);
				}
			}
			back.setVisible(true);
		}

	}
	//Main method
	//Returns: void
	//This method imports the map from the local txt file and put into a hashMap
	public static void main(String[] args) throws IOException{
		try {
			BufferedReader inFile = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/maps.txt")));
			int numMaps = Integer.parseInt(inFile.readLine());
			for (int i = 0; i<numMaps; i++) {
				int[][] grid = new int[13][15];
				ArrayList<Rectangle> walls = new ArrayList<>();
				ArrayList<Rectangle> destructible = new ArrayList<>();
				for (int j = 0; j<13;j++) { //rows
					String[] stringrow = inFile.readLine().split(" ");
					for (int k = 0; k<15;k++) { //columns
						int x = k * 50;
						int y = j * 50;
						grid[j][k] = Integer.parseInt(stringrow[k]);
						if (grid[j][k] == 1) {
							walls.add(new Rectangle(x, y, 50, 50));
						} else if (grid[j][k] == 2) {
							walls.add(new Rectangle(x, y, 50, 50));
							destructible.add(new Rectangle(x, y, 50, 50));
						}
					}
				}
				String[] RGB = inFile.readLine().split(",");
				int R = Integer.parseInt(RGB[0]);
				int G = Integer.parseInt(RGB[1]);
				int B = Integer.parseInt(RGB[2]);
				gamemaps.put(i,new Map(grid,walls,destructible,new Color(R,G,B)));
			}

			inFile.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found. Please check for maps.txt in the assets folder");
		}

		int mapNo = (int)Math.floor(Math.random()*(gamemaps.size()-1+1)+1); //random map
		map = gamemaps.get(mapNo-1);

		new Main();
	}

	//Return: none, Parameter: none
	//From Runnable Interface, while running it will take care of time-related prop items (Bomb and Fog)
	public void run() {
		while(true) {
			//Check for every single prop in the map
			for (int i = 0; i < map.getProps().size(); i++) {
				int time = map.getProps().get(i).getTime();
				//If it's a bomb and time is not 0 and game is not paused
				if (map.getProps().get(i).getName().equals("bomb") && time!=0 && !gamepaused) {
					map.getProps().get(i).setTime(time-1); //Decrease Time
					//If fog and time is not 0 and game is not paused
				} else if (map.getProps().get(i).getName().equals("fog") && time!=0 && !gamepaused) {
					if (map.getProps().get(i).getAffectedChar()!=null && map.getProps().get(i).getTime()==480 && soundswitch) {
						try {
							AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/dizzy.wav"));
							Clip effect = AudioSystem.getClip();
							effect.open(audio);
							effect.start();
						} catch (Exception e1) {
							
						} 
					}
					map.getProps().get(i).setTime(time-1); //Decrease Time
					//If it's fog and time = 0, make fog disappear or restore character's speed
				} else if (map.getProps().get(i).getName().equals("fog") && time == 0) {
					if (map.getProps().get(i).getAffectedChar()!=null) {
						map.getProps().get(i).getAffectedChar().restoreSpeed();
					}
					map.getMap()[map.getProps().get(i).getRow()][map.getProps().get(i).getColumn()]=0;
					map.getProps().remove(i);
					//If it's bomb and time = 0
				} else if (map.getProps().get(i).getName().equals("bomb") && time==0) {
					if (soundswitch) {
						try {
							AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/bomb.wav"));
							Clip effect = AudioSystem.getClip();
							effect.open(audio);
							effect.start();
						} catch (Exception e1) {

						} 
					}
					int x1 = 0; //Start of vertical distance
					int y1 = 0; //Start of horizontal distance
					int x2 = 0; //End of vertical distance
					int y2 = 0; //End of horizontal distance
					Prop bomb = map.getProps().get(i);
					x1 = bomb.getRow();
					y1 = bomb.getColumn();
					x2 = bomb.getRow();
					y2 = bomb.getColumn();
					map.removeBlock(bomb.getChar(),(bomb.getRow()), (bomb.getColumn()));

					//damage map: limit to damage only one block at a time and not through WALLS
					//loop through each block in all 4 directions of the bomb (based on player's power)
					for (int j = 1; j<=bomb.getChar().getPower();j++){ //checking above (the bomb)
						if(bomb.getRow()-j>=0 && (map.getMap()[bomb.getRow()-j][bomb.getColumn()] == 5)) {
							map.removeBlock(bomb.getChar(),(bomb.getRow()-j), (bomb.getColumn()));
						}
						if(bomb.getRow()-j>=0 && (map.getMap()[bomb.getRow()-j][bomb.getColumn()] == 1)) {
							break;
						}
						if(bomb.getRow()-j>=0 && (map.getMap()[bomb.getRow()-j][bomb.getColumn()] == 2)) {
							map.removeBlock(bomb.getChar(),(bomb.getRow()-j), (bomb.getColumn()));
							x1--;
							break;
						}
						x1--;
					}
					for (int j = 1; j<=bomb.getChar().getPower();j++) { //checking below (the bomb)
						if(bomb.getRow()+j<13 && (map.getMap()[bomb.getRow()+j][bomb.getColumn()] == 5)) {
							map.removeBlock(bomb.getChar(),(bomb.getRow()+j), (bomb.getColumn()));
						}
						if(bomb.getRow()+j<13 && (map.getMap()[bomb.getRow()+j][bomb.getColumn()] == 1)) {
							break;
						}
						if(bomb.getRow()+j<13 && (map.getMap()[bomb.getRow()+j][bomb.getColumn()] == 2)) {
							map.removeBlock(bomb.getChar(),(bomb.getRow()+j), (bomb.getColumn()));
							x2++;
							break;
						}
						x2++;
					}
					for (int j = 1; j<=bomb.getChar().getPower();j++){ //checking left
						if(bomb.getColumn()-j>=0 && (map.getMap()[bomb.getRow()][bomb.getColumn()-j] == 5)) {
							map.removeBlock(bomb.getChar(),(bomb.getRow()), (bomb.getColumn()-j));
						}
						if(bomb.getColumn()-j>=0 && (map.getMap()[bomb.getRow()][bomb.getColumn()-j] == 1)) {
							break;
						}
						if(bomb.getColumn()-j>=0 && (map.getMap()[bomb.getRow()][bomb.getColumn()-j] == 2)) {
							map.removeBlock(bomb.getChar(),(bomb.getRow()), (bomb.getColumn()-j));
							y1--;
							break;
						}
						y1--;
					}
					for (int j = 1; j<=bomb.getChar().getPower();j++){ //checking right
						if(bomb.getColumn()+j<15 && (map.getMap()[bomb.getRow()][bomb.getColumn()+j] == 5)) {
							map.removeBlock(bomb.getChar(),(bomb.getRow()), (bomb.getColumn()+j));
						}
						if(bomb.getColumn()+j<15 && (map.getMap()[bomb.getRow()][bomb.getColumn()+j] == 1)) {
							break;
						}
						if(bomb.getColumn()+j<15 && (map.getMap()[bomb.getRow()][bomb.getColumn()+j] == 2)) {
							y2++;
							map.removeBlock(bomb.getChar(),(bomb.getRow()), (bomb.getColumn()+j));
							break;
						}
						y2++;
					}
					if (x1 < 0) {
						x1 = 0;
					}
					if (x2 >12) {
						x2 = 12;
					}
					if (y1 < 0) {
						y1 = 0;
					}
					if (y2 >14) {
						y2=14;
					}
					//Calculate horizontal damage using starting to ending blocks horizontally and vertically
					Rectangle horizontaldamage = new Rectangle(y1*50,bomb.getRow()*50,(y2-y1+1)*50,50);
					Rectangle verticaldamage = new Rectangle(bomb.getColumn()*50,x1*50,50,(x2-x1+1)*50);
					damagerecs.put(horizontaldamage,30);
					damagerecs.put(verticaldamage,30);

					map.getProps().remove(i); //Remove the current bomb
					map.getBombWalls().remove(new Rectangle(bomb.getColumn()*50,bomb.getRow()*50,50,50)); //Remove this bomb from bombwalls so players can go through
					if (bomb.getChar()==char1) {
						char1bombson--; //The amount of bombs by char1 decreases (since the bomb is now removed)
					} else {
						char2bombson--; //The amount of bombs by char2 decreases (since the bomb is now removed)
					}

					//Check if there is a winner
					int winner = 0;
					Set<Rectangle> damages = new HashSet<Rectangle>(damagerecs.keySet());
					Iterator<Rectangle> iter = damages.iterator(); //Iterate through all damage rectangles
					while (iter.hasNext()) {
						Rectangle cur = iter.next();
						//the damage rectangles will hurt the character for 5 frames only
						if(damagerecs.get(cur)!=null &&damagerecs.get(cur)>25) { //If the current time for damage to display is more than 25
							winner = checkDamageCollision(cur); //Check if characters intersect with the rectangles
							if (winner != 0) { //If winner is 0 no winner is determined/players are protected by a shield
								repaint();
								if (winner==1) { //If winner is 1 means player 2 lost a life
									char2.loseLife();
									if (soundswitch) {
										try {
											AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/loselife.wav"));
											Clip effect = AudioSystem.getClip();
											effect.open(audio);
											effect.start();
										} catch (Exception e1) {

										} 
									}
								} else if (winner==2) { //If winner is 2 means player 1 lost a life
									char1.loseLife();
									if (soundswitch) {
										try {
											AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/loselife.wav"));
											Clip effect = AudioSystem.getClip();
											effect.open(audio);
											effect.start();
										} catch (Exception e1) {

										} 
									}
								} else if (winner==3) { //If winner is 3 means both players lost a life
									char1.loseLife();
									char2.loseLife();
									if (soundswitch) {
										try {
											AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/loselife.wav"));
											Clip effect = AudioSystem.getClip();
											effect.open(audio);
											effect.start();
										} catch (Exception e1) {

										} 
									}
								}
								//Detect if a winner is determined with 0 life or if it's a tie
								if (char1.getLives()==0 && char2.getLives()==0) {
									statusupdate();
									JOptionPane.showMessageDialog(this,"Game Over!");
									winnerPage(3);
								} else if (char1.getLives()==0) {
									statusupdate();
									JOptionPane.showMessageDialog(this,"Game Over!");
									winnerPage(2);

								} else if (char2.getLives()==0) {
									statusupdate();
									JOptionPane.showMessageDialog(this,"Game Over!");
									winnerPage(1);
								}
							}
						}
					}
				}
			}
			update();
			this.repaint(); //repaint after all that happened
			try {
				Thread.sleep(1000/FPS);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	//Return: none, Parameter: None
	//In this method, checks for various collisions for the characters
	public void update(){
		if (rect1.x+41 == rect2.x && rect1.y - rect2.y < 45 && rect2.y - rect1.y < 45) {
			move(false,true,false,false);
		} else if (rect2.x +41 == rect1.x && rect1.y - rect2.y <= 45 && rect2.y - rect1.y < 45) {
			move(true,false,false,false);
		} else if (rect1.y +45 == rect2.y && rect2.x - rect1.x < 45 && rect1.x-rect2.x < 45) {
			move(false,false,false,true);
		} else if (rect2.y +45 == rect1.y && rect2.x - rect1.x < 45 && rect1.x-rect2.x < 45) {
			move(false,false,true,false);
		} else {
			move(false,false,false,false);
		}
		keepInBound();
		if (screen == 1 && !gamepaused) {
			statusupdate(); //status update every frame if game is not paused
		}
		for (int i = 0; i < map.getProps().size();i++) { //If a mineset that is hidden temporarily and not removed, remove it
			if (map.getProps().get(i).getImageNo() == 10 && map.getProps().get(i).getName().equals("mineset")) {
				map.getProps().remove(i); //This is to arrive an index change and looping
			}
		}
		for(int i = 0; i < map.getWalls().size(); i++) { //check for walls (black)
			checkCollision(map.getWalls().get(i));
		}
		checkCollision(rect1);
		checkCollision(rect2);
		for(int j = 0; j < map.getBombLocs().size(); j++) {
			//check for bomb locations to see if players who set the bomb have left the location they set for the bomb
			Set<Rectangle> bombs = new HashSet<Rectangle>(map.getBombLocs().keySet());
			Iterator<Rectangle> iter = bombs.iterator();
			while (iter.hasNext()) {
				Rectangle cur = iter.next();
				boolean setting = checkBombCollision(cur,map.getBombLocs().get(cur));
				if (setting==false) { //If they are no longer in the bomb square, add it as a wall and remove it as a bomb location
					map.getBombWalls().add(cur);
					map.getBombLocs().remove(cur);
				}
			}
		}
		for(int k = 0; k < map.getBombWalls().size(); k++) { //check for bomb walls (just like walls players cannot go through them)
			checkCollision(map.getBombWalls().get(k));
		}
		for(int l = 0; l < map.getProps().size(); l++) {
			if (!map.getProps().get(l).getName().equals("bomb")) {
				checkPropCollision(new Rectangle(map.getProps().get(l).getColumn()*50,map.getProps().get(l).getRow()*50,50,50));
			}
		}
	}

	//Overriden PaintComponent
	//Return: None, Parameter: a Graphics g
	//This method paints the entire game and majority of the images, characters, props, damage area, when in game (screen == 1)
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (screen==1 && !gamepaused) {
			//Draw the character images and the character status boxes
			g.drawImage (imgs[0],850,600,100,40,this);
			g.drawImage (imgs[1],970,600,100,40,this);
			g.setColor(Color.WHITE);
			g.fillRect(800,20,380,210);
			g.fillRect(800,250,380,210);
			g.drawImage(charimgs[char1.getCharNum()][0],800,22,182,200,this);
			g.drawImage(charimgs[char2.getCharNum()][0],800,252,182,200,this);
			g.drawImage(propimgs[5],950,25,30,30,this);//life
			g.drawImage(propimgs[3],950,65,30,30,this);//numbomb
			g.drawImage(propimgs[1],950,105,30,30,this);//bombpower
			g.drawImage(propimgs[4],950,145,30,30,this);//speed
			g.drawImage(propimgs[5],950,255,30,30,this);//life
			g.drawImage(propimgs[3],950,295,30,30,this);//numbomb
			g.drawImage(propimgs[1],950,335,30,30,this);//bombpower
			g.drawImage(propimgs[4],950,375,30,30,this);//speed
			g.drawImage(propimgs[char1.getCurrentTool()],1050,100,80,80,this);
			g.drawImage(propimgs[char2.getCurrentTool()],1050,330,80,80,this);
			if(char1.getShield()) {
				g.drawImage(propimgs[2],950,185,30,30,this);//speed?
			}
			if(char2.getShield()) {
				g.drawImage(propimgs[2],950,415,30,30,this);//speed?
			}

			//Fill the map with the map color
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(map.getMapColor());
			g2.fillRect(0, 0, screenWidth, screenHeight);

			Graphics2D g4 = (Graphics2D) g;

			//Draw blocks
			g2.setColor(Color.BLACK);
			for(int i = 0; i<map.getWalls().size();i++) {
				g2.fill(map.getWalls().get(i));
			}
			//Draw destructible blocks
			Graphics2D g3 = (Graphics2D) g;
			g3.setColor(Color.GRAY);
			for (int j = 0; j<map.getDestructible().size();j++) {
				g3.fill(map.getDestructible().get(j));
			}

			//Draw damage area (base colour)
			Set<Rectangle> damages1 = new HashSet<Rectangle>(damagerecs.keySet());
			Iterator<Rectangle> iter1 = damages1.iterator();
			while (iter1.hasNext()) {
				g2.setColor(new Color(255,111,0));
				g2.fill(iter1.next());
			}
			//Draw damage area (center)
			Set<Rectangle> damages = new HashSet<Rectangle>(damagerecs.keySet());
			Iterator<Rectangle> iter = damages.iterator();
			while (iter.hasNext()) {
				Rectangle cur = iter.next();
				if (damagerecs.get(cur)==30) {
					checkBombCollision(cur,null);
					damagerecs.put(cur, damagerecs.get(cur)-1);
				} else if (damagerecs.get(cur)==0){
					damagerecs.remove(cur);
				} else {
					g4.setColor(Color.WHITE);
					Rectangle re1;
					Rectangle re2;
					if (cur.getWidth()>cur.getHeight()) {
						re1 = new Rectangle(((int)cur.getX())+5,(int)cur.getY()+5,(int)cur.getWidth()-10,(int)cur.getHeight()-10);
						damagerecs.put(cur, damagerecs.get(cur)-1);
						g4.fill(re1);
					} else if (cur.getWidth()<cur.getHeight()){
						re1 = new Rectangle(((int)cur.getX())+5,(int)cur.getY()+5,(int)cur.getWidth()-10,(int)cur.getHeight()-10);
						damagerecs.put(cur, damagerecs.get(cur)-1);
						g4.fill(re1);
					} else {
						re1 = new Rectangle(((int)cur.getX()),(int)cur.getY()+5,(int)cur.getWidth(),(int)cur.getHeight()-10);
						re2 = new Rectangle(((int)cur.getX())+5,(int)cur.getY(),(int)cur.getWidth()-10,(int)cur.getHeight());
						damagerecs.put(cur, damagerecs.get(cur)-1);
						g4.fill(re1);
						g4.fill(re2);
					}
				}
			}
			//Draw current character location
			g2.drawImage(charimgs[char1.getCharNum()][char1imageno], (int)rect1.getX(),(int)rect1.getY(),41,45,this);
			g3.drawImage(charimgs[char2.getCharNum()][char2imageno], (int)rect2.getX(),(int)rect2.getY(),41,45,this);

			//Draw props (always on top)
			if (map.getProps().size()!=0) {
				for(int i = 0; i < map.getProps().size();i++) {
					g.drawImage(propimgs[map.getProps().get(i).getImageNo()], map.getProps().get(i).getColumn()*50,map.getProps().get(i).getRow()*50,50,50,this);
				}
			}
		}

	}

	//Return: none, Parameter: none
	//Moves the current character location (runs every time the update method runs)
	void move(boolean Lrestrictions, boolean Rrestrictions, boolean Urestrictions, boolean Drestrictions) {
		if (!Lrestrictions && !Rrestrictions && !Urestrictions && !Drestrictions) {
			if(left1)
				rect1.x -= char1.getSpeed();
			else if(right1)
				rect1.x += char1.getSpeed();
			if(up1)
				rect1.y += -char1.getSpeed();
			else if(down1)
				rect1.y += char1.getSpeed();

			if(left2)
				rect2.x -= char2.getSpeed();
			else if(right2)
				rect2.x += char2.getSpeed();
			if(up2)
				rect2.y += -char2.getSpeed();
			else if(down2)
				rect2.y += char2.getSpeed();
		} else if (Lrestrictions) {
			if(right1)
				rect1.x += char1.getSpeed();
			if(up1)
				rect1.y += -char1.getSpeed();
			else if(down1)
				rect1.y += char1.getSpeed();
			if(left2)
				rect2.x -= char2.getSpeed();
			if(up2)
				rect2.y += -char2.getSpeed();
			else if(down2)
				rect2.y += char2.getSpeed();
		} else if (Rrestrictions) {
			if(left1)
				rect1.x -= char1.getSpeed();
			if(up1)
				rect1.y += -char1.getSpeed();
			else if(down1)
				rect1.y += char1.getSpeed();

			if(right2)
				rect2.x += char2.getSpeed();
			if(up2)
				rect2.y += -char2.getSpeed();
			else if(down2)
				rect2.y += char2.getSpeed();
		} else if (Urestrictions) {
			if(left1)
				rect1.x -= char1.getSpeed();
			else if(right1)
				rect1.x += char1.getSpeed();
			if(down1)
				rect1.y += char1.getSpeed();

			if(left2)
				rect2.x -= char2.getSpeed();
			else if(right2)
				rect2.x += char2.getSpeed();
			if(up2)
				rect2.y += -char2.getSpeed();
		} else if (Drestrictions) {
			if(left1)
				rect1.x -= char1.getSpeed();
			else if(right1)
				rect1.x += char1.getSpeed();
			if(up1)
				rect1.y += -char1.getSpeed();
			if(left2)
				rect2.x -= char2.getSpeed();
			else if(right2)
				rect2.x += char2.getSpeed();
			if(down2)
				rect2.y += char2.getSpeed();
		}
	}

	//Keep characters in bound for edges
	//Return: None, Parameter: None
	void keepInBound() {
		//add something here so rect1 and rect2 can't go into blocks or edges and be readjusted.
		if(!rect1.intersects(rect2)) { //if they're not intersecting
			if(rect1.x < 0)
				rect1.x = 0;
			else if(rect1.x > screenWidth - rect1.width)
				rect1.x = screenWidth - rect1.width;

			if(rect1.y < 0)
				rect1.y = 0;
			else if(rect1.y > screenHeight - rect1.height)
				rect1.y = screenHeight - rect1.height;

			if(rect2.x < 0)
				rect2.x = 0;
			else if(rect2.x > screenWidth - rect2.width)
				rect2.x = screenWidth - rect2.width;

			if(rect2.y < 0)
				rect2.y = 0;
			else if(rect2.y > screenHeight - rect2.height)
				rect2.y = screenHeight - rect2.height;
		} else {
			if (rect1.x>rect2.x && rect2.x==0) { //if rect1 is on the right of rect 2, pushing left
				rect2.x=0;
				rect1.x= rect2.x+41;
			}
			if (rect1.x<rect2.x && rect2.x==screenWidth-rect1.width) { //if rect1 is on the left of rect2, pushing right
				rect2.x = screenWidth-rect2.width;
				rect1.x = screenWidth-rect2.width-41;
			}
			if (rect2.y<rect1.y && rect2.y==0) { //if rect1 is below rect2, pushing up
				rect2.y=0;
				rect1.y=45; //downleft
			}
			if (rect1.y<rect2.y && rect2.y==screenHeight-rect2.height) { //if rect1 is above rect2 pushing down
				rect2.y=screenHeight-rect2.height;
				rect1.y=screenHeight-rect2.height-45;
			}
		}
	}

	//Check Collision (with the walls)
	//Return: none, Parameter: none
	private void checkCollision(Rectangle wall) {
		if(rect1!=wall && rect1.intersects(wall)) {
			//stop the rect1 from moving
			double left1 = rect1.getX();
			double right1 = rect1.getX() + rect1.getWidth();
			double top1 = rect1.getY();
			double bottom1 = rect1.getY() + rect1.getHeight();
			double left2 = wall.getX();
			double right2 = wall.getX() + wall.getWidth();
			double top2 = wall.getY();
			double bottom2 = wall.getY() + wall.getHeight();

			if(right1 > left2 && 
					left1 < left2 && 
					right1 - left2 < bottom1 - top2 && 
					right1 - left2 < bottom2 - top1)
			{
				//rect1 collides from left side of the wall
				rect1.x = wall.x - rect1.width;
			}
			else if(left1 < right2 &&
					right1 > right2 && 
					right2 - left1 < bottom1 - top2 && 
					right2 - left1 < bottom2 - top1)
			{
				//rect1 collides from right side of the wall
				rect1.x = wall.x + wall.width;
			}
			else if(bottom1 > top2 && top1 < top2)
			{
				//rect1 collides from top side of the wall
				rect1.y = wall.y - rect1.height;
			}
			else if(top1 < bottom2 && bottom1 > bottom2)
			{
				//rect1 collides from bottom side of the wall
				rect1.y = wall.y + wall.height;
			}
		}

		if (rect2!=wall && rect2.intersects(wall)) {
			//stop the rect2 from moving
			double left1 = rect2.getX();
			double right1 = rect2.getX() + rect2.getWidth();
			double top1 = rect2.getY();
			double bottom1 = rect2.getY() + rect2.getHeight();
			double left2 = wall.getX();
			double right2 = wall.getX() + wall.getWidth();
			double top2 = wall.getY();
			double bottom2 = wall.getY() + wall.getHeight();

			if(right1 > left2 && 
					left1 < left2 && 
					right1 - left2 < bottom1 - top2 && 
					right1 - left2 < bottom2 - top1)
			{
				//rect2 collides from left side of the wall
				rect2.x = wall.x - rect2.width;
			}
			else if(left1 < right2 &&
					right1 > right2 && 
					right2 - left1 < bottom1 - top2 && 
					right2 - left1 < bottom2 - top1)
			{
				//rect2 collides from right side of the wall
				rect2.x = wall.x + wall.width;
			}
			else if(bottom1 > top2 && top1 < top2)
			{
				//rect2 collides from top side of the wall
				rect2.y = wall.y - rect2.height;
			}
			else if(top1 < bottom2 && bottom1 > bottom2)
			{
				//rect2 collides from bottom side of the wall
				rect2.y = wall.y + wall.height;
			}
		}
		/*
		if ((int)rect1.x == (int)rect2.getMaxX()) {
			//System.out.println("Player 1's X is at Player 2's Max X");
			int numx = rect2.x;
			rect1.x = numx+41;
			rect2.x = numx;
		}
		if ((int)rect1.y == (int)rect2.getMaxY()) {
			int numy = rect2.y;
			rect1.y = numy+45;
			rect2.y = numy;
			//System.out.println("Player 1's Y is at Player 2's Max Y");
		}
		if ((int) rect1.getMaxX() == (int)rect2.x) {
			int numx = rect2.x;
			rect1.x = numx-41;
			rect2.x = numx;
			//System.out.println("Player 1's Max X is at Player 2's X");
		}
		if ((int) rect1.getMaxY() == (int)rect2.y) {
			int numy = rect2.y;
			rect1.y = numy-45;
			rect2.y = numy;
			//System.out.println("Player 1's Max Y is at Player 2's Y");
		} THIS PART IS CURRENTLY COMPLETELY USELESS BECAUSE THERES LOTS OF OCCASIONS WHERE CHARACTERS TOUCH IN X AND Y VALUES
		 */ 

	}

	//Checks if character touches a prop and performs actions
	//Return none, Parameter: Rectangle dimensions of the wall
	private void checkPropCollision(Rectangle wall) {

		if(rect1.intersects(wall)) {
			try {
			//Locate the prop
			int x = (int) (wall.getY()/50); //vertical distance / row
			int y = (int) (wall.getX()/50); //horizontal distance / column
			int index = map.getProps().indexOf(new Prop(null,"",x,y,0,0));
			Prop p = map.getProps().get(index);
			//Perform the action
			boolean effects = p.performAction(char1);
			//Special actions for characters that touch the mines and fog props 
			if (!p.getName().equals("mineset") && !p.getName().equals("fog")) {
				map.getProps().remove(index);
				map.getMap()[x][y] = 0;
			} else if (p.getName().equals("mineset")){
				if (effects) {
					map.getProps().remove(index);
					map.getMap()[x][y] = 0;
				}
			} else if (p.getName().equals("fog")) {
				if (p.getAffectedChar() != null) {
					if (effects) {
						map.getProps().get(index).setImageNo(10);
						map.getMap()[x][y] = 0;
					}
				}
			}

			//Since a mine set can decrease a characters' life, check if anyone has died, and display status first
			statusupdate();
			if (char1.getLives()==0 && char2.getLives()==0) {
				JOptionPane.showMessageDialog(this,"Game Over!");
				winnerPage(3); //Died at the same time, tie = 3

			} else if (char1.getLives()==0) {
				JOptionPane.showMessageDialog(this,"Player 1 stepped on a mine!");
				winnerPage(2); //player 2 wins

			} else if (char2.getLives()==0) {
				JOptionPane.showMessageDialog(this,"Player 2 stepped on a mine!");
				winnerPage(1); //player 1 wins
			}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,"Game Over!");
				statusupdate();
				if (char1.getLives()==0 && char2.getLives()==0) {
					JOptionPane.showMessageDialog(this,"Game Over!");
					winnerPage(3); //Died at the same time, tie = 3

				} else if (char1.getLives()==0) {
					JOptionPane.showMessageDialog(this,"Player 1 stepped on a mine!");
					winnerPage(2); //player 2 wins

				} else if (char2.getLives()==0) {
					JOptionPane.showMessageDialog(this,"Player 2 stepped on a mine!");
					winnerPage(1); //player 1 wins
				}
			}
		}

		//The exact same things happen for character 2
		if (rect2.intersects(wall)) {
			try {
			int x = (int) (wall.getY()/50); //vertical distance / row
			int y = (int) (wall.getX()/50); //horizontal distance / column
			int index = map.getProps().indexOf(new Prop(null,"",x,y,0,0));
			Prop p = map.getProps().get(index);
			boolean effects = p.performAction(char2);
			statusupdate();
				if (!p.getName().equals("mineset") && !p.getName().equals("fog")) {
					map.getProps().remove(index);
					map.getMap()[x][y] = 0;
				} else if (p.getName().equals("mineset")){
					if (effects) {
						map.getProps().remove(index);
						map.getMap()[x][y] = 0;
					}
				} else if (p.getName().equals("fog")) {
					if (p.getAffectedChar() != null) {
						if (effects) {
							map.getProps().get(index).setImageNo(10);
							map.getMap()[x][y] = 0;
						}
					}
				}
				statusupdate();
				if (char1.getLives()==0 && char2.getLives()==0) {
					JOptionPane.showMessageDialog(this,"Game Over!");
					winnerPage(3); //Died at the same time, tie = 3

				} else if (char1.getLives()==0) {
					JOptionPane.showMessageDialog(this,"Player 1 stepped on a mine!");
					winnerPage(2); //player 2 wins

				} else if (char2.getLives()==0) {
					JOptionPane.showMessageDialog(this,"Player 2 stepped on a mine!");
					winnerPage(1); //player 1 wins
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,"Game Over!");
				statusupdate();
				if (char1.getLives()==0 && char2.getLives()==0) {
					JOptionPane.showMessageDialog(this,"Game Over!");
					winnerPage(3); //Died at the same time, tie = 3

				} else if (char1.getLives()==0) {
					JOptionPane.showMessageDialog(this,"Player 1 stepped on a mine!");
					winnerPage(2); //player 2 wins

				} else if (char2.getLives()==0) {
					JOptionPane.showMessageDialog(this,"Player 2 stepped on a mine!");
					winnerPage(1); //player 1 wins
				}
			}
		}
	}

	//Checks if the player is colliding with the bomb / if a damage area is colliding with the bomb
	//Returns: boolean if they are colliding, parameter: takes in the damage rectangles of the bomb and the character that is colliding
	private boolean checkBombCollision(Rectangle wall, Character character) {

		if (character==null) { //if character is null that indicates we are checking for damage rectangles
			//Check for bombs within the damage range and if it's within, it will explode in 1 frame (from paintcomponent)
			Set<Rectangle> bombs = new HashSet<Rectangle>(map.getBombLocs().keySet());
			Iterator<Rectangle> iter = bombs.iterator(); //Iterate through all damage rectangles
			while (iter.hasNext()) {
				Rectangle bombloc = iter.next();
				if (wall.intersects(new Rectangle(((int)bombloc.getX()+5),(int)bombloc.getY()+5,45,45))) {
					int index = map.getProps().indexOf(new Prop(null,"",((int)bombloc.getY()/50),((int)bombloc.getX()/50),0,0));
					map.getProps().get(index).setTime(1);
				}
			}
			for (int i = 0; i < map.getBombWalls().size(); i++) {
				if (wall.intersects(new Rectangle(((int)map.getBombWalls().get(i).getX()+5),(int)map.getBombWalls().get(i).getY()+5,45,45))) {
					int index = map.getProps().indexOf(new Prop(null,"",((int)map.getBombWalls().get(i).getY()/50),((int)map.getBombWalls().get(i).getX()/50),0,0));
					map.getProps().get(index).setTime(1);
				}
			}
			
		} else {
			//Check if the player who set a bomb has left the bomb area
			if (character.equals(char1) && rect1.intersects(wall)) {
				return true;
			} else if (character.equals(char2) && rect2.intersects(wall)){
				return true;
			}
		}
		return false;
	}

	//Checks if the player is within the rectangle dimension of the bomb damage area
	//Return: the character that wins/survives, Parameter: takes in the rectangle dimension of the damage area
	private int checkDamageCollision(Rectangle wall) {

		//If the area includes both characters
		if (wall.intersects(rect1) && wall.intersects(rect2)) {
			//Check if player has shield first
			if (char1.getShield() && !char2.getShield()) { //If char1 has shield and char2 doesn't
				char1.setShield(false); //char1 only loses the shield
				if (soundswitch) {
					try {
						AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/shieldbreak.wav"));
						Clip effect = AudioSystem.getClip();
						effect.open(audio);
						effect.start();
					} catch (Exception e1) {

					} 
				}
				return 1; //returns who is the winner
			} else if (char2.getShield() && !char1.getShield()) { //If char2 has shield and char1 doesn't
				char2.setShield(false); //char2 only loses shield
				if (soundswitch) {
					try {
						AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/shieldbreak.wav"));
						Clip effect = AudioSystem.getClip();
						effect.open(audio);
						effect.start();
					} catch (Exception e1) {

					} 
				}
				return 2; //char2 wins
			} else if (char2.getShield() && char1.getShield()){ //If both has shield
				char1.setShield(false);
				char2.setShield(false);
				if (soundswitch) {
					try {
						AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/shieldbreak.wav"));
						Clip effect = AudioSystem.getClip();
						effect.open(audio);
						effect.start();
					} catch (Exception e1) {

					} 
				}
				return 0; //no one wins yet
			} else {
				return 3; //it's a tie and both chars lose a life
			}
		} else if (wall.intersects(rect1)) { //If area includes character 1 only
			if (char1.getShield()) {
				if (soundswitch) {
					try {
						AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/shieldbreak.wav"));
						Clip effect = AudioSystem.getClip();
						effect.open(audio);
						effect.start();
					} catch (Exception e1) {

					} 
				}
				char1.setShield(false);
				return 0;
			} else {
				return 2;
			}
		} else if (wall.intersects(rect2)) { //If area includes character 2 only
			if (char2.getShield()) {
				if (soundswitch) {
					try {
						AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/shieldbreak.wav"));
						Clip effect = AudioSystem.getClip();
						effect.open(audio);
						effect.start();
					} catch (Exception e1) {

					} 
				}
				char2.setShield(false);
				return 0;
			} else {
				return 1;
			}
		} else {
			return 0;
		}
	}

	public void keyTyped(KeyEvent e) {

	}

	//If pressing the key, actions are different for different screen
	//Return: none, Parameter:tTakes in the KeyEvent (key that was pressed) 
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		//If we are in character selection screen
		if (screen == 2) {
			if(key == KeyEvent.VK_SPACE) { //Space means selected/lock in for character 1
				char1selected = true;
				char1.setCharNum(charselects[0]);
				if(char1selected && char2selected) { //If now both characters have selected, start game
					startGame();
				}
			} 
			if(key == KeyEvent.VK_ENTER) { //Enter means selected/lock in for character 2
				char2selected = true;
				char2.setCharNum(charselects[1]);
				if(char1selected==true && char2selected==true) { //If now both characters have selected, start game
					startGame();
				}
			}

			//If character 1 has not selected and pressed A (left)
			if((!char1selected) && key == KeyEvent.VK_A) {
				if (charselects[0] != 0) { //if char1 is not at the left edge
					charselects[0]--;
					arrow1.setBounds(recs[charselects[0]]);
				}
				if (charselects[1]!=0 && charselects[0] == charselects[1]) { //if char2 is not at the edge and 1 overlap
					charselects[0]--;//I go left again
					arrow1.setBounds(recs[charselects[0]]);
				} else if (charselects[1]==0 && charselects[0] == charselects[1]) {//if char2 is at the edge and 1 go left
					charselects[0]++; //I don't change location
					arrow1.setBounds(recs[charselects[0]]);
				}
				//If character 1 has not selected and pressed D (right)	
			} else if ((!char1selected) && key == KeyEvent.VK_D) {
				if (charselects[0] != 3) { //if char1 is not at the right edge
					charselects[0]++;
					arrow1.setBounds(recs[charselects[0]]);
				}
				if (charselects[1]!=3 && charselects[0] == charselects[1]) { //if char2 is not at the edge and I overlap
					charselects[0] ++;//I go right again
					arrow1.setBounds(recs[charselects[0]]);
				} else if (charselects[1]==3 && charselects[0] == charselects[1]) {
					charselects[0]--;//I don't change location
					arrow1.setBounds(recs[charselects[0]]);
				}
			}
			//If character 2 has not selected and pressed left	
			if((!char2selected) && key == KeyEvent.VK_LEFT) {
				if (charselects[1] != 0) { //if char2 is not at the left edge
					charselects[1]--;
					arrow2.setBounds(recs[charselects[1]]);
				}
				if (charselects[0]!=0 && charselects[0] == charselects[1]) {//if char1 is not at the edge and 2 overlap
					charselects[1]--;//I go left again
					arrow2.setBounds(recs[charselects[1]]);
				} else if (charselects[0]==0 && charselects[0] == charselects[1]) {//if char1 is at the edge and 2 go left
					charselects[1]++; //I don't change location
					arrow2.setBounds(recs[charselects[1]]);
				}
				//If character 2 has not selected and pressed right	
			} else if ((!char2selected) && key == KeyEvent.VK_RIGHT) {
				if (charselects[1] != 3) { //if char2 is not at the right edge
					charselects[1]++;
					arrow2.setBounds(recs[charselects[1]]);
				}
				if (charselects[0]!=3 && charselects[0] == charselects[1]) { //if char1 is not at the edge and I overlap
					charselects[1] ++;//I go right again
					arrow2.setBounds(recs[charselects[1]]);
				} else if (charselects[0]==3 && charselects[0] == charselects[1]) {//if char1 is at the edge and I go right
					charselects[1]--;//I don't change location
					arrow2.setBounds(recs[charselects[1]]);
				}
			}

			//If we are in the game screen
		} else if (screen == 1) {
			if(key == KeyEvent.VK_A) {
				char1imageno = 1;
				left1 = true;
				right1 = false;
			}else if(key == KeyEvent.VK_D) {
				char1imageno = 3;
				right1 = true;
				left1 = false;
			}else if(key == KeyEvent.VK_W) {
				char1imageno = 2;
				up1 = true;
				down1 = false;
			}else if(key == KeyEvent.VK_S) {
				char1imageno = 0;
				down1 = true;
				up1 = false;
			}
			if(key == KeyEvent.VK_LEFT) {
				char2imageno = 1;
				left2 = true;
				right2 = false;
			}else if(key == KeyEvent.VK_RIGHT) {
				char2imageno = 3;
				right2 = true;
				left2 = false;
			}else if(key == KeyEvent.VK_UP) {
				char2imageno = 2;
				up2 = true;
				down2 = false;
			}else if(key == KeyEvent.VK_DOWN) {
				char2imageno = 0;
				down2 = true;
				up2 = false;
			}

			//If player pressed space: char1 set
			if(key == KeyEvent.VK_SPACE) {

				//Locate the block
				int x = ((rect1.x)+26)/50; //column
				int y = ((rect1.y)+26)/50; //row
				Prop p1 = null;
				if (char1.currenttool.equals("bomb")) {
					//Detect if a bomb can be placed
					if (char1bombson<char1.getNumBombs()) {
						p1 = char1.setProp(map,y,x,minute);
					} 
					if (p1!=null) {
						if (soundswitch) {
							try {
								AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/place.wav"));
								Clip effect = AudioSystem.getClip();
								effect.open(audio);
								effect.start();
							} catch (Exception e1) {

							} 
						}
						map.getProps().add(p1); //add this prop
						char1.addScore(5); //add a score for placing bombs
						char1.increaseBombsSet(); //increase # of bombs set
						char1bombson++; //increase # of bombs on the map right now
						map.getBombLocs().put(new Rectangle(x*50,y*50,50,50),char1); //put in bomb locations
					}
				} else if (char1.currenttool.equals("minebomb")){
					p1 = char1.setProp(map,y,x,minute);
					if (p1!=null) {
						if (soundswitch) {
							try {
								AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/placemine.wav"));
								Clip effect = AudioSystem.getClip();
								effect.open(audio);
								effect.start();
							} catch (Exception e1) {

							} 
						}
						map.getProps().add(p1);
						char1.addScore(10);
						char1.increasePropsSet();
					}
				} else if (char1.currenttool.equals("gas")){
					p1 = char1.setProp(map,y,x,minute);
					if (p1!=null) {
						if (soundswitch) {
							try {
								AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/gas.wav"));
								Clip effect = AudioSystem.getClip();
								effect.open(audio);
								effect.start();
							} catch (Exception e1) {

							} 
						}
						map.getProps().add(p1);
						char1.addScore(10);
						char1.increasePropsSet();
					}
				}
			} 
			//If player pressed enter: char2 set (same as char1)
			if(key == KeyEvent.VK_ENTER) {
				int x = ((rect2.x)+26)/50; //column
				int y = ((rect2.y)+26)/50; //row
				Prop p2 = null;
				if (char2.currenttool.equals("bomb")) {
					if (char2bombson<char2.getNumBombs()) {
						p2 = char2.setProp(map,y,x,minute);
					}
					if (p2!=null) {
						if (soundswitch) {
							try {
								AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/place.wav"));
								Clip effect = AudioSystem.getClip();
								effect.open(audio);
								effect.start();
							} catch (Exception e1) {

							} 
						}
						map.getProps().add(p2);
						char2.addScore(5);
						char2.increaseBombsSet();
						char2bombson++;
						map.getBombLocs().put(new Rectangle(x*50,y*50,50,50),char2);
					}
				} else if (char2.currenttool.equals("minebomb")){
					p2 = char2.setProp(map,y,x,minute);
					if (p2!=null) {
						if (soundswitch) {
							try {
								AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/placemine.wav"));
								Clip effect = AudioSystem.getClip();
								effect.open(audio);
								effect.start();
							} catch (Exception e1) {

							} 
						}
						map.getProps().add(p2);
						char2.addScore(10);
						char2.increasePropsSet();
					}
				} else if (char2.currenttool.equals("gas")){
					p2 = char2.setProp(map,y,x,minute);
					if (p2!=null) {
						if (soundswitch) {
							try {
								AudioInputStream audio = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream("/effects/gas.wav"));
								Clip effect = AudioSystem.getClip();
								effect.open(audio);
								effect.start();
							} catch (Exception e1) {

							} 
						}
						map.getProps().add(p2);
						char2.addScore(10);
						char2.increasePropsSet();
					}
				}
			}
		}
	}

	//Officially starting game
	//Return: none, Parameter: none
	public void startGame(){
		char1selected = false;
		char2selected = false;
		remove(instruction);
		remove(arrow1);
		remove(arrow2);
		remove(chlabel1);
		remove(chlabel2);
		remove(chlabel3);
		remove(chlabel4);
		remove(keyslabel);
		back.setVisible(false);

		mainmusic.stop();
		reset(mainmusic);

		if (musicswitch) {
			int randommusic = (int)Math.floor(Math.random()*(7)+1);
			if (randommusic == 1) {
				clip1.loop(Clip.LOOP_CONTINUOUSLY);
			} else if (randommusic == 2){
				clip2.loop(Clip.LOOP_CONTINUOUSLY);
			} else if (randommusic == 3){
				clip3.loop(Clip.LOOP_CONTINUOUSLY);
			} else if (randommusic == 4){
				clip4.loop(Clip.LOOP_CONTINUOUSLY);
			} else if (randommusic == 5){
				clip5.loop(Clip.LOOP_CONTINUOUSLY);
			} else if (randommusic == 6){
				clip6.loop(Clip.LOOP_CONTINUOUSLY);
			} else if (randommusic == 7){
				clip7.loop(Clip.LOOP_CONTINUOUSLY);
			}
		}

		int mapNo = (int)Math.floor(Math.random()*(gamemaps.size())+1); //random map
		map = gamemaps.get(mapNo-1);
		rect1 = new Rectangle(0, 0, 41, 45);
		rect2 = new Rectangle(705, 605, 41, 45);
		//rect2 = new Rectangle(0, 605, 41, 45);
		char1bombson = 0;
		char2bombson = 0;

		char1life.setBounds(985,25,30,30);
		char1bomb.setBounds(985,65,30,30);
		char1power.setBounds(985,105,30,30);
		char1speed.setBounds(985,145,30,30);
		char1score.setBounds(1020,25,300,70);
		char2life.setBounds(985,255,30,30);
		char2bomb.setBounds(985,295,30,30);
		char2power.setBounds(985,335,30,30);
		char2speed.setBounds(985,375,30,30);
		char2score.setBounds(1020,255,300,70);
		char1score.setFont(new Font("SansSerif", Font.PLAIN, 14));
		char2score.setFont(new Font("SansSerif", Font.PLAIN, 14));
		add(char1life);
		add(char1bomb);
		add(char1power);
		add(char1speed);
		add(char1score);
		add(char2life);
		add(char2bomb);
		add(char2power);
		add(char2speed);
		add(char2score);

		//A count down timer
		counterLabel = new JLabel();
		counterLabel.setBounds(925,520,200,100);
		counterLabel.setFont(new Font("Serif",Font.BOLD,30));
		counterLabel.setText("05:00");
		second = 0;
		minute = 5;
		countdownTimer();
		timer.start();
		add(counterLabel);
		addMouseListener(this);
		screen = 1;
		this.repaint();
	}

	//Key released (reposition character and direction)
	//Return: none, Parameter: KeyEvent the key that was released
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (screen == 1) {
			if(key == KeyEvent.VK_A) {
				char1imageno = 1;
				left1 = false;
			}else if(key == KeyEvent.VK_D) {
				char1imageno = 3;
				right1 = false;
			}else if(key == KeyEvent.VK_W) {
				char1imageno = 2;
				up1 = false;
			}else if(key == KeyEvent.VK_S) {
				char1imageno = 0;
				down1 = false;
			}
			if(key == KeyEvent.VK_LEFT) {
				char2imageno = 1;
				left2 = false;
			}else if(key == KeyEvent.VK_RIGHT) {
				char2imageno = 3;
				right2 = false;
			}else if(key == KeyEvent.VK_UP) {
				char2imageno = 2;
				up2 = false;
			}else if(key == KeyEvent.VK_DOWN) {
				char2imageno = 0;
				down2 = false;
			}
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	//MouseReleased, used to locate where the player has clicked using the mouse
	//Return: none, Parameter: takes in the MouseEvent / location pressed
	public void mouseReleased(MouseEvent e) {
		//Only "listens" on the game screen, where there's a pause and quit button/image
		if (screen == 1) {
			//Locate the mouse
			int x = e.getX();
			int y = e.getY();
			//If within pause range
			if (x>850 && x<950 && y>600 && y<640) {
				left1=false;
				right1=false;
				up1=false;
				down1=false;
				left2=false;
				right2=false;
				up2=false;
				down2=false;
				timer.stop();
				gamepaused = true;
				char1life.setVisible(false);
				char1bomb.setVisible(false);
				char1power.setVisible(false);
				char1speed.setVisible(false);
				char1score.setVisible(false);
				char2life.setVisible(false);
				char2bomb.setVisible(false);
				char2power.setVisible(false);
				char2speed.setVisible(false);
				char2score.setVisible(false);
				counterLabel.setVisible(false);
				JOptionPane.showMessageDialog(this,"Game Paused");
				//Restart everything and show everything again
				timer.restart();
				gamepaused = false;
				char1life.setVisible(true);
				char1bomb.setVisible(true);
				char1power.setVisible(true);
				char1speed.setVisible(true);
				char1score.setVisible(true);
				char2life.setVisible(true);
				char2bomb.setVisible(true);
				char2power.setVisible(true);
				char2speed.setVisible(true);
				char2score.setVisible(true);
				counterLabel.setVisible(true);
				//If within the quit range
			} else if (x>970&&x<1070 && y>600 && y<640) {
				left1=false;
				right1=false;
				up1=false;
				down1=false;
				left2=false;
				right2=false;
				up2=false;
				down2=false;
				timer.stop();
				//If selected yes from ConfirmDialog
				if (JOptionPane.showConfirmDialog (this, "Are you sure you want to quit?","Message", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					backToMain();
				} else {
					timer.start();
				}
			}
		}
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	//Updates the status of characters using getters when playing game
	//Return: none, Parameter: none
	public void statusupdate(){
		char1life.setText(Integer.toString(char1.getLives())); 
		char1bomb.setText(Integer.toString(char1.getNumBombs()));
		char1power.setText(Integer.toString(char1.getPower())); 
		char1speed.setText(Integer.toString(char1.getSpeed())); 
		char1score.setText("<html>Score: " + Integer.toString(char1.getScore()) + "<BR><BR>Current Tool: </html>"); 
		char2life.setText(Integer.toString(char2.getLives())); 
		char2bomb.setText(Integer.toString(char2.getNumBombs()));
		char2power.setText(Integer.toString(char2.getPower()));
		char2speed.setText(Integer.toString(char2.getSpeed()));
		char2score.setText("<html>Score: " + Integer.toString(char2.getScore()) + "<BR><BR>Current Tool: </html>"); 
	}

	//Return back to the main menu
	//Return: none, Parameter: none
	public void backToMain(){
		if (screen == 0) { //if came back from the help page
			try { 
				title.setVisible(false);
				rul1.setVisible(false);
				rul2.setVisible(false);
				rul3.setVisible(false);
				rul4.setVisible(false);
				rul5.setVisible(false);
				back.setVisible(false);
			} catch (NullPointerException er) {

			}
		} else if (screen == 1) { //if quitted from the game
			damagerecs.clear();
			left1=false;
			right1=false;
			up1=false;
			down1=false;
			left2=false;
			right2=false;
			up2=false;
			down2=false;
			timer.stop();
			counterLabel.setVisible(false);
			remove(char1life);
			remove(char1bomb);
			remove(char1power);
			remove(char1speed);
			remove(char1score);
			remove(char2life);
			remove(char2bomb);
			remove(char2power);
			remove(char2speed);
			remove(char2score);
			win.stop();
			lose.stop();
			clip1.stop();
			clip2.stop();
			clip3.stop();
			clip4.stop();
			clip5.stop();
			clip6.stop();
			clip7.stop();
			reset(win);
			reset(lose);
			reset(clip1);
			reset(clip2);
			reset(clip3);
			reset(clip4);
			reset(clip5);
			reset(clip6);
			reset(clip7);
			map.restart();
			int mapNo = (int)Math.floor(Math.random()*(gamemaps.size()-1+1)+1); //random map
			map = gamemaps.get(mapNo-1);
			screen = 0;
			this.repaint();
			if (musicswitch == true) {
				reset(mainmusic); //if came from game screen, reset music to 0
				mainmusic.start();
				mainmusic.loop(Clip.LOOP_CONTINUOUSLY);
			}
		} else if (screen == 2) {
			char1selected = false;
			char2selected = false;
			charselects[0] = 0;
			charselects[1] = 1;
			instruction.setVisible(false);
			chlabel1.setVisible(false);
			chlabel2.setVisible(false);
			chlabel3.setVisible(false);
			chlabel4.setVisible(false);
			arrow1.setVisible(false);
			arrow2.setVisible(false);
			keyslabel.setVisible(false);
			back.setVisible(false);
		} else if (screen == 3) { //if we came back from the winner page
			back.setVisible(false);
			remove(chlabel1);
			remove(chlabel2);
			remove(char1score);
			remove(char2score);
			remove(char1set);
			remove(char2set);
			remove(char1destruct);
			remove(char2destruct);
			remove(char1propsset);
			remove(char2propsset);
			remove(winner);
			win.stop();
			lose.stop();
			clip1.stop();
			clip2.stop();
			clip3.stop();
			clip4.stop();
			clip5.stop();
			clip6.stop();
			clip7.stop();
			reset(win);
			reset(lose);
			reset(clip1);
			reset(clip2);
			reset(clip3);
			reset(clip4);
			reset(clip5);
			reset(clip6);
			reset(clip7);
			screen = 0;
			if (musicswitch == true) {
				reset(mainmusic); //if came from winner screen, set music to 0
				mainmusic.start();
				mainmusic.loop(Clip.LOOP_CONTINUOUSLY);
			}
		} else if (screen == 4) { //if came back from props page
			for (int i = 0; i<proplabel.length;i++) {
				remove(proplabel[i]);
				remove(propimagelabels[i]);
				remove(propdescriptions[i]);
			}
		}
		//Set everything on the main menu visible
		start.setVisible(true);
		help.setVisible(true);
		exit.setVisible(true);
		sound.setVisible(true);
		music.setVisible(true);
		prop.setVisible(true);
		back.setVisible(false);
		mainimage.setVisible(true);
	}

	//Displays the winner page
	//Return: none, Parameter: integer indicating 
	public void winnerPage(int n){

		screen = 3;
		this.repaint();
		clip1.stop();
		clip2.stop();
		clip3.stop();
		clip4.stop();
		clip5.stop();
		clip6.stop();
		clip7.stop();
		damagerecs.clear();
		map.restart();
		left1=false;
		right1=false;
		up1=false;
		down1=false;
		left2=false;
		right2=false;
		up2=false;
		down2=false;
		timer.stop();
		counterLabel.setVisible(false);
		remove(char1life);
		remove(char1bomb);
		remove(char1power);
		remove(char1speed);
		remove(char2life);
		remove(char2bomb);
		remove(char2power);
		remove(char2speed);
		int one = charselects[0];
		int two = charselects[1];
		chlabel1 = new JLabel(images[one+4]);
		chlabel1.setBounds(209,200,182,200);
		chlabel2 = new JLabel(images[two+4]);
		chlabel2.setBounds(809,200,182,200);
		winner = new JLabel();
		//add 100 points for every life they have
		for (int i = 0; i<char1.getLives(); i++) {
			char1.addScore(100);
		}
		for (int j = 0; j<char2.getLives(); j++) {
			char2.addScore(100);
		}
		//add 1000 points for winning
		if (n == 1) {
			char1.addScore(1000);
			winner.setText("Player 1 Win!");
			if (musicswitch) {
				win.start();
			}
		} else if (n == 2) {
			char2.addScore(1000);
			winner.setText("Player 2 Win!");
			if (musicswitch) {
				win.start();
			}
		} else if (n == 3){
			winner.setText("It's a tie!");
			if (musicswitch) {
				lose.start();
			}
		} else if (n == 4) {
			if (char1.getScore()>char2.getScore()) {
				winner.setText("Player 1 Win!");
				if (musicswitch) {
					win.start();
				}
			} else if (char2.getScore()>char1.getScore()) {
				winner.setText("Player 2 Win!");
				if (musicswitch) {
					win.start();
				}
			} else {
				winner.setText("It's a tie!");
				if (musicswitch) {
					lose.start();
				}
			}
		}

		//Sets the JLabels
		winner.setFont(new Font("SansSerif", Font.PLAIN, 34));
		winner.setBounds(500,20,300,50);
		char1set = new JLabel("Bombs set: " + Integer.toString(char1.getBombsSet()));
		char2set = new JLabel("Bombs set: " + Integer.toString(char2.getBombsSet()));
		char1destruct = new JLabel("Blocks destructed: " + Integer.toString(char1.getBlockDestroyed()));
		char2destruct = new JLabel("Blocks destructed: " + Integer.toString(char2.getBlockDestroyed()));
		char1score.setText("Score: " + Integer.toString(char1.getScore()));
		char2score.setText("Score: " + Integer.toString(char2.getScore()));
		char1propsset = new JLabel("Props set: " + Integer.toString(char1.getPropsSet()));
		char2propsset = new JLabel("Props set: " + Integer.toString(char2.getPropsSet()));

		char1set.setFont(new Font("SansSerif", Font.PLAIN, 18));
		char2set.setFont(new Font("SansSerif", Font.PLAIN, 18));
		char1destruct.setFont(new Font("SansSerif", Font.PLAIN, 18));
		char2destruct.setFont(new Font("SansSerif", Font.PLAIN, 18));
		char1score.setFont(new Font("SansSerif", Font.PLAIN, 18));
		char2score.setFont(new Font("SansSerif", Font.PLAIN, 18));
		char1propsset.setFont(new Font("SansSerif", Font.PLAIN, 18));
		char2propsset.setFont(new Font("SansSerif", Font.PLAIN, 18));

		char1score.setBounds(240,420,300,50);
		char2score.setBounds(840,420,300,50);
		char1set.setBounds(240,470,160,50);
		char2set.setBounds(840,470,160,50);
		char1destruct.setBounds(240,520,200,50);
		char2destruct.setBounds(840,520,200,50);
		char1propsset.setBounds(240,570,200,50);
		char2propsset.setBounds(840,570,200,50);

		add(winner);
		add(chlabel1);
		add(chlabel2);
		add(char1score);
		add(char2score);
		add(char1set);
		add(char2set);
		add(char1destruct);
		add(char2destruct);
		add(char1propsset);
		add(char2propsset);
		back.setVisible(true);

	}

	//A count down timer
	//Return: none, parameter: none
	public void countdownTimer(){
		timer = new Timer (1000,new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				second--;
				if (second==-1) { //If second reaches -1 start over to 59
					minute --;
					second = 59;
				}
				counterLabel.setText(String.format("%02d:%02d", minute,second));
				if (minute == 0 && second == 0) { //If both minute and second is 0, then time is up
					counterLabel.setText(String.format("%02d:%02d", minute,second));
					timer.stop();
					JOptionPane.showMessageDialog(frame,"Time is up!");
					winnerPage(4);
				}
			}
		}
				);
	}

	//Reset a clip back to 0 (beginning)
	//Return: none, parameter: Clip that needs to be reset
	public void reset(Clip clipx) {
		clipx.setFramePosition(0);
	}

}

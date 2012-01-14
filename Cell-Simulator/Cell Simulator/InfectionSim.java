import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;
import javax.imageio.*;
import java.io.*;
import java.text.DecimalFormat;
public class InfectionSim extends JFrame
{
    //============================================================ main
    public static void main (String[] args)
    {
	//Set up the window
	JFrame window = new JFrame ("Cell Infection Demo");
	window.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	window.setContentPane (new SimGUI ());
	window.pack ();
	window.setVisible (true);
    }
}

class SimGUI extends JPanel
{
    CellSim simwindow;   // The simulation panel
    CellSim timer;

    JTextField _numberCells = new JTextField (3);  //Entry field for red blood cell number
    JTextField _numberVirus = new JTextField (3);  //for virus number
    JTextField _numberWBC = new JTextField (3);  //for white blood cell number
    JTextField _regenRate = new JTextField (3);  //for the rate of regeneration
    JCheckBox wbc = new JCheckBox ();  //a checkbox to see if the player wants white blood cells

    public JTextArea _readout = new JTextArea ();  //an area for the data readout at the end of the sim
    public JTabbedPane MainPanel = new JTabbedPane (); //tabbed pane
    public JPanel mybutt = new JPanel (); //main tab
    public JPanel tutorialPanel = new JPanel (); //options pane on tutorial tab
    public JPanel imagePanel = new JPanel (); //tutorial tab

    public JButton redbutton = new JButton ("Red Blood Cells"); //red blood cell button of tutorial
    public JButton virusbutton = new JButton ("Viruses"); //virus button of tutorial
    public JButton whitebutton = new JButton ("White Blood Cells"); //white blood cell button of tutorial
    public JButton backbutt = new JButton ("Back");

    public JTextArea _info = new JTextArea ();
    public ImageIcon bigpic = new ImageIcon ("bigpic2.gif"); //image for tutorial
    public ImageIcon bigpic3 = new ImageIcon ("bigpic3.gif"); //image for tutorial
    public ImageIcon bigpic4 = new ImageIcon ("bigpic4.gif"); //image for tutorial
    public ImageIcon bigpic5 = new ImageIcon ("bigpic5.gif"); //image for tutorial
    public JLabel bigpix = new JLabel (bigpic);
    public JLabel bigpix3 = new JLabel (bigpic3);
    public JLabel bigpix4 = new JLabel (bigpic4);
    public JLabel bigpix5 = new JLabel (bigpic5);

    public JScrollPane verticalPane = new JScrollPane (_info, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    public JScrollPane verticalPane2;

    public SimGUI ()
    {
	timer = new CellSim (true);  //initializes a clock for the data panel
	simwindow = new CellSim (timer);  //initializes sim panel
	JPanel optionPanel = new JPanel ();  //main panel
	optionPanel.setLayout (new GridLayout (7, 1));
	JPanel startstop = new JPanel (); //the other panels
	JPanel cellno = new JPanel ();
	JPanel whitebc = new JPanel ();
	JPanel wbcno = new JPanel ();
	JPanel virusno = new JPanel ();
	JPanel regen = new JPanel ();
	JPanel note = new JPanel ();

	//Add initialization buttons to the top panel (with listeners)
	JButton startButton = new JButton ("Start");
	JButton resetButton = new JButton ("Reset");
	startButton.addActionListener (new StartAction ());
	resetButton.addActionListener (new ResetAction ());
	startstop.add (startButton);
	startstop.add (resetButton);

	//Add red cell changing option
	cellno.add (new JLabel ("Number of Cells: "));
	cellno.add (_numberCells);

	//Add virus changing option
	virusno.add (new JLabel ("Number of Viruses: "));
	virusno.add (_numberVirus);

	//Add white cell checkbox
	whitebc.add (new JLabel ("White Blood Cells? "));
	wbc.addActionListener (new boxAction ());
	whitebc.add (wbc);

	//Add white cell text field
	wbcno.add (new JLabel ("Number of WBC: "));
	wbcno.add (_numberWBC);

	regen.add (new JLabel ("Regeneration Rate (/min): "));
	regen.add (_regenRate);

	//Add a nice note
	note.add (new JLabel ("Hit start to see the sim!"));

	//Add components to option panel
	optionPanel.add (startstop);
	optionPanel.add (cellno);
	optionPanel.add (virusno);
	optionPanel.add (whitebc);
	optionPanel.add (wbcno);
	optionPanel.add (regen);
	optionPanel.add (note);
	_numberWBC.setEditable (false);

	//Set the header text for the output field
	_readout.setText ("\t\tINFECTION DATA\n\t\t-------------------------\n\n\n\n\n\n");

	//Add components to the main tab
	mybutt.setLayout (new BorderLayout ());
	mybutt.add (simwindow, BorderLayout.CENTER);
	mybutt.add (optionPanel, BorderLayout.EAST);
	mybutt.add (_readout, BorderLayout.SOUTH);

	imagePanel.setLayout (new BorderLayout ()); //tutorial tab
	tutorialPanel.setLayout (new GridLayout (5, 1)); //button panel of tutorial tab

	//Add buttons to buttons panel of tutorial
	tutorialPanel.add (redbutton);
	tutorialPanel.add (virusbutton);
	tutorialPanel.add (whitebutton);
	tutorialPanel.add (backbutt);

	redbutton.addActionListener (new redbuttonhole ());
	backbutt.addActionListener (new backbutthole ());
	virusbutton.addActionListener (new virusbuttonhole ());
	whitebutton.addActionListener (new whitebuttonhole ());

	tutorialPanel.add (verticalPane);
	imagePanel.add (tutorialPanel, BorderLayout.WEST); //add the buttons panel of the tutorial tab
	imagePanel.add (bigpix, BorderLayout.CENTER); //add the picture to the tutorial tab

	MainPanel.addTab ("Simulation", mybutt); //add the simulation tab
	MainPanel.addTab ("Tutorial", imagePanel); //add the tutorial tab

	this.add (MainPanel);
    } //end constructor


    class boxAction implements ActionListener
    {
	public void actionPerformed (ActionEvent e)
	{
	    boolean white = wbc.isSelected ();
	    if (white)
		_numberWBC.setEditable (true);
	    if (!white)
		_numberWBC.setEditable (false);
	}
    }


    class redbuttonhole implements ActionListener
    {
	public void actionPerformed (ActionEvent e)
	{
	    imagePanel.remove (bigpix);

	    imagePanel.revalidate ();
	    tutorialPanel.remove (verticalPane);
	    tutorialPanel.revalidate ();
	    bigpix = new JLabel (bigpic3);
	    imagePanel.add (bigpix, BorderLayout.CENTER); //add the picture to the tutorial tab
	    _info.setText ("Red blood cells (also referred to as erythrocytes) \nare the most common type of blood cell and the vertebrate \norganism's principal means of delivering oxygen (O2)\n to the body tissues via the blood flow\n through the circulatory system. They take\n up oxygen in the lungs or gills \nand release it while squeezing\n through the body's capillaries.");
	    verticalPane = new JScrollPane (_info, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    verticalPane.setPreferredSize (new Dimension (140, 80));
	    tutorialPanel.add (verticalPane);
	}
    }


    class virusbuttonhole implements ActionListener
    {
	public void actionPerformed (ActionEvent e)
	{
	    imagePanel.remove (bigpix);

	    imagePanel.revalidate ();
	    tutorialPanel.remove (verticalPane);
	    tutorialPanel.revalidate ();
	    bigpix = new JLabel (bigpic4);
	    imagePanel.add (bigpix, BorderLayout.CENTER); //add the picture to the tutorial tab
	    _info.setText ("A virus is a small infectious agent that can replicate only \ninside the living cells of organisms. \nMost viruses are too small to be seen directly \nwith a light microscope. Viruses infect all types of organisms,\n from animals and plants\n to bacteria and archaea. Since the initial \ndiscovery of the tobacco mosaic virus by Martinus Beijerinck in 1898,\n about 5,000 viruses have been described in detail,\n although there are millions of different types.\n Viruses are found in almost every ecosystem on Earth and are the most \nabundant type of biological entity.\n The study of viruses is known as virology,\n a sub-speciality of microbiology.");
	    verticalPane = new JScrollPane (_info, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    verticalPane.setPreferredSize (new Dimension (140, 80));
	    tutorialPanel.add (verticalPane);
	}
    }


    class whitebuttonhole implements ActionListener
    {
	public void actionPerformed (ActionEvent e)
	{
	    imagePanel.remove (bigpix);

	    imagePanel.revalidate ();
	    tutorialPanel.remove (verticalPane);
	    tutorialPanel.revalidate ();
	    bigpix = new JLabel (bigpic5);
	    imagePanel.add (bigpix, BorderLayout.CENTER); //add the picture to the tutorial tab
	    _info.setText ("White blood cells, or leukocytes (also spelled leucocytes,leuco being Greek for white)\n, are cells of the immune system involved in defending the body\n against both infectious disease and foreign materials.\n Five different and diverse types of leukocytes exist,\n but they are all produced and derived from a multipotent cell\n in the bone marrow known as a hematopoietic stem cell.\n Leukocytes are found throughout the body, \nincluding the blood and lymphatic system./n The number of WBCs in the blood is often an indicator of disease. \nThere are normally between 4×10^9 and 1.1×10^10 \nwhite blood cells in a litre of blood,\n making up approximately 1% of blood in a healthy adult.\n An increase in the number of leukocytes over the upper limits\n is called leukocytosis, and a decrease below the lower limit\n is called leukopenia. The physical properties of leukocytes, such as volume,\n conductivity, and granularity, may change due to activation,\n the presence of immature cells, \nor the presence of malignant leukocytes in leukemia.");
	    verticalPane = new JScrollPane (_info, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    verticalPane.setPreferredSize (new Dimension (140, 80));
	    tutorialPanel.add (verticalPane);
	}
    }


    class backbutthole implements ActionListener
    {
	public void actionPerformed (ActionEvent e)
	{
	    imagePanel.remove (bigpix);
	    imagePanel.revalidate ();
	    tutorialPanel.remove (verticalPane);
	    tutorialPanel.revalidate ();
	    bigpix = new JLabel (bigpic);
	    imagePanel.add (bigpix, BorderLayout.CENTER); //add the picture to the tutorial tab
	    _info.setText ("Main Screen");
	    verticalPane = new JScrollPane (_info, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    verticalPane.setPreferredSize (new Dimension (140, 80));
	    tutorialPanel.add (verticalPane);
	}
    }


    class StartAction implements ActionListener
    {
	public void actionPerformed (ActionEvent e)
	{
	    //Get input data if it's there
	    int redcells = getNo (_numberCells.getText ());
	    int viruses = getNo (_numberVirus.getText ());
	    boolean white = wbc.isSelected ();
	    int whitecells = getNo (_numberWBC.getText ());
	    int regen = getNo (_regenRate.getText ());

	    //If the input was invalid or there was none, set default values
	    if (redcells == 0)
		redcells = 5;
	    if (viruses == 0)
		viruses = 2;
	    if (whitecells == 0)
		whitecells = 3;
	    if (regen > 100 || regen <= 0) //if the person enters a regeneration rate above 100 or 0 and below, regeneration is disabled
		regen = 15;

	    timer.sec = 0;
	    //Update the simulation panel
	    simwindow.numcells = redcells;
	    simwindow.updateInfo (white, whitecells, viruses, _readout, regen);

	    //Reset the text area if they've already done a sim
	    _readout.setText ("\t\tINFECTION DATA\n\t\t-------------------------\n\n\n\n\n\n");

	    //Set the simulation in motion
	    simwindow.gameON = true;
	    simwindow.setAnimation (true);
	    timer.setAnimation2 (true);
	}
    }


    class ResetAction implements ActionListener
    {
	public void actionPerformed (ActionEvent e)
	{ //End the simulation
	    resetGame ();
	}
    }


    public void resetGame ()
    {
	simwindow.setAnimation (false);
	timer.setAnimation2 (false);

	// CellSim timeri = new CellSim (true);
	// timer = timeri;
	simwindow.wipe (true);
	timer.wipe (false);

	_readout.setText ("\t\tINFECTION DATA\n\t\t-------------------------\n\n\n\n\n\n"); // CELL NUMBERS:\n     Red Blood Cells: " + simwindow.numcells + "     Viruses: " + simwindow.numvir);
	//   if (simwindow.whitebc)
	//       _readout.setText (_readout.getText () + "     White Blood Cells: " + simwindow.numwbc + "\n\n\n\n");
	//   else
	//       _readout.setText (_readout.getText () + "\nTOTAL TIME: " + timer.thetime + " seconds\n\n\n");
    }


    public int getNo (String parser)
    {
	int cells = 0;  //a default value
	try  //try to change the string to an int
	{
	    cells = Integer.parseInt (parser);
	}
	catch (NumberFormatException nfe)
	{
	}
	return cells;
    }
} //endclass BBPanel


class CellSim extends JPanel
{
    private int interval = 35;     // Milliseconds between updates.
    private Timer movecell;           // Timer fires to anmimate one step.
    public int numcells = 5, numvir = 2, numwbc = 3;  //The default values for the program
    public Cell[] RB, WB, VC, temp;  //The different arrays for the simulation
    boolean whitebc = false;  //Whether there are white blood cells or not
    public int type = 65756;
    public boolean alive = true;
    public int regen, rate, ran = 0, min = 60000, sizeB4 = 0;  //Regeneration variables
    public boolean gameON = true;  //Whether the sim is running
    public CellSim timer;  //The clock (must be sent in to start/stop it)
    JTextArea _details;  //The GUI's text area for the output data
    public int min2 = 60000, timeR = 0, rate2;

    //TIMER INSTANCE DATA FIELDS
    public Timer clocktimer;  //The clock
    public int sec = 0;  //Number of seconds
    private int tenthsec = 0;  //Number of tenths of a second
    public String thetime = "";  //A string so the time can be printed

    public CellSim (CellSim time)  //default constructor for the actual sim
    {
	setPreferredSize (new Dimension (300, 300));
	setBorder (BorderFactory.createLineBorder (Color.BLACK));
	movecell = new Timer (interval, new TimerAction ());
	timer = time;

	//Initialize the default array size and create a temp array for later
	RB = new Cell [5];
	VC = new Cell [2];
	temp = new Cell [RB.length];

	//Generate the cell objects in the arrays
	cellGenerator (whitebc);
    }


    public void wipe (boolean isclock)  //Resets EVERYTHING if the user wants multiple sims on the same GUI
    {
	if (isclock)
	{
	    sec = 0;
	    tenthsec = 0;
	    thetime = "";
	}
	else
	{
	    numcells = 3;
	    numvir = 2;
	    numwbc = 3;
	    Cell[] RBi = new Cell [5];
	    RB = RBi;
	    Cell[] WBi = new Cell [0];
	    WB = WBi;
	    Cell[] VCi = new Cell [2];
	    VC = VCi;
	    Cell[] tempi = new Cell [5];
	    temp = tempi;
	    sizeB4 = 0;
	    whitebc = false;
	    type = 65756;
	    alive = true;
	    gameON = true;
	    cellGenerator (whitebc);
	}
    }


    //THE TIMER INSTANCE OF THIS CLASS
    public CellSim (boolean thisisatimer)  //Creates a timer instance
    {
	clocktimer = new Timer (100, new TimerAction2 ());  //Makes the timer
	this.sec = 0;  //Sets the second and tenths of a second values
	this.tenthsec = 0;
    }


    public void move2 ()
    {
	timeR += (1000 * 1) + 3; //timeR is increased by this value, through trial and error
	this.tenthsec += (10 * 1); //tenth of a second
	if (this.tenthsec == 100) //every time tenth of a second = 100 it resets back to zero
	    this.tenthsec = 0;
	if (Math.abs (timeR - 6000) <= 100) //through trial and error this number seemed best fit for every second
	{
	    this.sec++;
	    timeR = 0;
	}
	DecimalFormat fmt = new DecimalFormat ("0.#"); //this is used to make the tenth of a second only two decimal spots
	this.thetime = ("" + this.sec + "." + fmt.format (tenthsec)); //sets the string variable so the time can be printed}

    }


    public void setAnimation2 (boolean turnOnOff)  //Starts and stops the timer
    {
	if (turnOnOff)
	{
	    clocktimer.start ();
	}
	else
	{
	    clocktimer.stop ();
	}
    }


    class TimerAction2 implements ActionListener  //Makes the timer keep time
    {
	public void actionPerformed (ActionEvent e)
	{
	    move2 ();
	}
    }


    //END TIMER METHODS


    public void cellGenerator (boolean white)  //Fills the appropiate arrays with cells
    {
	//Generate the red blood cells in the array
	for (int x = 0 ; x < RB.length ; x++)
	{
	    RB [x] = new Cell ((int) (Math.random () * 250 + 1), (int) (Math.random () * 250 + 1), (int) (Math.random () * 3 + 1), (int) (Math.random () * 2 + 1), 1, true);
	}
	RB = overlapCheck (RB, 1); //check to see if any of the cells overlap to avoid issues later on

	//If the user wants white blood cells (default is no white blood cells)
	if (white)
	{
	    for (int x = 0 ; x < WB.length ; x++)
	    {
		WB [x] = new Cell ((int) (Math.random () * 250 + 1), (int) (Math.random () * 250 + 1), (int) (Math.random () * 3 + 1), (int) (Math.random () * 2 + 1), 2, true);
	    }
	    WB = overlapCheck (WB, 2); //checks for overlap
	}

	//Generates the viruses in the array
	for (int x = 0 ; x < VC.length ; x++)
	{
	    VC [x] = new Cell ((int) (Math.random () * 250 + 1), (int) (Math.random () * 250 + 1), (int) (Math.random () * 3 + 1), (int) (Math.random () * 2 + 1), 3, true);
	}
	VC = overlapCheck (VC, 3); //checks for overlap

	//Generates cells in the temp array for regeneration purposes
	for (int x = 0 ; x < temp.length ; x++)
	{
	    temp [x] = new Cell ((int) (Math.random () * 250 + 1), (int) (Math.random () * 250 + 1), (int) (Math.random () * 3 + 1), (int) (Math.random () * 2 + 1), 3, true);
	}
    }


    public Cell[] overlapCheck (Cell[] L, int num)    //Tries to avoid cells being generated on top of each other
    {
	for (int x = 0 ; x < L.length ; x++)
	{
	    for (int z = x + 1 ; z < L.length ; z++)
	    {
		//If the cell images overlap it will generate a new one
		if (Math.abs (L [x].xcoord - L [z].xcoord) <= 50 && Math.abs (L [x].ycoord - L [z].ycoord) <= 30)
		{
		    L [x] = new Cell ((int) (Math.random () * 250 + 1), (int) (Math.random () * 250 + 1), (int) (Math.random () * 3 + 1), (int) (Math.random () * 2 + 1), num, true);
		    L = overlapCheck (L, num);  //then it will check to see if it overlaps with the others
		}
	    }
	}
	return L;
    }


    public void updateInfo (boolean white, int numwhite, int numvirus, JTextArea _hh, int cellReg)  //To update with user input
    {
	//Updates the option of white blood cells
	whitebc = white;
	numwbc = numwhite;
	numvir = numvirus;
	if (white)  //if the user wants white blood cells, creates the appropriate array
	    WB = new Cell [numwhite];

	//Updates the number of spaces in the red cell array
	Cell RBi[] = new Cell [numcells];
	RB = RBi;

	//Updates the number of spaces in the virus array
	Cell VCi[] = new Cell [numvirus];
	VC = VCi;

	//Enables the data output at the end
	_details = _hh;

	//Enables regeneration

	regen = cellReg;
	min = min / regen;
	rate = 60 / regen;
	rate *= 1000;


	//Creates cells to fill the new spaces
	cellGenerator (white);
    }


    public void setAnimation (boolean turnOnOff)  //Starts and stops the simulation
    {
	if (turnOnOff)
	{
	    movecell.start ();
	}
	else
	{
	    movecell.stop ();

	}
    }


    public void paintComponent (Graphics g)
    {
	super.paintComponent (g); // Paint background, border

	Image bkg = loadImage ("bloodback.jpg");
	g.drawImage (bkg, 0, 0, null);
	for (int x = 0 ; x < RB.length ; x++)
	{
	    RB [x].draw (g);         // Draws the red blood cells
	}
	if (whitebc)  //if there are white blood cells
	{
	    for (int x = 0 ; x < WB.length ; x++)
	    {
		WB [x].draw (g);         // Draws the white blood cells
	    }
	}
	for (int x = 0 ; x < VC.length ; x++)
	{
	    VC [x].draw (g);         // Draws the viruses
	}

    }


    public static Image loadImage (String name)  //Loads Images
    {
	Image img = null;
	try
	{
	    img = ImageIO.read (new File (name));
	}
	catch (IOException e)
	{
	}
	return img;
    }


    public void stopSim ()  //When the sim is over, this stops it and prints the data
    {
	setAnimation (false);  //stops the sim
	timer.setAnimation2 (false);  //stops the timer

	//Prints all the output text
	_details.setText ("\t\tINFECTION DATA\n\t\t-------------------------\n FINAL COUNT:\n     Red Blood Cells: " + numcells + "     Viruses: " + numvir);
	if (whitebc)
	    _details.setText (_details.getText () + "     White Blood Cells: " + numwbc + "\n\n\n\n");
	else
	    _details.setText (_details.getText () + "\nTOTAL TIME: " + timer.thetime + " seconds\n\n\n");
	timer.sec = 0;
	timer.tenthsec = 0;
	timer.thetime = "";
    }


    //ActionListener for moving the cells
    class TimerAction implements ActionListener
    {
	public void actionPerformed (ActionEvent e)
	{
	    if (gameON)  //If there are still cells/viruses alive
	    {
		//Generates a new cell every so often
		ran += 2 * interval;  //updates the random variable
		sizeB4 = RB.length;  //stores the length for a placeholder for the new cell

		if (Math.abs (ran - rate) <= 35)  //if the time is sufficiently far apart generates a new cell
		{
		    ran = 0;  //resets this

		    //Updates the arrays
		    temp = new Cell [RB.length + 1];
		    for (int x = 0 ; x < RB.length ; x++)
		    {
			temp [x] = RB [x];
		    }
		    RB = new Cell [RB.length + 1];
		    for (int x = 0 ; x < temp.length ; x++)
		    {
			RB [x] = temp [x];
		    }

		    //Increases the number of cells onscreen (counter), creates the new cell, and draws it
		    RB [sizeB4] = new Cell ((int) (Math.random () * 250 + 1), (int) (Math.random () * 250 + 1), (int) (Math.random () * 3 + 1), (int) (Math.random () * 2 + 1), 1, true);
		    numcells++;
		    sizeB4 = 0;
		    repaint ();
		}
		//Moves the red blood cells
		for (int x = 0 ; x < RB.length ; x++)
		{
		    RB [x].setBounds (getWidth (), getHeight ());
		    RB [x].move ();
		    repaint ();
		}
		//Moves the white blood cells (if the user chose to have them)
		if (whitebc)
		{
		    for (int x = 0 ; x < WB.length ; x++)
		    {
			WB [x].setBounds (getWidth (), getHeight ());
			WB [x].move ();
			repaint ();
		    }
		}
		//Moves the viruses
		for (int x = 0 ; x < VC.length ; x++)
		{
		    VC [x].setBounds (getWidth (), getHeight ());
		    VC [x].move ();
		    repaint ();
		}
	    }
	    else  //If either all the red blood cells or all the viruses are dead, stops the game
	    {
		stopSim ();
	    }
	}
    }


    class Cell  //The class for all the cells
    {
	//Constants
	final static int DIAMETER = 60;
	//Variables
	private int xcoord;           // x and y coordinates upper left
	private int ycoord;
	private double xspeed;   // Pixels to move each time move() is called.
	private double yspeed;
	private int rightBound;  // Maximum permissible x, y values.
	private int bottomBound;
	private Image cell;  //The picture of the cell
	private int which;  //Whether it's a red blood cell, white blood cell, or virus
	private boolean life;  //Whether the cell is actually alive or not

	public Cell (int x, int y, double velocityX, double velocityY, int which, boolean stayingalive)
	{
	    this.xcoord = x;
	    this.ycoord = y;
	    this.xspeed = velocityX;
	    this.yspeed = velocityY;
	    this.which = which;
	    this.life = stayingalive;
	    if (which == 1)  //Loads RBC image
		this.cell = loadImage ("bloodcell.gif");
	    else if (which == 2)  //Loads WBC image
		this.cell = loadImage ("whitebloodcell.gif");
	    else if (which == 3)  //Loads virus image
		this.cell = loadImage ("virus.gif");
	}


	public void setBounds (int width, int height)  //sets the boundaries
	{
	    rightBound = width - DIAMETER;
	    bottomBound = height - DIAMETER;
	}


	public void move ()  //to move the cell
	{
	    if (this.life)
	    {
		//Changes the cell's position depending on how fast it's moving
		this.xcoord += this.xspeed;
		this.ycoord += this.yspeed;

		//Bounce the cell off the walls if necessary.
		if (this.xcoord < 0)
		{ // If at or beyond left side
		    this.xcoord = 0;                    // Place against edge and
		    this.xspeed = -this.xspeed; // reverse direction.

		}
		else if (this.xcoord > rightBound)
		{ // If at or beyond right side
		    this.xcoord = rightBound;            // Place against right edge.
		    this.xspeed = -this.xspeed;  // Reverse direction.
		}

		if (this.ycoord < 0)
		{ // if we're at top
		    this.ycoord = 0;
		    this.yspeed = -this.yspeed;

		}
		else if (this.ycoord > bottomBound)
		{ // if we're at bottom
		    this.ycoord = bottomBound;
		    this.yspeed = -this.yspeed;
		}

		//RED BLOOD CELL COLLISIONS
		if (this.which == 1)
		{
		    for (int x = 0 ; x < RB.length ; x++)
		    {
			for (int y = x + 1 ; y < RB.length ; y++)
			{
			    if (Math.abs (RB [x].xcoord - RB [y].xcoord) <= 50 && Math.abs (RB [x].ycoord - RB [y].ycoord) <= 30)  //If two cells are touching
			    { //Reverses their direction
				RB [x].xspeed *= -1;
				RB [y].xspeed *= -1;
				RB [x].yspeed *= -1;
				RB [y].yspeed *= -1;
			    }
			}
		    }
		    for (int x = 0 ; x < RB.length ; x++)
		    {
			for (int y = 0 ; y < VC.length ; y++)
			{
			    if (Math.abs (RB [x].xcoord - VC [y].xcoord) <= 50 && Math.abs (RB [x].ycoord - VC [y].ycoord) <= 30)  //If a cell hits a virus
			    { //The cell dies
				RB [x].life = false;
				RB [x].xcoord = 5099;  //These are set to really big values so they can't collide
				RB [x].ycoord = 2399;

				numcells--;  //Decreases the total number onscreen for counter purposes
				if (numcells == 0)  //If there are no cells left the sim stops
				    gameON = false;
			    }
			}
		    }

		}
		//WHITE BLOOD CELL COLLISIONS
		else if (this.which == 2)
		{
		    for (int x = 0 ; x < WB.length ; x++)
		    {
			for (int y = x + 1 ; y < WB.length ; y++)
			{
			    if (Math.abs (WB [x].xcoord - WB [y].xcoord) <= 50 && Math.abs (WB [x].ycoord - WB [y].ycoord) <= 30)  //if two WBC are touching
			    { //Reverse the directions of the cells
				WB [x].xspeed *= -1;
				WB [y].xspeed *= -1;
				WB [x].yspeed *= -1;
				WB [y].yspeed *= -1;
			    }

			}
		    }
		}
		//VIRUS COLLISIONS
		else
		{
		    for (int x = 0 ; x < VC.length ; x++)
		    {
			for (int y = x + 1 ; y < VC.length ; y++)
			{
			    if (Math.abs (VC [x].xcoord - VC [y].xcoord) <= 45 && Math.abs (VC [x].ycoord - VC [y].ycoord) <= 30)  //if two viruses are touching
			    { //Reverses direction of cells
				VC [x].xspeed *= -1;
				VC [y].xspeed *= -1;
				VC [x].yspeed *= -1;
				VC [y].yspeed *= -1;
			    }

			}
		    }
		    if (whitebc)  //if there are white blood cells that can eat them
		    {
			for (int x = 0 ; x < VC.length ; x++)
			{
			    for (int y = 0 ; y < WB.length ; y++)
			    {
				if (Math.abs (VC [x].xcoord - WB [y].xcoord) <= 45 && Math.abs (VC [x].ycoord - WB [y].ycoord) <= 30)  //if viruses are touching WBCs
				{ //the virus dies
				    VC [x].life = false;
				    VC [x].xcoord = 832;  //and these are set to impossibly high values
				    VC [x].ycoord = 939;

				    numvir--;  //Decreases number of onscreen viruses for counter purposes
				    if (numvir == 0)  //if all the viruses are dead the sim ends
					gameON = false;
				}
			    }
			}
		    }
		}
	    }

	}


	public void draw (Graphics g)
	{
	    if (this.life == true)  //if the cell is alive, draws it.
		g.drawImage (this.cell, this.xcoord, this.ycoord, null);
	}


	public Image loadImage (String name)  //Loads Images
	{
	    Image img = null;
	    try
	    {
		img = ImageIO.read (new File (name));
	    }
	    catch (IOException e)
	    {
	    }

	    return img;
	}

	//Accessor Methods
	public int getDiameter ()
	{
	    return DIAMETER;
	}


	public int getX ()
	{
	    return xcoord;
	}


	public int getY ()
	{
	    return ycoord;
	}


	public void setPosition (int x, int y)  //Sets the position of the cell
	{
	    xcoord = x;
	    ycoord = y;
	}
    }
} //endclass

package project5;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.util.Random;
import java.util.Scanner;


/*
 * Generates maze for a given row and column
 * @author Ashwin kumar Muruganandam
 * */

public class MazeGen extends Frame {

	private static final long serialVersionUID = 1L;
	static int row;
	static int col;
	static CellWalls[] cellWall;
	double x = 50;
	double y = 75;

	/*
	 * Constructor, Calls the method which starts the GUI
	 * */
	MazeGen() {
		prepareGUI();
	}

	/*
	 * It brings up the GUI for the maze
	 * */
	public void prepareGUI() {
		setSize(1500, 1500);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
	}

	/*
	 * Prints the border of the maze
	 * @param g Graphics for the printing lines
	 * 
	 * */
	public void border(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Font font = new Font("Serif", Font.PLAIN, 24);
		g2.setFont(font);
		
		Line2D upperLine = new Line2D.Double();
		upperLine.setLine(50, 75, (25 * col) + 50, 75);
		g2.draw(upperLine);
		Line2D leftLine = new Line2D.Double();
		leftLine.setLine(50, 100, 50, (25 * row) + 75);
		g2.draw(leftLine);
		Line2D bottomLine = new Line2D.Double();
		bottomLine.setLine(50, (25 * row) + 75, 50 + (25 * col), (25 * row) + 75);
		g2.draw(bottomLine);
		Line2D rightLine = new Line2D.Double();
		rightLine.setLine(50 + (25 * col), 75, 50 + (25 * col), (25 * (row - 1)) + 75);
		g2.draw(rightLine);

	}
	
	
	/*
	 * It displays the walls, if visible param
	 *  of wall is true
	 * @param Graphics for printing maze
	 * 
	 * */

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Font font = new Font("Serif", Font.PLAIN, 24);
		g2.setFont(font);
		int hc = 0, vc =0;
		int vCol = 0;
		int hCol = 0;
		
		// For printing borders
		border(g);
		
		//For printing vertical lines
		while (vc < (col-1)*row) {
			if(vc == 379){
				System.out.println();
			}
			if (vCol < col - 1) {
				x += 25;
				vCol++;
			} else {
				x = 75;
				y += 25;
				vCol = 1;
			}
			Line2D line = new Line2D.Double();
			line.setLine(x, y, x, y + 25);
			if (cellWall[vc].isVisible) {
				g2.draw(line);
			}
			vc++;
		}
		x = 50;
		y = 100;
		
		// For printing horizontal lines
		while (hc < (row-1)*col ) {
			if(hc == 379){
				System.out.println();
			}

			Line2D line = new Line2D.Double();
			line.setLine(x, y, x + 25, y);
			if (cellWall[vc+hc].isVisible) {
				g2.draw(line);
			}

			if (hCol < col - 1) {
				x += 25;
				hCol++;
			} else {
				x = 50;
				y += 25;
				hCol = 0;
			}
			hc++;
		}
	}
	
	/*
	 * It initialize the walls, sets the visibility to true and 
	 * finds and sets its neighbouring cells.
	 * 
	 * @param row , row of the maze
	 * @param col, column of the maze
	 * @param cellWall, Walls present in the maze
	 * */

	public static void wallIntialize(int row, int col, CellWalls[] cellWall) {

		int VWallCell = 0;
		int HWallCell = 0;
		int vCol = 0;
		for (int i = 0; i < cellWall.length; i++) {
			cellWall[i] = new CellWalls();
			cellWall[i].setVisible(true);
			if (i < (col - 1) * row) {
				if (vCol < col - 1) {
					cellWall[i].setnCell1(VWallCell);
					cellWall[i].setnCell2(VWallCell + 1);
				} else {
					vCol = 0;
					VWallCell += 1;
					cellWall[i].setnCell1(VWallCell);
					cellWall[i].setnCell2(VWallCell + 1);
					
				}
				vCol++;VWallCell++;
			} else {
				cellWall[i].setnCell1(HWallCell);
				cellWall[i].setnCell2(HWallCell + col);
				HWallCell++;
			}

		}

	}

	public static void main(String[] args) {

		Scanner inp = new Scanner(System.in);
		System.out.println("Enter the number of rows");
		row = inp.nextInt();

		System.out.println("Enter the number of Columns");
		col = inp.nextInt();

		inp.close();

		DisjSets sets = new DisjSets(row * col);

		cellWall = new CellWalls[(row * (col - 1)) + ((row - 1) * col)];
		wallIntialize(row, col, cellWall);

		Random rand = new Random();

		while (sets.find(0) != sets.find((row * col) - 1)) {
			int currentWall = rand.nextInt((row * (col - 1)) + ((row - 1) * col));

			int cell1 = cellWall[currentWall].getnCell1();
			int cell2 = cellWall[currentWall].getnCell2();

			if (sets.find(cell1) != sets.find(cell2)) { 
				System.out.println(cell1 + " " + cell2);
				sets.union(cell1, cell2);
				cellWall[currentWall].setVisible(false);
			}

			if(sets.find(0) == sets.find((row * col) - 1)){
				
				sets.find((row*col)-1);
				System.out.println();
			}

		}

		MazeGen mazeGen = new MazeGen();
		mazeGen.setVisible(true);

		System.out.println();
	}
}

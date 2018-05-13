package turtle;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

/***************************************
 *             14.03.2018              *
 ***************************************/

public class Turtle extends JPanel{

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	private static int GRID_X;
	private static int GRID_Y;
	private static int gridCountX;
	private static int gridCountY;
	private static java.util.List<Point> currentPoints = new ArrayList<>();
	private static TurtlePencil[] turtles;
	private static int currentID = -1;

	private static String filename = "";


	public Turtle(){
		super();

		System.out.println(getSource(new File(filename)));

		GRID_X = WIDTH / this.gridCountX;
		GRID_Y = HEIGHT / this.gridCountY;
	}

	public void start(){

		JFrame f = new JFrame("Turtle Graphics");
		f.setSize(WIDTH, HEIGHT);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		f.setLocationRelativeTo(null);
		f.add(this);
		f.setVisible(true);

		while(true){

			for(int i = 0; i < turtles.length; i++){
				turtles[i].update();
			}

			f.repaint();

			try{
				Thread.sleep(10);
			} catch (InterruptedException excp){
				excp.printStackTrace();
			}
		}

	}

	public void executeLine(String line){

		if(line.length() < 1){ return;}

		if(line.charAt(0) == '#'){
			int pos = line.indexOf('=');
			setAttrib(line.substring(1, pos), line.substring(pos + 1, line.length()));
			return;
		}

		String[] attrib = line.split("=");

		if(attrib[0].equals("ID")) { currentID = Integer.parseInt(attrib[1]); return; }
		else setObjectAttrib(currentID, attrib[0], attrib[1]);

	}

	public void setObjectAttrib(int index, String key, String val){
		if(key.equals("x")){ this.turtles[index].setX(Integer.parseInt(val)); }
		else if(key.equals("y")){ this.turtles[index].setY(Integer.parseInt(val)); }
		else if(key.equals("program")){ this.turtles[index].setInstructions(val); }
	}

	public void setAttrib(String attrib, String value){

		if(attrib.equals("X")) this.gridCountX = Integer.parseInt(value);
		else if (attrib.equals("Y")) this.gridCountY = Integer.parseInt(value);
		else if (attrib.equals("COUNT"))  {
			this.turtles = new TurtlePencil[Integer.parseInt(value)];
			for(int i = 0; i < this.turtles.length; i++){
				this.turtles[i] = new TurtlePencil();
			}
		}

	}

	public String getSource(File file){
		StringBuilder builder = new StringBuilder();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String line = "";
			while((line = reader.readLine()) != null){
				executeLine(line);
				builder.append(line).append("\n");
			}

			for(int i = 0; i < this.turtles.length; i++){
				this.turtles[i].start();
			}

		} catch (IOException excp){
			excp.printStackTrace();
		}

		return builder.toString();
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.BLACK);
		for(TurtlePencil t : turtles){
			currentPoints = t.getPoints();
			for(int i = currentPoints.size() - 1; i > 0; i--){
				Point p1 = currentPoints.get(i);
				Point p2 = currentPoints.get(i - 1);
				g.drawLine(p1.x * GRID_X, p1.y * GRID_Y, p2.x * GRID_X, p2.y * GRID_Y);
			}
		}
	}

	public static void main(String[] args){

		if(args.length < 1){
			JFileChooser jfc = new JFileChooser();
			jfc.showOpenDialog(null);
			filename = jfc.getSelectedFile().getAbsolutePath();
		} 
		else {
			filename = args[0];
		}

		Turtle t = new Turtle();
		t.start();

	}
}

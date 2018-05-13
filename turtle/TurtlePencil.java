package turtle;

import java.awt.Point;
import java.util.ArrayList;

class TurtlePencil {

	private Point pos;
	private Point dir;
	private int ic;
	private String instructions;
	private boolean running = true;
	private java.util.List<Point> points = new ArrayList<>();


	public TurtlePencil(){
		pos = new Point(0, 0);
	}

	public void setX(int x){
		pos.x = x;
	}

	public void setY(int y){
		pos.y = y;
	}

	public void start(){
		points.add(new Point(pos.x, pos.y));
		dir = new Point(0, -1);
	}

	public void setInstructions(String instr){
		this.instructions = instr;
		ic = 0;
	}

	private void execute(){

		if(ic >= this.instructions.length() ){
			running = false;
			return;
		}

		switch(instructions.charAt(ic)){
			case '-' : 	pos.x += dir.x; 
						pos.y += dir.y;
						points.add(new Point(pos.x, pos.y));
						break;
			case '>' : turnRight(); break;
			case '<' : turnLeft(); break;
		};

		ic++;
	}

	public void turnLeft(){
		int x = dir.y;
		dir.y = -dir.x;
		dir.x = x;
	}

	public void turnRight(){
		int x = -dir.y;
		dir.y = dir.x;
		dir.x = x;
	}

	public void update(){
		if(running){
			execute();
		}
	}

	public java.util.List<Point> getPoints(){
		return this.points;
	}

}

package adt;

import java.awt.Point;
import java.util.LinkedList;

import game.scenes.world.World;

/**
 * Tile controls where this unit stands. So when you move this unit you must
 * first check if possible, then update the tiles, not the unit... Unless you
 * move to fight.
 * 
 * @author jonah
 *
 */
public abstract class Unit extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8256060192893431858L;

	protected String name;
	protected int strength;
	protected int size;
	protected int movepointsSTD;
	protected int movepoints;
	protected LinkedList<Point> path;

	public Unit(String animationSource, int amountOfImgs, String faction, String name, int strength, int size,
			int movepointsSTD) {
		super(animationSource, amountOfImgs, faction);
		this.name = name;
		this.strength = strength;
		this.size = size;
		this.movepointsSTD = movepointsSTD;
		movepoints = movepointsSTD;
		path = new LinkedList<Point>();
	}

	/**
	 * Peek at where this unit is headed.
	 */
	public Point peekAtPath(Point p) {
		return path.peek();
	}

	/**
	 * Try to walk to the next tile in the queue.
	 */
	public Point walkThePath(Point p) {
		return path.pop();
	}

	/**
	 * Look at where this unit is headed.
	 */
	public LinkedList<Point> getWholePath() {
		return path;
	}

	/**
	 * Use dijkstras algorithm to find a reverse queue with a valid path. + a bit of forced direction look.
	 */
	public void findPath(World world, Point from, Point to) {

	}

	private void pushToPath(Point p) {
		path.add(p);
	}

	private void setPath(LinkedList<Point> path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return name + ", Strength: " + strength + ", Size: " + size + ", moves left: " + movepoints;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public int getMovepointsSTD() {
		return movepointsSTD;
	}

	public void setMovepointsSTD(int movepointsSTD) {
		this.movepointsSTD = movepointsSTD;
	}

	public int getMovepoints() {
		return movepoints;
	}

	public void setMovepoints(int movepoints) {
		this.movepoints = movepoints;
	}

}

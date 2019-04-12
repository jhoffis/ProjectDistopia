package game.scenes.world;

import java.awt.Point;

/**
 * A linearnode list of points where line are to be drawn from and to. Goes in a
 * circle. Last point points to the first point.
 * 
 * Use the coordinates that world uses - f. eks 0 - 64
 * 
 * Problem: How can you sync one land with anothers?
 * 
 * Problem: You only update two points, but how do you connect these points to
 * the rest of the points and what points should be removed?
 * 
 * 
 * generer ved at man sjekker igjennom hele sånn som i den pathfinding videoen.
 * + bruk av vektorer som peker i retning mot der neste skal være. Det er bare at når en
 * finner 
 * 
 * 
 * @author jhoffis
 *
 */
public class LandPointers {

	private int userID;
	// Color 0-255
	private int r, g, b;
	private LinearNode<Point> head;

	public LandPointers(int userID, int r, int g, int b, Point e0, Point e1) {
		this.userID = userID;
		this.r = r;
		this.g = g;
		this.b = b;

		initFirstSquare(e0, e1);
	}

	private void initFirstSquare(Point e0, Point e1) {
		head = new LinearNode<Point>(e0);
		head.setNext(new LinearNode<Point>(new Point(e1.getX(), e0.getY())));
		head.getNext().setNext(new LinearNode<Point>(e1));
		head.getNext().getNext().setNext(new LinearNode<Point>(new Point(e0.getX(), e1.getY())));
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public LinearNode<Point> getHead() {
		return head;
	}

	public void setHead(LinearNode<Point> head) {
		this.head = head;
	}
}

package adt;

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

	private String name;
	private int strength;
	private int size;
	
	public Unit(String animationSource, int amountOfImgs, int userID, String name, int strength, int size) {
		super(animationSource, amountOfImgs, userID);
		this.name = name;
		this.strength = strength;
		this.size = size;
	}

	@Override
	public String toString() {
		return name + ", " + strength + ", " + size;
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

}

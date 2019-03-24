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

	public Unit(String animationSource, int amountOfImgs, int userID) {
		super(animationSource, amountOfImgs, userID);

	}

}

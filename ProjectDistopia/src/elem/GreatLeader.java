package elem;

import adt.Unit;

public class GreatLeader extends Unit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9184619069531364577L;
	
	
	
	public GreatLeader(String animationSource, int amountOfImgs, String faction, String name) {
		super(animationSource, amountOfImgs, faction, name, 100, 1, 2);
		animation.setAnimation(0, amountOfImgs);
	}



	@Override
	public void tick() {
		animation.updateFrame();
	}


	@Override
	public void resetExpendablePoints() {
		movepoints = movepointsSTD;
	}
}

package elem;

import adt.Unit;

public class GreatLeader extends Unit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9184619069531364577L;
	
	public GreatLeader(String animationSource, int amountOfImgs, int userID, String name) {
		super(animationSource, amountOfImgs, userID, name, 100, 1);
		animation.setAnimation(0, amountOfImgs);
	}



	@Override
	public void tick() {
		animation.updateFrame();
	}


}

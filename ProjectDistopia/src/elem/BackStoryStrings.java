package elem;

public class BackStoryStrings {

	private String aiazom = "Aiazom:\n"
			+ "The crule, but good looking beasts of Aiazom are four million years old. Unified under a single banner by their neverdying godess Aifrohm. These are robots used for sex by the ancient humans from before the Nearend. "
			+ "Through a mere mirical, Aifrohm gained the ability to see, to feel, to think. The wars she brought were devestating. She and her army destroyed the Solar Empire and "
			+ "the Eurasian Alpha Centauri Colonies, and now she has come to Authradgard to complete the end and fullfill her promised prophesy.";
	private String gazellia = "";
	private String jotnatium = "";
	private String wessland = "";
	private String anglia = "";
	private String theilron = "";

	public String getAiazom() {
		return aiazom;
	}

	public String getGazellia() {
		return gazellia;
	}

	public String getJotnatium() {
		return jotnatium;
	}

	public String getWessland() {
		return wessland;
	}

	public String getAnglia() {
		return anglia;
	}

	public String getTheilron() {
		return theilron;
	}

	public String getStory(int i) {
		switch (i) {
		case 0:
			return getAiazom();
		case 1:
			return getGazellia();
		case 2:
			return getJotnatium();
		case 3:
			return getWessland();
		case 4:
			return getAnglia();
		case 5:
			return getTheilron();
		}
		
		return "";
	}

}

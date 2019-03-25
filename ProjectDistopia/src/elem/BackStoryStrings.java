package elem;

public class BackStoryStrings {

	private String aiazom = "Aiazom:\n"
			+ "Created nearly four million years by the Ancient Humans, the beautiful Aiazom were created for one single purpose"
			+ "– to please their creators and realize their sexual fantasies.  Through a miracle, the immortal leader and goddess "
			+ "of the Aiazom, Aifrohm, gained the ability to think independently and used this to unite all the Aiazom under "
			+ "a single banner in order to exact revenge on their cruel masters. "
			+ "As one of the oldest and most advanced races of Earth, the Aiazom brought devastating "
			+ "wars to the other factions, destroying the Solar Empire and the Eurasian Alpha Centauri colonies. "
			+ "Now, Aifrohm have gathered her ardent followers in Authragard, reluctantly fighting alongside her allies, in order to fulfill her prophecy - eliminate humanity once and for all.";

	private String gazellia = "Gazellia:\n"
			+ "Animals suffered much while humankind was at their peak; meat production, big game hunting, skinning, destruction of various habitats, even harvesting of their yet unborn children in the form of eggs! Other animals were made weak as humans domesticated them, forming a dependent bond to their masters. Then, one day, as a human experiment went awry, Otto the Gazelle, managed to escape captivity, later noticing his intelligence increasing at a rapid rate.\r\n"
			+ "    When he reached a genius-level intellect, Otto replicated the experiment on fellow animals and founded Gazellia. As the animals gained a sense of nationalistic pride, Otto, inspired by Hitler himself, took on the mantle of dictator, reformed Nazism, and adopted the surname von Dörnberg. On the battlefield of Authragard, the animals of Gazellia are prepared to reclaim their place in the food chain.";
	private String jotnatium = "Jotnatium:\n"
			+ "Hailing from ancient Norwegian folktales, the Jotne have been enemies, but also sexual partners, to the Norse gods for time immemorial. Then, during a localized Ragnarok, the gods of Asgard was eliminated, allowing the Jotne, with their military prowess, to expand their empire from Jotunheim into the eight other realms, including Midgard – Earth.\r\n"
			+ "    The nobility of the Jotne was satisfied with their conquests and decided to suppress their warlike traditions in favor for peace, which many Jotne disagreed with. One of these was Gronkur, the bastard child of Tor, who led an insurgency, toppling the nobility and positioning himself as the leader of the newly founded Jotnatium. Their conquest throughout the galaxy has finally led them to Authragard, where they now face the remnants of the Midgardians who fled all those years ago.";
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

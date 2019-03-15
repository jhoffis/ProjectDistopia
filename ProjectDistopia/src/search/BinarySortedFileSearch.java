package search;

import java.util.List;

public class BinarySortedFileSearch {

	public int find(List<String> lines, String elem) {
		int amount = Integer.valueOf((String) lines.get(0));
		return binarySearch(lines, 1, amount + 1, elem, true);
	}

	public int findNearestLine(List<String> lines, String elem) {
		int amount = Integer.valueOf((String) lines.get(0));
		return binarySearch(lines, 1, amount + 1, elem, false);
	}

	private int binarySearch(List<String> lines, int min, int max, String target, boolean needExist) {
		int midpoint = (min + max) / 2;

		if (midpoint == lines.size()) {
			if (!needExist)
				return midpoint;
			else
				return -1;
		}

		if (lines.get(midpoint).split("=")[1].toLowerCase().compareTo(target.toLowerCase()) == 0) {
			return midpoint;
		} else if (lines.get(midpoint).split("=")[1].toLowerCase().compareTo(target.toLowerCase()) > 0) {
			if (min <= midpoint - 1) {
				midpoint = binarySearch(lines, min, midpoint - 1, target, needExist);
			} else if (needExist) {
				midpoint = -1;
			}
		} else if (midpoint + 1 <= max) {
			midpoint = binarySearch(lines, midpoint + 1, max, target, needExist);
		} else if (needExist) {
			midpoint = -1;
		}

		return midpoint;
	}
}

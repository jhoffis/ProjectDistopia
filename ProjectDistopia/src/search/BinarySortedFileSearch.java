package search;

import java.util.List;

public class BinarySortedFileSearch {

	public int find(List<String> lines, String elem, int pos) {
		int amount = Integer.valueOf((String) lines.get(0));
		return binarySearch(lines, 1, amount + 1, elem, true, pos);
	}

	public int findNearestLine(List<String> lines, String elem, int pos) {
		int amount = Integer.valueOf((String) lines.get(0));
		return binarySearch(lines, 1, amount + 1, elem, false, pos);
	}

	private int binarySearch(List<String> lines, int min, int max, String target, boolean needExist, int pos) {
		int midpoint = (min + max) / 2;
		int firSplit = pos;
		int secSplit = pos;
		if (pos > 0) {
			secSplit--;
		}

		if (midpoint == lines.size()) {
			if (!needExist)
				return midpoint;
			else
				return -1;
		}
		
		String lineInFile = lines.get(midpoint).split("=")[firSplit].split("#")[secSplit].toUpperCase();
		target = target.toUpperCase();
		
		if (lineInFile.compareTo(target) == 0) {
			return midpoint;
		} else if (lineInFile.compareTo(target) > 0) {
			if (min <= midpoint - 1) {
				midpoint = binarySearch(lines, min, midpoint - 1, target, needExist, pos);
			} else if (needExist) {
				midpoint = -1;
			}
		} else if (midpoint + 1 <= max) {
			midpoint = binarySearch(lines, midpoint + 1, max, target, needExist, pos);
		} else if (needExist) {
			midpoint = -1;
		}

		return midpoint;
	}
}

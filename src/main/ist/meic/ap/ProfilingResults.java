package ist.meic.ap;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map;

public class ProfilingResults {
  static SortedMap<String, Integer> sortedResults = new TreeMap<String, Integer>();

  public static void add(String key) {
    System.out.println("Entering add ------------");

    if (!sortedResults.containsKey(key)) {
      sortedResults.put(key, 0);
      System.out.println("Doesnt contain key");
    }

    sortedResults.put(key, sortedResults.get(key) + 1);
    System.out.println("asdasd " + sortedResults.size());
  }

  public static void printSortedResults() {
    System.out.println("Printing Sorted Results");
    System.out.println("Sorted Results size " + sortedResults.size());

    for (Map.Entry<String, Integer> entry : sortedResults.entrySet()) {
      String[] words = entry.getKey().split(" ");
      System.out.println(words[0] + " " + words[2] + " " + entry.getValue() + " " + words[1]);
    }
  }
}

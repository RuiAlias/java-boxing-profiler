package ist.meic.pa;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map;

public class ProfilingResults {
  static SortedMap<String, Integer> sortedResults = new TreeMap<String, Integer>();

  /*
  static long totalTime = 0;
  
  public static void addTime(long t){
	  totalTime += t;
  }
  
  public static long getTotalTime(){return totalTime;}
  */
  
  public static void add(String key) {
    if (!sortedResults.containsKey(key)) {
      sortedResults.put(key, 0);
    }

    sortedResults.put(key, sortedResults.get(key) + 1);
  }

  public static void printSortedResults() {
    for (Map.Entry<String, Integer> entry : sortedResults.entrySet()) {
      String[] words = entry.getKey().split(" ");
      System.err.println(words[0] + " " + words[2] + " " + entry.getValue() + " " + words[1]);
    }
  }
}

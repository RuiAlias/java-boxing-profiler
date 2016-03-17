package ist.meic.ap;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map;

public class ProfilingResults {
  static SortedMap<String, Integer> sortedResults = new TreeMap<String, Integer>();
  static long startTime = 0;
  static long totalTime = 0;
  
  public static void addStartTime(long t){
	  System.out.println("Start -> "+t);
	  startTime = t;
  }
  
  public static void addEndTime(long t){
	  System.out.println("End -> "+t);
	  totalTime += (t-startTime);
	  startTime = 0;
  }
  
  public static long getTotalTime(){return totalTime;}
  
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

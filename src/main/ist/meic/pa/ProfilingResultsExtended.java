package ist.meic.pa;

/**
 * Auxiliar class to store extended profiling results.
 */
public class ProfilingResultsExtended extends ProfilingResults {
  static long totalTime = 0;

  public static void addTime(long t) {
    totalTime += t;
  }

  public static void printBoxingTime() {
    System.out
        .println("Time Spent (Un)Boxing -> " + totalTime + " milliseconds.");
  }
}

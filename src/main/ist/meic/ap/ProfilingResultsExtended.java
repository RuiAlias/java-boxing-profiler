package ist.meic.ap;

public class ProfilingResultsExtended extends ProfilingResults {
	static long totalTime = 0;
	
	public static void addTime(long t){
		  totalTime += t;
	  }
	
	public static long getTotalTime(){return totalTime;}
}

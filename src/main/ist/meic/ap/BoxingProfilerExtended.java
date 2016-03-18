package ist.meic.ap;

public class BoxingProfilerExtended {
	static long totalTime = 0;
	
	//Count time spent (un)boxing extension
	public static void addTime(long t){
		  //System.out.println("Added -> "+t);
		  totalTime += t;
	  }
	  
	  public static long getTotalTime(){return totalTime;}
	  
}

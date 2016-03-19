package ist.meic.ap;

public class BoxingProfilerTranslatorExtended extends BoxingProfilerTranslator {
	
	public BoxingProfilerTranslatorExtended() {
		
		super.firstHalfTemplate = "{" +
		          "  long start = System.currentTimeMillis();"+
		          "  ist.meic.ap.ProfilingResultsExtended.add(\"";
		
		super.secondHalfTemplate = " %s\");" +
		          "  $_ = $proceed($$);" +
		          "  long end = System.currentTimeMillis();"+
		  	      "  ist.meic.ap.ProfilingResultsExtended.addTime(end-start);" +
		          "}";
	}
}
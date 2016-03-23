package ist.meic.ap;

public class BoxingProfilerTranslatorExtended extends BoxingProfilerTranslator {
	
	public BoxingProfilerTranslatorExtended() {
    super.template = "{" +
      "  long start = System.currentTimeMillis();"+
      "  ist.meic.ap.ProfilingResultsExtended.add(\"%s %s\");" +
      "  $_ = $proceed($$);" +
      "  long end = System.currentTimeMillis();"+
      "  ist.meic.ap.ProfilingResultsExtended.addTime(end-start);" +
      "}";
	}
}

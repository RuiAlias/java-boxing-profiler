package ist.meic.pa;

public class BoxingProfilerTranslatorExtended extends BoxingProfilerTranslator {
	
	public BoxingProfilerTranslatorExtended() {
    super.template = "{" +
      "  long start = System.currentTimeMillis();"+
      "  ist.meic.pa.ProfilingResultsExtended.add(\"%s %s\");" +
      "  $_ = $proceed($$);" +
      "  long end = System.currentTimeMillis();"+
      "  ist.meic.pa.ProfilingResultsExtended.addTime(end-start);" +
      "}";
	}
}

package ist.meic.pa;

/**
 * Sets up boxing and unboxing methods;
 * For every Class loaded, searches for (un)boxing method calls and injects
 * extended profiling code in it;
 */
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

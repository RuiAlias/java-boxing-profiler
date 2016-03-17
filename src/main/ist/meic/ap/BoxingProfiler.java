package ist.meic.ap;

import javassist.*;
import java.util.Arrays;

public class BoxingProfiler {
  public static void main(String[] args)
      throws NotFoundException, CannotCompileException, Throwable {
    if (args.length < 1) {
      System.out.println("Error: Argument missing");
    } else {
      Translator translator = new BoxingProfilerTranslator();
      ClassPool pool = ClassPool.getDefault();
      Loader classLoader = new Loader();
      classLoader.delegateLoadingOf("ist.meic.ap.");
      classLoader.addTranslator(pool, translator);

      String[] restArgs = new String[args.length - 1];
      System.arraycopy(args, 1, restArgs, 0, restArgs.length);
      classLoader.run(args[0], restArgs);

      ProfilingResults.printSortedResults();
      //System.out.println("Time Spent (Un)Boxing -> "+ProfilingResults.getTotalTime());
    }
  }
}

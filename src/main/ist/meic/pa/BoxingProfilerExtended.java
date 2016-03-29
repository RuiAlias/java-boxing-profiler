package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Translator;

/**
 * Program's entry point;
 * Innitializes custom class loader and parses command line arguments;
 * Prints profiling results.
 */
public class BoxingProfilerExtended {
  public static void main(String[] args)
      throws NotFoundException, CannotCompileException, Throwable {
    if (args.length < 1) {
      System.out.println("Error: Argument missing");
    } else {
      Translator translator = new BoxingProfilerTranslatorExtended();
      ClassPool pool = ClassPool.getDefault();
      Loader classLoader = new Loader();
      classLoader.delegateLoadingOf("ist.meic.pa.");
      classLoader.addTranslator(pool, translator);

      String[] restArgs = new String[args.length - 1];
      System.arraycopy(args, 1, restArgs, 0, restArgs.length);
      try {
        classLoader.run(args[0], restArgs);
      } catch (ClassNotFoundException cnfe) {
        System.out.println("Error: Class " + args[0] + " was not found");
      }

      ProfilingResults.printSortedResults();
      System.out.println("Time Spent (Un)Boxing -> "
          + ProfilingResultsExtended.getTotalTime() + " milliseconds.");
    }
  }
}

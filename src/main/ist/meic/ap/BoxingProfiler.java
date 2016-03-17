package ist.meic.ap;

import javassist.*;
import java.util.Arrays;

public class BoxingProfiler {
  public static void main(String[] args)
      throws NotFoundException, CannotCompileException, Throwable {
    if (args.length < 1) {
      System.out.println("Error: Argument missing");
    } else {
      HelpTranslator translator = new HelpTranslator();
      ClassPool pool = ClassPool.getDefault();
      Loader classLoader = new Loader();
      // classLoader.doDelegation = false;
      classLoader.addTranslator(pool, translator);

      String[] restArgs = new String[args.length - 1];
      System.arraycopy(args, 1, restArgs, 0, restArgs.length);
      classLoader.run(args[0], restArgs);
    }
  }
}

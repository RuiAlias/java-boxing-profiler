package ist.meic.ap;

import javassist.*;

public class BoxingProfilerTranslator implements Translator {
  public void start(ClassPool pool)
      throws NotFoundException, CannotCompileException {}

  public void onLoad(ClassPool pool, String className)
      throws NotFoundException, CannotCompileException {
    CtClass ctClass = pool.get(className);
    makeBoxingProfiler(ctClass);
  }

  void makeBoxingProfiler(CtClass ctClass)
      throws NotFoundException, CannotCompileException {}
}

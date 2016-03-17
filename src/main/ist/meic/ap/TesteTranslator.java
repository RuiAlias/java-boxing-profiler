package ist.meic.ap;

import javassist.*;

public class TesteTranslator implements Translator {
  public void start(ClassPool pool)
      throws NotFoundException, CannotCompileException {}

  public void onLoad(ClassPool pool, String className)
      throws NotFoundException, CannotCompileException {
    System.out.println("onLoad: " + className);

    if (className.equals("java.lang.Integer")) {
      System.out.println("Vou alterar o Integer");

      CtClass ctClass = pool.get(className);
      CtMethod intValueMethod = ctClass.getMethod("intValue", "()I");

      String testSrc = "System.out.println(\"text\");";
      intValueMethod.insertBefore(testSrc);
    }
  }
}

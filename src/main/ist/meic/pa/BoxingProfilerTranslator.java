package ist.meic.pa;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

/**
 * Sets up boxing and unboxing methods;
 * For every Class loaded, searches for (un)boxing method calls and injects
 * profiling code in it.
 */
public class BoxingProfilerTranslator implements Translator {
  private static final Set<String> autoboxingMethods =
      Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
          "java.lang.Integer.valueOf(int)",
          "java.lang.Long.valueOf(long)",
          "java.lang.Double.valueOf(double)",
          "java.lang.Float.valueOf(float)",
          "java.lang.Short.valueOf(short)",
          "java.lang.Boolean.valueOf(boolean)",
          "java.lang.Byte.valueOf(byte)",
          "java.lang.Character.valueOf(char)")));

  private static final Set<String> unboxingMethods =
      Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
          "java.lang.Integer.intValue()",
          "java.lang.Long.longValue()",
          "java.lang.Double.doubleValue()",
          "java.lang.Float.floatValue()",
          "java.lang.Short.shortValue()",
          "java.lang.Boolean.booleanValue()",
          "java.lang.Byte.byteValue()",
          "java.lang.Character.charValue()")));

  protected String template =
      "{" +
      "  ist.meic.pa.ProfilingResults.add(\"%s %s\");" +
      "  $_ = $proceed($$);" +
      "}";

  public void start(ClassPool pool)
      throws NotFoundException, CannotCompileException {}

  public void onLoad(ClassPool pool, String className)
      throws NotFoundException, CannotCompileException {
    CtClass ctClass = pool.get(className);

    makeBoxingProfiler(ctClass);
  }

  void makeBoxingProfiler(CtClass ctClass)
      throws NotFoundException, CannotCompileException {
    for (CtBehavior ctBehavior : ctClass.getDeclaredBehaviors()) {
      ctBehavior.instrument(new ExprEditor() {
        public void edit(MethodCall expr) {
          try {
            CtMethod calledMethod = expr.getMethod();

            String key = ctBehavior.getLongName() + " "
                + calledMethod.getDeclaringClass().getName();

            try {
              if (unboxingMethods.contains(calledMethod.getLongName())) {
                expr.replace(String.format(template, key, "unboxed"));
              } else if (autoboxingMethods
                  .contains(calledMethod.getLongName())) {
                expr.replace(String.format(template, key, "boxed"));
              }
            } catch (CannotCompileException e) {
              e.printStackTrace();
            }
          } catch (NotFoundException e) {
            e.printStackTrace();
          }
        }
      });
    }
  }
}

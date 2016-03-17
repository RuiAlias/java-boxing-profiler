package ist.meic.ap;

import java.util.ArrayList;

import javassist.*;
import javassist.expr.*;

public class HelpTranslator implements Translator {
  private ArrayList<CtMethod> autoboxingMethods = new ArrayList<CtMethod>();
  private ArrayList<CtMethod> unboxingMethods = new ArrayList<CtMethod>();

  public void start(ClassPool pool)
      throws NotFoundException, CannotCompileException {
    CtClass integerCc = pool.get("java.lang.Integer");
    CtMethod intValueCm = integerCc.getMethod("intValue", "()I");
    // CtMethod[] integerMethods = integerCc.getMethods();

    // for (CtMethod ctm : integerMethods) {
    // if (ctm.getName().equals("valueOf"))
    // System.out.println(ctm.getSignature());
    // }

    CtMethod valueOfCm =
        integerCc.getMethod("valueOf", "(I)Ljava/lang/Integer;");

    unboxingMethods.add(intValueCm);
    autoboxingMethods.add(valueOfCm);
  }

  public void onLoad(ClassPool pool, String className)
      throws NotFoundException, CannotCompileException {
    if (className.equals("ist.meic.ap.ProfilingResults")) return;

    CtClass ctClass = pool.get(className);

    helpEditor(ctClass);
  }

  void helpEditor(CtClass ctClass)
      throws NotFoundException, CannotCompileException {
    for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
      ctMethod.instrument(new ExprEditor() {
        public void edit(MethodCall expr) {
          // System.out.println("MethodCall " + expr.getClassName() + " "
          //     + expr.getMethodName() + " " + expr.getSignature());

          try {
            CtMethod calledMethod = expr.getMethod();

            String key = ctMethod.getLongName() + " "
              // + calledMethod.getDeclaringClass().getName();
              + expr.getClassName();

            final String template =
              "{" +
              "  ist.meic.ap.ProfilingResults.add(\"" + key + " %s\");" +
              "  $_ = $proceed($$);" +
              "}";

            if (unboxingMethods.contains(calledMethod)) {
              try {
                expr.replace(String.format(template, "unboxed"));
              } catch (CannotCompileException e) {
                e.printStackTrace();
              }
            }

            if (autoboxingMethods.contains(calledMethod)) {
              try {
                expr.replace(String.format(template, "boxed"));
              } catch (CannotCompileException e) {
                e.printStackTrace();
              }
            }
          } catch (javassist.NotFoundException e) {
          }
        }

        public void edit(NewExpr expr) {
          // System.out.println("NewExpr at line " + expr.getLineNumber());
        }
      });
    }
  }
}

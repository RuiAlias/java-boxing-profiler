package ist.meic.ap;

import java.util.ArrayList;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class BoxingProfilerTranslator implements Translator {
  private ArrayList<CtMethod> autoboxingMethods = new ArrayList<CtMethod>();
  private ArrayList<CtMethod> unboxingMethods = new ArrayList<CtMethod>();
  
  protected String template = "{" +
    "  ist.meic.ap.ProfilingResults.add(\"%s %s\");" +
    "  $_ = $proceed($$);" +
    "}";

  public void start(ClassPool pool)
      throws NotFoundException, CannotCompileException {
	  
    CtClass compileClass = pool.get("java.lang.Integer");
    CtMethod ValueCm = compileClass.getMethod("intValue", "()I");
    CtMethod ValueOfCm = compileClass.getMethod("valueOf", "(I)Ljava/lang/Integer;");
    unboxingMethods.add(ValueCm);
    autoboxingMethods.add(ValueOfCm);
    
    compileClass = pool.get("java.lang.Long");
    ValueCm = compileClass.getMethod("longValue", "()J");
    ValueOfCm = compileClass.getMethod("valueOf", "(J)Ljava/lang/Long;");
    unboxingMethods.add(ValueCm);
    autoboxingMethods.add(ValueOfCm);
    
    compileClass = pool.get("java.lang.Double");
    ValueCm = compileClass.getMethod("doubleValue", "()D");
    ValueOfCm = compileClass.getMethod("valueOf", "(D)Ljava/lang/Double;");
    unboxingMethods.add(ValueCm);
    autoboxingMethods.add(ValueOfCm);
    
    compileClass = pool.get("java.lang.Float");
    ValueCm = compileClass.getMethod("floatValue", "()F");
    ValueOfCm = compileClass.getMethod("valueOf", "(F)Ljava/lang/Float;");
    unboxingMethods.add(ValueCm);
    autoboxingMethods.add(ValueOfCm);
    
    compileClass = pool.get("java.lang.Short");
    ValueCm = compileClass.getMethod("shortValue", "()S");
    ValueOfCm = compileClass.getMethod("valueOf", "(S)Ljava/lang/Short;");
    unboxingMethods.add(ValueCm);
    autoboxingMethods.add(ValueOfCm);
    
    compileClass = pool.get("java.lang.Boolean");
    ValueCm = compileClass.getMethod("booleanValue", "()Z");
    ValueOfCm = compileClass.getMethod("valueOf", "(Z)Ljava/lang/Boolean;");
    unboxingMethods.add(ValueCm);
    autoboxingMethods.add(ValueOfCm);
    
    compileClass = pool.get("java.lang.Byte");
    ValueCm = compileClass.getMethod("byteValue", "()B");
    ValueOfCm = compileClass.getMethod("valueOf", "(B)Ljava/lang/Byte;");
    unboxingMethods.add(ValueCm);
    autoboxingMethods.add(ValueOfCm);
    
    compileClass = pool.get("java.lang.Character");
    ValueCm = compileClass.getMethod("charValue", "()C");
    ValueOfCm = compileClass.getMethod("valueOf", "(C)Ljava/lang/Character;");
    unboxingMethods.add(ValueCm);
    autoboxingMethods.add(ValueOfCm);
    
  }

  public void onLoad(ClassPool pool, String className)
      throws NotFoundException, CannotCompileException {
    CtClass ctClass = pool.get(className);

    makeBoxingProfiler(ctClass);
  }

  void makeBoxingProfiler(CtClass ctClass) throws NotFoundException, CannotCompileException {
    for (CtBehavior ctBehavior : ctClass.getDeclaredBehaviors()) {
      ctBehavior.instrument(new ExprEditor() {
        public void edit(MethodCall expr) {
          try {
            CtMethod calledMethod = expr.getMethod();
            
            String key = ctBehavior.getLongName() + " "
               + calledMethod.getDeclaringClass().getName();
            
            if (unboxingMethods.contains(calledMethod)) {
              try {
            	  
            	  expr.replace(String.format(template, key, "unboxed"));
              } catch (CannotCompileException e) {
                e.printStackTrace();
              }
            }

            if (autoboxingMethods.contains(calledMethod)) {
              try {
            	  
            	  expr.replace(String.format(template, key, "boxed"));
              } catch (CannotCompileException e) {
                e.printStackTrace();
              }
            }
          } catch (javassist.NotFoundException e) {
        	  e.printStackTrace();
          }
        }
      });
    }
  }
}

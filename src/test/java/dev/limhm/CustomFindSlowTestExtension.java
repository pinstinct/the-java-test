package dev.limhm;

import java.lang.reflect.Method;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

public class CustomFindSlowTestExtension implements AfterTestExecutionCallback,
    BeforeTestExecutionCallback {

  private long THRESHOLD;

  public CustomFindSlowTestExtension(long THRESHOLD) {
    this.THRESHOLD = THRESHOLD;
  }

  @Override
  public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
    Store store = getStore(extensionContext);
    store.put("START_TIME", System.currentTimeMillis());
  }


  @Override
  public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
    Method requiredTestMethod = extensionContext.getRequiredTestMethod();
    SlowTag annotation = requiredTestMethod.getAnnotation(SlowTag.class);
    String testMethodName = requiredTestMethod.getName();

    Store store = getStore(extensionContext);
    long startTime = store.remove("START_TIME", long.class);
    long duration = System.currentTimeMillis() - startTime;
    if (duration > THRESHOLD && annotation == null) {
      System.out.printf("Please consider mark method [%s] with @SlowTest.\n", testMethodName);
    }
  }


  private Store getStore(ExtensionContext extensionContext) {
    String testClassName = extensionContext.getRequiredTestClass().getName();
    String testMethodName = extensionContext.getRequiredTestMethod().getName();
    return extensionContext.getStore(Namespace.create(testClassName, testMethodName));
  }
}

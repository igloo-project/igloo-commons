package org.iglooproject.commons.util.context;

import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractExecutionContext implements IExecutionContext {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractExecutionContext.class);

  @Override
  public abstract ITearDownHandle open();

  @Override
  public void run(Runnable runnable) {
    try (ITearDownHandle _ = open()) {
      runnable.run();
    }
  }

  @Override
  public <T> T run(Callable<T> callable) throws Exception {
    try (ITearDownHandle _ = open()) {
      return callable.call();
    }
  }

  @Override
  public boolean isReady() {
    try (ITearDownHandle _ = open()) {
      return true;
    } catch (Exception e) {
      LOGGER.debug("{} is not ready to be opened.", getClass().getSimpleName(), e);
      return false;
    }
  }
}

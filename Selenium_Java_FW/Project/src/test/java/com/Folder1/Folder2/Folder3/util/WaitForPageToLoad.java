package com.dt.rts.ca.util;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.google.common.base.Throwables;
import com.thoughtworks.selenium.Wait;
import com.thoughtworks.selenium.webdriven.SeleneseCommand;

public   class WaitForPageToLoad extends SeleneseCommand<Void> {
  Logger log = Logger.getLogger(WaitForPageToLoad.class.getName());
  private int timeToWait = 250;

  /**
   * Overrides the default time to wait (in milliseconds) after a page has finished loading.
   *
   * @param timeToWait the time to wait, in milliseconds, after the page has finished loading
   */
  public void setTimeToWait(int timeToWait) {
    this.timeToWait = timeToWait;
  }

  public Void handleSeleneseCommand(final WebDriver driver, String timeout) {
    // Wait until things look like they've been stable for "timeToWait"
    if (!(driver instanceof JavascriptExecutor)) {
      // Assume that we Do The Right Thing
      return null;
    }

    long timeoutInMillis = Long.parseLong(timeout);

    // Micro sleep before we continue in case an async click needs processing.
    hesitate(timeToWait);

    Object result;
    try {
       result = ((JavascriptExecutor) driver).executeScript(
        "return !!document['readyState'];");
    } catch (WebDriverException e) {
      // Page might still be loading. Give it a chance to get some content.
      hesitate(500);
      try {
        result = ((JavascriptExecutor) driver).executeScript(
          "return !!document['readyState'];");
      } catch (WebDriverException e2) {
        log.warning("Cannot determine whether page supports ready state. Abandoning wait.");
        return null;
      }
    }

    log.fine("Does browser support readyState: " + result);

    Wait wait = (result != null && (Boolean) result) ?
        getReadyStateUsingWait(driver) : getLengthCheckingWait(driver);

    wait.wait(String.format("Failed to load page within %s ms", timeout), timeoutInMillis);

    hesitate(timeToWait);

    return null;
  }

  private void hesitate(long duration) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException e) {
      throw Throwables.propagate(e);
    }
  }

  public Wait getReadyStateUsingWait(final WebDriver driver) {
    return new Wait() {
      public boolean until() {
        try {
          Object result = ((JavascriptExecutor) driver).executeScript("return 'complete' == document.readyState;");

          if (result != null && result instanceof Boolean && (Boolean) result) {
            return true;
          }
        } catch (Exception e) {
          // Possible page reload. Fine
        }
        return false;
      }
    };
  }

  public Wait getLengthCheckingWait(final WebDriver driver) {
    return new Wait() {
      private int length;
      private long seenAt;

      @Override
      public boolean until() {
        // Page length needs to be stable for a second
        try {
          int currentLength = driver.findElement(By.tagName("body")).getText().length();
          if (seenAt == 0) {
            seenAt = System.currentTimeMillis();
            length = currentLength;
            return false;
          }

          if (currentLength != length) {
            seenAt = System.currentTimeMillis();
            length = currentLength;
            return false;
          }

          return System.currentTimeMillis() - seenAt > 1000;
        } catch (NoSuchElementException ignored) {}
        catch (NullPointerException ignored) {}

        return false;
      }
    };
  }

@Override
protected Void handleSeleneseCommand(WebDriver driver, String locator,
		String value) {
	// TODO Auto-generated method stub
	return null;
}
}
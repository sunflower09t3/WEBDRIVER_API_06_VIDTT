package TestNG;

import org.testng.annotations.Test;

public class TestNG_07_Loop {
  @Test (invocationCount=4)
  public void loopMethod() {
	  System.out.println("This is a loop method");
  }
}

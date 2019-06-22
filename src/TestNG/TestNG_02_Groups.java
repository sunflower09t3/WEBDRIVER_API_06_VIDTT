package TestNG;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNG_02_Groups {
  @Test (groups="regression test")
  public void TC_01() {
	  System.out.println("Test case 01");
  }
  
  @Test (groups= {"regression test","smoke test"})
  public void TC_02() {
	  System.out.println("Test case 02");
  }
  
  @Test (groups = "smoke test")
  public void TC_03() {
	  System.out.println("Test case 03");
  }
  
  @Test
  public void TC_04() {
	  System.out.println("Test case 04");
  }
  
  
  @BeforeMethod (alwaysRun = true)
  public void beforeMethod() {
	  System.out.println("Before method");
  }

  @AfterMethod (alwaysRun = true)
  public void afterMethod() {
	  System.out.println("After method");
  }

  @BeforeClass (alwaysRun = true)
  public void beforeClass() {
	  System.out.println("Before class");
  }

  @AfterClass (alwaysRun = true)
  public void afterClass() {
	  System.out.println("After class");
  }

  @BeforeTest (alwaysRun = true)
  public void beforeTest() {
	  System.out.println("Before test");
  }

  @AfterTest (alwaysRun = true)
  public void afterTest() {
	  System.out.println("After test");
  }

  @BeforeSuite (alwaysRun = true)
  public void beforeSuite() {
	  System.out.println("Before suite");
  }

  @AfterSuite (alwaysRun = true)
  public void afterSuite() {
	  System.out.println("After suite");
  }
}

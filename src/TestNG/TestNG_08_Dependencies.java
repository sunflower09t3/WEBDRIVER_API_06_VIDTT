package TestNG;

import org.testng.annotations.Test;


import org.testng.annotations.BeforeTest;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class TestNG_08_Dependencies {
  @Test (groups="group1")
  public void TC_01_AddUser() {
	  System.out.println("TC_01_AddUser");
	  Assert.assertTrue(false);
  }
  
  @Test (groups="group1")
  public void TC_02_EditUser() {
	  System.out.println("TC_02_EditUser");
  }
 
  @Test (dependsOnGroups="group1")
  public void TC_03_DeleteUser() {
	  System.out.println("TC_03_DeleteUser");
  }
  
  @BeforeTest 
  public void beforeTest() {
  }

  @AfterTest
  public void afterTest() {
  }

}

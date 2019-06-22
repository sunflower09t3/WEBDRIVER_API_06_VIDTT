package TestNG;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestNG_06_Parallel {
	WebDriver driver;
	
	@Parameters("browser type")
	@BeforeMethod
	public void setup(String browserType) {
		if (browserType.equals("Firefox")) {
			driver = new FirefoxDriver();
		} else if (browserType.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", ".\\Upload\\chromedriver.exe");
			driver = new ChromeDriver();
		} else {
			System.out.println("Pls select a correct browser");
		}
	}

	@Test
	public void login() {
		driver.get("http://demo.guru99.com/v4/");

		driver.findElement(By.name("uid")).sendKeys("mngr204279 ");
		driver.findElement(By.name("password")).sendKeys("UgUdUhu");
		driver.findElement(By.name("btnLogin")).click();

		Assert.assertEquals("Guru99 Bank Manager HomePage", driver.getTitle());
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
}

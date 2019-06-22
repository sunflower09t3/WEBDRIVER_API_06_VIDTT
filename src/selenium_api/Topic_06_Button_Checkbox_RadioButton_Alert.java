package selenium_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.security.UserAndPassword;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Topic_06_Button_Checkbox_RadioButton_Alert {
	WebDriver driver;

	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01() {
		driver.get("http://live.guru99.com/");
		WebElement myAccountLink = driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
		clickElementByJavascript(myAccountLink);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/login/");
		WebElement createAnAccountButton = driver.findElement(By.xpath("//a[@title='Create an Account']"));
		clickElementByJavascript(createAnAccountButton);
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.guru99.com/index.php/customer/account/create/");
	}

	@Test
	public void TC_02() {
		driver.get("http://demos.telerik.com/kendo-ui/styling/checkboxes");
		WebElement dualZoneCheckbox = driver
				.findElement(By.xpath("//label[text()='Dual-zone air conditioning']/preceding-sibling::input"));
		if (!dualZoneCheckbox.isSelected())
			clickElementByJavascript(dualZoneCheckbox);
		Assert.assertTrue(dualZoneCheckbox.isSelected());
		clickElementByJavascript(dualZoneCheckbox);
		Assert.assertFalse(dualZoneCheckbox.isSelected());
	}

	@Test
	public void TC_03() {
		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
		WebElement petrol147KwRadiobutton = driver
				.findElement(By.xpath("//label[text()='2.0 Petrol, 147kW']/preceding-sibling::input"));
		clickElementByJavascript(petrol147KwRadiobutton);
		Assert.assertTrue(petrol147KwRadiobutton.isSelected());
	}

	@Test
	public void TC_04() {
		driver.get("http://daominhdam.890m.com/");
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		alert.accept();
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@id='result'][contains(text(),'You clicked an alert successfully')]"))
						.isDisplayed());
	}

	@Test
	public void TC_05() {
		driver.get("http://daominhdam.890m.com/");
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		alert.dismiss();
		Assert.assertTrue(
				driver.findElement(By.xpath("//p[@id='result' and text()='You clicked: Cancel']")).isDisplayed());
	}

	@Test
	public void TC_06() {
		driver.get("http://daominhdam.890m.com/");
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		alert.sendKeys("DaoThiTuongVi");
		alert.accept();
		Assert.assertTrue(driver.findElement(By.xpath("//p[@id='result' and text()='You entered: DaoThiTuongVi']"))
				.isDisplayed());
	}

	@Test
	public void TC_07() {
		driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
		Assert.assertTrue(driver
				.findElement(By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]"))
				.isDisplayed());
		// Alert alert = driver.switchTo().alert();
		// alert.authenticateUsing(new UserAndPassword("admin", "admin"));
	}

	public void clickElementByJavascript(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}

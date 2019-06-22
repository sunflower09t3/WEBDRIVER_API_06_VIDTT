package selenium_api;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.sun.jna.platform.win32.OaIdl.ELEMDESC;

import org.testng.annotations.BeforeTest;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Topic_11_Waits {
	WebDriver driver;
	WebDriverWait wait;

	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test 
	public void TC_01_ImplicitWait() {
		/*Step 01 - Truy cập vào trang: 
		http://the-internet.herokuapp.com/dynamic_loading/2
		Step 02 - Define an implicit wait (If you set 2 seconds, test will fail)
		Step 03 - Click the start button
		Step 04 - Wait result text will appear
		Step 05 - Check result text is "Hello World!"*/
		
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		WebElement startButton = driver.findElement(By.xpath("//button[text()='Start']"));
		startButton.click();
		WebElement resultText = driver.findElement(By.xpath("//h4[text()='Hello World!']"));
		Assert.assertTrue(resultText.isDisplayed());
	}
	
	@Test
	public void TC_02_ExplicitWait() {
		/*Step 01 - Truy cập vào trang:
		http://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx
		Step 02 - Wait cho "Date Time Picker" được hiển thị (sử dụng: presence or visibility)
		Step 03 - In ra ngày đã chọn (Before AJAX call) -> hiện tại chưa chọn nên in ra = "No Selected Dates to display."
		Step 04 - Chọn ngày hiện tại (VD: 23/09/2017) (hoặc 1 ngày bất kì tương ứng trong tháng/ năm hiện tại)
		Step 05 - Wait cho đến khi "loader ajax" không còn visible (sử dụng: invisibility)
		Xpath: //div[@class='raDiv']
		Step 06 - Wait cho selected date = 23 được visible ((sử dụng: visibility)
		Xpath: //*[contains(@class,'rcSelected')]//a[text()='23']
		Step 07 - Verify ngày đã chọn bằng = Saturday, September 23, 2017*/
		
		//Step 1
		driver.get("http://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		
		//Step 2
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='ctl00_ContentPlaceholder1_Panel1']")));
		
		//Step 3
		System.out.println(driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")).getText());
		
		//Step 4
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='ctl00_ContentPlaceholder1_RadCalendar1_Top']//a[text()='5']")));
		driver.findElement(By.xpath("//table[@id='ctl00_ContentPlaceholder1_RadCalendar1_Top']//a[text()='5']")).click();
		
		//Step 5
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='raDiv']")));
		
		
		//Step 6
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='rcSelected']/a[text()='5']")));
		//Monday, November 05, 2018
		
		//Step 7
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']")).getText(), "Monday, November 05, 2018");
	}
	
	@Test
	public void TC_03_FluentWait() {
		/*Step 01 - Truy cập vào trang: 
		https://daominhdam.github.io/fluent-wait/
		Step 02 - Wait cho đến khi countdown time được visible (visibility)
		Step 03 - Sử dụng Fluent wait để:
		Mỗi 1s kiểm tra countdount= 00 được xuất hiện trên page hay chưa (giây đếm ngược về 00)
		Tức là trong vòng 15s (tổng thời gian), cứ mỗi 1 giây verify xem nó đã đếm ngược về giây 00 hay chưa*/
		
		driver.get("https://daominhdam.github.io/fluent-wait/");
		WebElement countDown =  driver.findElement(By.xpath("//div[@id='javascript_countdown_time']"));
		wait.until(ExpectedConditions.visibilityOf(countDown));
		Wait<WebElement> wait = new FluentWait<WebElement>(countDown)
				.withTimeout(15, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS);
		
		Boolean secondReachZero = wait.until(new Function<WebElement, Boolean>() {
			public Boolean apply(WebElement element) {
				System.out.println(element.getText());
				return element.getText().endsWith("00");
			}
		});
		}
	

	@AfterTest
	public void afterTest() {
		//driver.quit();
	}

}

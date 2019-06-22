package selenium_api;

import org.testng.annotations.Test;

import sun.security.pkcs11.wrapper.CK_LOCKMUTEX;

import org.testng.annotations.BeforeTest;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Topic_08_Iframe_WindowPopup {
	WebDriver driver;

	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test (enabled=false)
	public void TC_01_HandleIframe() {
		/*
		Step 01 - Truy cập vào trang: http://www.hdfcbank.com/ 
		Step 02 - Close popup nếu có hiển thị (switch qua iframe nếu có) - F5 (refresh page) nhiều lần thì sẽ xuất hiện popup
		Step 03 - Verify đoạn text được hiển thị:  What are you looking for? (switch qua iframe nếu có)
		Step 04: Verify banner image được hiển thị (switch qua iframe nếu có)
				Verify banner có đúng 6 images
		Step 05 - Verify flipper banner được hiển thị và có 8 items
		 */
		/*--------------Step 1--------------*/
		driver.get("http://www.hdfcbank.com/");
		
		/*--------------Step 2--------------*/
		List<WebElement> notificationIframe = driver
				.findElements(By.xpath("//iframe[@id='vizury-notification-template']"));
		if (notificationIframe.size() > 0) {
			System.out.println("size: " + notificationIframe.size());
			Assert.assertTrue(notificationIframe.get(0).isDisplayed());
			driver.switchTo().frame(notificationIframe.get(0));
			WebElement closeButton = driver.findElement(By.xpath("//div[@id='div-close']"));
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].click();", closeButton);
			driver.switchTo().defaultContent();
		}
		
		/*--------------Step 3--------------*/
		WebElement lookingForIframe = driver.findElement(By.xpath("//div[@class='flipBannerWrap']//iframe"));
		driver.switchTo().frame(lookingForIframe);
		assertTrue(driver.findElement(By.xpath("//span[@id='messageText' and text()='What are you looking for?']")).isDisplayed());
		driver.switchTo().defaultContent();
		
		/*--------------Step 4--------------*/
		WebElement banerIframe = driver.findElement(By.xpath("//div[@class='slidingbanners']//iframe"));
		driver.switchTo().frame(banerIframe);
		Assert.assertEquals(driver.findElements(By.xpath("//div[@id='productcontainer']//img[@class='bannerimage']")).size(), 6);
		driver.switchTo().defaultContent();
		
		/*--------------Step 5--------------*/
		List <WebElement> flipBannerImages = driver.findElements(By.xpath("//div[@class='flipBanner']//img[@class='front icon']"));
		Assert.assertEquals(flipBannerImages.size(), 8);
		for(WebElement image: flipBannerImages) {
			Assert.assertTrue(image.isDisplayed());
		}
		
	}
	
	@Test (enabled=false)
	public void TC_02_HandleWindowPopup1() {
			/*Step 01 - Truy cập vào trang: https://daominhdam.github.io/basic-form/index.html
			Step 02 - Click "Opening a new window: Click Here" link -> Switch qua tab mới
			Step 03 - Kiểm tra title của window mới = Google
			Step 04 - Close window mới
			Step 05 - Switch về parent window
			Step 06 - Kiểm tra đã quay về parent window thành công (title/ url)*/
		driver.get("https://daominhdam.github.io/basic-form/index.html");
		String parentWindowHandle = driver.getWindowHandle();
		System.out.println(parentWindowHandle);
		driver.findElement(By.xpath("//a[text()='Click Here']")).click();
		
		switchToChildWindow(parentWindowHandle);
		Assert.assertEquals(driver.getTitle(), "Google");
		
		WebElement searchBox = driver.findElement(By.xpath("//input[@id='lst-ib']"));
		searchBox.sendKeys("selenium");
		searchBox.submit();
		
		driver.close();
				
		driver.switchTo().window(parentWindowHandle);
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");
		
	}
	
	@Test
	public void TC_03_HandleWindowPopup2() {
			/*Step 01 - Truy cập vào trang: http://www.hdfcbank.com/
			Step 02 - Kiểm tra và close quảng cáo nếu có xuất hiện
			Step 03 - Click Angri link -> Mở ra tab/window mới -> Switch qua tab mới
			Step 04 - Click Account Details link -> Mở ra tab/window mới -> Switch qua tab mới
			Step 05- Click Privacy Policy link (nằm trong frame) -> Mở ra tab/window mới -> Switch qua tab mới
			Step 06- Click CSR link on Privacy Policy page
			Step 07 - Close tất cả popup khác - chỉ giữ lại parent window (http://www.hdfcbank.com/)
				Back về parent windows*/
		
		driver.get("http://www.hdfcbank.com/");
		String parentWindowHandle = driver.getWindowHandle();
		
		List<WebElement> notificationIframe = driver
				.findElements(By.xpath("//iframe[@id='vizury-notification-template']"));
		if (notificationIframe.size() > 0) {
			System.out.println("size: " + notificationIframe.size());
			Assert.assertTrue(notificationIframe.get(0).isDisplayed());
			driver.switchTo().frame(notificationIframe.get(0));
			WebElement closeButton = driver.findElement(By.xpath("//div[@id='div-close']"));
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].click();", closeButton);
			driver.switchTo().defaultContent();
		}
		
		WebElement agriLink = driver.findElement(By.xpath("//a[text()='Agri']"));
		agriLink.click();
		
		switchToChildWindowByTitle("HDFC Bank Kisan Dhan Vikas e-Kendra");
		
		WebElement accountDetailsLink = driver.findElement(By.xpath("//p[text()='Account Details']"));
		accountDetailsLink.click();
		
		switchToChildWindowByTitle("Welcome to HDFC Bank NetBanking");
		
		WebElement footerFrame = driver.findElement(By.xpath("//frame[@name='footer']"));
		driver.switchTo().frame(footerFrame);
		WebElement privacyPolicyLink = driver.findElement(By.xpath("//a[text()='Privacy Policy']"));
		privacyPolicyLink.click();
		driver.switchTo().parentFrame();
		
		switchToChildWindowByTitle("HDFC Bank - Leading Bank in India, Banking Services, Private Banking, Personal Loan, Car Loan");
		WebElement csrLink = driver.findElement(By.xpath("//a[text()='CSR']"));
		csrLink.click();
		
		closeAllChildWindows(parentWindowHandle);
		WebElement searchBox = driver.findElement(By.xpath("//input[@class='searchbox']"));
		searchBox.sendKeys("loan");
		
	}
	
	//Use this function if there are only 2 windows (parent and 1 child)
		public void switchToChildWindow(String parentWindowHandle) {
			Set<String> allWindows = driver.getWindowHandles();
			for(String windowHandle: allWindows) {
				if(!windowHandle.equals(parentWindowHandle)) {
					System.out.println(parentWindowHandle);
					driver.switchTo().window(windowHandle);
					break;
				}
			}
		}
		
		public void switchToChildWindowByTitle(String title) {
			Set<String> allWindowHandles = driver.getWindowHandles();
			for(String windowHandle: allWindowHandles) {
				driver.switchTo().window(windowHandle);
				if(driver.getTitle().equals(title)) {
					break;
				}
			}
		}
		
		public void closeAllChildWindows(String parentWindowHandle) {
			Set<String> allWindowHandles = driver.getWindowHandles();
			for(String windowHandle: allWindowHandles) {
				if(!windowHandle.equals(parentWindowHandle)) {
					driver.switchTo().window(windowHandle);
					driver.close();
				}
			}
			driver.switchTo().window(parentWindowHandle);
			Assert.assertEquals(driver.getWindowHandles().size(), 1);
		}

	@AfterTest
	public void afterTest() {
		// driver.quit();
	}

}

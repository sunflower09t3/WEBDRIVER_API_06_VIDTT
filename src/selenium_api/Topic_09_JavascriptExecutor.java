package selenium_api;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Topic_09_JavascriptExecutor {
	WebDriver driver;

	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test 
	public void TC_01_JavascriptExecutor() {
		/*Step 01 - Truy cập vào trang: http://live.guru99.com/
		Step 02 - Sử dụng JE để get domain của page
		Verify domain =  live.guru99.com
		Step 03 - Sử dụng JE để get URL của page
		Verify URL =  http://live.guru99.com/
		Step 04 - Open MOBILE page (Sử dụng JE)
		Step 05 - Add sản phẩm SAMSUNG GALAXY vào Cart (click vào ADD TO CART button bằng JE)
		Hướng dẫn: sử dụng following-sibling
		Step 06 - Verify message được hiển thị:  Samsung Galaxy was added to your shopping cart. (Sử dụng JE - Get innertext of the entire webpage )
		Step 07 - Open PRIVACY POLICY page (Sử dụng JE)
		Verify title của page = Privacy Policy (Sử dụng JE)
		Step 08 - Srcoll xuống cuối page
		Step 09 - Verify dữ liệu có hiển thị với chỉ 1 xpath: 
		WISHLIST_CNT	The number of items in your Wishlist.
		Hướng dẫn: sử dụng following-sibling
		Step 10 - Navigate tới domain: http://demo.guru99.com/v4/  (Sử dụng JE)
		Verify domain sau khi navigate = demo.guru99.com*/
		
		driver.get("http://live.guru99.com/");
		
		String domain = executeJavascript("return document.domain").toString();
		Assert.assertEquals(domain, "live.guru99.com");
		
		String url = executeJavascript("return document.URL").toString();
		Assert.assertEquals(url, "http://live.guru99.com/");
		
		WebElement mobileLink = driver.findElement(By.xpath("//a[text()='Mobile']"));
		clickOnElementJS(mobileLink);
		System.out.println(executeJavascript("return document.title"));
		Assert.assertEquals(executeJavascript("return document.title"), "Mobile");
		
		WebElement samsungAddToCartButton = driver.findElement(By.xpath("//h2[a[text()='Samsung Galaxy']]/following-sibling::div[@class='actions']/button"));
		clickOnElementJS(samsungAddToCartButton);
		Assert.assertTrue(executeJavascript("return document.documentElement.innerText;").toString().contains("Samsung Galaxy was added to your shopping cart."));
		
		WebElement privacyPolicyLink = driver.findElement(By.xpath("//a[text()='Privacy Policy']"));
		clickOnElementJS(privacyPolicyLink);
		
		Assert.assertEquals(executeJavascript("return document.title"), "Privacy Policy");
		
		scrollToBottom();
		
		Assert.assertTrue(driver.findElement(By.xpath("//th[text()='WISHLIST_CNT']/following-sibling::td[text()='The number of items in your Wishlist.']")).isDisplayed());
		
		navigateToUrl("http://demo.guru99.com/v4/");
		Assert.assertEquals(executeJavascript("return document.domain").toString(), "demo.guru99.com");
	}
	
	@Test 
	public void TC_02_RemoveAttributeInDom() {
		/*Step 01 - Truy cập vào trang: https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_input_disabled
		Step 02 - Remove thuộc tính disabled của field Last name
		Switch qua iframe nếu có
		Step 03 - Sendkey vào field Last name
		Eg. Automation Testing
		Step 04 - Click Submit button
		Step 05 - Verify dữ liệu sau khi submit chứa đoạn text đã fill trong field Lastname (Automation Testing)*/
		
		driver.navigate().to("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_input_disabled");
		WebElement iframeResult = driver.findElement(By.xpath("//iframe[@id='iframeResult']"));
		driver.switchTo().frame(iframeResult);
		WebElement lastNameTextbox = driver.findElement(By.xpath("//input[@name='lname']"));
		removeAttributeInDom(lastNameTextbox, "disabled");
		sendKeysToElement(lastNameTextbox, "Testing");
		WebElement firstNameTextbox = driver.findElement(By.xpath("//input[@name='fname']"));
		sendKeysToElement(firstNameTextbox, "Automation");
		WebElement submitButton = driver.findElement(By.xpath("//input[@value='Submit']"));
		clickOnElementJS(submitButton);
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'fname=Automation&lname=Testing')]")).isDisplayed());
		driver.switchTo().defaultContent();
	}
	
	@Test
	public void TC_03_CreateAnAccount() throws Exception {
		/*Step 01 - Truy cập vào trang: http://live.guru99.com/
		Step 02 - Click vào link "My Account" để tới trang đăng nhập (Sử dụng JE)
		Step 03 - Click CREATE AN ACCOUNT button để tới trang đăng kí tài khoản (Sử dụng JE)
		Step 04 - Nhập thông tin hợp lệ vào tất cả các field: First Name/ Last Name/ Email Address/ Password/ Confirm Password (Sử dụng JE)
		(Lưu ý: Tạo random cho dữ liệu tại field Email Address)
		Step 05 - Click REGISTER button (Sử dụng JE)
		Step 05 - Verify message xuất hiện khi đăng kí thành công: Thank you for registering with Main Website Store. (Sử dụng JE)
		Step 06 - Logout khỏi hệ thống (Sử dụng JE)
		Step 07 - Kiểm tra hệ thống navigate về Home page sau khi logout thành công (Sử dụng JE)*/
		
		driver.get("http://live.guru99.com/");
		WebElement myAccountLink = driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
		clickOnElementJS(myAccountLink);
		Assert.assertEquals(driver.getTitle(), "Customer Login");
		
		WebElement createAnAccountButton = driver.findElement(By.xpath("//a[@title='Create an Account']"));
		clickOnElementJS(createAnAccountButton);
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
		
		WebElement firstNameTextbox = driver.findElement(By.xpath("//input[@id='firstname']"));
		WebElement middleNameTextbox = driver.findElement(By.xpath("//input[@id='middlename']"));
		WebElement lastNameTextbox = driver.findElement(By.xpath("//input[@id='lastname']"));
		WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='email_address']"));
		WebElement passwordTextbox = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement confirmPasswordTextbox = driver.findElement(By.xpath("//input[@id='confirmation']"));
		
		sendKeysToElement(firstNameTextbox, "Dao");
		sendKeysToElement(lastNameTextbox, "Vi");
		sendKeysToElement(middleNameTextbox, "Thi Tuong");
		sendKeysToElement(emailTextbox, generateRandomEmailAddress());
		sendKeysToElement(passwordTextbox, "123456");
		sendKeysToElement(confirmPasswordTextbox, "123456");
		
		WebElement submitButton = driver.findElement(By.xpath("//button[@title='Register']"));
		clickOnElementJS(submitButton);
		
		Assert.assertTrue(driver.findElement(By.xpath("//span[text()='Thank you for registering with Main Website Store.']")).isDisplayed());
		
		WebElement AccountLink = driver.findElement(By.xpath("//span[text()='Account']"));
		clickOnElementJS(AccountLink);
		WebElement logoutMenuitem = driver.findElement(By.xpath("//a[text()='Log Out']"));
		clickOnElementJS(logoutMenuitem);
		
		Thread.sleep(10000);
		
		Assert.assertTrue(executeJavascript("return document.title").equals("Home page"));
	}
	
	public Object executeJavascript(String script) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(script);
	}
	
	public Object clickOnElementJS(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].click();", element);
	}
	
	public Object scrollToBottom() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.scrollBy(0, document.body.scrollHeight);");
	}

	public Object navigateToUrl(String url) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("window.location='" + url + "';");
		
	}
	
	public Object removeAttributeInDom(WebElement element, String attributeName) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].removeAttribute('" + attributeName + "')", element);
	}
	
	public Object sendKeysToElement(WebElement element, String keys) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("arguments[0].value='"+ keys +"'", element);
	}
	
	public String generateRandomEmailAddress() {
		Random random = new Random();
		int number = random.nextInt(9999999);
		return "daothituongvi" + number + "@gmail.com";
	}
	
	@AfterTest
	public void afterTest() {
		//driver.quit();
	}

}

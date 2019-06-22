package selenium_api;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Topic_03_WebDriver_WebElement {
	WebDriver driver;

	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_VerifyURLAndTitle() {
		//Navigate to http://live.guru99.com 
		driver.get("http://live.guru99.com");
		
		//Verify the home page's title
		String homePageTitle = driver.getTitle();
		Assert.assertEquals(homePageTitle, "Home page");
		
		//Click My account link
		WebElement myAccountLink = driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
		Assert.assertTrue(myAccountLink.isDisplayed());
		myAccountLink.click();
		
		//Click Create an account button
		WebElement createAccountButton = driver.findElement(By.xpath("//span[text()='Create an Account']"));
		Assert.assertTrue(createAccountButton.isDisplayed());
		createAccountButton.click();
		
		//Back to Login page
		driver.navigate().back();
		
		//Verify Login page's URL
		String loginPageURL = driver.getCurrentUrl();
		Assert.assertEquals(loginPageURL, "http://live.guru99.com/index.php/customer/account/login/");
		
		//Forward to Create an account page
		driver.navigate().forward();
		
		//Verify the Create an account page's URL
		String registerPageURL = driver.getCurrentUrl();
		Assert.assertEquals(registerPageURL, "http://live.guru99.com/index.php/customer/account/create/");
	}
	
	@Test 
	public void TC_02_LoginWithEmptyUserNameAndPassword() {
		//Navigate to http://live.guru99.com/
		driver.navigate().to("http://live.guru99.com/");
		
		//Click My account link
		WebElement myAccountLink = driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
		Assert.assertTrue(myAccountLink.isDisplayed());
		myAccountLink.click();
		
		//Click Login button without filling in Username and Password fields
		WebElement loginButton = driver.findElement(By.xpath("//button[@id='send2']"));
		Assert.assertTrue(loginButton.isDisplayed());
		loginButton.click();
		
		//Verify error messages are displayed
		WebElement emptyEmailErrorMsg = driver.findElement(By.xpath("//div[@id='advice-required-entry-email'][text()='This is a required field.']"));
		WebElement emptyPasswordErrorMsg = driver.findElement(By.xpath("//div[@id='advice-required-entry-pass'][text()='This is a required field.']"));
		Assert.assertTrue(emptyEmailErrorMsg.isDisplayed());
		Assert.assertTrue(emptyPasswordErrorMsg.isDisplayed());
	}
	
	@Test 
	public void TC_03_LoginWithInvalidEmail() {
		//Navigate to http://live.guru99.com/
		driver.get("http://live.guru99.com/");
		
		//Click My account link
		WebElement myAccountLink = driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
		Assert.assertTrue(myAccountLink.isDisplayed());
		myAccountLink.click();
		
		//Input an invalid email
		WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='email']"));
		emailTextbox.sendKeys("123434234@12312.123123");
		
		//Submit the form
		emailTextbox.submit();
		
		//Verify validation error message for email field is displayed
		WebElement invalidEmailErrorMsg = driver.findElement(By.xpath("//div[@id='advice-validate-email-email'][text()='Please enter a valid email address. For example johndoe@domain.com.']"));
		Assert.assertTrue(invalidEmailErrorMsg.isDisplayed());
	}
	
	@Test 
	public void TC_04_LoginWithIncorrectPassword() {
		//Navigate to http://live.guru99.com/
		driver.get("http://live.guru99.com/");
		
		//Click My account link
		WebElement myAccountLink = driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
		Assert.assertTrue(myAccountLink.isDisplayed());
		myAccountLink.click();
		
		//Input a vaild email
		WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='email']"));
		emailTextbox.sendKeys("automation@gmail.com");
		
		//Input an incorrect password
		WebElement passwordTextbox = driver.findElement(By.xpath("//input[@id='pass']"));
		passwordTextbox.sendKeys("123");
		
		//Submit the form
		passwordTextbox.submit();
		
		//Verify validation error message for password field is displayed
		WebElement invalidPasswordErrorMsg = driver.findElement(By.xpath("//div[@id='advice-validate-password-pass'][text()='Please enter 6 or more characters without leading or trailing spaces.']"));
		Assert.assertTrue(invalidPasswordErrorMsg.isDisplayed());
				
	}

	@Test 
	public void TC_05_CreateAnAccount() {
		//Navigate to http://live.guru99.com/
		driver.get("http://live.guru99.com/");
		
		//Click My account link
		WebElement myAccountLink = driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']"));
		Assert.assertTrue(myAccountLink.isDisplayed());
		myAccountLink.click();
		
		//Click Create an account button
		WebElement createAccountButton = driver.findElement(By.xpath("//span[text()='Create an Account']"));
		Assert.assertTrue(createAccountButton.isDisplayed());
		createAccountButton.click();
		
		//Fill in the registration form
		WebElement firstNameTextbox = driver.findElement(By.xpath("//input[@id='firstname']"));
		WebElement middleNameTextbox = driver.findElement(By.xpath("//input[@id='middlename']"));
		WebElement lastNameTextbox = driver.findElement(By.xpath("//input[@id='lastname']"));
		WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='email_address']"));
		WebElement passwordTextbox = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement confirmPasswordTextbox = driver.findElement(By.xpath("//input[@id='confirmation']"));
		firstNameTextbox.sendKeys("Dao");
		middleNameTextbox.sendKeys("Thi Tuong");
		lastNameTextbox.sendKeys("Vi");
		emailTextbox.sendKeys(generateRandomEmail());
		passwordTextbox.sendKeys("123456");
		confirmPasswordTextbox.sendKeys("123456");
		
		//Click Register button
		WebElement registerButton = driver.findElement(By.xpath("//span[text()='Register']"));
		Assert.assertTrue(registerButton.isDisplayed());
		registerButton.click();
		
		//Verify congratulation message is displayed
		WebElement registerSuccessMsg = driver.findElement(By.xpath("//span[text()='Thank you for registering with Main Website Store.']"));
		Assert.assertTrue(registerSuccessMsg.isDisplayed());
		
		//Logout
		WebElement headerAccountLink = driver.findElement(By.xpath("//div[@class='page-header-container']//span[text()='Account']"));
		Assert.assertTrue(headerAccountLink.isDisplayed());
		headerAccountLink.click();
		WebElement logoutLink = driver.findElement(By.xpath("//a[text()='Log Out']"));
		Assert.assertTrue(logoutLink.isDisplayed());
		logoutLink.click();
		
		//Wait until the home page is displayed
		WebElement homePageH2Element = driver.findElement(By.xpath("//h2[contains(text(),'This is demo site for')]"));
		Assert.assertTrue(homePageH2Element.isDisplayed());
		
		//Verify the browser is redirected to the home page after logging out
		String currentPageTitle = driver.getTitle();
		Assert.assertEquals(currentPageTitle, "Home page");
		
	}
	
	@Test 
	public void TC_06_VerifyElementIsDisplayed() {
		//Navigate to http://daominhdam.890m.com/
		driver.get("http://daominhdam.890m.com/");
		
		//Locate elements
		WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='mail']"));
		WebElement ageUnder18RadioButton = driver.findElement(By.xpath("//input[@id='under_18']"));
		WebElement educationTextArea = driver.findElement(By.xpath("//textarea[@id='edu']"));
		
		//Verify above elements are displayed
		if(emailTextbox.isDisplayed()) {
			emailTextbox.sendKeys("Automation Testing");
		}
		
		if(educationTextArea.isDisplayed()) {
			educationTextArea.sendKeys("Automation Testing");
		}
		
		if(ageUnder18RadioButton.isDisplayed()) {
			ageUnder18RadioButton.click();
		}
	}
	
	@Test 
	public void TC_07_VerifyElementIsEnabled() {
		//Navigate to http://daominhdam.890m.com/
		driver.get("http://daominhdam.890m.com/");
		
		//Locate elements
		WebElement emailTextbox = driver.findElement(By.xpath("//input[@id='mail']"));
		WebElement ageUnder18RadioButton = driver.findElement(By.xpath("//input[@id='under_18']"));
		WebElement educationTextArea = driver.findElement(By.xpath("//textarea[@id='edu']"));
		WebElement jobRole1Dropdown = driver.findElement(By.xpath("//select[@id='job1']"));
		WebElement developmentCheckbox = driver.findElement(By.xpath("//input[@id='development']"));
		WebElement slider1 = driver.findElement(By.xpath("//input[@id='slider-1']"));
		WebElement buttonIsEnabled = driver.findElement(By.xpath("//button[@id='button-enabled']"));
		WebElement passwordTextbox = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement radioButtonIsDisabled = driver.findElement(By.xpath("//input[@id='radio-disabled']"));
		WebElement biographyTextArea = driver.findElement(By.xpath("//textarea[@id='bio']"));
		WebElement jobRole2Dropdown = driver.findElement(By.xpath("//select[@id='job2']"));
		WebElement checkboxIsDisabled = driver.findElement(By.xpath("//input[@id='check-disbaled']"));
		WebElement slider2 = driver.findElement(By.xpath("//input[@id='slider-2']"));
		WebElement buttonIsDisabled = driver.findElement(By.xpath("//button[@id='button-disabled']"));
		
		//Verify if elements are enabled/disabled
		isElementEnabled(emailTextbox);
		isElementEnabled(ageUnder18RadioButton);
		isElementEnabled(educationTextArea);
		isElementEnabled(jobRole1Dropdown);
		isElementEnabled(developmentCheckbox);
		isElementEnabled(slider1);
		isElementEnabled(buttonIsEnabled);
		isElementEnabled(passwordTextbox);
		isElementEnabled(radioButtonIsDisabled);
		isElementEnabled(biographyTextArea);
		isElementEnabled(jobRole2Dropdown);
		isElementEnabled(checkboxIsDisabled);
		isElementEnabled(slider2);
		isElementEnabled(buttonIsDisabled);
		
	}
	
	@Test 
	public void TC_08_VerifyElementIsSelected() {
		//Navigate to http://daominhdam.890m.com/
		driver.get("http://daominhdam.890m.com/");
		
		//Locate elements
		WebElement ageUnder18RadioButton = driver.findElement(By.xpath("//input[@id='under_18']"));
		WebElement developmentCheckbox = driver.findElement(By.xpath("//input[@id='development']"));
		
		//Select those elements
		Assert.assertTrue(ageUnder18RadioButton.isDisplayed());
		ageUnder18RadioButton.click();
		Assert.assertTrue(developmentCheckbox.isDisplayed());
		developmentCheckbox.click();
		
		//Verify elements are selected, if not, select again
		if(!ageUnder18RadioButton.isSelected()) {
			ageUnder18RadioButton.click();
		}
		
		if(!developmentCheckbox.isSelected()) {
			developmentCheckbox.click();
		}
	}
	
	public String generateRandomEmail() {
		Random random = new Random();
		int number = random.nextInt(9999);
		String randomEmailAddress = "daothituongvi" + number + "@gmail.com";
		return randomEmailAddress;
	}
	
	public void isElementEnabled(WebElement element) {
		if(element.isEnabled())
			System.out.println("Element is enabled");
		else 
			System.out.println("Element is disabled");
	}
	
	@AfterTest
	public void afterTest() {
		//driver.quit();
	}

}

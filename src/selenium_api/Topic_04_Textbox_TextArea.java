package selenium_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Topic_04_Textbox_TextArea {
	WebDriver driver;
	String loginUserID, loginPassword, newCustomerName, newCustomerGender, newCustomerDateOfBirth, newCustomerAddress,
			newCustomerCity, newCustomerState, newCustomerPin, newCustomerMobileNumber, newCustomerEmail,
			newCustomerPassword, newCustomerID, editCustomerAddress, editCustomerState, editCustomerCity, 
			editCustomerPin, editCustomerMobileNumber, editCustomerEmail;

	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://demo.guru99.com/v4");

		loginUserID = "mngr155533";
		loginPassword = "aqAtAda";

		newCustomerName = "Dao Thi Tuong Vi";
		newCustomerAddress = "K137 Nguyen Phuoc Nguyen";
		newCustomerCity = "Da Nang";
		newCustomerDateOfBirth = "1991-02-01";
		newCustomerEmail = generateRamdomEmail();
		newCustomerMobileNumber = "01692803584";
		newCustomerPassword = "111111";
		newCustomerPin = "123456";
		newCustomerState = "Thank Khe";
		newCustomerGender = "female";

		editCustomerAddress = "455 Hoang Dieu";
		editCustomerCity = "HCM";
		editCustomerEmail = generateRamdomEmail();
		editCustomerMobileNumber = "987654321";
		editCustomerPin = "222222";
		editCustomerState = "Binh Thanh";
	}

	@Test
	public void TC_01_CreateNewCustomer() {
		// Login with an existing account (User = mngr155533 | Pass = aqAtAda)
		driver.findElement(By.xpath("//input[@name='uid']")).sendKeys(loginUserID);
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys(loginPassword);
		driver.findElement(By.xpath("//input[@name='password']")).submit();

		// Verify home page is displayed
		Assert.assertTrue(driver.findElement(By.xpath("//marquee[text()=\"Welcome To Manager's Page of Guru99 Bank\"]")).isDisplayed());

		// Select New customer menu item
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();

		// Fill in the registration form
		driver.findElement(By.xpath("//input[@name='name']")).sendKeys(newCustomerName);
		driver.findElement(By.xpath("//input[@name='rad1'][@value='f']")).click();
		driver.findElement(By.xpath("//input[@name=\"dob\"]")).sendKeys(newCustomerDateOfBirth);
		driver.findElement(By.xpath("//textarea[@name='addr']")).sendKeys(newCustomerAddress);
		driver.findElement(By.xpath("//input[@name='city']")).sendKeys(newCustomerCity);
		driver.findElement(By.xpath("//input[@name=\"state\"]")).sendKeys(newCustomerState);
		driver.findElement(By.xpath("//input[@name=\"pinno\"]")).sendKeys(newCustomerPin);
		driver.findElement(By.xpath("//input[@name=\"telephoneno\"]")).sendKeys(newCustomerMobileNumber);
		driver.findElement(By.xpath("//input[@name=\"emailid\"]")).sendKeys(newCustomerEmail);
		driver.findElement(By.xpath("//input[@name=\"password\"]")).sendKeys(newCustomerPassword);
		
		// Submit the form
		driver.findElement(By.xpath("//input[@name='sub']")).click();
		
		//Verify the user is created successfully
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Customer Registered Successfully!!!']")).isDisplayed());

		// Get the customer iD
		newCustomerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
		
		//Verify the user info is correct
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), newCustomerName);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), newCustomerGender);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), newCustomerDateOfBirth);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), newCustomerAddress);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), newCustomerCity);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), newCustomerState);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), newCustomerPin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), newCustomerMobileNumber);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), newCustomerEmail);
	
	}

	@Test 
	public void TC_02_EditCustomer() {
		// Click Edit customer menu item
		driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();

		// Enter the ID of newly created customer
		driver.findElement(By.xpath("//input[@name='cusid']")).sendKeys(newCustomerID);

		// Submit
		driver.findElement(By.xpath("//input[@name='AccSubmit']")).click();
		
		//Verify edit customer page is displayed
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Edit Customer']")).isDisplayed());

		// Verify values of Customer Name và Address fields
		Assert.assertEquals(driver.findElement(By.xpath("//input[@name='name']")).getAttribute("value"), newCustomerName);
		Assert.assertEquals(driver.findElement(By.xpath("//textarea[@name='addr']")).getText(), newCustomerAddress);

		//Edit the customer's info
		driver.findElement(By.xpath("//textarea[@name='addr']")).clear();
		driver.findElement(By.xpath("//textarea[@name='addr']")).sendKeys(editCustomerAddress);
		driver.findElement(By.xpath("//input[@name='city']")).clear();
		driver.findElement(By.xpath("//input[@name='city']")).sendKeys(editCustomerCity);
		driver.findElement(By.xpath("//input[@name='state']")).clear();
		driver.findElement(By.xpath("//input[@name='state']")).sendKeys(editCustomerState);
		driver.findElement(By.xpath("//input[@name='pinno']")).clear();
		driver.findElement(By.xpath("//input[@name='pinno']")).sendKeys(editCustomerPin);
		driver.findElement(By.xpath("//input[@name='telephoneno']")).clear();
		driver.findElement(By.xpath("//input[@name='telephoneno']")).sendKeys(editCustomerMobileNumber);
		driver.findElement(By.xpath("//input[@name='emailid']")).clear();
		driver.findElement(By.xpath("//input[@name='emailid']")).sendKeys(editCustomerEmail);
		
		//Submit
		driver.findElement(By.xpath("//input[@name='sub']")).click();
		
		//Verify editing customer successful
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Customer details updated Successfully!!!']")).isDisplayed());
		 
		//Verify the customer's info is modified successfully
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), editCustomerAddress);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), editCustomerCity);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), editCustomerState);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), editCustomerPin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), editCustomerMobileNumber);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), editCustomerEmail);

	}

	public String generateRamdomEmail() {
		Random random = new Random();
		int randomeNumber = random.nextInt(999999);
		return "daothituongvi" + randomeNumber + "@gmail.com";
	}

	@AfterTest
	public void afterTest() {
		 driver.quit();
	}

}

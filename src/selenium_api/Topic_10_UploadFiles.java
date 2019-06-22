package selenium_api;
import org.testng.annotations.Test;


import org.testng.annotations.BeforeTest;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Topic_10_UploadFiles {
	WebDriver driver;
	String projectPath, image01Name, image02Name, image03Name, image01Path, image02Path, image03Path;
	String videoName, videoPath;
	
	@BeforeTest
	public void beforeTest() {
		//Firefox
		driver = new FirefoxDriver();
		
		//Chrome
		//System.setProperty("webdriver.chrome.driver", "D:\\Automated testing online\\Software\\chromedriver_win32\\chromedriver.exe");
		//driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		projectPath = System.getProperty("user.dir");
		image01Name = "image01.jpg";
		image02Name = "image02.jpg";
		image03Name = "image03.jpg";
		videoName = "7z1805-x64.exe";
		image01Path = projectPath + "\\Upload\\" + image01Name;
		image02Path = projectPath + "\\Upload\\" + image02Name;
		image03Path = projectPath + "\\Upload\\" + image03Name;
		videoPath = projectPath + "\\Upload\\" + videoName;
	}

	@Test (enabled=false)
	public void TC_01_Exercise01() {
		/*Step 01 - Truy cập vào trang: 
		http://blueimp.github.com/jQuery-File-Upload/
		Step 02 - Sử dụng phương thức sendKeys để upload file nhiều file cùng lúc chạy cho 3 trình duyệt (IE/ Firefox/ Chrome)
		Upload 3 file:
		Image01.png
		Image02.png
		Image03.png
		Step 03 - Kiểm tra file đã được chọn thành công
		Step 04 - Click Start button để upload cho cả 3 file
		Step 05 - Sau khi upload thành công verify cả 3 file đã được upload*/
		
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		String[] filePaths = {image01Path, image02Path, image03Path};
		
		//Upload one by one
		uploadMultipleFilesOneByOne("//input[@name='files[]']", filePaths);
		
		//Upload all at once
		//uploadMultipleFilesAtOnce("//input[@name='files[]']", filePaths);
		
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-upload fade in']//p[@class='name' and text()='" + image01Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-upload fade in']//p[@class='name' and text()='" + image02Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-upload fade in']//p[@class='name' and text()='" + image03Name + "']")).isDisplayed());
		
		List <WebElement> startButtons = driver.findElements(By.xpath("//tr[@class='template-upload fade in']//button[@class='btn btn-primary start']"));
		
		for(WebElement button: startButtons) {
			button.click();
		}
		
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-download fade in']//p[@class='name']/a[text()='" + image01Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-download fade in']//p[@class='name']/a[text()='" + image02Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-download fade in']//p[@class='name']/a[text()='" + image03Name + "']")).isDisplayed());
		
		List<WebElement> uploadImages = driver.findElements(By.xpath("//tr[@class='template-download fade in']//span[@class='preview']//img"));
		Assert.assertTrue(isImageDisplayed(uploadImages.get(0)));
		Assert.assertTrue(isImageDisplayed(uploadImages.get(1)));
		Assert.assertTrue(isImageDisplayed(uploadImages.get(2)));
	}
	
	
	@Test (enabled=false)
	public void TC_02_Exercise04() {
		/*Step 01 - Open URL: 'https://encodable.com/uploaddemo/'
		Step 02 - Choose Files to Upload (Ex: UploadFile.jpg)
		Step 03 - Select dropdown (Upload to: /uploaddemo/files/)
		Step 04 - Input random folder to 'New subfolder? Name:) textbox (Ex: dam1254353)
		Step 05 - Input email and firstname (dam@gmail.com/ DAM DAO)
		Step 06 - Click Begin Upload (Note: Wait for page load successfully)
		Step 07 - Verify information
			   + Email Address: dam@gmail.com/ First Name: DAM DAO
			   + File name: UploadFile.jpg
		Step 08 - Click 'View Uploaded Files' link
		Step 09 - Click to random folder (Ex: dam1254353)
		Step 09 - Verify file name exist in folder (UploadFile.jpg)*/
		
		String emailAddress = "tuongvi@gmail.com";
		String firstName = "Tuong Vi";
		String randomFolderName = generateRandomFolderName();
		
		driver.get("https://encodable.com/uploaddemo/");
		WebElement browserFileButton = driver.findElement(By.xpath("//input[@id='uploadname1']"));
		browserFileButton.sendKeys(image01Path);
		
		Select uploadToDropdown = new Select(driver.findElement(By.xpath("//select[@name='subdir1']")));
		uploadToDropdown.selectByVisibleText("/uploaddemo/files/");
		
		WebElement subFolderTextbox = driver.findElement(By.xpath("//input[@id='newsubdir1']"));
		subFolderTextbox.sendKeys(randomFolderName);
		
		WebElement emailAddressTextbox = driver.findElement(By.xpath("//input[@id='formfield-email_address']"));
		WebElement firstNameTextbox = driver.findElement(By.xpath("//input[@id='formfield-first_name']"));
		emailAddressTextbox.sendKeys(emailAddress);
		firstNameTextbox.sendKeys(firstName);

		WebElement beginUploadButton = driver.findElement(By.xpath("//input[@id='uploadbutton']"));
		beginUploadButton.click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//dt[text()='Your upload is complete:']")).isDisplayed());
		
		Assert.assertTrue(driver.findElement(By.xpath("//dd[text()='Email Address: " + emailAddress + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//dd[text()='First Name: " + firstName + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//dt[contains(text(),'File 1 of 1:')]/a[text()='" + image01Name + "']")).isDisplayed());
		
		WebElement verifyUploadFilesLink = driver.findElement(By.xpath("//a[text()='View Uploaded Files']"));
		verifyUploadFilesLink.click();
		
		Assert.assertEquals(driver.getTitle(), "FileChucker: Uploaded Files");
		
		WebElement uploadFolder = driver.findElement(By.xpath("//a[text()='" + randomFolderName + "']"));
		uploadFolder.click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + image01Name + "']")).isDisplayed());
	}
	
	@Test (enabled=false)
	public void TC_03_UploadFilesWebix() {
		//Navigate to the web page
		driver.get("https://docs.webix.com/samples/21_upload/01_init_list.html");
		
		//Upload 3 files
		String[] filePaths = {image01Path, image02Path, image03Path};
		uploadMultipleFilesOneByOne("//input[@class='webix_hidden_upload']", filePaths);
		
		//Verify there are 3 files uploaded
		List<WebElement> uploadedFilesList = driver.findElements(By.xpath("//div[@class='webix_list_item']"));
		Assert.assertEquals(uploadedFilesList.size(), 3);
		
		//Verify they are uploaded successfully
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='" + image01Name + "']/preceding-sibling::div[@class='webix_upload_server']/span[@class='webix_icon wxi-check']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='" + image02Name + "']/preceding-sibling::div[@class='webix_upload_server']/span[@class='webix_icon wxi-check']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='" + image03Name + "']/preceding-sibling::div[@class='webix_upload_server']/span[@class='webix_icon wxi-check']")).isDisplayed());
		
		//Click Get value button and verify the name of uploaded files are displayed
		WebElement getValueButton = driver.findElement(By.xpath("//button[text()='Get value']"));
		getValueButton.click();
		WebElement message = driver.findElement(By.xpath("//div[@class='webix_message_area']//pre"));
		Assert.assertTrue(message.getText().contains(image01Name));
		Assert.assertTrue(message.getText().contains(image02Name));
		Assert.assertTrue(message.getText().contains(image03Name));
	}
	
	@Test (enabled=false)
	public void TC_04_Execise03() throws Exception {
		/*Step 01 - Truy cập vào trang: http://blueimp.github.com/jQuery-File-Upload/
		Step 02 - Sử dụng Robot để upload file chạy cho 3 trình duyệt (IE/ Firefox/ Chrome)
		Step 03 - Kiểm tra file đã được tải lên thành công*/
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		WebElement addFilesButton = driver.findElement(By.xpath("//span[contains(@class,'fileinput-button')]"));
		
		//Upload multiple files at once
		/*addFilesButton.click();
		String filePaths = "\"" + image01Path + "\"" + " " + "\"" + image02Path + "\"" + " " + "\"" + image03Path + "\"";
		uploadFileByRobotClass(filePaths);*/
		
		addFilesButton.click();
		uploadFileByRobotClass(image01Path);
		addFilesButton.click();
		uploadFileByRobotClass(image02Path);
		addFilesButton.click();
		uploadFileByRobotClass(image03Path);
		
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-upload fade in']//p[@class='name' and text()='" + image01Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-upload fade in']//p[@class='name' and text()='" + image02Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-upload fade in']//p[@class='name' and text()='" + image03Name + "']")).isDisplayed());
		
		List <WebElement> startButtons = driver.findElements(By.xpath("//tr[@class='template-upload fade in']//button[@class='btn btn-primary start']"));
		
		for(WebElement button: startButtons) {
			button.click();
		}
		
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-download fade in']//p[@class='name']/a[text()='" + image01Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-download fade in']//p[@class='name']/a[text()='" + image02Name + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-download fade in']//p[@class='name']/a[text()='" + image03Name + "']")).isDisplayed());
		
		List<WebElement> uploadImages = driver.findElements(By.xpath("//tr[@class='template-download fade in']//span[@class='preview']//img"));
		Assert.assertTrue(isImageDisplayed(uploadImages.get(0)));
		Assert.assertTrue(isImageDisplayed(uploadImages.get(1)));
		Assert.assertTrue(isImageDisplayed(uploadImages.get(2)));
	
	}
	
	@Test
	public void TC_05_Exercise02() throws Exception {
		/*Step 01 - Truy cập vào trang:
		http://blueimp.github.com/jQuery-File-Upload/
		Step 02 - Sử dụng AutoIT để upload file chạy cho 3 trình duyệt (IE/ Firefox/ Chrome)
		Step 03 - Kiểm tra file đã được tải lên thành công*/
		driver.get("http://blueimp.github.com/jQuery-File-Upload/");
		WebElement addFilesButton = driver.findElement(By.xpath("//span[contains(@class,'fileinput-button')]"));
		addFilesButton.click();
		Runtime.getRuntime().exec(new String[] {".\\upload\\firefox.exe", image01Path});
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-upload fade in']//p[@class='name' and text()='" + image01Name + "']")).isDisplayed());
		
		WebElement startButton = driver.findElement(By.xpath("//tr[@class='template-upload fade in']//button[@class='btn btn-primary start']"));
		startButton.click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='template-download fade in']//p[@class='name']/a[text()='" + image01Name + "']")).isDisplayed());
		
		WebElement uploadImage = driver.findElement(By.xpath("//tr[@class='template-download fade in']//span[@class='preview']//img"));
		Assert.assertTrue(isImageDisplayed(uploadImage));
	}
	
	public void uploadFileByRobotClass(String filePath) throws Exception {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection strSelection = new StringSelection(filePath);
		clipboard.setContents(strSelection, null);
		System.out.println(filePath);
		
		Robot robot = new Robot();
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	
	public String generateRandomFolderName() {
		Random random = new Random();
		int number = random.nextInt(999999);
		return "tuongvi" + number;
	}
	
	public void uploadMultipleFilesOneByOne (String addFilesButtonXpath, String [] filePaths) {
		WebElement addFilesButton;
		for(String filePath: filePaths) {
			addFilesButton = driver.findElement(By.xpath(addFilesButtonXpath));
			addFilesButton.sendKeys(filePath);
		}
	}
	
	//Only apply for Upload element <input type='file'> which has 'multiple' attribute
	public void uploadMultipleFilesAtOnce(String addFilesButtonXpath, String [] filePaths) {
		WebElement addFilesButton = driver.findElement(By.xpath(addFilesButtonXpath));
		String allFilesPath = "";
		for(int i=0; i<filePaths.length; i++) {
			if(i < filePaths.length - 1)
				allFilesPath = allFilesPath.concat(filePaths[i]).concat("\n");
			else
				allFilesPath = allFilesPath.concat(filePaths[i]);
		}
		addFilesButton.sendKeys(allFilesPath);
	}
	
	public Boolean isImageDisplayed(WebElement image) {
		Boolean isImageDisplayed = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", image);
		return isImageDisplayed;
	}
	

	@AfterTest
	public void afterTest() {
//		driver.quit();
	}

}

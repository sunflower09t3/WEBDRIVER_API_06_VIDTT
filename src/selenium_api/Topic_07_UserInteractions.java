package selenium_api;

import org.testng.annotations.Test;

import org.testng.annotations.BeforeTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Topic_07_UserInteractions {
	WebDriver driver;

	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test 
	public void TC_01_HoverMouseCase1() {
		/*Step 01 - Truy cập vào trang: https://daominhdam.github.io/basic-form/index.html
		Step 02 - Hover chuột vào title: 'Hover over me'
		Step 03 - Verify tooltip này được hiển thị
		Step 04 - Get text của tooltip và so sánh nó có phải là 'Hooray!' hay không
		*/
		driver.get("https://daominhdam.github.io/basic-form/index.html");
		WebElement hoverOverMeLink = driver.findElement(By.xpath("//a[text()='Hover over me']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(hoverOverMeLink).perform();
		Assert.assertEquals(hoverOverMeLink.getAttribute("title"), "Hooray!");
	}

	@Test 
	public void TC_01_HoverMouseCase2() {
		/*Step 01 - Truy cập vào trang: http://www.myntra.com/
		Step 02 - Hover chuột vào Menu để login
		Step 03 - Chọn Login button
		Step 04 - Verify Login form được hiển thị
		*/
		driver.get("http://www.myntra.com/");
		WebElement profileIcon = driver.findElement(By.xpath("//div[@class='desktop-userIconsContainer']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(profileIcon).perform();
		WebElement loginLink = driver.findElement(By.xpath("//a[text()='login']"));
		loginLink.click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[@id='mountRoot']//div[@class='login-box']")).isDisplayed());
	}

	@Test 
	public void TC_02_ClickAndHold() {
		/*
		 * Step 01 - Truy cập vào trang: http://jqueryui.com/resources/demos/selectable/display-grid.html 
		 * Step 02 - Click and hold từ 1-> 4 
		 * Step 03 - Sau khi chọn kiểm tra rằng có đúng 4 phần tử đã được chọn thành công với xpath:
		 */
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");
		WebElement number1 = driver.findElement(By.xpath("//ol[@id='selectable']/li[text()='1']"));
		WebElement number4 = driver.findElement(By.xpath("//ol[@id='selectable']/li[text()='4']"));
		Actions actions1 = new Actions(driver);
		//actions.dragAndDrop(number1, number4).perform();
		actions1.clickAndHold(number1).moveToElement(number4).release().perform();
		List <WebElement> selectedNumbers1 = driver.findElements(By.xpath("//ol[@id='selectable']/li[contains(@class,\"ui-selected\")]"));
		Assert.assertEquals(selectedNumbers1.size(), 4);
		for(WebElement number: selectedNumbers1) {
			Assert.assertTrue(Integer.parseInt(number.getText()) > 0 && Integer.parseInt(number.getText()) < 5);
		}
		
		/*
		 * Step 01 - Truy cập vào trang: http://jqueryui.com/resources/demos/selectable/display-grid.html 
		 * Step 02 - Press Ctrl key + select 2, 6, 8, 10
		 * Step 03 - Sau khi chọn kiểm tra rằng có đúng 4 phần tử đã được chọn thành công với xpath:
		 */
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");
		WebElement number2 = driver.findElement(By.xpath("//ol[@id='selectable']/li[text()='2']"));
		WebElement number6 = driver.findElement(By.xpath("//ol[@id='selectable']/li[text()='6']"));
		WebElement number8 = driver.findElement(By.xpath("//ol[@id='selectable']/li[text()='8']"));
		WebElement number10 = driver.findElement(By.xpath("//ol[@id='selectable']/li[text()='10']"));
		Actions actions2 = new Actions(driver);
		actions2.keyDown(Keys.CONTROL).click(number2).click(number6).click(number8).click(number10).keyUp(Keys.CONTROL).perform();
		List<WebElement> selectedNumbers2 = driver.findElements(By.xpath("//ol[@id='selectable']/li[contains(@class,\"ui-selected\")]"));
		Assert.assertEquals(selectedNumbers2.size(), 4);
		for(WebElement number: selectedNumbers2) {
			String[] array = {"2", "6", "8", "10"};
			Assert.assertTrue(Arrays.asList(array).contains(number.getText()));
		}
	}
	
	@Test 
	public void TC_03_DoubleClick() {
		/*Step 01 - Truy cập vào trang: http://www.seleniumlearn.com/double-click
		Step 02 - Double click vào element: Double-Click Me!
		Step 03 - Verify text trong alert được hiển thị: 'The Button was double-clicked.'
		Step 04 - Accept Javascript alert*/
		driver.get("http://www.seleniumlearn.com/double-click");
		WebElement doubleClickButton = driver.findElement(By.xpath("//button[text()='Double-Click Me!']"));
		Actions actions = new Actions(driver);
		actions.doubleClick(doubleClickButton).perform();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "The Button was double-clicked.");
		alert.accept();
	}
	
	
	@Test 
	public void TC_04_RightClick() {
		/*Step 01 - Truy cập vào trang: http://swisnl.github.io/jQuery-contextMenu/demo.html
		Step 02 - Right click vào element: right click me
		Step 03 - Hover chuột vào element: Quit
		Step 04 - Verify element Quit (visible + hover) với xpath:
		Step 05 - Click chọn Quit
		Step 06 - Accept Javascript alert (Chrome only)
		*/
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");
		WebElement rightClickButton = driver.findElement(By.xpath("//span[text()='right click me']"));
		Actions actions = new Actions(driver);
		actions.contextClick(rightClickButton).perform();
		WebElement quitMenuItem = driver.findElement(By.xpath("//li[contains(@class,'context-menu-icon-quit')]"));
		actions.moveToElement(quitMenuItem).perform();
		Assert.assertTrue(driver.findElement(By.xpath("//li[contains(@class,'context-menu-icon-quit') and contains(@class,'context-menu-visible') and contains(@class,'context-menu-hover')]")).isDisplayed());
		actions.click(quitMenuItem).perform();;
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}
	
	@Test 
	public void TC_05_DragAndDrop() {
		/*Step 01 - Truy cập vào trang: http://demos.telerik.com/kendo-ui/dragdrop/angular
		Step 02 - Kéo hình tròn nhỏ vào hình tròn lớn
		Step 03 - Verify message đã thay đổi: You did great!
		*/
		driver.get("http://demos.telerik.com/kendo-ui/dragdrop/angular");
		WebElement smallCirle = driver.findElement(By.xpath("//div[@id='draggable']"));
		WebElement largeCircle = driver.findElement(By.xpath("//div[@id='droptarget']"));
		Actions actions1 = new Actions(driver);
		actions1.dragAndDrop(smallCirle, largeCircle).perform();
		Assert.assertEquals(largeCircle.getText(), "You did great!");
		
		/*Step 01 - Truy cập vào trang: http://jqueryui.com/resources/demos/droppable/default.html
		Step 02 - Kéo hình chữ nhật: Drag me to my target vào hình Drop here
		Step 03 - Verify message đã thay đổi: Dropped!
		*/
		driver.get("http://jqueryui.com/resources/demos/droppable/default.html");
		WebElement smallSquare = driver.findElement(By.xpath("//div[@id='draggable']"));
		WebElement largeSquare = driver.findElement(By.xpath("//div[@id='droppable']"));
		Actions actions2 = new Actions(driver);
		actions2.dragAndDrop(smallSquare, largeSquare).perform();
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='droppable']/p")).getText(), "Dropped!");
	}

	@AfterTest
	public void afterTest() {
		// driver.quit();
	}

}

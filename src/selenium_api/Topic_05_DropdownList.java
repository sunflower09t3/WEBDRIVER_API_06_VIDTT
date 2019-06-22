package selenium_api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

public class Topic_05_DropdownList {
	WebDriver driver;
	WebDriverWait wait;

	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 30);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test(enabled = false)
	public void TC_01_HandleHTMLDropdownList() {
		// Navigate to http://daominhdam.890m.com/
		driver.get("http://daominhdam.890m.com/");

		// Create a Select instance
		Select jobRole1Select = new Select(driver.findElement(By.xpath("//select[@id='job1']")));

		// Verify dropdown Job Role 01 does not support multiple selections
		Assert.assertFalse(jobRole1Select.isMultiple());

		// Select 'Automation Tester' option by using selectByVisibleText method
		jobRole1Select.selectByVisibleText("Automation Tester");

		// Verify the option is selected successfully
		Assert.assertEquals(jobRole1Select.getFirstSelectedOption().getText(), "Automation Tester");

		// Select 'Manual Tester' option by using selectByValue method
		jobRole1Select.selectByValue("manual");

		// Verify the option is selected successfully
		Assert.assertEquals(jobRole1Select.getFirstSelectedOption().getText(), "Manual Tester");

		// Select 'Mobile Tester' option by using selectByIndex method
		jobRole1Select.selectByIndex(3);

		// Verify the option is selected successfully
		Assert.assertEquals(jobRole1Select.getFirstSelectedOption().getText(), "Mobile Tester");

		// Verify the dropdown has 5 options in total
		Assert.assertEquals(jobRole1Select.getOptions().size(), 5);
	}

	@Test(enabled = true)
	public void TC_02_HandleCustomDropdownList() throws Exception {
		/*// JQUERY
		String number = "18";
		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
		selectItemInCustomDropdownByJavascrip("//span[@id='number-button']", "//ul[@id='number-menu']/descendant::div", number);
		Assert.assertEquals(
				driver.findElement(By.xpath("//span[@id='number-button']/*[@class='ui-selectmenu-text']")).getText(),
				number);

		// ANGULAR
		String state = "Wyoming";
		driver.get("https://material.angular.io/components/select/examples");
		selectItemInCustomDropdownByJavascrip("//mat-select[@id='mat-select-5']", "//div[@id='cdk-overlay-0']//mat-option/span",
				state);
		Assert.assertTrue(
				driver.findElement(By.xpath("//mat-select[@id='mat-select-5']//span[text()='" + state + "']")).isDisplayed());
		
		
		//TELERIK site
		String capColor = "Orange";
		String capSize = "M - 7 1/4\"";
		driver.get("https://demos.telerik.com/kendo-ui/dropdownlist/index");
		selectItemInCustomDropdown("//input[@id='color']/preceding-sibling::span", "//ul[@id='color_listbox']/li", capColor);
		Assert.assertEquals(driver.findElement(By.xpath("//input[@id='color']/preceding-sibling::span/span[@class='k-input']")).getText(), capColor);
		selectItemInCustomDropdownByJavascrip("//select[@id='size']/preceding-sibling::span", "//ul[@id='size_listbox']/li", capSize);
		Assert.assertEquals(driver.findElement(By.xpath("//select[@id='size']/preceding-sibling::span/span[@class='k-input']")).getText(), capSize);
			
		
		//Mikerodham site
		String option = "Second Option";
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItemInCustomDropdown("//li[contains(text(),'Please select an item')]", "//ul[@class='dropdown-menu']//a", option);
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='dropdown-toggle']")).getText(), option);
		*/
		
		// Editable dropdown
		String defaultPlace = "Audi";
		driver.get("http://indrimuska.github.io/jquery-editable-select/");
		selectItemInCustomDropdownByClick("//div[@id='default-place']/input", "//div[@id='default-place']//li", defaultPlace);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='default-place']//li[contains(@class,'es-visible')]")).getText(), defaultPlace);
		
		//Multiple selections
		/*List <String> months = new ArrayList<String>();
		months.add("January");
		months.add("Febuary");*/
		
		
	}

	public void selectItemInCustomDropdownByJavascript(String parentLocator, String allItemsLocator,
			String expectedValue) throws Exception {
		// 1 - Scroll and click to open dropdown
		WebElement parentDropdown = driver.findElement(By.xpath(parentLocator));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", parentDropdown);
		// driver.findElement(By.xpath(parentLocator)).click();

		List<WebElement> allItemsDropdown = driver.findElements(By.xpath(allItemsLocator));

		// 2 - Wait for all items visible
		wait.until(ExpectedConditions.visibilityOfAllElements(allItemsDropdown));

		// Loop through the list, find the item, scroll and click
		for (WebElement item : allItemsDropdown) {
			if (item.getText().equals(expectedValue)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", item);
				item.click();
				break;
			}
		}
	}

	public void selectItemInCustomDropdownByClick(String parentLocator, String allItemsLocator, String expectedValue) {
		// 1 - Scroll and click to open dropdown
		WebElement parentDropdown = driver.findElement(By.xpath(parentLocator));
		driver.findElement(By.xpath(parentLocator)).click();

		List<WebElement> allItemsDropdown = driver.findElements(By.xpath(allItemsLocator));

		// 2 - Wait for all items visible
		wait.until(ExpectedConditions.visibilityOfAllElements(allItemsDropdown));

		// Loop through the list, find the item, scroll and click
		for (WebElement item : allItemsDropdown) {
			if (item.getText().equals(expectedValue)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", item);
				item.click();
				break;
			}
		}
	}

	@AfterTest
	public void afterTest() {
		// driver.quit();
	}

}

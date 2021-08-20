package wordPressTests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;

import resources.Base;
import resources.LoginUtility;

public class ModifyTextFieldsTest extends Base {

	public WebDriver driver;

	public static Logger log = LogManager.getLogger(Base.class.getName());

	@BeforeMethod
	public void login() throws IOException {
		driver = initializeDriver();
		LoginUtility lu = new LoginUtility(driver);
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		lu.performLogin(url, username, password);
	}

	@Test
	public void modifyFirstName() {
		driver.findElement(By.xpath("//input[@id='first_name']")).clear();
		driver.findElement(By.xpath("//input[@id='first_name']")).sendKeys("John");
		// Validate Change; Do Not Save
		Assert.assertEquals(driver.findElement(By.xpath("//button[contains(text(),'Save profile details')]")).isEnabled(), true);
		Assert.assertEquals(driver.findElement(By.id("first_name")).getAttribute("value"), "John");
		// Validate Other Fields Have Not Changed
		Assert.assertEquals(driver.findElement(By.id("last_name")).getAttribute("value"), "Greely");
		Assert.assertEquals(driver.findElement(By.id("display_name")).getAttribute("value"), "pgreely21");
		Assert.assertEquals(driver.findElement(By.id("description")).getAttribute("value"), "");
	}

	@Test
	public void modifyLastName() {
		driver.findElement(By.xpath("//input[@id='last_name']")).clear();
		driver.findElement(By.xpath("//input[@id='last_name']")).sendKeys("Miller");
		// Validate Change; Do Not Save
		Assert.assertEquals(driver.findElement(By.xpath("//button[contains(text(),'Save profile details')]")).isEnabled(), true);
		Assert.assertEquals(driver.findElement(By.id("last_name")).getAttribute("value"), "Miller");
		// Validate Other Fields Have Not Changed
		Assert.assertEquals(driver.findElement(By.id("first_name")).getAttribute("value"), "Peter");
		Assert.assertEquals(driver.findElement(By.id("display_name")).getAttribute("value"), "pgreely21");
		Assert.assertEquals(driver.findElement(By.id("description")).getAttribute("value"), "");
	}

	@Test
	public void modifyDisplayName() {
		driver.findElement(By.xpath("//input[@id='display_name']")).clear();
		driver.findElement(By.xpath("//input[@id='display_name']")).sendKeys("");
		driver.findElement(By.xpath("//input[@id='display_name']")).sendKeys("jMiller21");
		// Validate Change; Do Not Save
		Assert.assertEquals(driver.findElement(By.xpath("//button[contains(text(),'Save profile details')]")).isEnabled(), true);
		Assert.assertEquals(driver.findElement(By.id("display_name")).getAttribute("value"), "jMiller21");
		// Validate Other Fields Have Not Changed
		Assert.assertEquals(driver.findElement(By.id("first_name")).getAttribute("value"), "Peter");
		Assert.assertEquals(driver.findElement(By.id("last_name")).getAttribute("value"), "Greely");
		Assert.assertEquals(driver.findElement(By.id("description")).getAttribute("value"), "");
	}

	@Test
	public void modifyDescription() {
		driver.findElement(By.xpath("//textarea[@id='description']")).clear();
		driver.findElement(By.xpath("//textarea[@id='description']")).sendKeys("Passionate software testing engineer with 15 years experience!");
		// Validate Change; Do Not Save
		Assert.assertEquals(driver.findElement(By.xpath("//button[contains(text(),'Save profile details')]")).isEnabled(), true);
		Assert.assertEquals(driver.findElement(By.id("description")).getAttribute("value"), "Passionate software testing engineer with 15 years experience!");
		// Validate Other Fields Have Not Changed
		Assert.assertEquals(driver.findElement(By.id("first_name")).getAttribute("value"), "Peter");
		Assert.assertEquals(driver.findElement(By.id("last_name")).getAttribute("value"), "Greely");
		Assert.assertEquals(driver.findElement(By.id("display_name")).getAttribute("value"), "pgreely21");
	}

	@AfterMethod
	public void teardown() {
		driver.close();
	}
}

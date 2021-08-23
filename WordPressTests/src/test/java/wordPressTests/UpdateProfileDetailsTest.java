package wordPressTests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.Base;
import resources.LoginUtility;

public class UpdateProfileDetailsTest extends Base {

	public WebDriver driver;
	By saveProfile = By.xpath("//button[contains(text(),'Save profile details')]");

	public static Logger log = LogManager.getLogger(Base.class.getName());

	@BeforeTest
	public void initialize() throws IOException {
		driver = initializeDriver();
		login();
	}

	/**
	 * Method to perform the login actions.
	 */
	public void login() {
		LoginUtility lu = new LoginUtility(driver);
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		lu.performLogin(url, username, password);
	}

	@Test
	public void updateAllTextFieldsSave() {
		// Validate Default Values Expected
		Assert.assertEquals(driver.findElement(By.id("first_name")).getAttribute("value"), "Peter");
		Assert.assertEquals(driver.findElement(By.id("last_name")).getAttribute("value"), "Greely");
		Assert.assertEquals(driver.findElement(By.id("display_name")).getAttribute("value"), "pgreely21");
		Assert.assertEquals(driver.findElement(By.id("description")).getAttribute("value"), "");
		Assert.assertEquals(driver.findElement(saveProfile).isEnabled(), false);

		// Modify All Fields
		modifyValues("John", "Wick", "jwick21", "Passionate software testing engineer with 15 years experience!");

		// Validate Button Enabled
		Assert.assertEquals(driver.findElement(saveProfile).isEnabled(), true);

		// Save Changes and Logout
		driver.findElement(saveProfile).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//body/div[@id='wpcom']/div[1]/div[2]/div[1]/div[1]/button[1]")).click();
		driver.findElement(By.xpath("//button[contains(text(),'Log out')]")).click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Login
		login();

		// Validate Saved Changes
		Assert.assertEquals(driver.findElement(By.id("first_name")).getAttribute("value"), "John");
		Assert.assertEquals(driver.findElement(By.id("last_name")).getAttribute("value"), "Wick");
		Assert.assertEquals(driver.findElement(By.id("display_name")).getAttribute("value"), "jwick21");
		Assert.assertEquals(driver.findElement(By.id("description")).getAttribute("value"), "Passionate software testing engineer with 15 years experience!");
		Assert.assertEquals(driver.findElement(saveProfile).isEnabled(), false);
	}

	/**
	 * Method to localize changing the text fields all at once.
	 * 
	 * @param firstName User first name to enter
	 * @param lastName User last name to enter
	 * @param displayName User display name to enter
	 * @param description User description to enter
	 */
	public void modifyValues(String firstName, String lastName, String displayName, String description) {
		driver.findElement(By.xpath("//input[@id='first_name']")).clear();
		driver.findElement(By.xpath("//input[@id='first_name']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@id='last_name']")).clear();
		driver.findElement(By.xpath("//input[@id='last_name']")).sendKeys(lastName);
		driver.findElement(By.xpath("//input[@id='display_name']")).clear();
		driver.findElement(By.xpath("//input[@id='display_name']")).sendKeys("");
		driver.findElement(By.xpath("//input[@id='display_name']")).sendKeys(displayName);
		driver.findElement(By.xpath("//textarea[@id='description']")).clear();
		driver.findElement(By.xpath("//textarea[@id='description']")).sendKeys("");
		driver.findElement(By.xpath("//textarea[@id='description']")).sendKeys(description);
	}

	@AfterTest
	public void teardown() {
		modifyValues("Peter", "Greely", "pgreely21", " ");
		driver.findElement(saveProfile).click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.close();
	}
}

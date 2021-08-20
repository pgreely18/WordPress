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

public class LoginTest extends Base {

	public WebDriver driver;

	public static Logger log = LogManager.getLogger(Base.class.getName());

	@BeforeTest
	public void initialize() throws IOException {
		driver = initializeDriver();
	}

	@Test
	public void baseLogin() {
		LoginUtility lu = new LoginUtility(driver);
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		lu.performLogin(url, username, password);

		// Validate Default Values Expected
		Assert.assertEquals(driver.findElement(By.className("edit-gravatar__label")).getText(), "Click to change photo");
		Assert.assertEquals(driver.findElement(By.id("first_name")).getAttribute("value"), "Peter");
		Assert.assertEquals(driver.findElement(By.id("last_name")).getAttribute("value"), "Greely");
		Assert.assertEquals(driver.findElement(By.id("display_name")).getAttribute("value"), "pgreely21");
		Assert.assertEquals(driver.findElement(By.id("description")).getAttribute("value"), "");
		Assert.assertEquals(driver.findElement(By.xpath("//button[contains(text(),'Save profile details')]")).isEnabled(), false);
		Assert.assertEquals(driver.findElement(By.className("profile-link__title")).getText(), "Senior Software Test Engineer");
		Assert.assertEquals(driver.findElement(By.className("profile-link__url")).getText(), "www.linkedin.com/in/peter-greely-076954b8/");
		System.out.println("Script Finished executing.");
	}

	@AfterTest
	public void teardown() {
		driver.close();
	}
}

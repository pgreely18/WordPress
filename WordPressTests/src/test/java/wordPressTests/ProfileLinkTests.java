package wordPressTests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import resources.Base;
import resources.LoginUtility;

public class ProfileLinkTests extends Base {

	public WebDriver driver;
	By addProfile = By.xpath("//body/div[@id='wpcom']/div[1]/div[2]/div[2]/main[1]/div[3]/div[2]/button[1]");
	By addURL = By.xpath("//button[contains(text(),'Add URL')]");
	By addSite = By.xpath("//button[contains(text(),'Add Site')]");
	By profileTitle = By.className("profile-link__title");
	By profileURL = By.className("profile-link__url");

	public static Logger log = LogManager.getLogger(Base.class.getName());

	@BeforeTest
	public void initialize() throws IOException {
		driver = initializeDriver();
		LoginUtility lu = new LoginUtility(driver);
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		lu.performLogin(url, username, password);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0, 500)");
		removeLink();
	}

	/**
	 * Add the expected Profile link.
	 */
	public void restoreLink() {
		// TODO Add System out to see where script is when hitting failures
		driver.findElement(addProfile).click();
		// TODO Maybe add a wait for this button ?
		driver.findElement(addURL).click();
		driver.findElement(By.name("value")).sendKeys("www.linkedin.com/in/peter-greely-076954b8/");
		driver.findElement(By.name("title")).sendKeys("Senior Software Test Engineer");
		driver.findElement(addSite).click();
	}

	@Test
	public void addProfileLink() {
		// Add New Profile Link
		driver.findElement(addProfile).click();
		driver.findElement(addURL).click();
		// Validate New Fields Appeared
		Assert.assertEquals(driver.findElement(By.name("value")).getAttribute("placeholder"), "Enter a URL");
		Assert.assertEquals(driver.findElement(By.name("title")).getAttribute("placeholder"), "Enter a description");
		Assert.assertEquals(driver.findElement(addSite).isEnabled(), false);
		Assert.assertEquals(driver.findElement(By.xpath("//button[contains(text(),'Cancel')]")).isEnabled(), true);
		// Add New Profile Site
		driver.findElement(By.name("value")).sendKeys("www.linkedin.com/in/peter-greely-076954b8/");
		driver.findElement(By.name("title")).sendKeys("Senior Software Test Engineer");
		Assert.assertEquals(driver.findElement(addSite).isEnabled(), true);
		driver.findElement(addSite).click();
		// Validate Existence of new Site
		Assert.assertEquals(driver.findElement(profileTitle).getText(), "Senior Software Test Engineer");
		Assert.assertEquals(driver.findElement(profileURL).getText(), "www.linkedin.com/in/peter-greely-076954b8/");
	}

	@Test
	public void deleteProfileLink() {
		// First Validate the Profile Link Existence
		Assert.assertEquals(driver.findElement(profileTitle).getText(), "Senior Software Test Engineer");
		Assert.assertEquals(driver.findElement(profileURL).getText(), "www.linkedin.com/in/peter-greely-076954b8/");

		// Delete the Link
		removeLink();

		// Second Validate the Profile Link Removed
		Assert.assertEquals(driver.findElement(By.className("profile-links__no-links")).getText(), "You have no sites in your profile links. You may add sites if you'd like.");
	}

	/**
	 * Action to Remove the expected profile link.
	 */
	public void removeLink() {
		driver.findElement(By.xpath("//body/div[@id='wpcom']/div[1]/div[2]/div[2]/main[1]/div[4]/div[1]/ul[1]/li[1]/button[1]")).click();
	}

	@AfterTest
	public void teardown() {
		restoreLink();
		driver.close();
	}
}

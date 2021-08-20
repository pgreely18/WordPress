package resources;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	public WebDriver driver;

	By email = By.xpath("//input[@id='usernameOrEmail']");
	By password = By.xpath("//input[@id='password']");
	By continueButton = By.xpath("//button[contains(text(),'Continue')]");
	By login = By.xpath("//button[contains(text(),'Log In')]");
	By myProfile = By.xpath("//header/a[3]/span[1]/img[1]");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getEmail() {
		return driver.findElement(email);
	}

	public WebElement getPassword() {
		return driver.findElement(password);
	}

	public WebElement getContinueButton() {
		return driver.findElement(continueButton);
	}

	public WebElement getLogin() {
		WebDriverWait wObj = new WebDriverWait(driver, 10);
		wObj.until(ExpectedConditions.visibilityOfElementLocated(login));
		return driver.findElement(login);
	}

	public WebElement getMyProfile() {
		return driver.findElement(myProfile);
	}
}

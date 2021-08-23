package resources;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class LoginUtility {

	public WebDriver driver;
	public Properties prop;

	public static Logger log = LogManager.getLogger(Base.class.getName());

	public LoginUtility(WebDriver driver) {
		this.driver = driver;
	}

	public void performLogin(String url, String username, String password) {
		driver.get(url);
		LoginPage lp = new LoginPage(driver);
		lp.getEmail().sendKeys(username);
		lp.getContinueButton().click();
		lp.getPassword().sendKeys(password);
		lp.getLogin().click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lp.getMyProfile().click();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("My Profile should be loaded...");
	}
}

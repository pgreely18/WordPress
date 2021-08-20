package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Base {
	public static WebDriver driver;
	public Properties prop;

	public WebDriver initializeDriver() throws IOException {

		prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") +
				"\\src\\main\\java\\resources\\data.properties");

		prop.load(fis);
		String browserName = prop.getProperty("browser");
		System.out.println(browserName);

		if (browserName.contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			if (browserName.contains("headless")) {
				options.addArguments("headless");
			}
			driver = new ChromeDriver(options);
			// execute in chrome driver
		} else if (browserName.equals("firefox")) {
			driver = new FirefoxDriver();
			// firefox code
		} else if (browserName.equals("IE")) {
			// IE code
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}
}

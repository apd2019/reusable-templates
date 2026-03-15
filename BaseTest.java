/** The --disable-dev-shm-usage flag tells the browser to use the standard /tmp directory (or another temporary directory on the regular filesystem) instead of the /dev/shm shared RAM space to create anonymous shared memory files. 

--no-sandbox = It is used by developers when testing browser behavior or running Electron apps in restricted environments (like Linux Docker containers), where the sandbox fails to initialize.

 * --headless */




package java.com.automation.base;

import java.com.automation.utils.ConfigReader;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	/** threadlocal ensures each thread (parallel test) has its own WebDriver */
	public static ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
	
	public WebDriver getDriver() {
		return driverThread.get();
	}
	
	@BeforeMethod
	public void setUp() {
		String browser = ConfigReader.get("browser").toLowerCase();
		boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));
		int implicitWait = Integer.parseInt(ConfigReader.get("implicit.wait"));
		WebDriver driver = null;
		
		switch(browser) {
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			if(headless) firefoxOptions.addArguments("--headless");
			driver = new FirefoxDriver(firefoxOptions);
			break;
			
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
			
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			if(headless) chromeOptions.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
			driver = new ChromeDriver(chromeOptions);
			break;			
		}
		
		/** set implicit wait globally */
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
		
		/** Maximize browser window */
		driver.manage().window().maximize();
		
		/** store driver in ThreadLocal */
		driverThread.set(driver);
		
		/** navigate to base URL */
		driver.get(ConfigReader.get("base.url"));
		
	}
	
	@AfterMethod
	public void tearDown() {
		if(getDriver() != null) {
			getDriver().quit();
			driverThread.remove(); /** clean up ThreadLocal to prevent memory leaks */
		}
	}
	
	/** */
	
}

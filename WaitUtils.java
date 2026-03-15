/**
	 * Selenium needs to wait for elements before interacting. WaitUtils wraps Selenium's WebDriverWait with common conditions (element visible, clickable, present). Using explicit waits is much more reliable than Thread.sleep().	 
*/


package java.com.automation.utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {
	private WebDriver driver;
	private WebDriverWait wait;
	
	public WaitUtils(WebDriver driver) {
		this.driver = driver;
		int timeout = Integer.parseInt(ConfigReader.get("explicit.wait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	}
	/** wait until element is visible on the page */
	public WebElement waitForVisibility(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	/** wait until the element is clickable */
	public WebElement waitForClickability(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	/** wait until the element is present in DOM (not necessarily visible) */
	public WebElement waitForPresence(By locator) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	/** wait until element text contains expected value */
	public boolean waitForTextPresent(By locator, String text) {
		return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}
}

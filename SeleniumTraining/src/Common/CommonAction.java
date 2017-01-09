package Common;

import java.text.DateFormatSymbols;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonAction {
	public static WebDriver driver;

	public static void setup() {
		System.setProperty(Constant.gecko_property, Constant.gecko_path);
		driver = new FirefoxDriver();
		driver.get(Constant.URL);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public static void teardown() {
		driver.quit();
	}
	//Click Element
	public static void clickElement(String element_xpath) {
		WebElement element = driver.findElement(By.xpath(element_xpath));
		waitForPageLoad(1000);
		element.click();
	}
	
	public static void waitForPageLoad(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Wait for Element Present
	public static void waitForElementPresent(String xpath_ePresent) {
		WebDriverWait wait_Present = new WebDriverWait(driver, 30);
		wait_Present.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath_ePresent)));
	}

	// Wait for Element be clickable
	public static void waitforElementClickable(String xpath_eClickable) {
		WebDriverWait wait_clickable = new WebDriverWait(driver, 30);
		wait_clickable.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath_eClickable)));
	}

	public static void selectByText(String xpath, String text) {

		WebElement s = driver.findElement(By.xpath(xpath));
		s.click();
		waitForPageLoad(2000);
		List<WebElement> lst = s.findElements(By.tagName("option"));
		for (WebElement e : lst) {
			String t = e.getText();
			if (t.equals(text)) {
				s.sendKeys(t);
				s.sendKeys(Keys.ENTER);
				waitForPageLoad(1000);
				break;
			}
		}

	}

	public static String[] parseDate(String date) {
		String result[] = new String[3];
		String split_date[] = date.split("/");
		int month = Integer.parseInt(split_date[0]);
		int abs_day = Integer.parseInt(split_date[1]);
		result[1] = String.valueOf(abs_day);
		result[2] = split_date[2];
		result[0] = getMonth(month);
		return result;
	}

	public static String getMonth(int int_month) {
		String name_month = new DateFormatSymbols().getMonths()[int_month - 1];
		String short_namemonth = name_month.substring(0, 3);
		return short_namemonth;
	}

}

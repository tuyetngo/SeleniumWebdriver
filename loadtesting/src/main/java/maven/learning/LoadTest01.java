package maven.learning;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class LoadTest01 {
	private WebDriver driver;
	private String submit_button = "//button[@type='submit']";
	private String cloud_group = "//div[span//h3[contains(., \"Cloud Devices\")]]";
	// Replace deviceName and platformVersion variable in below xpath. Example: deviceName = Galaxy S7 and platformVersion = 7.0
	private String device = "//div[span//h3[contains(., \"Cloud Devices\")]]/../following-sibling::div/div/div/div/div[2]/div[1]/div[text()=\"<deviceName>\"]/parent::div/parent::div/div[3]/div[contains(.,\"<platformVersion>\")][1]/../..//div[contains(@style, \"rgb(104, 159, 56)\")]";
	private String launch_button = "//button[div/span[text()=\"Launch\"]]";
	private String initalization = "//p[contains(normalize-space(text()),\"Clearing up\") or contains(normalize-space(text()),\"Setting up\") or contains(normalize-space(text()),\"Start session\") or contains(normalize-space(text()),\"Establishing connection\")]";
	private String change_location_button = "//button[div/div/span[contains(.,\"Set device location\")]]";
	private String popup_change_location = "//h3[text()=\"Set device location\"]";
	private String latitude_input = "//div[contains(.,\"Latitude\")]/input";
	private String longitude_input = "//div[contains(.,\"Longitude\")]/input";
	private String set_location_button = "//h3[text()=\"Set device location\"]/../div/form//button[contains(.,\"Set\")]";
	private String change_location_successfully_message = "//div[contains(.,\"Device location successfully set to\")]";
	
    @BeforeClass
    public void beforeClass() {
    		System.setProperty("webdriver.chrome.driver", "/Users/tuyetngo/Documents/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
    
    @Test
    public void loginAndLaunchAManualSession() throws InterruptedException {
    		// Login portal
        driver.get("https://portal.kobiton.com/login");
        (new WebDriverWait(driver, 10))
	    .until(ExpectedConditions.presenceOfElementLocated(By.name("emailOrUsername")));	 
        driver.findElement(By.name("emailOrUsername")).sendKeys("prada0459");
        driver.findElement(By.name("password")).sendKeys("Orasipartner2017");
        (new WebDriverWait(driver, 10))
	    .until(ExpectedConditions.elementToBeClickable(By.name("emailOrUsername")));	
        driver.findElement(By.xpath(submit_button)).click();
        (new WebDriverWait(driver, 20))
	    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(cloud_group)));
        String device_url = "https://portal.kobiton.com/devices";
        String current_url = driver.getCurrentUrl();
        
        // Verify we are on devices page
        Assert.assertEquals(device_url, current_url);
        Thread.sleep(2000);
        
        // Scroll to specific device
        WebElement device_ele = driver.findElement(By	.xpath(device));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", device_ele);
        device_ele.click();

        	// Click on launch button
        Thread.sleep(5000);
        (new WebDriverWait(driver, 10))
	    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(launch_button)));
        WebElement device_launch_button_ele = driver.findElement(By.xpath(launch_button));
        device_launch_button_ele.click();
        // Wait for starting manual session
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(initalization)));
        (new WebDriverWait(driver, 60)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(initalization)));
        Thread.sleep(30000);
        String launch_url = "https://portal.kobiton.com/devices/launch?key=";
        String current_launch_url = driver.getCurrentUrl();
        // Verify we are on devices page
        Assert.assertTrue(current_launch_url.contains(launch_url));
        
        // Change device location
        changeLoction(54.2, 87);
        // Verify we changed location successfully
	  	Boolean message = driver.findElement(By.xpath(change_location_successfully_message)).isDisplayed();
	  	Assert.assertTrue(message); 
    }
    
    public void changeLoction(double latitude, double longtitude) throws InterruptedException {
    		// Click on button change location
    		driver.findElement(By.xpath(change_location_button)).click();
    		// Wait for pop up
    		(new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(popup_change_location)));
    		// Set latitude value
    		driver.findElement(By.xpath(latitude_input)).sendKeys(String.valueOf(latitude));
    		// Set longtitude value
    		driver.findElement(By.xpath(longitude_input)).sendKeys(String.valueOf(longtitude));
    		
    		// Click change location
    		driver.findElement(By.xpath(popup_change_location)).click();
    		(new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(By.xpath(set_location_button)));
    		driver.findElement(By.xpath(set_location_button)).click();
    		Thread.sleep(10000);
    }
}

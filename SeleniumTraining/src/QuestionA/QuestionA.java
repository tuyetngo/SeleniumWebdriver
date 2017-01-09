package QuestionA;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Common.CommonAction;
import Common.ReadDataTest;

public class QuestionA {
	HashMap<String, ArrayList<String>> data = ReadDataTest.readfile("DataFiles\\DataTest.xlsx", 0);
	int item_data = 0;
	@BeforeMethod
	public void beforetest() {
		CommonAction.setup();
	}

	@Test
	public void loginInvalid() {
		int item_testinvalid = 2;		
		CommonAction.driver.findElement(By.xpath("//*[@id='username']")).sendKeys(data.get("Username").get(item_testinvalid).toString());
		CommonAction.driver.findElement(By.xpath("//*[@id='password']")).sendKeys(data.get("Password").get(item_testinvalid).toString());
		CommonAction.clickElement("//*[@id='btnSubmit']");
		CommonAction.waitForPageLoad(3000);
		String message_error = CommonAction.driver
				.findElement(
						By.xpath("//*[@id='pageMessage' and contains(@class, 'validation-summary-errors')]"))
				.getText();
		AssertJUnit.assertEquals(message_error,
				"Incorrect credentials, please try again.");
	}

	@Test
	public void loginValid() {
		int item_testvalid = 0;		
		CommonAction.driver.findElement(By.xpath("//*[@id='username']")).sendKeys(data.get("Username").get(item_testvalid).toString());
		CommonAction.driver.findElement(By.xpath("//*[@id='password']")).sendKeys(data.get("Password").get(item_testvalid).toString());
		CommonAction.clickElement("//*[@id='btnSubmit']");
		CommonAction.waitForPageLoad(4000);
		String home_title = CommonAction.driver.getTitle();
		Assert.assertEquals(home_title, "Home | PA Tool");
		CommonAction.clickElement("//a[contains(text(),'Logout')]");
		CommonAction.waitForPageLoad(3000);
		String login_title = CommonAction.driver.getTitle();
		Assert.assertEquals(login_title, "Login | PA Tool");
	}

	@AfterMethod
	public void aftertest() {
		CommonAction.teardown();
	}
}

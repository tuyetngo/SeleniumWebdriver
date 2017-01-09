package QuestionB;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Common.CommonAction;
import Common.ReadDataTest;

public class QuestionB {
	int item_data = 0;
	HashMap<String, ArrayList<String>> data = ReadDataTest.readfile("DataFiles\\DataTest.xlsx", 0);
	@BeforeMethod
	public void beforetest() {
		CommonAction.setup();
		
		// Login with valid account
		CommonAction.driver.findElement(By.xpath("//*[@id='username']"))
				.sendKeys(data.get("Username").get(item_data).toString());
		CommonAction.driver.findElement(By.xpath("//*[@id='password']"))
				.sendKeys(data.get("Password").get(item_data).toString());
		CommonAction.clickElement("//*[@id='btnSubmit']");
		CommonAction.waitForPageLoad(4000);
		String home_title = CommonAction.driver.getTitle();
		Assert.assertEquals(home_title, "Home | PA Tool");

	}

	@Test
	public void testScenario2() {
		
		// Click item Search All in View Feedbacks on Menu
		CommonAction.clickElement("//a[contains(text(),'View Feedbacks')]");
		
		CommonAction.clickElement("//a[contains(text(),'Search All')]");
		
		CommonAction.waitForPageLoad(3000);

		// Verify label
		String feedback_title = CommonAction.driver.findElement(By.xpath("//h2")).getText();
		Assert.assertEquals(feedback_title, "Search All Feedbacks");

		// Select 'My feedbacks for Others' in View option drop down list
		CommonAction.selectByText("//select[@id='viewOption']", data.get("ViewOption").get(item_data).toString());
		
		CommonAction.waitForPageLoad(2000);
		

		// Click on Calendar icon of From Date
		CommonAction
				.clickElement("//*[@id='main']//label[contains(text(),'From')]/following-sibling::img");

		// Wait to load date picker
		CommonAction.waitForPageLoad(1000);

		// Select month
		CommonAction
				.selectByText("//*[@id='ui-datepicker-div']//select[contains(@class,'ui-datepicker-month')]",CommonAction.parseDate(data.get("FromDate").get(item_data).toString())[0]);
		// Wait to load date picker
				CommonAction.waitForPageLoad(2000);
				
		// Select year
		CommonAction
				.selectByText("//*[@id='ui-datepicker-div']//select[contains(@class,'ui-datepicker-year')]",CommonAction.parseDate(data.get("FromDate").get(item_data).toString())[2]);
		
		// Wait to page load
				CommonAction.waitForPageLoad(3000);
				
		// Select date
		CommonAction
				.clickElement("//*[@id='ui-datepicker-div']/table//td//a[text()="+CommonAction.parseDate(data.get("FromDate").get(item_data).toString())[1]+"]");

		// Wait to set from date
		CommonAction.waitForPageLoad(1000);

		// Click on Calendar icon of To Date
		CommonAction
				.clickElement("//*[@id='main']//label[contains(text(),'To')]/following-sibling::img");
		
		// Wait to page load
				CommonAction.waitForPageLoad(1000);
				
		// Select month
		CommonAction
				.selectByText(
						"//*[@id='ui-datepicker-div']//select[contains(@class,'ui-datepicker-month')]",	CommonAction.parseDate(data.get("ToDate").get(item_data).toString())[0]);
		// select year
		CommonAction
				.selectByText(
						"//*[@id='ui-datepicker-div']//select[contains(@class,'ui-datepicker-year')]",
						CommonAction.parseDate(data.get("ToDate").get(item_data).toString())[2]);
		
		// Wait to page load
		CommonAction.waitForPageLoad(1000);
		
		// select to date
		CommonAction
				.clickElement("//*[@id='ui-datepicker-div']/table//td//a[text()="+CommonAction.parseDate(data.get("ToDate").get(item_data).toString())[1]+"]");
		
		// Wait to set to date in text box
				CommonAction.waitForPageLoad(1000);
				
		// Click 'Search' button
		CommonAction.clickElement("//*[@id='btnSearch']");
		
		// Wait to return the result
		CommonAction.waitForPageLoad(4000);
		
		//Count number of result on grid
		int total_page = Integer.parseInt(CommonAction.driver.findElement(By.xpath("//td[@dir='ltr']/span")).getText());
		int total_row = 0;
		for(int i = 1; i<=total_page; i++){
			total_row += CommonAction.driver.findElements(By.xpath("//table[@id='list']/tbody/tr[@class='ui-widget-content jqgrow ui-row-ltr']")).size();
			CommonAction.clickElement(".//*[@id='next']/span");
			CommonAction.waitForPageLoad(2000);
		}
		//Compare with the returned result by system
		String page_info = CommonAction.driver.findElement(By.xpath("//div[@class='ui-paging-info']")).getText();
		String result = StringUtils.substringAfter(page_info, "of").trim();
		Assert.assertEquals(String.valueOf(total_row), result);
	}

	@AfterMethod
	public void afterTest() {
		CommonAction.teardown();
	}
}
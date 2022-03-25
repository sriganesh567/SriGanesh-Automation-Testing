package com.SriGanesh.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.SriGanesh.runner.BasicRunner;

import io.cucumber.java.en.*;
import junit.framework.Assert;

public class HomePage {
	
	
	WebDriver driver = BasicRunner.driver;
	@Given("I am on Ficusrealtime homepage")
	public void i_am_on_ficusrealtime_homepage() throws InterruptedException {
	driver.get("https://www.ficusrealtime.com/");
	//Thread.sleep(50346);
	}
	@Then("i should see title as Ficus Realtime")
	public void i_should_see_title_as_ficus_realtime() {
	    Assert.assertEquals(driver.getTitle().toString(),"Ficus Realtime");
	}
	@Then("^on the top right side i should see \"(.*)\" and \"(.*)\"$")
	public void on_the_top_right_side_i_should_see_and(String mobile, String email) {
	    // Write code here that turns the phrase above into concrete actions
		WebDriverWait w = new WebDriverWait(driver, 30);
		w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class = 'icon-phone4']//parent::a")));
		 Assert.assertEquals(driver.findElement(By.xpath("//*[@class = 'icon-phone4']//parent::a")).getText().toString(),mobile);
		 Assert.assertEquals(driver.findElement(By.xpath("//*[@class = 'icon-mail']//parent::a")).getText().toString(),email);
			
	}
	@Then("I should see ClientLogin Button along with facebook twitter and google links")
	public void i_should_see_client_login_button_along_with_facebook_twitter_and_google_links() {
	    // Write code here that turns the phrase above into concrete actions
	   Assert.assertNotNull(driver.findElements(By.cssSelector("a.fb")));
	   Assert.assertNotNull(driver.findElements(By.cssSelector("a.tw")));
	   Assert.assertNotNull(driver.findElements(By.cssSelector("a.gp")));
	}
	@Then("^I should see \"(.*)\"$")
	public void i_should_see(String alltabls) {
		String[] tabs = alltabls.split(",");
		List<String> tabsexpected = Arrays.asList(tabs);
		Collections.sort(tabsexpected);
		List<String> tabsactual  = new ArrayList<String>();
		List<WebElement> tabsfound = driver.findElements(By.xpath("//*[@class = 'nav wtf-menu']/li/a"));
		
		int listtabsize = tabsfound.size();
		for(int i = 0;i<listtabsize;i++) {
			tabsactual.add(tabsfound.get(i).getText());
			
		}
		Collections.sort(tabsactual);
		
		
		for(int i = 0;i<tabs.length;i++) {
			
			 Assert.assertEquals(tabsactual.get(i).trim(), tabsexpected.get(i).trim());
		}
		
	}

	@Then("^I should see imageslider with \"(.*)\"$")
	public void i_should_see_imageslider_with(String count) {
		int icount =Integer.valueOf(count);
		 Assert.assertEquals(driver.findElements(By.xpath("//*[contains(@class,'tp-banner-container')]//ul/li")).size(),icount);
		Assert.assertNotNull(driver.findElement(By.xpath("//*[contains(@src,'banner-img9.jpg')]")));
		Assert.assertNotNull(driver.findElement(By.xpath("//*[contains(@src,'b1.jpg')]")));
		
		
	}
	
	@Then("^I should see Why KeaGMP link which shows text when clicked$")
	public void IshouldseeWhyKeaGMPlinkwhichshowstextwhenclicked() {
		 Assert.assertEquals(driver.findElement(By.cssSelector(".appointment-title")).getText(),"Why KeaGMP?");
		 driver.findElement(By.cssSelector(".icon-chevron-down")).click();
		 Assert.assertNotNull(driver.findElement(By.xpath("//*[contains(@class, 'bgcolo-3 text-center') and contains(@style, 'block')]")));
			 
		
	}
	
	@Then("^I should see \"(.*)\" of services$")
	public void i_should_see_of_services(String allservices) {
		String[] services = allservices.split(",");
		List<String> servicesexpected = Arrays.asList(services);
		Collections.sort(servicesexpected);
		List<String> servicesactual  = new ArrayList<String>();
		List<WebElement> servicesfound = driver.findElements(By.xpath("//*[@class = 'services-one']//h5"));
		
		int listtabsize = servicesfound.size();
		for(int i = 0;i<listtabsize;i++) {
			servicesactual.add(servicesfound.get(i).getText());
			
		}
		Collections.sort(servicesactual);
		
		
		for(int i = 0;i<services.length;i++) {
			
			 Assert.assertEquals(servicesactual.get(i).trim(), servicesexpected.get(i).trim());
		}
		
	}
}

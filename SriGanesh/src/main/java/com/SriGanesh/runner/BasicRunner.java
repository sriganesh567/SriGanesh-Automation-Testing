package com.SriGanesh.runner;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.*;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
@Test

@CucumberOptions(features = "Features", glue = "com.SriGanesh.steps")
//, plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})

public class BasicRunner{
	  private TestNGCucumberRunner testNGCucumberRunner;
	  public String browser;
	  public static String type;
	  public String remote;
	  public static  WebDriver driver;
		 DesiredCapabilities des;
		@BeforeClass(alwaysRun = true)
	    public void setUpClass() {
	        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	   //     System.out.println("in" +browser);
	    }
	    @BeforeMethod(alwaysRun = true)
	    @Parameters({ "browser", "remote", "type" })
	    public void setUpMethod(String browser, String remote, String type) throws Exception {
	    	this.remote = remote;
	    	this.browser = browser;
	    	this.type  = type;
	    		    }
	    @Before
	    public void Setup() throws MalformedURLException {
	    	if(type.equals("ui")) {
				if(browser.equals("chrome")){
					if(remote.equals("remote")) {
				WebDriverManager.chromedriver().setup();
				 des = new DesiredCapabilities();
				des.setCapability("browsername", "chrome");
				driver = new RemoteWebDriver(new URL("http://ec2-35-172-178-20.compute-1.amazonaws.com:4444/wd/hub") ,des);
				}else {
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver();
				}
				}
				if(browser.equals("firefox")){
					if(remote.equals("remote")) {
				WebDriverManager.firefoxdriver().setup();
				des = new DesiredCapabilities();
				des.setCapability("browsername", "firefox");
				driver = new RemoteWebDriver(new URL("http://ec2-34-207-129-15.compute-1.amazonaws.com:4444/wd/hub") ,des);
				}else {
					WebDriverManager.chromedriver().setup();
					driver = new FirefoxDriver();
				}
				}
		    
		    driver.manage().window().maximize();
		    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
				}


	    }
	   

	    @SuppressWarnings("unused")
	    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
	        // the 'featureWrapper' parameter solely exists to display the feature
	        // file in a test report
	        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
	    }

	    /**
	     * Returns two dimensional array of {@link PickleWrapper}s with their
	     * associated {@link FeatureWrapper}s.
	     *
	     * @return a two dimensional array of scenarios features.
	     */
	    @DataProvider
	    public Object[][] scenarios() {
	        if (testNGCucumberRunner == null) {
	            return new Object[0][0];
	        }
	        return testNGCucumberRunner.provideScenarios();
	    }
		@After
		public void tearDown() {
			if(type.equals("ui")) {
				driver.close();}
			
		}
		

		

	    @AfterClass(alwaysRun = true)
	    public void tearDownClass() {
	        if (testNGCucumberRunner == null) {
	            return;
	        }
	        testNGCucumberRunner.finish();
	    }

}


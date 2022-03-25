package com.SriGanesh.steps;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.SriGanesh.runner.BasicRunner;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;

public class Listener  {
	
	
	public void afterStep(Scenario scenario) throws Throwable {
		
		String type = BasicRunner.type;
		WebDriver driver = BasicRunner.driver;
		if(type.equals("ui")) {if(scenario.isFailed()) {
			byte[] srcBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(srcBytes, "image/png", "SC1");
			}}
		
	}
}

package TestSuites;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Base.Base;
import Pages.makemytrip;

public class SmokeTest extends Base {

	makemytrip cw = new makemytrip();

	@BeforeTest
	public void invokeBrowser() {
		logger = report.createTest("Executing Test Cases");
		System.out.println("Steps of Execution:");
		System.out.println("Smoke Test:");
		cw.invokeBrowser();
		reportPass("Browser is Invoked");

	}

	@Test(priority = 1)
	public void bookOutstation() throws InterruptedException, IOException, InvalidFormatException {

		cw.OpenUrl();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		reportPass("All Test Cases Passed Successfully");
	}

	@AfterTest
	public void closeBrowser() {
		cw.endReport();
		cw.closeBrowser();
	}
}
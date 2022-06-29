package Pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.io.Files;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Base.Base;

public class makemytrip extends Base {

	By cabs = By.xpath("//span[text()='Cabs']");

	By from = By.xpath("//input[text()='From']");
	By delhi = By.xpath("//input[@placeholder='From']");
	By s1 = By.xpath("//span[text()='Delhi, India']");

	By manali = By.xpath("//input[@placeholder='To']");
	By s2 = By.xpath("//span[text()='Manali, Himachal Pradesh, India']");

	By search = By.xpath("//a[text()='Search']");
	By suv = By.xpath("//label[text()='SUV']");
//	By prices=By.xpath("(//*[@id='List']/div[1]/div[1]/div[3]/div/div[2]/div/p[1])[2]");
	By prices = By.xpath("//*[@id='List']/div[1]/div[1]/div[3]/div/div[2]/div/p[1]");

	By gift = By.xpath("//span[text()='Gift Cards']");
	By corCard = By.xpath("//*[@id='root']/div/div[2]/div/div[1]/div/div[2]/div[3]/ul/li[3]/div/img");
//	By corCard=By.xpath("/html/body/div/div/div[2]/div/div[1]/div/div[2]/div[3]/ul/li[3]/div/img");

	By name = By.name("senderName");
	By mob = By.name("senderMobileNo");
	By email = By.name("senderEmailId");
	By buy = By.xpath("//button[text()='BUY NOW']");
	By errors = By.xpath("//p[@class='red-text font11 append-top5']");
	By hotel = By.xpath("//span[text()='Hotels']");
	By adult = By.xpath("//ul[@data-cy='adultCount']/li");

	public void OpenUrl() {

		driver.get("https://www.makemytrip.com/");
		System.out.println("MakeMyTrip website is opened successfuly");
	}

	public void bookCab() throws InterruptedException, IOException {
		JSONArray address;
		String fromStr = null;
		String toStr = null;
		String dateStr = null;
		String timeStr = null;

		// Reading data from json
		try {
			JSONParser parser = new JSONParser();
			FileReader reader = new FileReader(System.getProperty("user.dir") + "\\Data\\Json\\HackathonJsonData.json");
			Object obj = parser.parse(reader);
			JSONObject obj1 = (JSONObject) obj;
			JSONArray address1 = (JSONArray) obj1.get("CabDetails");
			address = address1;
			JSONObject from1 = (JSONObject) address.get(0);
			fromStr = (String) from1.get("From");
			toStr = (String) from1.get("To");
			dateStr = (String) from1.get("Date");
			timeStr = (String) from1.get("Time");

			System.out.println("Data read from JSON:");
			System.out.println("From location: " + fromStr);
			System.out.println("To location: " + toStr);
			System.out.println("Date: " + dateStr);
			System.out.println("Time: " + timeStr);
		} catch (Exception e) {
			System.out.println(e);
		}
		By date = By.xpath("//div[@aria-label='" + dateStr + "']");
		By time = By.xpath("//li[text()='" + timeStr + "']");
		
//		JavascriptExecutor js = 

		logger = report.createTest("Booking one way outstation from Delhi to Manali.");

		WebElement currentElement = driver.switchTo().activeElement();

		(currentElement).click();

		wait(20, cabs);
		driver.findElement(cabs).click();
		System.out.println("Cabs button is clicked");
		driver.findElement(By.xpath("//span[text()='From']")).click();
		driver.findElement(delhi).sendKeys(fromStr);
		reportPass("Delhi is Selected");
		wait(20, s1);
		driver.findElement(s1).click();
		driver.findElement(manali).sendKeys(toStr);
		reportPass("Manali is Selected");

		wait(20, s2);
		driver.findElement(s2).click();
		driver.findElement(date).click();
		
		System.out.println("working till here");
		
		WebElement timeElement = driver.findElement(time);
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", timeElement);
		
		System.out.println("All details are filled");

		Screenshot("cabSearch");

		reportPass("Cab Search screenshot is taken successfully");

		driver.findElement(search).click();
		wait(200, suv);

		driver.findElement(suv).click();
		reportPass("SUV is Selected");

		Screenshot("cabFilter");

		reportPass("Cab Filter screenshot is taken successfully");

		wait(20, prices);
		String price = driver.findElement(prices).getText();
		System.out.println("The lowest price is: " + price);

		reportPass("Lowest Prices are Obtained");
		driver.findElement(cabs).click();
	}

	public void giftCard() throws InterruptedException, IOException, InvalidFormatException {
		logger = report.createTest("Display Error message after entering wrong data in Gift Cards.");
		String currentHandle = driver.getWindowHandle();
		driver.findElement(gift).click();
		Set<String> handle1 = driver.getWindowHandles();
		for (String actual : handle1) {
			if (!actual.equalsIgnoreCase(currentHandle)) {
				driver.switchTo().window(actual);
			}
		}
		Thread.sleep(3000);

		wait(200, corCard);
		WebElement corCardEle = driver.findElement(corCard);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", corCardEle);
//		driver.findElement(corCard).click();
		System.out.println("Corporate giftcard is selected successfully");

		// To scroll down and up in window
		js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)", "");
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,-250)", "");
		System.out.println("Scrolling up and down is done successfully");

		// Read data from excel
		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "\\Data\\Excel\\HackathonExcelData.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFSheet sheet = wb.getSheetAt(0);
		XSSFRow row1 = sheet.getRow(1);
		XSSFCell cell1 = row1.getCell(0);
		XSSFCell cell2 = row1.getCell(1);
		XSSFCell cell3 = row1.getCell(2);

		String exName = cell1.getStringCellValue();
		long exNum = (long) cell2.getNumericCellValue();
		String exMail = cell3.getStringCellValue();

		System.out.println("Data read from Excel:");
		System.out.println("Entered name is" + " " + cell1.getStringCellValue());
		System.out.println("Entered mobile number is" + " " + (long) cell2.getNumericCellValue());
		System.out.println("Entered email id is" + " " + cell3.getStringCellValue());

		driver.findElement(name).sendKeys(exName);
		driver.findElement(mob).sendKeys(Long.toString(exNum));
		driver.findElement(email).sendKeys(exMail);
		driver.findElement(buy).click();
		String error = driver.findElement(errors).getText();
		System.out.println("The Error message is: " + error);
		reportPass("Error Message is Obtained");

		// Taking screenshot
		Screenshot("Error");

		reportPass("Error Screenshot is taken successfully");

		// Closing the browser
		driver.close();

		// Navigate to home page
		driver.switchTo().window(currentHandle);
	}

	public void hotel() throws IOException {
		logger = report.createTest("Obtaining number of adult persons and storing in a list.");
		driver.findElement(hotel).click();
		driver.findElement(By.id("guest")).click();

		Screenshot("hotelSearch");
		reportPass("Hotel-Search Screenshot is taken successfully");

		System.out.println("The Adults numbers are: ");
		List<WebElement> adults = driver.findElements(adult);
		for (int i = 0; i < adults.size(); i++) {
			System.out.println(adults.get(i).getText());
		}
		reportPass("No of Adults are Obtained");
	}
}

package Testing;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Test1 {
	public static void main(String args[]) throws InterruptedException {

		WebDriver driver;
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		
		driver.get("https://www.makemytrip.com/gift-cards/");
		
		//*[@id="root"]/div/div[2]/div/div[1]/div/div[2]/div[3]/ul/li[3]/div/img
		//*[@id="root"]/div/div[2]/div/div[1]/div/div[2]/div[3]/ul/li[3]/div/div/p[1]
		
		WebElement findElement = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div/div[1]/div/div[2]/div[3]/ul/li[3]/div/img"));
//		System.out.println(attribute);
		
//		WebElement corCardEle = driver.findElement(corCard);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", findElement);
		
//		String text = driver.findElement(By.xpath("//*[@id='root']/div/div[2]/div/div[1]/div/div[2]/div[3]/ul/li[3]/div/div/p[1]")).getText();
//		System.out.println(text);
		
		Thread.sleep(3000);
		
		driver.quit();

		

	}
}

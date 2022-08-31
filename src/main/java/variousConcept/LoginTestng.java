package variousConcept;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTestng {

	WebDriver driver;
	String browser;
	String url;
	By userNameField = By.xpath("//input[@id='username']");
	By passwordField = By.xpath("//input[@id='password']");
	By singinButtonField = By.xpath("//button[@name='login']");
	By dashBoardField = By.xpath("//h2[contains(text(),'Dashboard')]");
	By customerField = By.xpath("//span[contains(text(),'Customers')]");
	By addCustomerField = By.xpath("//a[contains(text(),'Add Customer')]");
	By addContactField = By.xpath("//h5[contains(text(),'Add Contact')]");
	By fullNametactField = By.xpath("//input[@id='account']");
	By companyField = By.xpath("//select[@id ='cid']");
	By countryField = By.xpath("//select[@id ='country']");
	By emailField = By.xpath("//input[@id ='email']");

	@BeforeClass
	public void readConfig() {
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			Properties prop = new Properties();
			prop.load(input);
			browser = prop.getProperty("browser");
			url = prop.getProperty("url");

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@BeforeMethod
	public void init() {
		if (browser.equalsIgnoreCase("chrome")) {

			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browser.equalsIgnoreCase("firefox")) {

			System.setProperty("webdriver.gecko.driver", "driver\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@Test(priority =1)
	public void loginTest() {
		driver.findElement(userNameField).sendKeys("demo@techfios.com");
		driver.findElement(passwordField).sendKeys("abc123");
		driver.findElement(singinButtonField).click();
		Assert.assertEquals(driver.findElement(dashBoardField).getText(), "Dashboard", "page not found");
	}

	@Test(priority=2)
	public void addcustomer() throws InterruptedException {
		loginTest();
		Thread.sleep(2000);
		driver.findElement(customerField).click();
		driver.findElement(addCustomerField).click();
		Thread.sleep(3000);
		Assert.assertEquals(driver.findElement(addContactField).getText(), "Add Contact", "page not found");

		generateRandomNum(999);
		
		driver.findElement(fullNametactField).sendKeys("selenium" + generateRandomNum(999));
		selectdropDown(driver.findElement(companyField), "Techfios");
		selectdropDown(countryField, "Monaco");
		driver.findElement(emailField).sendKeys(generateRandomNum(999)+"abc@gmail.com");
	}

	
     private int generateRandomNum(int boundarynum) {
    	 Random rnd = new Random();
 		int genNum = rnd.nextInt(boundarynum);
 		return genNum;
	}

	private void selectdropDown(By locator, String visibletext) {
    	 Select sel =new Select (driver.findElement(locator));
 		 sel.selectByVisibleText(visibletext);
	}

	private void selectdropDown(WebElement element, String visibletext) {
    	 Select sel =new Select (element);
 		 sel.selectByVisibleText(visibletext);
	}

//	@Test
	public void loginTest2() {
		driver.findElement(userNameField).sendKeys("demo@techfios.com");
		driver.findElement(passwordField).sendKeys("abc123");
		driver.findElement(singinButtonField).click();

	}

}

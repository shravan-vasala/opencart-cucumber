package stepDefinitions;


import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;

//import java.util.HashMap;
//import java.util.List;

import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataReader;

public class Steps {
	WebDriver driver;
	HomePage hp;
	LoginPage lp;
	MyAccountPage macc;
	
	List<HashMap<String, String>> datamap; 
	Logger logger;
	ResourceBundle rb;
	String br;
	
	
	@Before // JUnit hook -executes once before starting
	public void setup() {
		logger =LogManager.getLogger(this.getClass());
		rb = ResourceBundle.getBundle("config");
		br = rb.getString("browser");
	}
	
	@After
	public void tearDown(Scenario scenario) {
		System.out.println("Scenario status ====> " + scenario.getStatus());
		if (scenario.isFailed()) {
			TakesScreenshot ts = (TakesScreenshot) driver;
			byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", scenario.getName());	
		} 
		
		driver.quit();
		
	}
	@Given("User Launch browser")
	public void user_launch_browser() {
		if (br.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (br.equals("edge")) {
			driver = new EdgeDriver();
		} else {
			driver = new ChromeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@Given("opens URL {string}")
	public void opens_url(String url) {
	    driver.get(url);
	    driver.manage().window().maximize();
	}

	@When("User navigate to MyAccount menu")
	public void user_navigate_to_my_account_menu() {
		hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on Login");
		
	}

	@When("click on Login")
	public void click_on_login() {
		hp.clickLogin();
		logger.info("Clicked on Login");
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_email_as_and_password_as(String email, String password) {
		lp = new LoginPage(driver);
		lp.setEmail(email);
		logger.info("Provided email");
		lp.setPassword(password);
		logger.info("Provide password");
		
	}

	@When("Click on Login button")
	public void click_on_login_button() {
		lp.clickLogin();
		logger.info("Clicked on Login Button");
	}

	@Then("User navigates to My Account Page")
	public void user_navigates_to_my_account_page() {
		macc = new MyAccountPage(driver);
		boolean target = macc.isMyAccountPageExists();
		if (target) {
			logger.info("LoginSuccess");
			Assert.assertTrue(true);
		} else {
			logger.info("Login failed");
			Assert.fail();
		}
	} 
	

	@Then("check User navigates to MyAccount Page by passing Email and Password with excel row {string}")
	public void check_user_navigates_to_my_account_page_by_passing_email_and_password_with_excel_row(String rows) {
		
		datamap = DataReader.data(System.getProperty("user.dir")+"\\testData\\Opencart_LoginData.xlsx", "Sheet1");
		
		int index = Integer.parseInt(rows) - 1;
		String email = datamap.get(index).get("username");
		String pwd = datamap.get(index).get("password");
		String expRes = datamap.get(index).get("res");
		
		lp = new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		try {
			macc = new MyAccountPage(driver);
			boolean status = macc.isMyAccountPageExists();
			if (expRes.equals("Valid")) {
				if (status) {
					macc.clickLogout();
					Assert.assertTrue(true);
				} else {
					Assert.fail();
				}
			}
			
			if (expRes.equals("Invalid")) {
				if (status) {
					macc.clickLogout();
					Assert.fail();
				} else {
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			Assert.fail();
		}
		
	}

}

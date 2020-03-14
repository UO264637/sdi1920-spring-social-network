package com.uniovi.tests;

import java.util.List;
import static org.junit.Assert.*;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class SecurityTests {

	// CARMEN
//	static String PathFirefox65 = "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe";
//	static String Geckdriver024 = "D:\\UNI\\Tercero\\SDI\\Sesion 5\\Material\\geckodriver024win64.exe";

	// RICHI
	static String PathFirefox65 = "D:\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "E:\\Clase\\SDI\\Material\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:8090";

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	/**
	 * Before each test we navigate to the homepage
	 */
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	/**
	 * After each test we delete the cookies
	 */
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	@BeforeClass
	static public void begin() {
	}

	/**
	 * After the last test we close the explorer
	 */
	@AfterClass
	static public void end() {
		driver.quit();
	}

	

	/********************************************************************************
	 * SECURITY TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR21 - Try to access to the list of user. We must be returned to the login
	 * form.
	 */
	@Test
	public void PR21() {
		driver.navigate().to("http://localhost:8090/user/list");
		PO_LoginView.checkTitle(driver, PO_Properties.getSPANISH());
	}

	/**
	 * PR22 - Try to access the list of posts of an user. We must be returned to the
	 * login form.
	 */
	@Test
	public void PR22() {
		driver.navigate().to("http://localhost:8090/publication/list/chandler@friends.com");
		PO_LoginView.checkTitle(driver, PO_Properties.getSPANISH());
	}

	/**
	 * PR23 - Try to access an administrator option (it could be a placeholder). It
	 * must be warned indicating that's a prohibited action.
	 */
	@Test
	public void PR23() {
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_LoginView.fillForm(driver, "rachel@friends.com", "123");
		PO_View.checkElement(driver, "id", "userHeader");
		driver.navigate().to("http://localhost:8090/admin");
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Forbidden", PO_View.getTimeout());
	}
	
}
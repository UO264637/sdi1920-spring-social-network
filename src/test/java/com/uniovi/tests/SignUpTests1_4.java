package com.uniovi.tests;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;

import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class SignUpTests1_4 {

	// CARMEN
	static String PathFirefox65 = "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "D:\\UNI\\Tercero\\SDI\\Sesion 5\\Material\\geckodriver024win64.exe";

	// RICHI
//	static String PathFirefox65 = "D:\\Mozilla Firefox\\firefox.exe";
//	static String Geckdriver024 = "E:\\Clase\\SDI\\Material\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";

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
	 * SIGN UP TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR01 - User registry with valid data
	 */
	@Test
	public void PR01() {
		// Register as Josefo with email prueba@correo.com
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "prueba@correo.com", "Josefo", "Perez", "12345678", "12345678");
		PO_View.checkElement(driver, "text", "Bienvenido a la página");
	}

	/**
	 * PR02 - User registry with invalid data Empty email, empty name and/or empty
	 * surname)
	 */
	@Test
	public void PR02() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		
		// Empty email 
		PO_RegisterView.fillForm(driver, "", "Josefo", "Perez", "12345678", "12345678");
		PO_View.getP();
		PO_RegisterView.checkKey(driver, "Error.empty", PO_Properties.getSPANISH());
		
		// Empty name
		PO_RegisterView.fillForm(driver, "prueba@correo.com", "", "Perez", "12345678", "12345678");
		PO_RegisterView.checkKey(driver, "Error.signup.name.length", PO_Properties.getSPANISH());
		
		// Empty surname
		PO_RegisterView.fillForm(driver, "prueba@correo.com", "Josefo", "", "12345678", "12345678");
		PO_RegisterView.checkKey(driver, "Error.signup.surname.length", PO_Properties.getSPANISH());
	}

	/**
	 * PR03 - User registry with invalid data Not-matching password
	 */
	@Test
	public void PR03() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		
		// Short password
		PO_RegisterView.fillForm(driver, "prueba@correo.com", "Josefo", "Perez", "12", "12");
		PO_View.getP();
		PO_RegisterView.checkKey(driver, "Error.signup.password.length", PO_Properties.getSPANISH());
		
		// Non-matching password
		PO_RegisterView.fillForm(driver, "prueba@correo.com", "Josefo", "Perez", "12345678", "87654321");
		PO_RegisterView.checkKey(driver, "Error.signup.confirmPassword.coincidence", PO_Properties.getSPANISH());
	}

	/**
	 * PR04 - User registry with invalid data Already registered email
	 */
	@Test
	public void PR04() {
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		
		PO_RegisterView.fillForm(driver, "rachel@friends.com", "Josefo", "Perez", "12345678", "12345678");
		PO_View.getP();
		PO_RegisterView.checkKey(driver, "Error.signup.email.duplicate", PO_Properties.getSPANISH());
	}

}
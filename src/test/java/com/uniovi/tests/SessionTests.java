package com.uniovi.tests;


import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class SessionTests {

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
	 * LOG IN TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR05 - Login with valid data As Admin
	 */
	@Test
	public void PR05() {
		logAs("admin@email.com", "admin");
	}

	/**
	 * PR06 - Login with valid data As standard user
	 */
	@Test
	public void PR06() {
		logAs("rachel@friends.com", "123");
	}

	/**
	 * PR07 - Login with invalid data As standard user: Email and password empty
	 */
	@Test
	public void PR07() {
		// TODO
	}

	/**
	 * PR08 - Login with invalid data As standard user: Existing email but incorrect
	 * password
	 */
	@Test
	public void PR08() {
		// TODO
	}

	/********************************************************************************
	 * LOG OUT TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR09 - Clicking the option should close the session and redirect to the login
	 * page
	 */
	@Test
	public void PR09() {
		logAs("rachel@friends.com", "123");
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		PO_LoginView.checkTitle(driver, PO_Properties.getSPANISH());
	}

	/**
	 * PR10 - Check the logout button is not visible when not logged in
	 */
	@Test
	public void PR10() {
		logAs("rachel@friends.com", "123");
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Regístrate", PO_View.getTimeout());
	}

	/********************************************************************************
	 * HELPING AND NAVIGATION METHODS
	 * 
	 ********************************************************************************/

	private void logAs(String email, String password) {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, email, password);
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "id", "userHeader");
	}

}
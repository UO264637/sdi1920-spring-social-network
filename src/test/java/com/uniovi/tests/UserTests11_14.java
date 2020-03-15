package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class UserTests11_14 {

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
	 * USER LIST TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR11 - Show the list of users and check that it list all the registered users
	 * on the system
	 */
	@Test
	public void PR11() {
		logAs("rachel@friends.com", "123");

		// Count the number of users
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		// Everyone excep from admin and rachel (Chandler, Monica, Phoebe, Joey and Ross)
		assertTrue(elementos.size() == 5);
		PO_View.checkElement(driver, "text", "Phoebe");
		PO_View.checkElement(driver, "text", "Chandler");
		PO_View.checkElement(driver, "text", "Monica");
		PO_View.checkElement(driver, "text", "Joey");
		PO_View.checkElement(driver, "text", "Ross");
	}

	/********************************************************************************
	 * USER SEARCH TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR12 - Empty search must return the whole users list
	 */
	@Test
	public void PR12() {
		logAs("rachel@friends.com", "123");

		SeleniumUtils.esperarSegundos(driver, 1);

		search("");

		// Count the number of users
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		// Everyone excep from admin and rachel (Chandler, Monica, Phoebe, Joey and Ross)
		assertTrue(elementos.size() == 5);
		PO_View.checkElement(driver, "text", "Phoebe");
		PO_View.checkElement(driver, "text", "Chandler");
		PO_View.checkElement(driver, "text", "Monica");
		PO_View.checkElement(driver, "text", "Joey");
		PO_View.checkElement(driver, "text", "Ross");
	}

	/**
	 * PR13 - Search with inexsitant text must return an empty list
	 */
	@Test
	public void PR13() {
		logAs("rachel@friends.com", "123");

		SeleniumUtils.esperarSegundos(driver, 1);

		search("Patata");

		// Como no debería aparecer ningún usuario tampoco debería aparecer ningún email (Todos los emails deben llevar "@")
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "@", 1);
	}

	/**
	 * PR14 - Specific search showing the correct list
	 */
	@Test
	public void PR14() {
		logAs("rachel@friends.com", "123");
		
		SeleniumUtils.esperarSegundos(driver, 1);

		search("le");

		// Count the number of users
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		// Everyone excep from admin and rachel (Chandler, Monica, Phoebe, Joey y Ross)
		assertTrue(elementos.size() == 3);
		PO_View.checkElement(driver, "text", "Chandler"); // Due to name
		PO_View.checkElement(driver, "text", "Monica"); // Due to surname
		PO_View.checkElement(driver, "text", "Ross"); // Due to surname
	}

	/********************************************************************************
	 * HELPING AND NAVIGATION METHODS
	 * 
	 ********************************************************************************/
	private void search(String search) {
		WebElement email = driver.findElement(By.name("searchText"));
		email.click();
		email.clear();
		email.sendKeys(search);
		By boton = By.id("searchBtn");
		driver.findElement(boton).click();
	}

	private void logAs(String email, String password) {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, email, password);
		// Comprobamos que entramos en la pagina privada de Alumno
		PO_View.checkElement(driver, "id", "userHeader");
	}

}
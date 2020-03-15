package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class FriendTests15_19 {

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
	 * SEND FRIEND REQUESTS TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR15 - Send a frienship request, check it is listed to the other user
	 */
	@Test
	@Transactional
	public void PR15() {
		// We log as user
		logAs("rachel@friends.com", "123");
		SeleniumUtils.esperarSegundos(driver, 1);
		// And request a new friend, we'll use Ross
		By enlace = By.xpath("//td[contains(text(), 'Ross')]/following-sibling::*[3]");
		driver.findElement(enlace).click();
		List<WebElement> alert = SeleniumUtils.EsperaCargaPagina(driver, "class", "alert", PO_View.getTimeout());
		assertTrue(alert.size() == 1);
		alert.get(0).click();
		// We log in as Ross
		PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
		logAs("ross@friends.com", "123");
		// And look how many friend requests it has
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "friends-menu",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
		elementos.get(0).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//*[@id=\"friends-menu\"]/ul/li[1]/a",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
		elementos.get(0).click();
		// It should have only one friend request
		List<WebElement> requests = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(requests.size() == 1);
	}

	/**
	 * PR16 - Try to send a frienship request to an already requested user
	 */
	@Test
	@Transactional
	public void PR16() {
		// We log as user
		logAs("rachel@friends.com", "123");
		SeleniumUtils.esperarSegundos(driver, 1);
		// And request a new friend, we'll use Ross
		By enlace = By.xpath("//td[contains(text(), 'Monica')]/following-sibling::*[3]");
		driver.findElement(enlace).click();
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "solicitado", PO_View.getTimeout());
	}

	/********************************************************************************
	 * FRIEND REQUESTS LIST TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR17 - Show the list of pending friend requests. Check with a list of various
	 * requests.
	 */
	@Test
	public void PR17() {
		// We log as user
		logAs("monica@friends.com", "123");
		SeleniumUtils.esperarSegundos(driver, 1);
		// And look how many friend requests it has
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "friends-menu",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
		elementos.get(0).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//*[@id=\"friends-menu\"]/ul/li[1]/a",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
		elementos.get(0).click();
		// It should have only three pending friend requests
		List<WebElement> requests = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(requests.size() == 3);
	}

	/********************************************************************************
	 * ACCEPT REQUEST TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR18 - Over the list of pending friend request, click on the accept button of
	 * one of them and check that the solicitude dissappears.
	 */
	@Test
	public void PR18() {
		// We log as user
		logAs("monica@friends.com", "123");
		SeleniumUtils.esperarSegundos(driver, 1);
		// And look how many friend requests it has
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "friends-menu",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
		elementos.get(0).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//*[@id=\"friends-menu\"]/ul/li[1]/a",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
		elementos.get(0).click();
		// We accept Chandler request
		By enlace = By.xpath("//td[contains(text(), 'Chandler')]/following-sibling::*[2]");
		driver.findElement(enlace).click();
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Chandler", PO_View.getTimeout());
	}

	/********************************************************************************
	 * FRIEND LIST TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR19 - List the friends. Check all the expected users are listed.
	 */
	@Test
	public void PR19() {
		// We log as user
		logAs("rachel@friends.com", "123");
		SeleniumUtils.esperarSegundos(driver, 1);
		// And go to the friends
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "id", "friends-menu",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
		elementos.get(0).click();
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//*[@id=\"friends-menu\"]/ul/li[2]/a",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
		elementos.get(0).click();
		// It should have only two friends
		List<WebElement> requests = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
		assertTrue(requests.size() == 2);

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
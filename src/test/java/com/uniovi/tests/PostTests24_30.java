package com.uniovi.tests;

import java.util.List;
import static org.junit.Assert.*;

import org.junit.*;
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

public class PostTests24_30 {

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
	 * NEW POST TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR24 - Go to the form of creating publications, fill it with valid data and
	 * using the submit button. Check the publication appears in the list of
	 * publications of the user.
	 */
	@Test
	public void PR24() {
		logAs("rachel@friends.com", "123");
		
		// Go to Add Publication
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'publications-menu')]/a");
		elementos.get(0).click();
		
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/add')]");
		elementos.get(0).click();
		
		// Fill form
		PO_PrivateView.fillFormAddPublication(driver, "Publicación 1", "Ejemplo de publicación");
		
		// Go to List publications
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'publications-menu')]/a");
		elementos.get(0).click();
		
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/list')]");
		elementos.get(0).click();

		elementos = PO_View.checkElement(driver, "text", "Publicación 1");
	}

	/**
	 * PR25 - Go to the form of creating publications, fill it with invalida data
	 * (empty title) and using submit. Check the warning shows up.
	 */
	@Test
	public void PR25() {
		logAs("rachel@friends.com", "123");
				
		// Go to add publication
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'publications-menu')]/a");
		elementos.get(0).click();
		
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/add')]");
		elementos.get(0).click();
		
		// Fill form
		PO_PrivateView.fillFormAddPublication(driver, "", "Ejemplo de publicación");
		PO_View.getP();
		PO_RegisterView.checkKey(driver, "Error.publication.title.length", PO_Properties.getSPANISH());
		
	}

	/********************************************************************************
	 * LIST MY POSTS TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR26 - Show post list of an user and check all its publcations are listed
	 */
	@Test
	public void PR26() {
		logAs("rachel@friends.com", "123");
		
		// Go to List Publication
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'publications-menu')]/a");
		elementos.get(0).click();
		
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/list')]");
		elementos.get(0).click();
		
		// Check she has 3 publications (2 + 1)
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 3);

		PO_View.checkElement(driver, "text", "Title1");
		PO_View.checkElement(driver, "text", "Title7");
	}

	/********************************************************************************
	 * LIST MY FRIEND'S POST TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR27 - Show the publications of a friend and check it list all.
	 */
	@Test
	public void PR27() {
		logAs("rachel@friends.com", "123");
		
		// Go to List Friends
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'friends-menu')]/a");
		elementos.get(0).click();
		
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'friends/list')]");
		elementos.get(0).click();
		
		// Go to Phoebe publicatios
		elementos = PO_View.checkElement(driver, "free",
		"//td[contains(text(), 'phoebe')]/following-sibling::*/a[contains(@class,'btn')]");
		System.out.println(elementos.size());
		elementos.get(0).click();
				
		// Check Phoebe has 1 publication
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
		
		PO_View.checkElement(driver, "text", "Title4");
	}

	/**
	 * PR28 - Using an access through URL try to list the publications of and user
	 * we are not friend with. Check that the system shows a error warning.
	 */
	@Test
	public void PR28() {
		logAs("rachel@friends.com", "123");
		
		driver.navigate().to("http://localhost:8090/publication/list/ross@friends.com");
		
		PO_View.checkElement(driver, "text", "Bienvenido a la página");
	}

	/********************************************************************************
	 * NEW POST WITH IMAGE TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR29 - From the form of creating a publication, create a valid publciation
	 * with an image. Check the image shows up with the publication.
	 */
	@Test
	public void PR29() {
		logAs("chandler@friends.com", "123");
		
		// Go to List Friends
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'publications-menu')]/a");
		elementos.get(0).click();
		
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/list')]");
		elementos.get(0).click();
				
		// has an image in one post
		elementos = PO_View.checkElement(driver, "class", "img-thumbnail");
		assertTrue(elementos.size() == 1);
	}

	/**
	 * PR30 - Create a publication with valid data without image. Check the
	 * publication is created and listed correctly without the image.
	 */
	@Test
	public void PR30() {
		logAs("phoebe@friends.com", "123");
		
		// Go to Add Publication
		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'publications-menu')]/a");
		elementos.get(0).click();
		
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/add')]");
		elementos.get(0).click();
		
		// Fill form
		PO_PrivateView.fillFormAddPublication(driver, "Publicación 1", "Ejemplo de publicación");
		
		// Go to List publications
		elementos = PO_View.checkElement(driver, "free", "//li[contains(@id,'publications-menu')]/a");
		elementos.get(0).click();
		
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'publication/list')]");
		elementos.get(0).click();

		elementos = PO_View.checkElement(driver, "text", "Publicación 1");

		// Check there are no images
		try {
			elementos = PO_View.checkElement(driver, "class", "img-thumbnail");
			fail();
		} catch (Exception e) {
			
		}
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
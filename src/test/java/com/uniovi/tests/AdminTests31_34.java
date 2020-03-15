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
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.util.SeleniumUtils;

import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class AdminTests31_34 {

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
	 * ADMIN LISTING USERS TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR31 - Show the list of users and check it does show all the users in the
	 * system.
	 */
	@Test
	public void PR31() {
		logAs("admin@email.com", "admin");

		// Contamos el número de filas de notas
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		// Todos menos el admin y ross que está en la siguiente pagina (Chandler, Monica, Phoebe, Joey y Rachel)
		assertTrue(elementos.size() == 5);
		PO_View.checkElement(driver, "text", "Rachel");
		PO_View.checkElement(driver, "text", "Phoebe");
		PO_View.checkElement(driver, "text", "Chandler");
		PO_View.checkElement(driver, "text", "Monica");
		PO_View.checkElement(driver, "text", "Joey");
		
		// Vamos a la siguiente página
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class,'page-link')]");
		elementos.get(3).click();

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
		
		PO_View.checkElement(driver, "text", "Ross");
	}

	/********************************************************************************
	 * ADMIN MULTIPLE DELETE TESTS
	 * 
	 ********************************************************************************/

	/**
	 * PR32 - List the users and delete the first user on the list. Check the list
	 * updates and the user dissappears.
	 */
	@Test
	public void PR32() {
		logAs("admin@email.com", "admin");
		
		// Comprobamos que está Rachel (Siempre aparece la primera)
		PO_View.checkElement(driver, "text", "Rachel");
					
		// Borramos el primero
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//td[contains(text(), '')]/following-sibling::*/input[contains(@name,'cb')]");
		elementos.get(0).click();
		By boton = By.id("deleteButton");
		driver.findElement(boton).click();
		
		// Comprobamos que no está Rachel pero están los otros 5
		SeleniumUtils.textoNoPresentePagina(driver, "Rachel");
		
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
				PO_View.getTimeout());
		assertTrue(elementos.size() == 5);
		
		// La segunda página debería estar vacía
		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class,'page-link')]");
		elementos.get(3).click();

		try {
			elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
			PO_View.getTimeout());
			fail();
		} catch (Exception e) {
					
		}
	}

	/**
	 * PR33 - List the users and delete the last user on the list. Check the list
	 * updates and the user dissappears.
	 */
	@Test
	public void PR33() {
		logAs("admin@email.com", "admin");
		
		// Comprobamos que está Ross (Siempre aparece el último)
		PO_View.checkElement(driver, "text", "Ross");
							
		// Borramos el último (ahora solo hay una página)
		List<WebElement> elementos = PO_View.checkElement(driver, "free",
				"//td[contains(text(), '')]/following-sibling::*/input[contains(@name,'cb')]");
		elementos.get(elementos.size()-1).click();
		By boton = By.id("deleteButton");
		driver.findElement(boton).click();
				
		// Comprobamos que solo quedan 4 
		SeleniumUtils.textoNoPresentePagina(driver, "Ross");
			
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
					PO_View.getTimeout());
		assertTrue(elementos.size() == 4);
	}

	/**
	 * PR34 - List the users, delete three users and check the list updates and
	 * those users disappears.
	 */
	@Test
	public void PR34() {
		logAs("admin@email.com", "admin");
		
		// Comprobamos que quedan 4 					
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
							PO_View.getTimeout());
		assertTrue(elementos.size() == 4);
							
		// Borramos el último (ahora solo hay una página)
		elementos = PO_View.checkElement(driver, "free",
				"//td[contains(text(), '')]/following-sibling::*/input[contains(@name,'cb')]");
		elementos.get(0).click();
		elementos.get(1).click();
		elementos.get(2).click();
		By boton = By.id("deleteButton");
		driver.findElement(boton).click();
				
		// Comprobamos que solo quedan 4
			
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
					PO_View.getTimeout());
		assertTrue(elementos.size() == 1);
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
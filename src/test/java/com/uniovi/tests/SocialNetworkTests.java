package com.uniovi.tests;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;

import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class SocialNetworkTests {

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
		// TODO
	}

	/**
	 * PR10 - Check the logout button is not visible when not logged in
	 */
	@Test
	public void PR10() {
		// TODO
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
		// TODO
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
		// TODO
	}

	/**
	 * PR13 - Search with inexsitant text must return an empty list
	 */
	@Test
	public void PR13() {
		// TODO
	}

	/**
	 * PR14 - Specific search showing the correct list
	 */
	@Test
	public void PR14() {
		// TODO
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
		// TODO
	}

	/**
	 * PR22 - Try to access the list of posts of an user. We must be returned to the
	 * login form.
	 */
	@Test
	public void PR22() {
		// TODO
	}

	/**
	 * PR23 - Try to access an administrator option (it could be a placeholder). It
	 * must be warned indicating that's a prohibited action.
	 */
	@Test
	public void PR23() {
		// TODO
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
		// TODO
	}

	/**
	 * PR25 - Go to the form of creating publications, fill it with invalida data
	 * (empty title) and using submit. Check the warning shows up.
	 */
	@Test
	public void PR25() {
		// TODO
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
		// TODO
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
		// TODO
	}

	/**
	 * PR28 - Using an access through URL try to list the publications of and user
	 * we are not friend with. Check that the system shows a error warning.
	 */
	@Test
	public void PR28() {
		// TODO
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
		// TODO
	}

	/**
	 * PR30 - Create a publication with valid data without image. Check the
	 * publication is created and listed correctly without the image.
	 */
	@Test
	public void PR30() {
		// TODO
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
		// TODO
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
		// TODO
	}

	/**
	 * PR33 - List the users and delete the last user on the list. Check the list
	 * updates and the user dissappears.
	 */
	@Test
	public void PR33() {
		logAs("admin@email.com", "admin");
		// TODO
	}

	/**
	 * PR34 - List the users, delete three users and check the list updates and
	 * those users disappears.
	 */
	@Test
	public void PR34() {
		logAs("admin@email.com", "admin");
		// TODO
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
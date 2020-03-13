package com.uniovi.tests.pageobjects;

import org.openqa.selenium.WebDriver;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_UserListView extends PO_NavView {
	
	static public void checkTitle(WebDriver driver, int language) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("users", language), getTimeout());
	}

	static public void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1,
			int locale2) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_UserListView.checkTitle(driver, locale1);
		// Cambiamos a segundo idioma
		PO_UserListView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_UserListView.checkTitle(driver, locale2);
		// Volvemos a Español.
		PO_UserListView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_UserListView.checkTitle(driver, locale1);
	}
	
}

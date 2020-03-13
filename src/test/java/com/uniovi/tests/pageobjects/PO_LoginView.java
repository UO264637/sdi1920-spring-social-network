package com.uniovi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.uniovi.tests.util.SeleniumUtils;

public class PO_LoginView extends PO_NavView {
	static public void fillForm(WebDriver driver, String dnip, String passwordp) {
		WebElement dni = driver.findElement(By.name("username"));
		dni.click();
		dni.clear();
		dni.sendKeys(dnip);
		WebElement password = driver.findElement(By.name("password"));
		password.click();
		password.clear();
		password.sendKeys(passwordp);
		By boton = By.className("btn");
		driver.findElement(boton).click();
	}
	
	static public void checkTitle(WebDriver driver, int language) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		SeleniumUtils.EsperaCargaPagina(driver, "text", p.getString("identification", language), getTimeout());
	}

	static public void checkChangeIdiom(WebDriver driver, String textIdiom1, String textIdiom2, int locale1,
			int locale2) {
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_LoginView.checkTitle(driver, locale1);
		// Cambiamos a segundo idioma
		PO_LoginView.changeIdiom(driver, textIdiom2);
		// COmprobamos que el texto de bienvenida haya cambiado a segundo idioma
		PO_LoginView.checkTitle(driver, locale2);
		// Volvemos a Español.
		PO_LoginView.changeIdiom(driver, textIdiom1);
		// Esperamos a que se cargue el saludo de bienvenida en Español
		PO_LoginView.checkTitle(driver, locale1);
	}
}

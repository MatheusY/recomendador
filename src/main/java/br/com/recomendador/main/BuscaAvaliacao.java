package br.com.recomendador.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.recomendador.entity.Restaurante;

public class BuscaAvaliacao {

	public static void main(String[] args) {

		GerenciadorDaAvaliacao gerenciadorDaAvaliacao = new GerenciadorDaAvaliacao();

		String projectPath = System.getProperty("user.dir");
		// System.setProperty("webdriver.gecko.driver",
		// "/home/matheus/.mozilla/firefox/geckodriver");

		System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/chromedriver/chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		// driver.get("https://www.tripadvisor.com.br/Restaurants-g303631-Sao_Paulo_State_of_Sao_Paulo.html");

		driver.get(
				"https://www.tripadvisor.com.br/Restaurants-g303631-Sao_Paulo_State_of_Sao_Paulo.html#EATERY_OVERVIEW_BOX");

		// List<WebElement> perfil =
		// driver.findElements(By.xpath("//div[contains(@class,
		// 'global-nav-profile')]/a"));
		//
		// perfil.get(1).click();
		//
		// WebElement botaoGoogle =
		// driver.findElement(By.className("regGoogleContinue"));
		//
		// botaoGoogle.click();

		List<String> linksRestaurantes = new ArrayList<>();

		

		List<WebElement> proxPags = driver.findElements(By.xpath("//*[@id=\"EATERY_LIST_CONTENTS\"]/div[3]/div/div/a"));
		WebElement proxPag = proxPags.get(3);
		proxPag.click();
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
//		for (int pag = 0; pag < 2; pag++) {
//			// WebElement proxPag = driver.findElement(By.className("nav next rndBtn
//			// ui_button primary taLnk"));
//			try {
//				proxPag = driver.findElement(By.className("next"));
//				proxPag.click();
//			} catch (Exception ex) {
//				proxPag = driver.findElement(By.className("next"));
//				proxPag.click();
//			}
//			try {
//
//				String currentUrl = driver.getCurrentUrl();
//				driver.get(currentUrl);
////				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//			} catch (Exception ex) {
//				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//			}
//		}
		
		List<WebElement> restaurantes = driver.findElements(
				By.xpath("//div[contains(@class, 'restaurants-list-ListCell__nameBlock--1hL7F')]/span/a"));
		// List<WebElement> restaurantes =
		// driver.findElements(By.xpath("//a[contains(@class,'property_title')]"));
		for (WebElement restaurante : restaurantes) {
			String link = restaurante.getAttribute("href");
			if (link != null)
				linksRestaurantes.add(link);
		}
		linksRestaurantes.remove(1);
		linksRestaurantes.remove(0);

		for (String link : linksRestaurantes) {
			WebDriver driverRestaurante = new ChromeDriver();
			driverRestaurante.get(link);

			Restaurante restaurante = gerenciadorDaAvaliacao.obtemRestaurante(driverRestaurante);
			if (restaurante == null) {
				driverRestaurante.close();
				driverRestaurante.quit();
			} else
				gerenciadorDaAvaliacao.buscarAvaliacoesPorRestaurante(driverRestaurante, restaurante);
		}

	}

}

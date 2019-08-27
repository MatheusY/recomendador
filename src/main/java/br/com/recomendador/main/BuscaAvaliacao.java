package br.com.recomendador.main;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.com.recomendador.entity.Restaurante;

public class BuscaAvaliacao {

	public static void main(String[] args) {

		GerenciadorDaAvaliacao gerenciadorDaAvaliacao = new GerenciadorDaAvaliacao();

		String projectPath = System.getProperty("user.dir");
//		System.setProperty("webdriver.gecko.driver", "/home/matheus/.mozilla/firefox/geckodriver");

		System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/chromedriver/chromedriver");

		WebDriver driver = new ChromeDriver();

		driver.get("https://www.tripadvisor.com.br/Restaurants-g303631-Sao_Paulo_State_of_Sao_Paulo.html");

//		List<WebElement> perfil = driver.findElements(By.xpath("//div[contains(@class, 'global-nav-profile')]/a"));
//		
//		perfil.get(1).click();
//		
//		WebElement botaoGoogle = driver.findElement(By.className("regGoogleContinue"));
//		
//		botaoGoogle.click();

		List<WebElement> restaurantes = driver.findElements(By.xpath("//div[contains(@class, 'photo_booking')]/a"));

		List<String> linksRestaurantes = new ArrayList<>();

		for (int pag = 0; pag < 10; pag++) {
			for (WebElement restaurante : restaurantes) {
				String link = restaurante.getAttribute("href");
				if (link != null)
					linksRestaurantes.add(link);
			}

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
			WebElement proxPag = driver.findElement(By.className("nav next rndBtn ui_button primary taLnk"));
			proxPag.click();
			String currentUrl = driver.getCurrentUrl();
			driver.get(currentUrl);
		}

	}

}

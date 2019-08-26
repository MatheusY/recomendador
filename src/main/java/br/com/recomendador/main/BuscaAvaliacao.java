package br.com.recomendador.main;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.recomendador.entity.Restaurante;

public class BuscaAvaliacao {

	public static void main(String[] args) {

		GerenciadorDaAvaliacao gerenciadorDaAvaliacao = new GerenciadorDaAvaliacao();

		String projectPath = System.getProperty("user.dir");
//		System.setProperty("webdriver.gecko.driver", "/home/matheus/.mozilla/firefox/geckodriver");

		System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/chromedriver/chromedriver");

		WebDriver driver = new ChromeDriver();

		driver.get("https://www.tripadvisor.com.br/Restaurants-g303631-Sao_Paulo_State_of_Sao_Paulo.html");

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

				gerenciadorDaAvaliacao.buscarAvaliacoesPorRestaurante(driverRestaurante, restaurante);
			}
			WebElement proxPag = driver.findElement(By.className("nav next rndBtn ui_button primary taLnk"));
			proxPag.click();
			String currentUrl = driver.getCurrentUrl();
			driver.get(currentUrl);
		}
//		WebElement webElement = findElements.get(0);
//		
//		webElement.getText();
//
//		List<WebElement> imagem = webElement.findElements(By.xpath("//span[contains(@class, 'imgWrap')]/img"));
//		List<WebElement> imagem = webElement.findElements(By.xpath("//div[contains(@class, 'listing rebrand')]/div/a/div/span/img"));		
//		String linkImg = imagem.get(0).getAttribute("src");
//		
//		String titulo = webElement.findElement(By.xpath("//div[contains(@class, 'title')]/a")).getText();
//		
//		System.out.println(titulo + " | " + linkImg);
//		driver.get(
//				"https://www.tripadvisor.com.br/Restaurant_Review-g303631-d6351139-Reviews-Meime_Pizzaria-Sao_Paulo_State_of_Sao_Paulo.html");

		gerenciadorDaAvaliacao.salvaClientes();

		gerenciadorDaAvaliacao.salvaTipos();

	}

}

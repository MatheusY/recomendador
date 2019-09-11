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

public class BuscaAvaliacao2 {

	public static void main(String[] args) {

		GerenciadorDaAvaliacao gerenciadorDaAvaliacao = new GerenciadorDaAvaliacao();

		String projectPath = System.getProperty("user.dir");
		// System.setProperty("webdriver.gecko.driver",
		// "/home/matheus/.mozilla/firefox/geckodriver");

		System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/chromedriver/chromedriver");

		WebDriver driver = new ChromeDriver();


		driver.get(
				"https://www.tripadvisor.com.br/Restaurant_Review-g303631-d815838-Reviews-Jam-Sao_Paulo_State_of_Sao_Paulo.html");


		List<String> linksRestaurantes = new ArrayList<>();

		



			Restaurante restaurante = new Restaurante();
			restaurante.setNome("Jam - Itaim Bibi");
			
				gerenciadorDaAvaliacao.buscarAvaliacoesPorRestaurante(driver, restaurante);
		}

}

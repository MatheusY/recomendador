package br.com.recomendador.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.recomendador.entity.Restaurante;

public class GerenciadorDaAvaliacao {
	private Set<String> clientes = new LinkedHashSet<>();
	private Map<Restaurante, Map<Integer, Integer>> avaliacoes = new HashMap<Restaurante, Map<Integer, Integer>>();

	private Set<String> tiposComidas = new LinkedHashSet<>();

	private File clienteFile = new File("cliente.csv");
	private File avaliacoesFile = new File("avaliacoes.csv");
	private File restauranteFile = new File("restaurantes.csv");
	private File tipoFile = new File("tipos.csv");
	private File restauranteTipoFile = new File("restauranteTipoFile.csv");

	private int idRestaurante = 1;

	private FileWriter fileWriter;

	private static final String COMMA_DELIMITER = ",";

	private static final String NEW_LINE_SEPARATOR = "\n";

	public void salvaClientes() {
		try {
			fileWriter = new FileWriter(clienteFile, true);
			Iterator<String> iteratorClientes = clientes.iterator();
			for (int cont = 0; cont < clientes.size(); cont++) {
				fileWriter.append(String.valueOf(cont + 1));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(iteratorClientes.next());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
//			for (String cliente : clientes) {
//				fileWriter.append(cliente);
//				fileWriter.append(NEW_LINE_SEPARATOR);
//			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void buscarAvaliacoesPorRestaurante(WebDriver driver, Restaurante restaurante) {

		List<WebElement> tipos = driver.findElements(By.xpath("//div[contains(@class, 'header_links')]/a"));
		if (!tipos.isEmpty() || tipos.size() > 1) {
			tipos.remove(0);
			tipos.forEach(tipo -> tiposComidas.add(tipo.getText()));
			for (int i = 0; i < tipos.size(); i++) {
				if (tiposComidas.contains(tipos.get(i).getText()))
					salvaRestaurantesTipos(i + 1);
			}
		}

		boolean termino = true;
		Map<Integer, Integer> avaliacao = new HashMap<>();
		
		while (termino) {
			gravaAvaliacao(driver, avaliacao);

			if (driver.findElements(By.xpath("//a[contains(@class, 'nav next ui_button primary disabled')]"))
					.size() != 0) {
				avaliacoes.put(restaurante, avaliacao);
				termino = false;
			}
			if (termino) {
				WebElement proximo = driver.findElement(By.className("next"));
				proximo.click();
				String currentUrl = driver.getCurrentUrl();
				driver.get(currentUrl);
//				driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//				System.out.println("Página: " + pagina);
			}
		}
		driver.close();
		driver.quit();

		salvaAvaliacoes();
	}

	private void salvaRestaurantesTipos(int id_tipo) {
		try {
			fileWriter = new FileWriter(restauranteTipoFile, true);
			fileWriter.append(String.valueOf(idRestaurante));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(id_tipo));
			fileWriter.append(NEW_LINE_SEPARATOR);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void salvaAvaliacoes() {
		try {
			fileWriter = new FileWriter(avaliacoesFile, true);
			for (Map.Entry<Restaurante, Map<Integer, Integer>> a : avaliacoes.entrySet()) {
				Restaurante restaurante = a.getKey();
				String nomeRestaurante = restaurante.getNome();
				int total = 0;
				for (Map.Entry<Integer, Integer> valorAvaliacao : a.getValue().entrySet()) {
					fileWriter.append(nomeRestaurante);
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(valorAvaliacao.getKey()));
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(String.valueOf(valorAvaliacao.getValue()));
					fileWriter.append(NEW_LINE_SEPARATOR);
//					System.out.println("Nome: " + valorAvaliacao.getKey() + " | " + valorAvaliacao.getValue());
					total++;
				}
				System.out.print("Total: " + total);
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void gravaAvaliacao(WebDriver driver, Map<Integer, Integer> avaliacao) {
		List<WebElement> avaliacoes = driver.findElements(By.className("rev_wrap"));
		for (WebElement elemento : avaliacoes) {
			WebElement informacao = elemento.findElement(By.className("info_text"));
//			String titulo = elemento.findElement(By.className("noQuotes")).getText();
			String[] nome = informacao.getText().split("\n");
			boolean nota5 = elemento.findElements(By.className("bubble_50")).size() != 0;
			boolean nota4 = elemento.findElements(By.className("bubble_40")).size() != 0;
			boolean nota3 = elemento.findElements(By.className("bubble_30")).size() != 0;
			boolean nota2 = elemento.findElements(By.className("bubble_20")).size() != 0;
			int nota;
			if (nota5)
				nota = 5;
			else if (nota4)
				nota = 4;
			else if (nota3)
				nota = 3;
			else if (nota2)
				nota = 2;
			else
				nota = 1;

			clientes.add(nome[0]);
			Integer idCliente = buscaCliente(nome[0]);
			boolean eRepetido = avaliacao.containsKey(idCliente);
			if (!eRepetido)
				avaliacao.put(idCliente, nota);
		}

	}

	private Integer buscaCliente(String nome) {
		Iterator<String> iteratorClientes = clientes.iterator();
		for (int i = 0; i < clientes.size(); i++) {
			String nomeCliente = iteratorClientes.next();
			if (nome.equals(nomeCliente)) {
				return i + 1;
			}
		}
		return 0;
	}

	public Restaurante obtemRestaurante(WebDriver driver) {
		String nomeRestaurante = driver.findElement(By.className("ui_header")).getText();

		String enderecoRestaurante = driver.findElement(By.className("street-address")).getText();

		List<WebElement> imagens = driver
				.findElements(By.xpath("//*[contains(@class, 'large_photo_wrapper')]/div/div/img"));

		String linkImagem = imagens.get(0).getAttribute("src");

		Restaurante restaurante = new Restaurante();

		restaurante.setNome(nomeRestaurante);
		restaurante.setEndereco(enderecoRestaurante);
		restaurante.setImagem(linkImagem);
		salvaRestaurante(restaurante);
		return restaurante;
	}

	private void salvaRestaurante(Restaurante restaurante) {
		try {
			fileWriter = new FileWriter(restauranteFile, true);
			fileWriter.append(String.valueOf(idRestaurante));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(restaurante.getNome());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(restaurante.getEndereco());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(restaurante.getImagem());
			fileWriter.append(NEW_LINE_SEPARATOR);
			idRestaurante++;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void salvaTipos() {
		Iterator<String> iteratorTipo = tiposComidas.iterator();
		int idTipo = 1;
		try {
			fileWriter = new FileWriter(tipoFile, true);
			while (iteratorTipo.hasNext()) {
				fileWriter.append(String.valueOf(idTipo));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(iteratorTipo.next());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
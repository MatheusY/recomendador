package br.com.recomendador.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import br.com.recomendador.entity.Restaurante;

public class GerenciadorDaAvaliacao {
	private Set<String> clientes = new LinkedHashSet<>();
	private Set<String> clientesNaoSalvo;
	private Set<String> tiposNaoSalvo;
	private Map<Restaurante, Map<Integer, Integer>> avaliacoes = new HashMap<Restaurante, Map<Integer, Integer>>();

	private Set<String> tiposComidas = new LinkedHashSet<>();

	private File clienteFile = new File("cliente.csv");
	private File avaliacoesFile = new File("avaliacoes.csv");
	private File restauranteFile = new File("restaurantes.csv");
	private File tipoFile = new File("tipos.csv");
	private File restauranteTipoFile = new File("restauranteTipoFile.csv");

	private int idRestaurante = 102;

	private FileWriter fileWriter;

	private static final String COMMA_DELIMITER = ",";

	private static final String NEW_LINE_SEPARATOR = "\n";

	public GerenciadorDaAvaliacao() {
		carregaDados();
	}

	private void carregaDados() {
		carregaCliente();
		carregaTipos();
//		carregaIdRestaurante();

	}

	private void carregaIdRestaurante() {
		BufferedReader br = null;
		String linha = "";
		int cont = 1;
		try {

			br = new BufferedReader(new FileReader("restaurantes.csv"));
			while ((linha = br.readLine()) != null) {
				cont++;
			}
			idRestaurante = cont;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void carregaTipos() {
		BufferedReader br = null;
		String linha = "";
		try {

			br = new BufferedReader(new FileReader("tipos.csv"));
			while ((linha = br.readLine()) != null) {

				String[] tipo = linha.split(COMMA_DELIMITER);

				tiposComidas.add(tipo[1]);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void carregaCliente() {
		BufferedReader br = null;
		String linha = "";
		try {

			br = new BufferedReader(new FileReader("cliente.csv"));
			while ((linha = br.readLine()) != null) {

				String[] cliente = linha.split(COMMA_DELIMITER);

				clientes.add(cliente[1]);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void salvaClientes() {
		try {
			int idCliente = clientes.size() - clientesNaoSalvo.size();
			fileWriter = new FileWriter(clienteFile, true);
			Iterator<String> iteratorClientes = clientesNaoSalvo.iterator();
			for (int cont = 0; cont < clientesNaoSalvo.size(); cont++) {
				fileWriter.append(String.valueOf(idCliente + 1));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(iteratorClientes.next());
				fileWriter.append(NEW_LINE_SEPARATOR);
				idCliente++;
			}
			// for (String cliente : clientes) {
			// fileWriter.append(cliente);
			// fileWriter.append(NEW_LINE_SEPARATOR);
			// }
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void buscarAvaliacoesPorRestaurante(WebDriver driver, Restaurante restaurante) {

		tiposNaoSalvo = new LinkedHashSet<>();
		List<WebElement> tipos = driver.findElements(By.xpath("//div[contains(@class, 'header_links')]/a"));
		if (!tipos.isEmpty() || tipos.size() > 1) {
			tipos.remove(0);
			tipos.forEach(tipo -> tiposNaoSalvo.add(tipo.getText()));
			tiposNaoSalvo = tiposNaoSalvo.stream().filter(tipo -> !tiposComidas.contains(tipo))
					.collect(Collectors.toSet());
			tipos.forEach(tipo -> tiposComidas.add(tipo.getText()));
			salvaTipos();
			for (int i = 0; i < tipos.size(); i++) {
				if (tiposComidas.contains(tipos.get(i).getText()))
					salvaRestaurantesTipos(i + 1);
			}
		}
		clientesNaoSalvo = new LinkedHashSet<>();

		boolean termino = true;
		Map<Integer, Integer> avaliacao = new HashMap<>();
//		List<WebElement> findElements = driver.findElements(By.className("last"));
//		WebElement ultimaPagina = findElements.get(2);
//		ultimaPagina.click();
//		String ultimaUrl = driver.getCurrentUrl();
//		driver.get(ultimaUrl);
		
		int cont = 0;
		while (termino) {
			driver = gravaAvaliacao(driver, avaliacao);

			if (driver.findElements(By.xpath("//a[contains(@class, 'nav next ui_button primary disabled')]"))
					.size() != 0) {
				avaliacoes.put(restaurante, avaliacao);
				termino = false;
			}
			if (termino) {
				WebElement proximo;
				try {
					proximo = driver.findElement(By.className("next"));
				} catch (NoSuchElementException e) {
					String currentUrl = driver.getCurrentUrl();
					driver.manage().timeouts().implicitlyWait(1, TimeUnit.HOURS);
					driver.close();
					driver.quit();
					driver = new ChromeDriver();
					driver.get(currentUrl);
					proximo = driver.findElement(By.className("next"));
				}
				proximo.click();
				String currentUrl = driver.getCurrentUrl();
				driver.get(currentUrl);
			}
		}
		driver.close();
		driver.quit();

		salvaAvaliacoes();
		salvaClientes();
	}

	private void salvaRestaurantesTipos(int id_tipo) {
		try {
			fileWriter = new FileWriter(restauranteTipoFile, true);
			fileWriter.append(String.valueOf(idRestaurante - 1));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.valueOf(id_tipo));
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.flush();
			fileWriter.close();
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
					// System.out.println("Nome: " + valorAvaliacao.getKey() + " | " +
					// valorAvaliacao.getValue());
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

	private WebDriver gravaAvaliacao(WebDriver driver, Map<Integer, Integer> avaliacao) {
		List<WebElement> avaliacoes = new ArrayList<>();
		try {
			avaliacoes = driver.findElements(By.className("rev_wrap"));
		} catch (NoSuchElementException e) {
			String currentUrl = driver.getCurrentUrl();
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.HOURS);
			driver.close();
			driver.quit();
			driver = new ChromeDriver();
			driver.get(currentUrl);
			avaliacoes = driver.findElements(By.className("rev_wrap"));
		}
		for (WebElement elemento : avaliacoes) {
			WebElement informacao = elemento.findElement(By.className("info_text"));
			// String titulo = elemento.findElement(By.className("noQuotes")).getText();
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

			int tamanhoClienteAntes = clientes.size();
			clientes.add(nome[0]);
			if (tamanhoClienteAntes != clientes.size())
				clientesNaoSalvo.add(nome[0]);
			Integer idCliente = buscaCliente(nome[0]);
			boolean eRepetido = avaliacao.containsKey(idCliente);
			if (!eRepetido)
				avaliacao.put(idCliente, nota);
		}
		return driver;

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
		boolean valido = salvaRestaurante(restaurante);
		if (valido)
			return restaurante;
		else
			return null;
	}

	private boolean salvaRestaurante(Restaurante restaurante) {
		try {
			boolean verificaDuplicada = buscaRestaurante(restaurante);
			if (verificaDuplicada)
				return false;
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
			fileWriter.flush();
			fileWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean buscaRestaurante(Restaurante restaurante) {
		BufferedReader br = null;
		String linha = "";
		try {

			br = new BufferedReader(new FileReader("restaurantes.csv"));
			while ((linha = br.readLine()) != null) {

				String[] rest = linha.split(COMMA_DELIMITER);

				if (restaurante.getNome().equals(rest[1]))
					return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public void salvaTipos() {
		int idTipo = tiposComidas.size() - tiposNaoSalvo.size();
		Iterator<String> iteratorTipo = tiposNaoSalvo.iterator();
		try {
			fileWriter = new FileWriter(tipoFile, true);
			while (iteratorTipo.hasNext()) {
				fileWriter.append(String.valueOf(idTipo + 1));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(iteratorTipo.next());
				fileWriter.append(NEW_LINE_SEPARATOR);
				idTipo++;
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

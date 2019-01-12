package br.com.recomendador.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class geradorDeAvaliacao {
	public static void main(String[] args) {
		List<Integer> clientes = new ArrayList<>();
		List<Integer> restaurantes = new ArrayList<>();
		List<Integer> notas = new ArrayList<>();

		for (int x = 0; x < 10000; x++) {
			Random rand = new Random();
			int cliente = rand.nextInt(600) + 1;
			int restaurante = rand.nextInt(100) + 1;
			int nota = rand.nextInt(5) + 1;

			if (x != 0) {
				boolean repetido = false;
				for (int y = 0; y < x; y++) {
					if (clientes.get((y)).equals(cliente) && restaurantes.get((y)).equals(restaurante)) {
						x--;
						repetido = true;
					}
				}
				if(!repetido) {
					clientes.add(cliente);
					restaurantes.add(restaurante);
					notas.add(nota);
				}

			} else {
				clientes.add(cliente);
				restaurantes.add(restaurante);
				notas.add(nota);
			}
		}

		for (int i = 0; i < clientes.size(); i++) {
			System.out.println("INSERT INTO TB_AVALIACAO(id_avaliacao, nota, id_cliente, id_restaurante) VALUES ("
					+ (i + 1) + ", " + notas.get(i) + ", " + clientes.get(i) + ", " + restaurantes.get(i) + ");");
		}

	}
}

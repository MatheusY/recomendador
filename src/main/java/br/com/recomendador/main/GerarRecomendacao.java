package br.com.recomendador.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import br.com.recomendador.entity.Avaliacao;
import br.com.recomendador.exception.SystemException;

public class GerarRecomendacao {

	private static final String COMMA_DELIMITER = ",";

	private static final String NEW_LINE_SEPARATOR = "\n";

	public void GeraAvaliacaoCSV(List<Avaliacao> avaliacoes) throws SystemException {
		try {
			File file = new File("Avaliacao.csv");
			FileWriter fileWriter = new FileWriter(file);
			
			for (Avaliacao avaliacao : avaliacoes) {
				fileWriter.append(String.valueOf(avaliacao.getCliente().getId()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(avaliacao.getRestaurante().getId()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(String.valueOf(avaliacao.getNota()));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			
			fileWriter.flush();
			fileWriter.close();
		} catch (Exception e) {
			System.out.println("Erro ao gerar o csv");
			throw new SystemException("PROBLEMA AO GERAR O CSV", e);
		}

	}

	public List<Long> geraRecomendacao(Long idCliente) throws IOException, TasteException {
		File file = new File("Avaliacao.csv");
		FileDataModel model = new FileDataModel(file);

		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

		ThresholdUserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		List<RecommendedItem> recommendations = recommender.recommend(idCliente, 3);

		List<Long> listaId = new ArrayList<>();
		for (RecommendedItem recommendation : recommendations) {
			listaId.add(recommendation.getItemID()); 
		}
		return listaId;
	}
}

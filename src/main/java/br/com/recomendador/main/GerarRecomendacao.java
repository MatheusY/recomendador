package br.com.recomendador.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import br.com.recomendador.entity.Avaliacao;
import br.com.recomendador.exception.SystemException;

public class GerarRecomendacao {

	private static final String COMMA_DELIMITER = ",";

	private static final String NEW_LINE_SEPARATOR = "\n";
	File file = new File("Avaliacao.csv");

	private FileWriter fileWriter;

	public void geraAvaliacaoCSV(List<Avaliacao> avaliacoes) throws SystemException {
		try {
			fileWriter = new FileWriter(file);

			for (Avaliacao avaliacao : avaliacoes) {
				escreveAvaliacao(fileWriter, avaliacao);
			}
			fileWriter.flush();
			fileWriter.close();

		} catch (Exception e) {
			System.out.println("Erro ao gerar o csv");
			throw new SystemException("PROBLEMA AO GERAR O CSV", e);
		}
	}

	private void escreveAvaliacao(FileWriter fileWriter, Avaliacao avaliacao) throws IOException {
		fileWriter.append(String.valueOf(avaliacao.getCliente().getId()));
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(String.valueOf(avaliacao.getRestaurante().getId()));
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(String.valueOf(avaliacao.getNota()));
		fileWriter.append(NEW_LINE_SEPARATOR);
	}

	public void adicionaAvaliacao(Avaliacao avaliacao) throws SystemException {
		try {
			fileWriter = new FileWriter(file, true);
			escreveAvaliacao(fileWriter, avaliacao);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Erro ao adicionar avaliação no csv");
			throw new SystemException("PROBLEMA AO ADICIONAR NO CSV", e);
		}
	}


	public List<Long> geraRecomendacaoUserPearson(Long idCliente) throws IOException, TasteException {
		FileDataModel model = new FileDataModel(file);

		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		
//		UserSimilarity sim = new PearsonCorrelationSimilarity(model, Weighting.WEIGHTED);

		ThresholdUserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
		
		
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		List<RecommendedItem> recommendations = recommender.recommend(idCliente, 3);

		List<Long> listaId = new ArrayList<>();
		for (RecommendedItem recommendation : recommendations) {
			listaId.add(recommendation.getItemID());
		}
		return listaId;
	}
	
	public List<Long> geraRecomendacaoUserEuclidean(Long idCliente) throws IOException, TasteException {
		FileDataModel model = new FileDataModel(file);

		EuclideanDistanceSimilarity similarity = new EuclideanDistanceSimilarity(model);
		
//		UserSimilarity sim = new PearsonCorrelationSimilarity(model, Weighting.WEIGHTED);

		ThresholdUserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
		
		
		UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		List<RecommendedItem> recommendations = recommender.recommend(idCliente, 3);

		List<Long> listaId = new ArrayList<>();
		for (RecommendedItem recommendation : recommendations) {
			listaId.add(recommendation.getItemID());
		}
		return listaId;
	}
	
	public List<Long> geraRecomendacaoItemEuclidean(Long idCliente) throws IOException, TasteException {
		FileDataModel model = new FileDataModel(file);

		EuclideanDistanceSimilarity similarity = new EuclideanDistanceSimilarity(model);
		

		GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
		
		
		List<RecommendedItem> recommendations = recommender.recommend(idCliente, 3);

		List<Long> listaId = new ArrayList<>();
		for (RecommendedItem recommendation : recommendations) {
			listaId.add(recommendation.getItemID());
		}
		return listaId;
	}
	
	
	public List<Long> geraRecomendacaoItemLogLikeSimilarity(Long idCliente) throws IOException, TasteException {
		FileDataModel model = new FileDataModel(file);

		 LogLikelihoodSimilarity similarity = new LogLikelihoodSimilarity(model);
		
		GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, similarity);
		
		List<RecommendedItem> recommendations = recommender.recommend(idCliente, 3);

		List<Long> listaId = new ArrayList<>();
		for (RecommendedItem recommendation : recommendations) {
			listaId.add(recommendation.getItemID());
		}
		return listaId;
	}
	
	
	
	public List<Long> geraRecomendacaoUserLogLikeSimilarity(Long idCliente) throws IOException, TasteException {
		FileDataModel model = new FileDataModel(file);
		
		 LogLikelihoodSimilarity similarity = new LogLikelihoodSimilarity(model);
		
		ThresholdUserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
		
		GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		
		List<RecommendedItem> recommendations = recommender.recommend(idCliente, 3);

		List<Long> listaId = new ArrayList<>();
		for (RecommendedItem recommendation : recommendations) {
			listaId.add(recommendation.getItemID());
		}
		return listaId;
	}
}

package br.com.recomendador.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.recomendador.dao.ITipoDAO;
import br.com.recomendador.entity.Tipo;

@Stateless
public class TipoBusiness implements ITipoBusiness {
	
	@Inject
	private ITipoDAO tipoDAO;
	
	@Override
	public List<Tipo> listarTipos() {
		return tipoDAO.searchAll();
	}

}

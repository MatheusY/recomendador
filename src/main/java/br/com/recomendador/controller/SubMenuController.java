package br.com.recomendador.controller;

import javax.faces.bean.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class SubMenuController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6873059915222478587L;

	
	public void abreMenu() {
		renderizarTela();
	}
}

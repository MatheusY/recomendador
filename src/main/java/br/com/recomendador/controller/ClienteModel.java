package br.com.recomendador.controller;

import java.io.Serializable;

import javax.faces.bean.SessionScoped;

import br.com.recomendador.entity.Cliente;

@SessionScoped
public class ClienteModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6462175656474438867L;

	private Cliente cliente;


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}

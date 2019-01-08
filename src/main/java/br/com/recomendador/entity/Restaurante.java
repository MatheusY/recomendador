package br.com.recomendador.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TB_RESTAURANTE")
public class Restaurante implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4863953864167272898L;

	@Id
	@GeneratedValue
	@Column(name="ID_RESTAURANTE")
	private Long id;
	
	@Column(name="NOME", unique=true)
	private String nome;
	
	@Column(name="ENDERECO")
	private String endereco;
	
	@Column(name="imagem")
	private String imagem;
	
	@Column(name="TIPO")
	private String tipo;
	
	@OneToMany(mappedBy="restaurante", targetEntity = Avaliacao.class, cascade = CascadeType.ALL)
	List<Avaliacao> avaliacoes = new ArrayList<>();
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}

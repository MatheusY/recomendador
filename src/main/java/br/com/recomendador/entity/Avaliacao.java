package br.com.recomendador.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TB_AVALIACAO", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "ID_CLIENTE", "ID_RESTAURANTE" }) })
public class Avaliacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3869475147238087584L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "avaliaca_sequence")
	@SequenceGenerator(name = "avaliaca_sequence", sequenceName = "ava_seq", allocationSize = 1)
	@Column(name = "ID_AVALIACAO")
	private Long id;

	@Column(name = "NOTA")
	private Integer nota;

	@ManyToOne
	@JoinColumn(name = "ID_CLIENTE")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "ID_RESTAURANTE")
	private Restaurante restaurante;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

}

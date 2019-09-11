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
@Table(name="TB_RESTAURANTE_TIPO", uniqueConstraints = {@UniqueConstraint(columnNames = {"ID_RESTAURANTE", "ID_TIPO"})})
public class RestauranteTipo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7784956976161850410L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurante_tipo_sequence")
	@SequenceGenerator(name = "restaurante_tipo_sequence", sequenceName = "rest_tipo_seq", allocationSize = 1)
	@Column(name = "ID_RESTAURANTE_TIPO")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "ID_RESTAURANTE")
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name = "ID_TIPO")
	private Tipo tipo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	
	
}

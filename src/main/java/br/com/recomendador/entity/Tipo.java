package br.com.recomendador.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="TB_TIPO")
public class Tipo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7770059570056088334L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tipo_sequence")
	@SequenceGenerator(name="tipo_sequence", sequenceName="tip_seq")
	@Column(name="ID_TIPO")
	private Long id;
	
	@Column(name="NOME", unique=true)
	private String nome;
	
	@OneToMany(mappedBy="tipo", targetEntity = RestauranteTipo.class)
	List<RestauranteTipo> restauranteTipos = new ArrayList<>();

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

	public List<RestauranteTipo> getRestauranteTipos() {
		return restauranteTipos;
	}

	public void setRestauranteTipos(List<RestauranteTipo> restauranteTipos) {
		this.restauranteTipos = restauranteTipos;
	}
	
}

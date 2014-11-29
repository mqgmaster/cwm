package br.ufrgs.inf.gar.condo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table
public class Sector extends AbstractEntity<Integer, Sector> {
	
	@Id
	@GeneratedValue
	@Column
	private Integer id;

	@Column
	private String name;
	
	@ManyToOne
    @JoinColumn(name="condominium_id")
    private Condominium condominium;
	
	public Sector() {
	}

	public Sector(String name, Condominium condo) {
		this.name = name;
		this.condominium = condo;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Condominium getCondominium() {
		return condominium;
	}

	public void setCondominium(Condominium condominium) {
		this.condominium = condominium;
	}
}

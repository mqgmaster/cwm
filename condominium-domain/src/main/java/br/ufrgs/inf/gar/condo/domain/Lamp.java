package br.ufrgs.inf.gar.condominium.domain;

import java.io.Serializable;

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
public class Lamp implements Serializable {

	@Id
	@GeneratedValue
    @Column
    private Integer id;
	
	@ManyToOne
    @JoinColumn(name="sector_id")
    private Sector sector;
    
    @Column(name="is_on")
    private Boolean isOn;
    
    public Lamp() {
	}
    
    public Lamp(Sector sector) {
    	this.sector = sector;
        this.isOn = false;
    }
    
    public boolean equals(Object another) {
        if ( !(another instanceof Lamp) ) return false;
 
        final Lamp apt = (Lamp) another;
         
        return this.id.equals(apt.getId());
    }
     
    public int hashCode() {
        return id.hashCode();
    }

	public Integer getId() {
		return id;
	}

	public Boolean isOn() {
		return isOn;
	}

	public void setOn(Boolean isOn) {
		this.isOn = isOn;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}
}

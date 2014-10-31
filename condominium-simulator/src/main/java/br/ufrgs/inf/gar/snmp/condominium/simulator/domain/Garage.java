package br.ufrgs.inf.gar.snmp.condominium.simulator.domain;

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
public class Garage implements Serializable {

	@Id
	@GeneratedValue
    @Column
    private Integer id;
    
    @Column
    private Integer number;
     
    @Column(name="is_occupied")
    private Boolean isOccupied;
    
    @ManyToOne
    @JoinColumn(name="apartment_id")
    private Apartment apartment;
    
    @ManyToOne
    @JoinColumn(name="condominium_id")
    private Condominium condominium;
    
    public Garage() {
	}
    
    public Garage(Integer number, Apartment apt, Condominium condominium) {
        this.number = number;
        this.apartment = apt;
        this.isOccupied = false;
        this.condominium = condominium;
    }
    
    public boolean equals(Object another) {
        if ( !(another instanceof Garage) ) return false;
 
        final Garage apt = (Garage) another;
         
        return this.id.equals(apt.getId());
    }
     
    public int hashCode() {
        return id.hashCode();
    }

	public Integer getId() {
		return id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(Boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public Condominium getCondominium() {
		return condominium;
	}

	public void setCondominium(Condominium condominium) {
		this.condominium = condominium;
	}
}

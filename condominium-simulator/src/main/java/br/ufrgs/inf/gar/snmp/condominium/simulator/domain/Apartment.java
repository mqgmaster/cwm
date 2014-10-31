package br.ufrgs.inf.gar.snmp.condominium.simulator.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
 
/**
 * @author Mauricio Quatrin Guerreiro
 */
 
@SuppressWarnings("serial")
@Entity
@Table
public class Apartment implements Serializable {
     
    @Id
    @GeneratedValue
    @Column
    private Integer id;
    
    @Column
    private Integer number;
     
    @Column(name="owner_name")
    private String ownerName;
    
    @Column(name="num_rooms")
    private Integer numRooms;
    
    @Column(name="num_people")
    private Integer numPeople;
    
    @Column(name="water_consumption")
    private Float waterConsumption;
     
    @Column(name="light_consumption")
    private Float lightConsumption;
    
    @ManyToOne
    @JoinColumn(name="condominium_id")
    private Condominium condominium;
    
    public Apartment() {
	}
     
    public Apartment(Integer number, String ownerName, Integer numRooms, Condominium condominium) {
        this.number = number;
        this.ownerName = ownerName;
        this.numRooms = numRooms;
        this.condominium = condominium;
        this.numPeople = 0;
        this.lightConsumption = new Float(0);
        this.waterConsumption = new Float(0);
    }
     
    public boolean equals(Object another) {
        if ( !(another instanceof Apartment) ) return false;
 
        final Apartment apt = (Apartment) another;
         
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

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Integer getNumRooms() {
		return numRooms;
	}

	public void setNumRooms(Integer numRooms) {
		this.numRooms = numRooms;
	}

	public Integer getNumPeople() {
		return numPeople;
	}

	public void setNumPeople(Integer numPeople) {
		this.numPeople = numPeople;
	}

	public Float getWaterConsumption() {
		return waterConsumption;
	}

	public void setWaterConsumption(Float waterConsumption) {
		this.waterConsumption = waterConsumption;
	}

	public Float getLightConsumption() {
		return lightConsumption;
	}

	public void setLightConsumption(Float lightConsumption) {
		this.lightConsumption = lightConsumption;
	}

	public Condominium getCondominium() {
		return condominium;
	}

	public void setCondominium(Condominium condominium) {
		this.condominium = condominium;
	}
}
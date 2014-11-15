package br.ufrgs.inf.gar.condominium.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
 
/**
 * @author Mauricio Quatrin Guerreiro
 */
 
@SuppressWarnings("serial")
@Entity
@Table
public class Condominium implements Serializable {
     
    @Id
    @GeneratedValue
    @Column
    private Integer id;
     
    @Column
    private String name;
     
    @Column
    private String address;
    
    @Column(name="manager_name")
    private String managerName;
    
    @Column(name="water_consumption")
    private Float waterConsumption; //litros de agua consumidos pelo condominio, retirando
    								//o consumo de cada apartamento
    
    @Column(name="water_cons_limit")
    private Float waterConsLimit;
    
    @Column(name="light_consumption")
    private Float lightConsumption; //watts consumidos pelo condominio, retirando
    								//o consumo de cada apartamento
    
    @Column(name="light_cons_limit")
    private Float lightConsLimit;
    
    @Column(name="num_unknown_people")
    private Integer numUnknownPeople; 	//numero total de pessoas não identificadas no condominio (ladrão?).
    
    public Condominium() {
    }
     
    public Condominium(String name, String address, String managerName) {
        this.name = name;
        this.address = address;
        this.managerName = managerName;
        this.waterConsumption = new Float(0);
        this.waterConsLimit = new Float(0.01);
        this.lightConsumption = new Float(0);
        this.lightConsLimit = new Float(0.8);
        this.numUnknownPeople = 0;
    }
     
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object another) {
        if ( !(another instanceof Condominium) ) return false;
 
        final Condominium cat = (Condominium) another;
         
        return this.id.equals(cat.getId()); 
    }
     
    public int hashCode() {
        return id.hashCode();
    }

	public Integer getId() {
		return id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
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

	public Integer getNumUnknownPeople() {
		return numUnknownPeople;
	}

	public void setNumUnknownPeople(Integer numUnknownPeople) {
		this.numUnknownPeople = numUnknownPeople;
	}

	public Float getWaterConsLimit() {
		return waterConsLimit;
	}

	public void setWaterConsLimit(Float waterConsLimit) {
		this.waterConsLimit = waterConsLimit;
	}

	public Float getLightConsLimit() {
		return lightConsLimit;
	}

	public void setLightConsLimit(Float lightConsLimit) {
		this.lightConsLimit = lightConsLimit;
	}
}
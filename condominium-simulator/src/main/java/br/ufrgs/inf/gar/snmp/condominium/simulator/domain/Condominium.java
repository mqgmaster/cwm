package br.ufrgs.inf.gar.snmp.condominium.simulator.domain;

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
    private Float waterConsumption; //litros
    
    @Column(name="light_consumption")
    private Float lightConsumption; //watts
    
    public Condominium() {
    }
     
    public Condominium(String name, String address, String managerName) {
        this.name = name;
        this.address = address;
        this.managerName = managerName;
        this.waterConsumption = new Float(0);
        this.lightConsumption = new Float(0);
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
}
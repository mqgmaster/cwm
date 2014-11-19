package br.ufrgs.inf.gar.condo.domain;

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
public class Condominium extends AbstractEntity<Integer, Condominium> {
     
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
    
    @Column(name="total_water_usage")
    private String totalWaterUsage; 		//litros de agua consumidos pelo condominio, retirando
    									//o consumo de cada apartamento
    @Column(name="instant_water_usage")
    private String instantWaterUsage; 
    
    @Column(name="total_water_limit")
    private String totalWaterLimit;
    
    @Column(name="total_electric_usage")
    private String totalElectricUsage; 		//watts consumidos pelo condominio, retirando
    								     	//o consumo de cada apartamento
    @Column(name="total_electric_limit")
    private String totalElectricLimit;
    
    @Column(name="instant_electric_usage")
    private String instantElectricUsage;
    
    @Column(name="num_unknown_people")
    private Integer numUnknownPeople; 		//numero total de pessoas não identificadas no condominio (ladrão?).
    
    public Condominium() {
    }
     
    public Condominium(String name, String address, String managerName) {
        this.name = name;
        this.address = address;
        this.managerName = managerName;
        this.totalWaterUsage = "0.0";
        this.totalWaterLimit = "0.0";
        this.instantWaterUsage = "0.0";
        this.totalElectricUsage = "0.0";
        this.totalElectricLimit = "0.0";
        this.instantElectricUsage = "0.0";
        this.numUnknownPeople = 0;
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

	public Integer getNumUnknownPeople() {
		return numUnknownPeople;
	}

	public void setNumUnknownPeople(Integer numUnknownPeople) {
		this.numUnknownPeople = numUnknownPeople;
	}

	public String getTotalWaterUsage() {
		return totalWaterUsage;
	}
	
	public Float getTotalWaterUsageFloat() {
		return Float.valueOf(totalWaterUsage);
	}

	public void setTotalWaterUsage(String totalWaterUsage) {
		this.totalWaterUsage = totalWaterUsage;
	}
	
	public void setTotalWaterUsage(Float totalWaterUsage) {
		this.totalWaterUsage = String.valueOf(totalWaterUsage);
	}

	public String getInstantWaterUsage() {
		return instantWaterUsage;
	}
	
	public Float getInstantWaterUsageFloat() {
		return Float.valueOf(instantWaterUsage);
	}

	public void setInstantWaterUsage(String instantWaterUsage) {
		this.instantWaterUsage = instantWaterUsage;
	}
	
	public void setInstantWaterUsage(Float instantWaterUsage) {
		this.instantWaterUsage = String.valueOf(instantWaterUsage);
	}

	public String getTotalWaterLimit() {
		return totalWaterLimit;
	}
	
	public Float getTotalWaterLimitFloat() {
		return Float.valueOf(totalWaterLimit);
	}

	public void setTotalWaterLimit(String totalWaterLimit) {
		this.totalWaterLimit = totalWaterLimit;
	}
	
	public void setTotalWaterLimit(Float totalWaterLimit) {
		this.totalWaterLimit = String.valueOf(totalWaterLimit);
	}

	public String getTotalElectricUsage() {
		return totalElectricUsage;
	}
	
	public Float getTotalElectricUsageFloat() {
		return Float.valueOf(totalElectricUsage);
	}

	public void setTotalElectricUsage(String totalElectricUsage) {
		this.totalElectricUsage = totalElectricUsage;
	}
	
	public void setTotalElectricUsage(Float totalElectricUsage) {
		this.totalElectricUsage = String.valueOf(totalElectricUsage);
	}

	public String getTotalElectricLimit() {
		return totalElectricLimit;
	}
	
	public Float getTotalElectricLimitFloat() {
		return Float.valueOf(totalElectricLimit);
	}

	public void setTotalElectricLimit(String totalElectricLimit) {
		this.totalElectricLimit = totalElectricLimit;
	}
	
	public void setTotalElectricLimit(Float totalElectricLimit) {
		this.totalElectricLimit = String.valueOf(totalElectricLimit);
	}

	public String getInstantElectricUsage() {
		return instantElectricUsage;
	}

	public Float getInstantElectricUsageFloat() {
		return Float.valueOf(instantElectricUsage);
	}

	public void setInstantElectricUsage(String instantElectricUsage) {
		this.instantElectricUsage = instantElectricUsage;
	}
	
	public void setInstantElectricUsage(Float instantElectricUsage) {
		this.instantElectricUsage = String.valueOf(instantElectricUsage);
	}
}
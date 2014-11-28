package br.ufrgs.inf.gar.condo.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
    
    @AttributeOverride(name="value", column= @Column(name="total_water_usage"))
    @Embedded
    private UsageValue totalWaterUsage; 	//litros de agua consumidos pelo condominio, retirando
    									//o consumo de cada apartamento
    @AttributeOverride(name="value", column= @Column(name="instant_water_usage"))
    @Embedded
    private UsageValue instantWaterUsage; 
    
    @AttributeOverride(name="value", column= @Column(name="instant_water_limit"))
    @Embedded
    private UsageValue instantWaterLimit;
    
    @AttributeOverride(name="value", column= @Column(name="total_electric_usage"))
    @Embedded
    private UsageValue totalElectricUsage; 		//watts consumidos pelo condominio, retirando
    								     	//o consumo de cada apartamento
    @AttributeOverride(name="value", column= @Column(name="instant_electric_limit"))
    @Embedded
    private UsageValue instantElectricLimit;
    
    @AttributeOverride(name="value", column= @Column(name="instant_electric_usage"))
    @Embedded
    private UsageValue instantElectricUsage;
    
    @AttributeOverride(name="value", column= @Column(name="apt_instant_electric_limit"))
    @Embedded
    private UsageValue aptInstantElectricLimit;
    
    @AttributeOverride(name="value", column= @Column(name="apt_instant_water_limit"))
    @Embedded
    private UsageValue aptInstantWaterLimit;
    
    @Column(name="num_unknown_people")
    private Integer numUnknownPeople; 		//numero total de pessoas não identificadas no condominio (ladrão?).
    
    public Condominium() {
    }
     
    public Condominium(String name, String address, String managerName) {
        this.name = name;
        this.address = address;
        this.managerName = managerName;
        this.totalWaterUsage = new UsageValue();
        this.instantWaterLimit = new UsageValue();
        this.instantWaterUsage = new UsageValue();
        this.totalElectricUsage = new UsageValue();
        this.instantElectricLimit = new UsageValue();
        this.instantElectricUsage = new UsageValue();
        this.aptInstantElectricLimit = new UsageValue();
        this.aptInstantWaterLimit = new UsageValue();
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

	public UsageValue getTotalWaterUsage() {
		return totalWaterUsage;
	}
	
	public void setTotalWaterUsage(UsageValue totalWaterUsage) {
		this.totalWaterUsage = totalWaterUsage;
	}
	
	public UsageValue getInstantWaterUsage() {
		return instantWaterUsage;
	}
	
	public void setInstantWaterUsage(UsageValue instantWaterUsage) {
		this.instantWaterUsage = instantWaterUsage;
	}
	
	public UsageValue getInstantWaterLimit() {
		return instantWaterLimit;
	}
	
	public void setInstantWaterLimit(UsageValue instantWaterLimit) {
		this.instantWaterLimit = instantWaterLimit;
	}
	
	public UsageValue getTotalElectricUsage() {
		return totalElectricUsage;
	}
	
	public void setTotalElectricUsage(UsageValue totalElectricUsage) {
		this.totalElectricUsage = totalElectricUsage;
	}
	
	public UsageValue getInstantElectricLimit() {
		return instantElectricLimit;
	}
	
	public void setInstantElectricLimit(UsageValue instantElectricLimit) {
		this.instantElectricLimit = instantElectricLimit;
	}
	
	public UsageValue getInstantElectricUsage() {
		return instantElectricUsage;
	}

	public void setInstantElectricUsage(UsageValue instantElectricUsage) {
		this.instantElectricUsage = instantElectricUsage;
	}
	
	public UsageValue getAptInstantElectricLimit() {
		return aptInstantElectricLimit;
	}
	
	public void setAptInstantElectricLimit(UsageValue aptInstantElectricLimit) {
		this.aptInstantElectricLimit = aptInstantElectricLimit;
	}
	
	public UsageValue getAptInstantWaterLimit() {
		return aptInstantWaterLimit;
	}
	
	public void setAptInstantWaterLimit(UsageValue aptInstantWaterLimit) {
		this.aptInstantWaterLimit = aptInstantWaterLimit;
	}
}
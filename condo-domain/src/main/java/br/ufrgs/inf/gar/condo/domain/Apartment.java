package br.ufrgs.inf.gar.condo.domain;

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
public class Apartment extends AbstractEntity<Integer, Apartment> {
     
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
    
    @Column(name="total_water_usage")
    private String totalWaterUsage; 		
    
    @Column(name="instant_water_usage")
    private String instantWaterUsage; 
    								
    @Column(name="total_water_limit")
    private String totalWaterLimit;
    
    @Column(name="total_electric_usage")
    private String totalElectricUsage; 		
    								     
    @Column(name="total_electric_limit")
    private String totalElectricLimit;
    
    @Column(name="instant_electric_usage")
    private String instantElectricUsage;
    
    @ManyToOne
    @JoinColumn(name="sector_id")
    private Sector sector;
    
    public Apartment() {
	}
     
    public Apartment(Integer number, String ownerName, Integer numRooms, Sector sector) {
        this.number = number;
        this.ownerName = ownerName;
        this.numRooms = numRooms;
        this.sector = sector;
        this.numPeople = 0;
        this.totalWaterUsage = "0.0";
        this.totalWaterLimit = "0.0";
        this.instantWaterUsage = "0.0";
        this.totalElectricUsage = "0.0";
        this.totalElectricLimit = "0.0";
        this.instantElectricUsage = "0.0";
    }

    public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
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
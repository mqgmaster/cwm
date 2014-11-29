package br.ufrgs.inf.gar.condo.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
    
    @AttributeOverride(name="value", column= @Column(name="total_water_usage"))
    @Embedded
    private UsageValue totalWaterUsage; 		
    
    @AttributeOverride(name="value", column= @Column(name="instant_water_usage"))
    @Embedded
    private UsageValue instantWaterUsage; 
    								
    @AttributeOverride(name="value", column= @Column(name="total_electric_usage"))
    @Embedded
    private UsageValue totalElectricUsage; 		
    								     
    @AttributeOverride(name="value", column= @Column(name="instant_electric_usage"))
    @Embedded
    private UsageValue instantElectricUsage;
    
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
        this.totalWaterUsage = new UsageValue();
        this.instantWaterUsage = new UsageValue();
        this.totalElectricUsage = new UsageValue();
        this.instantElectricUsage = new UsageValue();
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
	
	public UsageValue getTotalElectricUsage() {
		return totalElectricUsage;
	}
	
	public void setTotalElectricUsage(UsageValue totalElectricUsage) {
		this.totalElectricUsage = totalElectricUsage;
	}
	
	public UsageValue getInstantElectricUsage() {
		return instantElectricUsage;
	}

	public void setInstantElectricUsage(UsageValue instantElectricUsage) {
		this.instantElectricUsage = instantElectricUsage;
	}
}
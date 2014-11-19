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
public class Garage extends AbstractEntity<Integer, Garage> {

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
    @JoinColumn(name="sector_id")
    private Sector sector;
    
    public Garage() {
	}
    
    public Garage(Integer number, Apartment apt, Sector sector) {
        this.number = number;
        this.apartment = apt;
        this.isOccupied = false;
        this.sector = sector;
    }
    
    @Override
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

	public boolean isOccupied() {
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

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}
}

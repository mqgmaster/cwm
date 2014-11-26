package br.ufrgs.inf.gar.condo.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
public class ApartmentWaterUsage extends AbstractEntity<Integer, ApartmentWaterUsage> implements Usage {

	@Id
    @GeneratedValue
    @Column
    private Integer id;
    
	@Column(name="total_water_usage")
    private String totalWaterUsage; 		
    
    @Column(name="instant_water_usage")
    private String instantWaterUsage; 
    								
    @Column(name="total_water_limit")
    private String totalWaterLimit;
    
    @OneToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apt;
    
    public ApartmentWaterUsage(Apartment apt) {
    	this.totalWaterUsage = "0.0";
        this.totalWaterLimit = "0.0";
        this.instantWaterUsage = "0.0";
        this.apt = apt;
    }
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public String getTotal() {
		return totalWaterUsage;
	}
	
	@Override
	public Float getTotalFloat() {
		return Float.valueOf(totalWaterUsage);
	}

	@Override
	public void setTotal(String totalWaterUsage) {
		this.totalWaterUsage = totalWaterUsage;
	}
	
	@Override
	public void setTotal(Float totalWaterUsage) {
		this.totalWaterUsage = String.valueOf(totalWaterUsage);
	}

	@Override
	public String getInstant() {
		return instantWaterUsage;
	}
	
	@Override
	public Float getInstantFloat() {
		return Float.valueOf(instantWaterUsage);
	}

	@Override
	public void setInstant(String instantWaterUsage) {
		this.instantWaterUsage = instantWaterUsage;
	}
	
	@Override
	public void setInstant(Float instantWaterUsage) {
		this.instantWaterUsage = String.valueOf(instantWaterUsage);
	}

	@Override
	public String getTotalLimit() {
		return totalWaterLimit;
	}
	
	@Override
	public Float getTotalLimitFloat() {
		return Float.valueOf(totalWaterLimit);
	}

	@Override
	public void setTotalLimit(String totalWaterLimit) {
		this.totalWaterLimit = totalWaterLimit;
	}
	
	@Override
	public void setTotalLimit(Float totalWaterLimit) {
		this.totalWaterLimit = String.valueOf(totalWaterLimit);
	}
}

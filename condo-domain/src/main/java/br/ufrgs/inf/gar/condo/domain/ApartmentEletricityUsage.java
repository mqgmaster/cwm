package br.ufrgs.inf.gar.condo.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
public class ApartmentEletricityUsage extends AbstractEntity<Integer, ApartmentEletricityUsage> implements Usage {

	@Id
    @GeneratedValue
    @Column
    private Integer id;
    
	@Column(name="total_electric_usage")
    private String totalElectricUsage; 		
    								     
    @Column(name="total_electric_limit")
    private String totalElectricLimit;
    
    @Column(name="instant_electric_usage")
    private String instantElectricUsage;
    
    @OneToOne
    @JoinColumn(name = "apartment_id")
    private Apartment apt;
    
    public ApartmentEletricityUsage(Apartment apt) {
    	this.totalElectricUsage = "0.0";
        this.totalElectricLimit = "0.0";
        this.instantElectricUsage = "0.0";
        this.apt = apt;
    }
	
	@Override
	public Integer getId() {
		return id;
	}
	
	@Override
	public String getTotal() {
		return totalElectricUsage;
	}
	
	@Override
	public Float getTotalFloat() {
		return Float.valueOf(totalElectricUsage);
	}

	@Override
	public void setTotal(String totalElectricUsage) {
		this.totalElectricUsage = totalElectricUsage;
	}
	
	@Override
	public void setTotal(Float totalElectricUsage) {
		this.totalElectricUsage = String.valueOf(totalElectricUsage);
	}

	@Override
	public String getTotalLimit() {
		return totalElectricLimit;
	}
	
	@Override
	public Float getTotalLimitFloat() {
		return Float.valueOf(totalElectricLimit);
	}

	@Override
	public void setTotalLimit(String totalElectricLimit) {
		this.totalElectricLimit = totalElectricLimit;
	}
	
	@Override
	public void setTotalLimit(Float totalElectricLimit) {
		this.totalElectricLimit = String.valueOf(totalElectricLimit);
	}

	@Override
	public String getInstant() {
		return instantElectricUsage;
	}

	@Override
	public Float getInstantFloat() {
		return Float.valueOf(instantElectricUsage);
	}

	@Override
	public void setInstant(String instantElectricUsage) {
		this.instantElectricUsage = instantElectricUsage;
	}
	
	@Override
	public void setInstant(Float instantElectricUsage) {
		this.instantElectricUsage = String.valueOf(instantElectricUsage);
	}
}

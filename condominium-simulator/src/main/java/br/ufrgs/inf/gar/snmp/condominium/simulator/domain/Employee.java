package br.ufrgs.inf.gar.snmp.condominium.simulator.domain;

import java.io.Serializable;

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
public class Employee implements Serializable {
	
	@Id
	@GeneratedValue
    @Column
    private Integer id;
    
    @Column
    private String name;
     
    @Column(name="role")
    private String role;
    
    @Column(name="month_wage")
    private Integer monthWage;
    
    @Column(name="week_workload")
    private Integer weekWorkload;
    
    @Column(name="is_working")
    private Boolean isWorking;
    
    @ManyToOne
    @JoinColumn(name="condominium_id")
    private Condominium condominium;
    
    public Employee(String name, String role, Integer monthWage, Integer weekWorkload, Condominium condominium) {
        this.name = name;
        this.role = role;
        this.monthWage = monthWage;
        this.weekWorkload = weekWorkload;
        this.condominium = condominium;
    }
    
    public boolean equals(Object another) {
        if ( !(another instanceof Employee) ) return false;
 
        final Employee apt = (Employee) another;
         
        return this.id.equals(apt.getId());
    }
     
    public int hashCode() {
        return id.hashCode();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getMonthWage() {
		return monthWage;
	}

	public void setMonthWage(Integer monthWage) {
		this.monthWage = monthWage;
	}

	public Condominium getCondominium() {
		return condominium;
	}

	public void setCondominium(Condominium condominium) {
		this.condominium = condominium;
	}

	public Integer getWeekWorkload() {
		return weekWorkload;
	}

	public void setWeekWorkload(Integer weekWorkload) {
		this.weekWorkload = weekWorkload;
	}

	public Boolean getIsWorking() {
		return isWorking;
	}

	public void setIsWorking(Boolean isWorking) {
		this.isWorking = isWorking;
	}
}

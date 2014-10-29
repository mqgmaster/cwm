package br.ufrgs.inf.gar.snmp.condominium.simulator.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
/**
 * @author Mauricio Quatrin Guerreiro
 */
 
@SuppressWarnings("serial")
@Entity
@Table
public class CondominiumInfo implements Serializable {
     
    @Id
    @Column
    private String name;
     
    @Column
    private String parent;
     
    @Column
    private Integer level;
     
    public CondominiumInfo(Integer level, String name, String parent) {
        this.name = name;
        this.level = level;
        this.parent = parent;
    }
     
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }
 
    public void setLevel(Integer level) {
        this.level = level;
    }
 
    public String getParent() {
        return parent;
    }
 
    public void setParent(String parent) {
        this.parent = parent;
    }
 
    public boolean equals(Object another) {
        if ( !(another instanceof CondominiumInfo) ) return false;
 
        final CondominiumInfo cat = (CondominiumInfo) another;
         
        return this.name.equals(cat.getName()) && 
            this.level.equals(cat.getLevel()) &&
            this.parent.equals(cat.getParent());
    }
     
    public int hashCode() {
        return name.hashCode() * parent.hashCode() * level.hashCode();
    }
}
package br.ufrgs.inf.gar.condo.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class AbstractEntity<PK extends Comparable<? super PK>, T extends AbstractEntity<PK, T>> 
		implements Serializable, Comparable<T> {
	
	public abstract PK getId();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean equals(Object another) {
		Class<? extends AbstractEntity> clazz = this.getClass();
        if (clazz == null || another == null) return false;
        if(!clazz.isAssignableFrom(another.getClass())) return false;
 
        T obj = null;
		try {
			obj = (T) another.getClass().newInstance();
			return this.getId().equals(obj.getId());
		} catch (Exception e) {
			return false;
		}
    }
	
	@Override
    public int hashCode() {
        return this.getId().hashCode();
    }

	@Override
	public int compareTo(T o) {
		return this.getId().compareTo(o.getId());
	}
}

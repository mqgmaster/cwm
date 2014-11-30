package br.ufrgs.inf.gar.condo.domain;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class UsageValue extends AbstractEntity<String, UsageValue> {

	@Basic
	private String value;
	
	public UsageValue() {
		this.value = "0.0";
	}
	
	public UsageValue(Float value) {
		this.value = String.valueOf(value);
	}
	
	public UsageValue(String value) throws UnsupportedOperationException {
		try {
			Float.valueOf(value);
		} catch (Exception e) {
			throw new UnsupportedOperationException("String is not a float valid value");
		}
		this.value = value;
	}
	
	public void set(Double value) {
		this.value = String.valueOf(value);
	}
	
	public void set(Float value) {
		this.value = String.valueOf(value);
	}
	
	public void set(String value) throws UnsupportedOperationException {
		try {
			Float.valueOf(value);
		} catch (Exception e) {
			throw new UnsupportedOperationException("String is not a float valid value");
		}
		this.value = value;
 	}
	
	public void add(Float value) {
		this.value = String.valueOf(value + Float.valueOf(this.value));
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public Float toFloat() {
		return Float.valueOf(value);
	}
	
	public Double toDouble() {
		return Double.valueOf(value);
	}

	@Override
	public String getId() {
		return value;
	}
}

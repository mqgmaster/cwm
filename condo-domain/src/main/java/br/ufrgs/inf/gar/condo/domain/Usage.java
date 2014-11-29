package br.ufrgs.inf.gar.condo.domain;

public interface Usage {
	
	public String getTotal();
	
	public Float getTotalFloat();

	public void setTotal(String total);
	
	public void setTotal(Float total);

	public String getInstant();
	
	public Float getInstantFloat();

	public void setInstant(String instant);
	
	public void setInstant(Float instant);

	public String getTotalLimit();
	
	public Float getTotalLimitFloat();

	public void setTotalLimit(String totalLimit);
	
	public void setTotalLimit(Float totalLimit);
}

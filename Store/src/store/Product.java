package store;

import java.math.BigDecimal;

public interface Product {
	public Product clone();
	public String getName();
	public BigDecimal getPrice();
	public String getInformation();
}

package store;

import java.math.BigDecimal;

public interface Product {
	// interjes produktu
	// użyty dla wzorców Factory Method i Prototype
	public Product clone(); // Prototype
	public String getName();
	public BigDecimal getPrice();
	public String getInformation();
}

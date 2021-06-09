package store;

import java.math.BigDecimal;

public abstract class ProductDecorator implements Product {
	private Product wrappee;
	
	public ProductDecorator(Product wrappee) {
		this.wrappee = wrappee;
	}
	
	@Override
	public Product clone() {
		return this.wrappee.clone();
	}

	@Override
	public String getName() {
		return this.wrappee.getName();
	}

	@Override
	public BigDecimal getPrice() {
		return this.wrappee.getPrice();
	}

	@Override
	public String getInformation() {
		return this.wrappee.getInformation();
	}

}

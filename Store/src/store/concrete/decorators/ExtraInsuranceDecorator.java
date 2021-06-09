package store.concrete.decorators;

import java.math.BigDecimal;

import store.Product;
import store.ProductDecorator;

public class ExtraInsuranceDecorator extends ProductDecorator {

	public ExtraInsuranceDecorator(Product wrappee) {
		super(wrappee);
	}
	
	@Override
	public Product clone() {
		ExtraInsuranceDecorator clone = new ExtraInsuranceDecorator(super.clone());
		return clone;
	}

	@Override
	public String getName() {
		String name = super.getName();
		name += " + ";
		name += "Dodatkowe ubezpieczenie";
		return name;
	}

	@Override
	public BigDecimal getPrice() {
		BigDecimal price = super.getPrice();
		BigDecimal extraPrice = new BigDecimal(150);
		price = price.add(extraPrice);
		return price;
	}

	@Override
	public String getInformation() {
		String info = super.getInformation();
		info += "\n";
		info += "Dodatkowe ubezpieczenie: Tak";
		return info;
	}
}

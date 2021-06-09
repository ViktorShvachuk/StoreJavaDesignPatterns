package store.concrete.decorators;

import java.math.BigDecimal;

import store.Product;
import store.ProductDecorator;

public class SystemConfigurationDecorator extends ProductDecorator {

	public SystemConfigurationDecorator(Product wrappee) {
		super(wrappee);
	}

	@Override
	public Product clone() {
		SystemConfigurationDecorator clone = new SystemConfigurationDecorator(super.clone());
		return clone;
	}

	@Override
	public String getName() {
		String name = super.getName();
		name += " + ";
		name += "Konfiguracja systemu";
		return name;
	}

	@Override
	public BigDecimal getPrice() {
		BigDecimal price = super.getPrice();
		BigDecimal extraPrice = new BigDecimal(50);
		price = price.add(extraPrice);
		return price;
	}

	@Override
	public String getInformation() {
		String info = super.getInformation();
		info += "\n";
		info += "Konfiguracja systemu: Tak";
		return info;
	}

}

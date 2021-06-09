package store.concrete.creators;

import java.math.BigDecimal;

import store.Product;
import store.ProductCreator;
import store.concrete.products.Smartphone;

public class ExpensiveSmartphoneCreator extends ProductCreator {

	@Override
	public Product createProduct() {
		Smartphone smartphone = new Smartphone();
		smartphone.setName("Drogi telefon");
		smartphone.setPrice(new BigDecimal(3000));
		smartphone.setCameraMp(128);
		smartphone.setProcessor("Snapdragon");
		smartphone.setDisplay("2208x1768");
		return smartphone;
	}

}

package store.concrete.creators;

import java.math.BigDecimal;

import store.Product;
import store.ProductCreator;
import store.concrete.products.Smartphone;

public class CheapSmartphoneCreator extends ProductCreator {

	@Override
	public Product createProduct() {
		Smartphone smartphone = new Smartphone();
		smartphone.setName("Tani telefon");
		smartphone.setPrice(new BigDecimal(400));
		smartphone.setCameraMp(8);
		smartphone.setProcessor("SC7731E");
		smartphone.setDisplay("960x480");
		return smartphone;
	}

}

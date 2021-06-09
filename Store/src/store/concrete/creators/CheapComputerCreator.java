package store.concrete.creators;

import java.math.BigDecimal;

import store.Product;
import store.ProductCreator;
import store.concrete.products.Computer;

public class CheapComputerCreator extends ProductCreator {

	@Override
	public Product createProduct() {
		Computer computer = new Computer();
		computer.setName("Tani komputer");
		computer.setPrice(new BigDecimal(1500));
		computer.setMemoryType("HDD");
		computer.setProcessor("I3-500");
		computer.setSizeOfMemory(512);
		computer.setSizeOfRAM(4);
		return computer;
	}

}

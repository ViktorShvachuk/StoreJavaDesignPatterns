package store.concrete.creators;

import java.math.BigDecimal;

import store.Product;
import store.ProductCreator;
import store.concrete.products.Computer;

public class ExpensiveComputerCreator extends ProductCreator {

	@Override
	public Product createProduct() {
		Computer computer = new Computer();
		computer.setName("Drogi komputer");
		computer.setPrice(new BigDecimal(8000));
		computer.setMemoryType("SSD");
		computer.setProcessor("I9-1100");
		computer.setSizeOfMemory(1024);
		computer.setSizeOfRAM(32);
		return computer;
	}

}

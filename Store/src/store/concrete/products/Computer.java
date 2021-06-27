package store.concrete.products;

import java.math.BigDecimal;

import store.Product;

public class Computer implements Product {
	private String processor;
	private int sizeOfRAM;
	private int sizeOfMemory;
	private String memoryType;
	private BigDecimal price;
	private String name;

	@Override
	public Product clone() {
		Computer clone = new Computer();
		clone.name = this.name;
		clone.processor = this.processor;
		clone.sizeOfRAM = this.sizeOfRAM;
		clone.sizeOfMemory = this.sizeOfMemory;
		clone.memoryType = this.memoryType;
		clone.price = this.price; // BigDecimal "immutable", więc nie muszę tworzyć kopię
		return clone;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public int getSizeOfRAM() {
		return sizeOfRAM;
	}

	public void setSizeOfRAM(int sizeOfRAM) {
		this.sizeOfRAM = sizeOfRAM;
	}

	public int getSizeOfMemory() {
		return sizeOfMemory;
	}

	public void setSizeOfMemory(int sizeOfMemory) {
		this.sizeOfMemory = sizeOfMemory;
	}

	public String getMemoryType() {
		return memoryType;
	}

	public void setMemoryType(String memoryType) {
		this.memoryType = memoryType;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BigDecimal getPrice() {
		return this.price;
	}

	@Override
	public String getInformation() {
		String info = "";
		info += "Procesor: " + this.processor + "\n";
		info += "Pamięć RAM: " + this.sizeOfRAM + " gb" + "\n";
		info += "Pamięć: " + this.sizeOfMemory + " gb" + "\n";
		info += "Typ pamięci: " + this.memoryType;
		return info;
	}

}

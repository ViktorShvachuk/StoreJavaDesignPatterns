package store.concrete.products;

import java.math.BigDecimal;

import store.Product;

public class Smartphone implements Product {
	private String name;
	private BigDecimal price;
	private String processor;
	private String display;
	private int cameraMp;
	
	@Override
	public Product clone() {
		Smartphone clone = new Smartphone();
		clone.name = this.name;
		clone.price = this.price;
		clone.display = this.display;
		clone.cameraMp = this.cameraMp;
		clone.processor = this.processor;
		return clone;
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
		info += "Kamera: " + this.cameraMp + " MP" + "\n";
		info += "Ekran: " + this.display;
		return info;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public int getCameraMp() {
		return cameraMp;
	}

	public void setCameraMp(int cameraMp) {
		this.cameraMp = cameraMp;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}

package store;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Cart {
	private ArrayList<Product> products;
	
	public void addProduct(Product product) {
		this.products.add(product);
	}
	
	public void removeProduct(Product product) {
		this.products.remove(product);
	}
	
	public void clear() {
		this.products.clear();
	}
	
	public ArrayList<Product> getProducts() {
		return this.products;
	}
	
	public BigDecimal getPrice() {
		BigDecimal price = new BigDecimal(0);
		for (Product product : products) {
			price = price.add(product.getPrice());
		}
		return price;
	}
	
	public CartMemento saveState() {
		CartMemento cartMemento = new CartMemento(this, (ArrayList<Product>) products.clone());
		return cartMemento;
	}
	
	public void restore(CartMemento memento) {
		memento.restore();
	}
	
	public class CartMemento {
		private ArrayList<Product> products;
		private Cart cart;
		
		public CartMemento(Cart cart, ArrayList<Product> products) {
			this.cart = cart;
			this.products = products;
		}
		
		public void restore() {
			this.cart.products = (ArrayList<Product>) products.clone();
		}
	}
}

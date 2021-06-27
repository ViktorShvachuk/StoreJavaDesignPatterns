package store;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Cart {
	// Klasa koszyka dla przechowywania produktów użytkownika
	private ArrayList<Product> products; // lista produktów w koszyku
	
	public Cart() {//konstruktor
		products = new ArrayList<Product>();
	}
	
	// dodawanie produktu
	public void addProduct(Product product) {
		this.products.add(product);
	}
	
	// usuwanie produktu
	public void removeProduct(Product product) {
		this.products.remove(product);
	}
	
	// wyczyszczenie calego koszyka
	public void clear() {
		this.products.clear();
	}
	
	// pobieranie listy produktów
	public ArrayList<Product> getProducts() {
		return this.products;
	}
	
	// zwraca cene wszystkich produktów w koszyku
	public BigDecimal getPrice() {
		BigDecimal price = new BigDecimal(0);
		for (Product product : products) {
			price = price.add(product.getPrice());
		}
		return price;
	}
	
	// ---- Użycie wzorca Memento ----
	// Zapisuje stan koszyka i zwraca obiekt cartMemento(obiektu w którym zapisany stan)
	public CartMemento saveState() {
		CartMemento cartMemento = new CartMemento(this, (ArrayList<Product>) products.clone());
		return cartMemento;
	}
	
	// Zmienia stan koszyka na stan który został zapisany w CartMemento, który podany jako parameter metody
	public void restore(CartMemento memento) {
		memento.restore(); // w obiekcie CartMemento jest referncja do obiektu Cart, 
		//a więc metoda restore może zmienić(i zmienia) koszyk z którego został utworzony obiekt CartMemento
	}
	
	public class CartMemento {
		// Klasa CartMemento została zadeklarowana w środku klasy Cart
		// Takie podejście zezwała na dostęp do prywatnych pół klasy Cart, używając metod klasy CartMemento
		// Zostało to użyte w metodzie restore.
		private ArrayList<Product> products;
		private Cart cart;
		
		public CartMemento(Cart cart, ArrayList<Product> products) {
			// zapisane stanu koszyka
			this.cart = cart;
			this.products = products;
		}
		
		public void restore() {
			// zmiana stanu koszyka na ten, który jest zapisany w obiekcie CartMemento
			this.cart.products = (ArrayList<Product>) products.clone();
		}
	}
}

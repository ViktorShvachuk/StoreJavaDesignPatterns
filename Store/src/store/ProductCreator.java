package store;

public abstract class ProductCreator {
	// klasa abstrakcyjna dla użycia factory method
	// Klasy "concrete", które dziedziczą po tej klasie są w packag'u "store.concrete.creators"
	public abstract Product createProduct();
}

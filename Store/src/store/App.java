package store;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import store.Cart.CartMemento;
import store.concrete.creators.*;
import store.concrete.decorators.*;
import store.exceptions.EmptyHistoryException;

public class App {
	private static Cart cart;
	private static LinkedList<CartMemento> history; // I NEED QUEUEE, maybe i can use LinkedList (pop, push)
	private static LinkedList<CartMemento> redoHistory;
	private static ArrayList<Product> availableProducts;
	private static Scanner scanner;

	public static void main(String[] args) {
		prepare();
		doMainLoop();
	}
	
	private static void prepare() {
		scanner = new Scanner(System.in);
		
		cart = new Cart();
		history = new LinkedList<CartMemento>();
		redoHistory = new LinkedList<CartMemento>();
		availableProducts = new ArrayList<Product>();
		
		ArrayList<ProductCreator> productCreators = new ArrayList<ProductCreator>();
		
		productCreators.add(new CheapComputerCreator());
		productCreators.add(new ExpensiveComputerCreator());
		productCreators.add(new CheapSmartphoneCreator());
		productCreators.add(new ExpensiveSmartphoneCreator());

		for (ProductCreator productCreator : productCreators) {
			availableProducts.add(productCreator.createProduct());
		}
	}
	
	private static void doMainLoop() {
		int userInput;
		boolean exit = false;
		while(!exit) {
			System.out.println("Wybierz jedną z opcji");
			System.out.println("1 - Sprawdź dostępne produkty");
			System.out.println("2 - Sprawdź swój koszyk");
			System.out.println("3 - Zakończ");
			System.out.println("4 - Udno");
			System.out.println("5 - Redo");
			
			userInput = scanner.nextInt();
			
			switch (userInput) {
			case 1: {
				doProductsListLoop();
				break;
			}
			case 2: {
				doCartLoop();
				break;
			}
			case 3: {
				exit = true;
				break;
			}
			case 4: {
				try {
					undo();
					System.out.println("Zapisano poprawnie...");
				} catch (EmptyHistoryException e) {
					System.out.println("Nie udało się. Historia jest pusta.");
				}
				break;
			}
			case 5: {
				try {
					redo();
					System.out.println("Zapisano poprawnie...");
				} catch (EmptyHistoryException e) {
					System.out.println("Nie udało się. Historia jest pusta.");
				}
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + userInput);
			}
		}
		
		System.out.println("Program został wylączony.");
	}
	
	private static void doProductsListLoop() {
		int userInput;
		while(true) {
			System.out.println("Wybierz produkt");
			System.out.println("0 - wyjdź z listy produktów");
			
			for (int i = 1; i <= availableProducts.size(); i++) {
				Product product = availableProducts.get(i-1);
				System.out.println(i + " - " + product.getName());
			}
			
			userInput = scanner.nextInt();
			if (userInput == 0) break;
			
			if (userInput > availableProducts.size()) {
				System.out.println("Wystąpił bląd. Spróbuj ponownie.");
				continue;
			}
			
			Product chosenProduct = availableProducts.get(userInput - 1); 
			doProductDetails(chosenProduct.clone());
		}
	}
	
	private static void doProductDetails(Product product)
	{
		int userInput = 0;

		System.out.println("Nazwa produktu: " + product.getName());
		System.out.println("Opis:");
		System.out.println(product.getInformation());
		System.out.println("Cena: " + product.getPrice().toString());
		
		while (userInput != 1) {
			System.out.println("Wybierz opcję");
			System.out.println("0 - Powrót do listy");
			System.out.println("1 - Dodaj do koszyka");
			
			userInput = scanner.nextInt();
			if (userInput == 0) return;
		}
			
		Product productSkonfigurowany = new SystemConfigurationDecorator(product);
		
		System.out.println("Czy chcesz żebyś my skonfigórowaliśmy system?");
		System.out.println("Cena po zabezpieczeniu: " + productSkonfigurowany.getPrice());
		System.out.println("0(albo nie 1) - Nie");
		System.out.println("1 - Tak");
		
		userInput = scanner.nextInt();
		
		if (userInput == 1) product = productSkonfigurowany;
			
		Product produktZabezpieczony = new ExtraInsuranceDecorator(product);
		
		System.out.println("Czy chcesz dodatkowe ubezpieczenie?");
		System.out.println("Cena po konfiguracji: " + produktZabezpieczony.getPrice());
		System.out.println("0(albo nie 1) - Nie");
		System.out.println("1 - Tak");
		
		userInput = scanner.nextInt();
		
		if (userInput == 1) product = produktZabezpieczony;
		
		addToHistory();
		cart.addProduct(product);
		
		System.out.println("Produkt został dodany do koszyka");
	}
	
	private static void doCartLoop() {
		int userInput;
		ArrayList<Product> products = cart.getProducts();
		while(true) {
			System.out.println("Wybierz produkt");
			System.out.println("0 - wyjdź z koszyka");
			
			for (int i = 1; i <= products.size(); i++) {
				Product product = products.get(i-1);
				System.out.println(i + " - " + product.getName());
			}
			
			userInput = scanner.nextInt();
			if (userInput == 0) break;
			
			if (userInput > products.size()) {
				System.out.println("Wystąpił bląd. Spróbuj ponownie.");
				continue;
			}
			
			Product chosenProduct = products.get(userInput - 1); 
			doCartProductDetails(chosenProduct);
		}
	}
	
	private static void doCartProductDetails(Product product) {
		int userInput = 0;

		System.out.println("Nazwa produktu: " + product.getName());
		System.out.println("Opis:");
		System.out.println(product.getInformation());
		System.out.println("Cena: " + product.getPrice().toString());
		
		while (userInput != 1) {
			System.out.println("Wybierz opcję");
			System.out.println("0 - Powrót do koszyka");
			System.out.println("1 - Usuń z koszyka");
			
			userInput = scanner.nextInt();
			if (userInput == 0) return;
		}
			
		if (userInput == 1)
		{
			addToHistory();
			cart.removeProduct(product);
		}
		System.out.println("Produkt został usunięty z koszyka");
	}
	
	private static void addToHistory() {
		redoHistory.clear();
		history.push(cart.saveState());
		if (history.size() > 5) {
			history.removeFirst();
		}
	}
	
	private static void undo() throws EmptyHistoryException {
		if (history.size() < 1) throw new EmptyHistoryException();
		// restore last cart state and add current state to redoHistory;
		redoHistory.push(cart.saveState());
		
		CartMemento lastState = history.pop();
		cart.restore(lastState);
	}
	
	private static void redo() throws EmptyHistoryException {
		if (redoHistory.size() < 1) throw new EmptyHistoryException();
		// restore last cart state and add current state to redoHistory;
		history.push(cart.saveState());
		
		CartMemento lastState = redoHistory.pop();
		cart.restore(lastState);
	}

}

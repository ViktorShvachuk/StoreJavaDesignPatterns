package store;

import java.util.ArrayList;
import java.util.Scanner;

import store.Cart.CartMemento;
import store.concrete.creators.*;
import store.concrete.products.*;
import store.concrete.decorators.*;

public class App {
	private static Cart cart;
	private static ArrayList<CartMemento> history;
	private static ArrayList<Product> availableProducts;
	private static Scanner scanner;

	public static void main(String[] args) {
		prepare();
		doMainLoop();
	}
	
	private static void prepare() {
		scanner = new Scanner(System.in);
		
		cart = new Cart();
		history = new ArrayList<CartMemento>();
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
		System.out.print("Cena po konfiguracji: " + productSkonfigurowany.getPrice());
		System.out.println("0(albo nie 1) - Nie");
		System.out.println("1 - Tak");
		
		userInput = scanner.nextInt();
		
		if (userInput == 1) product = productSkonfigurowany;
			
		Product produktZabezpieczony = new ExtraInsuranceDecorator(product);
		
		System.out.println("Czy chcesz dodatkowe ubezpieczenie?");
		System.out.print("Cena po konfiguracji: " + produktZabezpieczony.getPrice());
		System.out.println("0(albo nie 1) - Nie");
		System.out.println("1 - Tak");
		
		userInput = scanner.nextInt();
		
		if (userInput == 1) product = produktZabezpieczony;
		
		cart.addProduct(product);
		System.out.println("Produkt został dodany do koszyka");
	}
	
	private static void doCartLoop() {
			System.out.println("Wybierz produkt");
			
			for (int i = 0; i < availableProducts.size(); i++) {
				Product product = availableProducts.get(i);
				System.out.println(i + " - " + product.getName());
			}
	}

}

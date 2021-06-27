package store;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import store.Cart.CartMemento;
import store.concrete.creators.*;
import store.concrete.decorators.*;
import store.exceptions.EmptyHistoryException;

public class App {
	/*
	 * Klasa którą można nazwać client
	 * Używa innych klas, obiektów
	 * Ma metodę main
	 */
	private static Cart cart; // koszyk z produktami użytkownika aplikacji
	private static LinkedList<CartMemento> history; // historia koszyka, użyty wzorzec memento.
	private static LinkedList<CartMemento> redoHistory; // historia przewrucen(undo) koszyka(żeby można było po udno zrobić redo), użyty wzorzec memento.
	private static ArrayList<Product> availableProducts; // lista produktów, które można kupić
	private static Scanner scanner; // scanner używany do odczytywania dancyh od użytkownika

	public static void main(String[] args) {
		prepare(); // tworzą się obiekty, które używane w aplikacji (koszyk, listy, przedmioty do sprzedarzy...)
		doMainLoop(); // działanie samej aplikacji
	}
	
	private static void prepare() {
		scanner = new Scanner(System.in);
		
		cart = new Cart();
		history = new LinkedList<CartMemento>();
		redoHistory = new LinkedList<CartMemento>();
		availableProducts = new ArrayList<Product>();
		
		// użycie wzorca FactoryMethod dla utworzenia produktów do sprzedaży
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
		// Ta metoda odpowiada za główne "menu" aplikacji
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
				doProductsListLoop(); // lista dostepnych produktow, udostepnia możliwość dodania do koszyka
				break;
			}
			case 2: {
				doCartLoop(); // koszyk, można sprawdzić produkty w koszyku
				break;
			}
			case 3: {
				exit = true; // koniec dzialania aplikacji(pętli)
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
				System.out.println("Nie poprawna wartość. Spróbuj jeszcze raz.");
				break;
			}
		}
		
		System.out.println("Program został wylączony.");
	}
	
	private static void doProductsListLoop() {
		// metoda która odpowiada za wyświetlanie dostępnych produktów. 
		// użytkownik może wybrać jakiś produkt, żeby wejść w szczegóły
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
			// Wejście do szczegółów produktu
			// Użycie wzorca Prototyp, do klonowania obiektu
			// Usuwa to możliwość zmiany produktu dostępnego. Zmieniamy tylko kopie.
			doProductDetails(chosenProduct.clone());
		}
	}
	
	private static void doProductDetails(Product product)
	{
		// szczegóły produktu
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
		
		// Użycie wzorca dekorator, do modyfikacji produktów.
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
		
		addToHistory(); // zapisywania danego stanu. Memento
		cart.addProduct(product); // dodanie do koszyka
		
		System.out.println("Produkt został dodany do koszyka");
	}
	
	private static void doCartLoop() {
		// koszyk
		// Pokazuje wszystkie produkty dodane do koszka.
		// Możliwość wejść w szczegóły produktu.
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
			doCartProductDetails(chosenProduct); // szczegóły produktu z koszyka
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
			addToHistory(); // Zapisanie do historii, Memento
			cart.removeProduct(product); // usunięcie produktu z koszyka
			System.out.println("Produkt został usunięty z koszyka");
		}
	}
	
	private static void addToHistory() {
		// zapisywanie do historii koszyka, Memento
		redoHistory.clear(); // wyczyszczamy historię redo, możliwość redo istnieje tylko po undo 
		history.push(cart.saveState()); // saveState zwraca stan obiektu w danym momencie. Zapisujemy stan do historii
		// Ograniczamy historie do 5, żeby nie zajmowała zbytnio miejsca
		if (history.size() > 5) {
			history.removeFirst();
		}
	}
	
	private static void undo() throws EmptyHistoryException {
		// przewrócenie koszyka do poprzedniego stanu, Memento
		if (history.size() < 1) throw new EmptyHistoryException(); // jeżeli nic nie mamy w historii to wyrzucamy wyjątek

		redoHistory.push(cart.saveState()); // zapisujemy stan przed przewróceniem do historii redo, żeby można było przywrócić
		
		CartMemento lastState = history.pop(); // wyciągamy z historii ostatni stan (został usunięty z historii)
		cart.restore(lastState); // zapisujemy ten stan w koszyku
	}
	
	private static void redo() throws EmptyHistoryException {
		// Działą tak samo jak undo, tylko zamieniamy redoHistory i history
		if (redoHistory.size() < 1) throw new EmptyHistoryException();
		
		history.push(cart.saveState());
		
		CartMemento lastState = redoHistory.pop();
		cart.restore(lastState);
	}

}

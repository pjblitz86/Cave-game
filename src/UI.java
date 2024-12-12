import java.util.ArrayList;

public class UI {

	public void printMenu(int maxTries, int moveCounter, ArrayList<String> equipment) {
		System.out.println("-".repeat(20));
        System.out.println("Jus esate oloje, likes bandymu skaicius: " + (maxTries - moveCounter));
        System.out.println("Turima įranga: " + equipment);
        System.out.println("Ar eiti toliau? Y/N");
	}
	
	public void printException(CaveException e) {
		System.out.println(e.getMessage());
	}
	
	public void printGameWon(int moveCounter, int treasureQuantity) {
		System.out.println("GAME WON! Treasure: " + treasureQuantity + ". Moves used: " + moveCounter);
	}
	
	public void printGameOver(int moveCounter) {
		System.out.println("Max move limit reached, moves used: " + moveCounter + " thus GAME OVER, son!");
	}
	
	public void printCriticalGameOver(int moveCounter) {
		System.out.println("Kritinis ivykis ir nebeturite įrangos, Naudota bandymu: " + moveCounter + ". GAME OVER");
	}
	
	public void printDeathGameOver(int moveCounter) {
		System.out.println("GAME OVER, your final move count: " + moveCounter);
	}
	
	public void printDidNotTryGameOver(int moveCounter, int treasureQuantity) {
		System.out.println("You didn't even try finding the treasure, returning from cave, your final move count: " + moveCounter + ". Treasure value: " + treasureQuantity);
	}
}

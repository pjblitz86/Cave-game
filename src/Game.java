import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private int moveCounter;
    private int maxTries;
    private int winningNumber;
    private int treasureQuantity;
    private boolean isGameOngoing;
    private ArrayList<String> equipment;

    public Game(int maxTries) {
        this.maxTries = maxTries;
        initialize();
    }

    private void initialize() {
        moveCounter = 0;
        winningNumber = 5;
        treasureQuantity = 4;
        isGameOngoing = true;
        equipment = new ArrayList<>(List.of("Fakelas", "Virve"));
    }

    public void start() {
        Random random = new Random();
        UI ui = new UI();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                int randomNum = random.nextInt(9);
                ui.printMenu(maxTries, moveCounter, equipment);
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("y")) {
                    handleMove(ui, randomNum);
                    if (!isGameOngoing)
                    	break;
                } else {
                    ui.printDidNotTryGameOver(moveCounter, treasureQuantity);
                    break;
                }
            }
        }
    }

    private void handleMove(UI ui, int randomNum) {
        try {
            moveCounter++;
            processMove(randomNum);
            checkRandomEvent(randomNum);

            if (isGameWon(randomNum)) {
                ui.printGameWon(moveCounter, treasureQuantity);
                isGameOngoing = false;
                return;
            }

            updateTreasure(randomNum);

            if (isGameOver()) {
                ui.printGameOver(moveCounter);
                isGameOngoing = false;
                return;
            }
        } catch (CaveException e) {
            handleException(ui, randomNum, e);
        }
    }

    private void processMove(int randomNum) throws CaveException {
        switch (randomNum) {
            case 1 -> throw new AkmuoUzvirtoException("Uzvirto akmuo", true);
            case 2 -> throw new SusmukoGrindysException("Susmuko grindys", true);
            case 3 -> throw new NetiketasGyvunasException("Netikėtas gyvūnas", false);
            case 4 -> throw new UzpuoleDrakonasException("Užpuolė drakonas", true);
            case 5 -> System.out.println("Pabėgai su lobiu");
            case 6 -> System.out.println("Radai naujos irangos: kardas");
            case 7 -> System.out.println("Radai papildomo lobio");
            default -> System.out.println("Viskas ok, bet dar neradai lobio");
        }
    }

    private void checkRandomEvent(int randomNum) {
        if (randomNum == 3) {
            System.out.println("Atbaidai gyvuna, bet sugadini fakela");
            loseEquipment("Fakelas");
            System.out.println("Turima įranga: " + equipment);
        } else if (randomNum == 6) {
            equipment.add("Kardas");
            System.out.println("Turima įranga: " + equipment);
        }
    }

    private boolean isGameWon(int randomNum) {
        return randomNum == winningNumber;
    }

    private boolean isGameOver() {
        return moveCounter >= maxTries;
    }

    private void updateTreasure(int randomNum) {
        if (randomNum > 7) {
            treasureQuantity++;
            System.out.println("Lobis padidėjo. Dabartinė vertė: " + treasureQuantity);
        } else if (randomNum == 7) {
            treasureQuantity += 2;
            System.out.println("Lobis stipriai padidėjo. Dabartinė vertė: " + treasureQuantity);
        } else {
            treasureQuantity--;
            System.out.println("Lobis sumažėjo. Dabartinė vertė: " + treasureQuantity);
        }
    }

    private void handleException(UI ui, int randomNum, CaveException e) {
        ui.printException(e);

        if (e.isKritine()) {
            if (equipment.isEmpty()) {
                ui.printCriticalGameOver(moveCounter);
                isGameOngoing = false;
            } else if (isDeathEvent(randomNum)) {
                ui.printDeathGameOver(moveCounter);
                isGameOngoing = false;
            }
        } else {
        	checkRandomEvent(randomNum);
        }
    }

    private boolean isDeathEvent(int randomNum) {
        if (randomNum == 4 && equipment.contains("Kardas")) {
            handleDragonEncounter();
            return false;
        } else if ((randomNum == 1 || randomNum == 2) && equipment.contains("Virve")) {
            handleTrapEscape();
            return false;
        } else {
            System.out.println("You die...");
            isGameOngoing = false;
            return true;
        }
    }

    private void handleDragonEncounter() {
        System.out.println("Gerai, kad turejai karda, tai kovoji su drakonu, bet prarandi karda. Zaidimas sunkeja.");
        loseEquipment("Kardas");
    }

    private void handleTrapEscape() {
        System.out.println("Gerai, kad turejai virve, tai issigelbsti, bet virve sugadinta. Zaidimas sunkeja.");
        loseEquipment("Virve");
    }

    private void loseEquipment(String item) {
        if (equipment.contains(item)) {
            equipment.remove(item);
            System.out.println("Prarandate įrangą: " + item);
        } else {
            System.out.println("Irangos neprarandi, nes ir neturejai");
        }
    }
}

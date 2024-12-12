public class App {

    static int maxTries = 4;

    public static void main(String[] args) {
    	Game game = new Game(maxTries);
        game.start();
    	
    }
}

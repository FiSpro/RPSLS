
public class Parallel {
	
	public static int p = 5; // number of players
	public static int r = 100; // number of rounds
	public static PlayerThread players[];

	// returns the final winner from all players
	public static void getWinner() {
		PlayerThread winner = players[0];
		for (int i = 0; i < players.length; i++) {
			 if (players[i].win.get() > winner.win.get()) {
				winner = players[i];
				}
			 }
		System.out.println("Winner is: Player " + winner.getIndex());
	}
		
	public static void main(String[] args) throws InterruptedException {
		
		players = new PlayerThread[p];
		
		for (int i = 0; i < p; i++) {
			players[i] = new PlayerThread(i);
		}
		
		// start of parallel part
				long pps = System.currentTimeMillis();
		
		for (int i = 0; i < p; i++) {
			players[i].start();
		}
		
		for (int i = 0; i < p; i++) {
			players[i].join();
		}
		
		for (int i = 0; i < p; i++) {
			players[i].getResults();
		}
		
		getWinner();
		long ppe = System.currentTimeMillis();
		// end of parallel part
		
		System.out.println("Parallel part: " + ((double)(ppe - pps) / 1000) + " seconds");
	}
}


public class Sequential {
	
	/*
	  Scissors cuts Paper
	  Paper covers Rock
	  Rock crushes Lizard
	  Lizard poisons Spock
	  Spock smashes Scissors
	  Scissors decapitates Lizard
	  Lizard eats Paper
	  Paper disproves Spock
	  Spock vaporizes Rock
	  Rock crushes Scissors
	*/
	
	public static int p = 5; // number of players
	public static int r = 100; // number of rounds
	public static Players players[] = new Players[p];

	public static void initializePlayers() {		
		for (int i = 0; i < p; i++) {
			players[i] = new Players(i + 1);	
		}
	}
	
	// returns number from 0 to 4 which corresponds to rpsls
	public static int chooseMove() {
		int move = (int)(Math.random() * 5);
		return move;
	}
	
	// 0 = Rock, 1 = Paper, 2 = Scissors, 3 = Lizard, 4 = Spock
	// determines the result
	public static void result(Players p1, Players p2) {
		p1.choice = chooseMove();
		p2.choice = chooseMove();
		
		if (p1.choice == 0) {
			if (p2.choice == 1 || p2.choice == 4) {
				p1.loss++;
				p2.win++;
			}
			else if (p2.choice == 0) {
				result(p1, p2);
			}
			else {
				p1.win++;
				p2.loss++;
			}
		}
		
		else if (p1.choice == 1) {
			if (p2.choice == 2 || p2.choice == 3) {
				p1.loss++;
				p2.win++;
			}
			else if (p2.choice == 1) {
				result(p1, p2);
			}
			else {
				p1.win++;
				p2.loss++;
			}
		}
		
		else if (p1.choice == 2) {
			if (p2.choice == 0 || p2.choice == 4) {
				p1.loss++;
				p2.win++;
			}
			else if (p2.choice == 2) {
				result(p1, p2);
			}
			else {
				p1.win++;
				p2.loss++;
			}
		}
		
		else if (p1.choice == 3) {
			if (p2.choice == 0 || p2.choice == 2) {
				p1.loss++;
				p2.win++;
			}
			else if (p2.choice == 3) {
				result(p1, p2);
			}
			else {
				p1.win++;
				p2.loss++;
			}
		}
		
		else if (p1.choice == 4) {
			if (p2.choice == 1 || p2.choice == 3) {
				p1.loss++;
				p2.win++;
			}
			else if (p2.choice == 4) {
				result(p1, p2);
			}
			else {
				p1.win++;
				p2.loss++;
			}
		}
	}
	
	// returns the final winner from all players
	public static void getWinner() {
		Players winner = players[0];
		for (int i = 1; i < players.length; i++) {
			 if (players[i].win > winner.win) {
				winner = players[i];
			}
		}
		System.out.println("Winner is: Player " + winner.getIndex());
	}

	public static void main(String[] args) {
		// start of sequential part
		long sps = System.currentTimeMillis();
		initializePlayers();
		
		for (int i = 0; i < p; i++) {
			for (int j = i+1; j < p; j++) {
				if (i == j) {
					break;
				}
				for (int k = 0; k < r; k++) {
					result(players[i], players[j]);
				}
			}
		}
		
		for (int i = 0; i < p; i++) {
			players[i].getResults();
		}
		
		getWinner();
		long spe = System.currentTimeMillis();
		// end of sequential part
		
		System.out.println("Sequential part: " + ((double)(spe - sps) / 1000) + " seconds");
	}
}

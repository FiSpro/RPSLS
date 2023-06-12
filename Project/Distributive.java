import mpi.*;

public class Distributive {

	public static int rank; // current processor
	public static int size; // number of processors (players) = 5
	public static int r = 100; // number of rounds
	public static Players players[];
	public static int[] wins;
	public static int[] losses;
	public static int allWins[];
	public static int allLosses[];

	// returns number from 0 to 4 which corresponds to rpsls
	public static int chooseMove() {
		int move = (int) (Math.random() * 5);
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
			} else if (p2.choice == 0) {
				result(p1, p2);
			} else {
				p1.win++;
				p2.loss++;
			}
		}

		else if (p1.choice == 1) {
			if (p2.choice == 2 || p2.choice == 3) {
				p1.loss++;
				p2.win++;
			} else if (p2.choice == 1) {
				result(p1, p2);
			} else {
				p1.win++;
				p2.loss++;
			}
		}

		else if (p1.choice == 2) {
			if (p2.choice == 0 || p2.choice == 4) {
				p1.loss++;
				p2.win++;
			} else if (p2.choice == 2) {
				result(p1, p2);
			} else {
				p1.win++;
				p2.loss++;
			}
		}

		else if (p1.choice == 3) {
			if (p2.choice == 0 || p2.choice == 2) {
				p1.loss++;
				p2.win++;
			} else if (p2.choice == 3) {
				result(p1, p2);
			} else {
				p1.win++;
				p2.loss++;
			}
		}

		else if (p1.choice == 4) {
			if (p2.choice == 1 || p2.choice == 3) {
				p1.loss++;
				p2.win++;
			} else if (p2.choice == 4) {
				result(p1, p2);
			} else {
				p1.win++;
				p2.loss++;
			}
		}
	}

	// collects wins and losses for each player
	public static void collectResults() {
		for (int i = 0; i < players.length; i++) {
			wins[i] = players[i].win;
			losses[i] = players[i].loss;
			}
		}
	
	// gathers all wins and losses separately into two arrays
	public static void gatherResults() {
		collectResults();
		
		MPI.COMM_WORLD.Gather(wins, 0, wins.length, MPI.INT, allWins, 0, wins.length, MPI.INT, 0);
		MPI.COMM_WORLD.Gather(losses, 0, losses.length, MPI.INT, allLosses, 0, losses.length, MPI.INT, 0);
	}

	// returns the final winner from all players and prints the time for program execution
	public static void calculateWinner(long dps, long dpe) {
		if (rank == 0) {
			for (int i = 0; i < players.length; i++) {
				players[i].win = 0;
				players[i].loss = 0;
			}

			for (int i = 0; i < allWins.length; i++) {
				players[i % size].win = players[i % size].win + allWins[i];
				players[i % size].loss = players[i % size].loss + allLosses[i];
			}

			Players winner = players[0];
			winner.getResults();
			for (int i = 1; i < players.length; i++) {
				players[i].getResults();
				if (players[i].win > winner.win) {
					winner = players[i];
				}
			}
			System.out.println("Winner is: Player " + winner.getIndex());
			System.out.println("Distributive part: " + ((double) (dpe - dps) / 1000) + " seconds");
		}
	}
	
	public static void main(String[] args) {

		// start of distributive part
		MPI.Init(args);
		rank = MPI.COMM_WORLD.Rank();
		size = MPI.COMM_WORLD.Size();
		players = new Players[size];
		wins = new int[size];
		losses = new int[size];
		allWins =  new int[players.length * size];
		allLosses = new int[players.length * size];

		long dps = System.currentTimeMillis();

		for (int i = 0; i < size; i++) {
			players[i] = new Players(i + 1);
		}

		for (int i = 0; i < r; i++) {
			for (int j = 0; j < size; j++) {
				if (rank == j) {
					break;
				}
				result(players[rank], players[j]);
			}
		}

		gatherResults();
		long dpe = System.currentTimeMillis();
		// Calculate the winner
		calculateWinner(dps, dpe);

		MPI.Finalize();
		// end of distributive part
	}
}


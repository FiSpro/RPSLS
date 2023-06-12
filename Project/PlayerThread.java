import java.util.concurrent.atomic.AtomicInteger;

public class PlayerThread extends Thread{
	
	public int index;
	public int choice;
	public AtomicInteger win, loss;
	
	public PlayerThread(int id) {
		this.index = id;
		this.win = new AtomicInteger(0);
		this.loss = new AtomicInteger(0);
	}
	
	// returns the player's index in the array
	public int getIndex() {
		return index;
	}
	
	public static int chooseMove() {
		int move = (int)(Math.random() * 5);
		return move;
	}
	
	public void play(PlayerThread p1, PlayerThread p2) {
		p1.choice = chooseMove();
		p2.choice = chooseMove();
		
		if (p1.choice == 0) {
			if (p2.choice == 1 || p2.choice == 4) {
				p1.loss.addAndGet(1);
				p2.win.addAndGet(1);
			}
			else if (p2.choice == 0) {
				play(p1, p2);
			}
			else {
				p1.win.addAndGet(1);
				p2.loss.addAndGet(1);
			}
		}
		
		else if (p1.choice == 1) {
			if (p2.choice == 2 || p2.choice == 3) {
				p1.loss.addAndGet(1);
				p2.win.addAndGet(1);
			}
			else if (p2.choice == 1) {
				play(p1, p2);
			}
			else {
				p1.win.addAndGet(1);
				p2.loss.addAndGet(1);
			}
		}
		
		else if (p1.choice == 2) {
			if (p2.choice == 0 || p2.choice == 4) {
				p1.loss.addAndGet(1);
				p2.win.addAndGet(1);
			}
			else if (p2.choice == 2) {
				play(p1, p2);
			}
			else {
				p1.win.addAndGet(1);
				p2.loss.addAndGet(1);
			}
		}
		
		else if (p1.choice == 3) {
			if (p2.choice == 0 || p2.choice == 2) {
				p1.loss.addAndGet(1);
				p2.win.addAndGet(1);
			}
			else if (p2.choice == 3) {
				play(p1, p2);
			}
			else {
				p1.win.addAndGet(1);
				p2.loss.addAndGet(1);
			}
		}
		
		else if (p1.choice == 4) {
			if (p2.choice == 1 || p2.choice == 3) {
				p1.loss.addAndGet(1);
				p2.win.addAndGet(1);
			}
			else if (p2.choice == 4) {
				play(p1, p2);
			}
			else {
				p1.win.addAndGet(1);
				p2.loss.addAndGet(1);
			}
		}
	}
	
	public void run() {
		
		PlayerThread players[] = Parallel.players;
		int rounds = Parallel.r;
		
		for (int i = getIndex(); i < players.length; i++) {
			if (i == getIndex())
					continue;
			
			for (int j = 0; j < rounds; j++) {
				play(players[getIndex()], players[i]);
			}
		}
	}
	
	// prints final results for each player
	public void getResults() {
		System.out.println("Player " + index + " has played " + (win.get() + loss.get()) + " games with " + win + " wins and "  + loss + " losses");
	}

}

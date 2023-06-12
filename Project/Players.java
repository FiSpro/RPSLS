
public class Players {

	public int index;
	public int choice;
	public int win, loss;
	
	public Players(int id) {
		this.index = id;
	}
	
	// returns the player's index in the array
	public int getIndex() {
		return index;
	}
	
	// prints final results for each player
	public void getResults() {
		System.out.println("Player " + index + " has played " + (win + loss) + " games with " + win + " wins and "  + loss + " losses");
	}
	
}

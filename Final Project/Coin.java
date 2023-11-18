public class Coin {
    Player player;

    public Coin(Player p) {
        this.player = p;
    }

    public Player getPlayer() {
        return player;
    }

    public enum Player {
        Player1, Player2
    }
}

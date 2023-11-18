public class Grid {
    Column[] grid;

    public Grid(int cols, int rows) {
        grid = new Column[cols];
        for (int i = 0; i < cols; i++) {
            grid[i] = new Column(rows);
        }
    }

    public void insertCoin(int col, Coin.Player p) {
        grid[col].insertCoin(p);
    }
}

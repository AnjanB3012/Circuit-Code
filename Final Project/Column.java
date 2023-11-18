public class Column {
    int size;
    Coin[] col;

    public Column(int cols) {
        col = new Coin[cols];
        size = 0;
    }

    public boolean isFull() {
        return (col.length == size);
    }

    public void insertCoin(Coin.Player p) {
        if (isFull())
            throw new IndexOutOfBoundsException();
        else {
            col[size] = new Coin(p);
            size++;
        }
    }
}

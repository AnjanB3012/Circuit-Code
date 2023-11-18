import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connectfour extends JFrame {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private JButton[][] buttons;
    private Grid grid;
    private String currentPlayer = "Blue";
    private boolean status = false;
    private String winner;

    private ImageIcon blueIcon = new ImageIcon(getClass().getResource("/Player 1.png"));
    private ImageIcon redIcon = new ImageIcon(getClass().getResource("/Player 2.png"));
    private JTextField textField1=new JTextField();
    private String Player1Name;
    private JTextField textField2=new JTextField();
    private String Player2Name;


    public Connectfour() {
        setupStartScreen();
        setTitle("Circuit Code");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(ROWS + 1, COLUMNS));

        buttons = new JButton[ROWS][COLUMNS];
        grid = new Grid(COLUMNS, ROWS);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(70, 70));
                buttons[i][j].addActionListener(new ButtonClickListener(j));
                add(buttons[i][j]);
            }
        }

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetGame());
        add(resetButton);

        pack();
        setLocationRelativeTo(null);
    }

    private void setupStartScreen() {
        JFrame startFrame = new JFrame("Connect Four - Start Game");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setSize(400, 200);
        startFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Circuit Code");
        JPanel textview1= new JPanel();
        JPanel textview2= new JPanel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel label1=new JLabel("Player 1 Name:");
        label1.setFont(new Font("Arial",Font.PLAIN,12));
        textview1.add(label1,BorderLayout.NORTH);
        startFrame.add(titleLabel, BorderLayout.NORTH);
        textField1.setPreferredSize(new Dimension(100,40));
        textField1.setHorizontalAlignment(JTextField.CENTER);
        textview1.add(textField1,BorderLayout.SOUTH);
        JLabel label2=new JLabel("Player 2 Name:");
        label2.setFont(new Font("Arial",Font.PLAIN,12));
        textview2.add(label2,BorderLayout.NORTH);
        textField2.setPreferredSize(new Dimension(100,40));
        textField2.setHorizontalAlignment(JTextField.CENTER);
        textview2.add(textField2,BorderLayout.SOUTH);
        JPanel infopanel=new JPanel();
        infopanel.add(textview1,BorderLayout.NORTH);
        infopanel.add(textview2,BorderLayout.CENTER);
        startFrame.add(infopanel,BorderLayout.CENTER);
        
        
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            Player1Name=textField1.getText();
            Player2Name=textField2.getText();
            startFrame.setVisible(false);
            status = true;
            setVisible(true);
        });
        buttonPanel.add(startButton);
        startFrame.add(buttonPanel,BorderLayout.SOUTH);

        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int column;

        public ButtonClickListener(int column) {
            this.column = column;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (status) {
                dropCoin(column);
                if (!status) {
                    showEndScreen();
                }
            }
        }
    }

    private void dropCoin(int column) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (buttons[i][column].getIcon() == null) {
                buttons[i][column].setIcon(currentPlayer.equals("Blue") ? blueIcon : redIcon);
                grid.insertCoin(column, currentPlayer.equals("Blue") ? Coin.Player.Player1 : Coin.Player.Player2);
                if (checkForWinner(i, column)) {
                    status = false;
                    winner = currentPlayer;
                }
                switchPlayer();
                break;
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("Blue") ? "Red" : "Blue";
    }

    private void resetGame() {
        status = true;
        winner = null;
        currentPlayer = "Blue";
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                buttons[i][j].setIcon(null);
            }
        }
        grid = new Grid(COLUMNS, ROWS);
    }

    private void showEndScreen() {
        JFrame endFrame = new JFrame("Connect Four - Circuit Complete");
        endFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        endFrame.setSize(400, 200);
        endFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Circuit Complete!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        endFrame.add(titleLabel, BorderLayout.NORTH);

        if(winner.equals("Blue"))
        {
            JLabel winnerLabel = new JLabel("The winner is " + Player1Name);
            winnerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            winnerLabel.setHorizontalAlignment(JLabel.CENTER);
            endFrame.add(winnerLabel, BorderLayout.CENTER);
            endFrame.setLocationRelativeTo(null);
            endFrame.setVisible(true);
        }
        if(winner.equals("Red"))
        {
            JLabel winnerLabel = new JLabel("The winner is " + Player2Name);
            winnerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            winnerLabel.setHorizontalAlignment(JLabel.CENTER);
            endFrame.add(winnerLabel, BorderLayout.CENTER);
            endFrame.setLocationRelativeTo(null);
            endFrame.setVisible(true);
        }

        
    }

    private boolean checkForWinner(int row, int column) {
        return checkHorizontal(row, column) || checkVertical(row, column) || checkDiagonal(row, column);
    }

    private boolean checkHorizontal(int row, int column) {
        int count = 1;
        for (int j = column - 1; j >= 0 && buttons[row][j].getIcon() == buttons[row][column].getIcon(); j--) {
            count++;
        }
        for (int j = column + 1; j < COLUMNS && buttons[row][j].getIcon() == buttons[row][column].getIcon(); j++) {
            count++;
        }
        return count >= 4;
    }

    private boolean checkVertical(int row, int column) {
        int count = 1;
        for (int i = row - 1; i >= 0 && buttons[i][column].getIcon() == buttons[row][column].getIcon(); i--) {
            count++;
        }
        for (int i = row + 1; i < ROWS && buttons[i][column].getIcon() == buttons[row][column].getIcon(); i++) {
            count++;
        }
        return count >= 4;
    }

    private boolean checkDiagonal(int row, int column) {
        int count = 1;
        for (int i = row - 1, j = column - 1; i >= 0 && j >= 0 && buttons[i][j].getIcon() == buttons[row][column].getIcon(); i--, j--) {
            count++;
        }
        for (int i = row + 1, j = column + 1; i < ROWS && j < COLUMNS && buttons[i][j].getIcon() == buttons[row][column].getIcon(); i++, j++) {
            count++;
        }

        if (count >= 4) return true;

        count = 1;
        for (int i = row - 1, j = column + 1; i >= 0 && j < COLUMNS && buttons[i][j].getIcon() == buttons[row][column].getIcon(); i--, j++) {
            count++;
        }
        for (int i = row + 1, j = column - 1; i < ROWS && j >= 0 && buttons[i][j].getIcon() == buttons[row][column].getIcon(); i++, j--) {
            count++;
        }

        return count >= 4;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Connectfour());
    }
}

class Column {
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

class Coin {
    Player player;

    public Coin(Player p) {
        this.player = p;
    }

    public Player getPlayer() {
        return player;
    }

    enum Player {
        Player1, Player2
    }
}

class Grid {
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

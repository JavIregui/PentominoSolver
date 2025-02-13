import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PentominoSolver extends JPanel {
    private Board board;
    private PieceStack pieces;

    private int cellSize = 60;

    private boolean resizing = false;

    public PentominoSolver(int row, int col, int cellSize) {
        if(cellSize <= 60 || cellSize >= 25){
            this.cellSize = cellSize;
        }
        
        initComponents(row, col);
        configEvents();
    }

    private void initComponents(int row, int col) {
        // Window
        setPreferredSize(new Dimension(cellSize * col + 200, cellSize * row + 200));
        setLayout(null);
        setFocusable(true);

        // Components
        board = new Board(col, row);
        pieces = new PieceStack();
        board.placePiece(0, 0, pieces.getPiece()[0]);
    }

    private void configEvents() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                onWindowResized();
            }
        });
    }

    private void onWindowResized() {

        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);

        if(window.getWidth()< window.getHeight()){
            cellSize = (window.getWidth() - 200) / board.getWidth();
        } else {
            cellSize = (window.getHeight() - 200) / board.getHeight();
        }
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g, 0, 0, cellSize, 3);
    }
}
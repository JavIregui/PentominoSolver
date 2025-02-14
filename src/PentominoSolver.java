import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PentominoSolver extends JPanel {
    private Board board;
    private PieceStack pieces;

    private int cellSize = 60;

    private boolean resizing = false;
    private Timer resizeTimer;
    private static final int RESIZE_DELAY_MS = 100;

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
                scheduleWindowAdjustment();
            }
        });
    }

    private void scheduleWindowAdjustment() {
        if (resizeTimer != null) {
            resizeTimer.stop();
        }
        
        resizeTimer = new Timer(RESIZE_DELAY_MS, e -> adjustWindowSize());
        resizeTimer.setRepeats(false);
        resizeTimer.start();
    }

    private void adjustWindowSize() {
        if (!resizing) {
            resizing = true;
            
            SwingUtilities.invokeLater(() -> {
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
                
                int currentWidth = window.getWidth();
                int currentHeight = window.getHeight();
                
                int availableWidth = currentWidth - 200;
                int availableHeight = currentHeight - 200;
                
                int cellSizeW = availableWidth / board.getWidth();
                int cellSizeH = availableHeight / board.getHeight();
                int newCellSize = Math.min(cellSizeW, cellSizeH);
                newCellSize = Math.max(newCellSize, 1);
                
                int desiredWidth = board.getWidth() * newCellSize + 200;
                int desiredHeight = board.getHeight() * newCellSize + 200;
                
                Dimension minSize = window.getMinimumSize();
                Dimension maxSize = window.getMaximumSize();
                
                desiredWidth = Math.max(minSize.width, Math.min(desiredWidth, maxSize.width));
                desiredHeight = Math.max(minSize.height, Math.min(desiredHeight, maxSize.height));
                
                if (desiredWidth != currentWidth || desiredHeight != currentHeight) {
                    window.setSize(desiredWidth, desiredHeight);
                    window.validate(); 
                }
                
                cellSize = newCellSize;
                repaint();
                resizing = false;
            });
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g, 0, 0, cellSize, 3);
    }
}
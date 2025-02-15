import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PentominoSolver extends JPanel {
    private Board board;
    private List<List<char[][]>> allOrientations;
    private Stack<Placement> placementStack = new Stack<>();
    private List<Integer> pieceOrder;
    private int currentOrderIndex;
    private int currentPieceIndex = 0;
    private int currentOrientationIndex = 0;
    private int currentRow = 0;
    private int currentCol = 0;
    private boolean solving = false;
    private boolean solutionFound = false;

    private int cellSize = 60;
    private final int gutter = 3;

    private boolean resizing = false;
    private Timer resizeTimer;
    private static final int RESIZE_DELAY_MS = 100;

    private boolean autoMode = false;
    private Timer autoTimer;
    private static final int AUTO_DELAY_MS = 1;

    public PentominoSolver(int row, int col, int cellSize) {
        if(cellSize <= 60 || cellSize >= 25){
            this.cellSize = cellSize;
        }
        
        initComponents(row, col);
        configEvents();
        initOrientations();
        initAutoTimer();
    }

    private void initComponents(int row, int col) {
        setPreferredSize(new Dimension(cellSize * col + 200, cellSize * row + 200));
        setLayout(null);
        setFocusable(true);

        board = new Board(col, row);
    }

    private void configEvents() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scheduleWindowAdjustment();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && solving && !autoMode) {
                    nextStep();
                }
            }
        });
    }

    private void initOrientations() {
        allOrientations = new ArrayList<>();
        PieceStack tempStack = new PieceStack();
        char[][][][] allShapes = tempStack.getAllShapes();
        
        for (char[][][] shapeVariants : allShapes) {
            List<char[][]> orientationsForShape = new ArrayList<>();
            for (char[][] variant : shapeVariants) {
                orientationsForShape.addAll(generateRotations(variant));
            }
            allOrientations.add(orientationsForShape);
        }
    }

    public static List<char[][]> generateRotations(char[][] piece) {
        List<char[][]> rotations = new ArrayList<>();
        char[][] current = piece;
        
        for (int i = 0; i < 4; i++) {
            rotations.add(current);
            current = rotateClockwise(current);
            if (Arrays.deepEquals(current, piece)) break;
        }
        return rotations;
    }

    private static char[][] rotateClockwise(char[][] piece) {
        int rows = piece.length;
        int cols = piece[0].length;
        char[][] rotated = new char[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = piece[i][j];
            }
        }
        return rotated;
    }

    private void initAutoTimer() {
        autoTimer = new Timer(AUTO_DELAY_MS, e -> {
            if (solving && !solutionFound) {
                nextStep();
            } else {
                autoTimer.stop();
            }
        });
    }

    public void setAutoMode(boolean enabled) {
        this.autoMode = enabled;
        if (autoMode && solving) {
            autoTimer.start();
        } else {
            autoTimer.stop();
        }
    }

    public void startSolving() {
        solving = true;
        solutionFound = false;
        
        // Generar nuevo orden aleatorio de piezas
        pieceOrder = new ArrayList<>();
        for (int i = 0; i < allOrientations.size(); i++) {
            pieceOrder.add(i);
        }
        Collections.shuffle(pieceOrder);
        
        currentOrderIndex = 0;
        currentOrientationIndex = 0;
        currentRow = 0;
        currentCol = 0;
        placementStack.clear();
        board.reset();
        
        if (autoMode) {
            autoTimer.start();
        }
        repaint();
    }

    public void nextStep() {
        if (solutionFound || currentOrderIndex >= pieceOrder.size()) {
            if (autoMode) autoTimer.stop();
            return;
        }

        int currentPiece = pieceOrder.get(currentOrderIndex);
        List<char[][]> orientations = allOrientations.get(currentPiece);

        while (currentOrientationIndex < orientations.size()) {
            char[][] orientation = orientations.get(currentOrientationIndex);
            while (currentRow < board.getHeight()) {
                while (currentCol < board.getWidth()) {
                    if (tryPlacePiece(currentRow, currentCol, orientation)) {
                        placementStack.push(new Placement(
                        currentOrderIndex,               
                        currentPiece,                     
                        currentOrientationIndex, 
                        currentRow, 
                        currentCol, 
                        orientation
                    ));
                        
                        currentOrderIndex++;
                        currentOrientationIndex = 0;
                        currentRow = 0;
                        currentCol = 0;
                        
                        repaint();
                        
                        if (currentOrderIndex == pieceOrder.size()) {
                            solutionFound = true;
                            JOptionPane.showMessageDialog(this, "¡Solución encontrada!");
                        }
                        return;
                    }
                    currentCol++;
                }
                currentCol = 0;
                currentRow++;
            }
            currentRow = 0;
            currentOrientationIndex++;
        }

        backtrack();
        repaint();

        if (autoMode && !solutionFound) {
            autoTimer.start();
        }
    }

    private boolean tryPlacePiece(int row, int col, char[][] piece) {
        if (board.placePiece(row, col, piece)) {
            if (board.solvable()) {
                return true;
            }
            board.unplacePiece(row, col, piece);
        }
        return false;
    }

    private void backtrack() {
        if (!placementStack.isEmpty()) {
            Placement last = placementStack.pop();
            board.unplacePiece(last.row, last.col, last.orientation);
            currentOrderIndex = last.orderIndex;  // Restaurar orderIndex
            currentOrientationIndex = last.orientationIndex + 1;
            currentRow = last.row;
            currentCol = last.col;
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró solución.");
            solving = false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g, 50, 50, cellSize, gutter);
    }

    public int getBoardWidth() {
        return board.getWidth();
    }

    private class Placement {
        int orderIndex;
        int pieceIndex;
        int orientationIndex;
        int row;
        int col;
        char[][] orientation;

        public Placement(int orderIndex, int pieceIndex, int orientationIndex, int row, int col, char[][] orientation) {
            this.orderIndex = orderIndex;
            this.pieceIndex = pieceIndex;
            this.orientationIndex = orientationIndex;
            this.row = row;
            this.col = col;
            this.orientation = orientation;
        }
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
}
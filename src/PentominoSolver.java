import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class PentominoSolver extends JPanel {

    private Board board;
    private PieceQueue pieces;
    private Stack<Piece> placed;

    private int cellSize = 60;
    private final int gutter = 3;

    private boolean resizing = false;
    private Timer resizeTimer;
    private static final int RESIZE_DELAY_MS = 100;

    private Timer autoTimer;
    private static final int AUTO_DELAY_MS = 1000;
    private boolean solving;

    public PentominoSolver(int row, int col, int cellSize) {
        if (row * col != 60) {
            throw new IllegalArgumentException("Board size is not valid");
        }

        if(cellSize <= 60 || cellSize >= 25){
            this.cellSize = cellSize;
        }

        this.solving = false;
        
        initComponents(row, col);
        configEvents();
        initAutoTimer();
    }

    private void initComponents(int row, int col) {
        setPreferredSize(new Dimension(cellSize * col + 200, cellSize * row + 200));
        setLayout(null);
        setFocusable(true);

        board = new Board(col, row);
        pieces = new PieceQueue();
        placed = new Stack<>();
    }

    private void configEvents() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scheduleWindowAdjustment();
            }
        });
    }

    private void initAutoTimer() {
        autoTimer = new Timer(AUTO_DELAY_MS, e -> {
            if (solving) {
                nextStep();
            } else {
                autoTimer.stop();
            }
        });
    }

    public void startSolving() {
        solving = true;
        autoTimer.start();
    }

    public void stopSolving() {
        solving = false;
    }

    private void nextStep() {
        // 1. Caso base: si ya no hay piezas por colocar, se encontró la solución.
        if (pieces.isEmpty()) {
            solving = false;
            autoTimer.stop();
            JOptionPane.showMessageDialog(this, "¡Solución encontrada!");
            return;
        }
        
        // 2. Verificamos si el estado actual del tablero es resolvible (heurística)
        if (!board.solvable()) {
            backtrack();
            repaint();
            return;
        }
        
        // 3. Buscamos la primera casilla vacía en el tablero.
        int targetX = -1, targetY = -1;
        boolean foundEmpty = false;
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                if (board.getGrid()[y][x] == '0') {  // Se asume que board.isEmpty(x, y) indica si la celda está libre
                    targetX = x;
                    targetY = y;
                    foundEmpty = true;
                    break;
                }
            }
            if (foundEmpty) break;
        }
        
        // Si por alguna razón el tablero está lleno, también es solución.
        if (!foundEmpty) {
            solving = false;
            autoTimer.stop();
            JOptionPane.showMessageDialog(this, "¡Solución encontrada!");
            return;
        }
        
        // 4. Intentamos colocar alguna de las piezas restantes en la casilla (targetX, targetY)
        boolean placedPiece = false;
        
        // Recorremos la cola de piezas (suponiendo que podemos acceder a ellas por índice)
        for (int i = 0; i < pieces.size(); i++) {
            Piece currentPiece = pieces.getNextPiece();
            int rotacionesIntentadas = 0;
            int totalRotaciones = 8; // Por ejemplo, 8 (4 rotaciones + 4 del reflejo)
            
            // Se prueban todas las rotaciones posibles de la pieza
            while (rotacionesIntentadas < totalRotaciones) {
                if (board.placePiece(targetX, targetY, currentPiece.getShape())) {
                    // Si la pieza cabe, se coloca en el tablero
                    currentPiece.setPosition(targetX, targetY); // Guarda la posición en la pieza (si lo usas en backtracking)
                    placed.push(currentPiece);
                    placedPiece = true;
                    break;  // Salimos del bucle de rotaciones
                } else {
                    currentPiece.nextRotation();  // Probamos la siguiente rotación/reflejo
                    rotacionesIntentadas++;
                }
            }
            
            if (placedPiece) break;
            else {
                // Si no se pudo colocar la pieza, reiniciamos su rotación para intentos futuros
                currentPiece.resetRotation();
                pieces.addPiece(currentPiece);
            }
        }
        
        // 5. Si ninguna pieza pudo colocarse en la casilla encontrada, se hace backtracking.
        if (!placedPiece) {
            backtrack();
        }
        
        repaint();
    }

    private void backtrack() {
        if (!placed.isEmpty()) {
            Piece lastPlaced = placed.pop();
            // Se quita la pieza del tablero utilizando su posición guardada
            board.unplacePiece(lastPlaced.getX(), lastPlaced.getY(), lastPlaced);
            // Se devuelve la pieza a la cola para poder reintentarlo en otra posición
            pieces.addPiece(lastPlaced);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g, 2, 2, cellSize, gutter);
    }

    public int getBoardWidth() {
        return board.getWidth();
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
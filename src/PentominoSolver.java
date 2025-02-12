import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PentominoSolver extends JPanel {
    private Board board;
    private PieceStack pieces;

    public PentominoSolver(int row, int col) {
        inicializarComponentes(row, col);
        configurarEventos();
    }

    private void inicializarComponentes(int row, int col) {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);
        setFocusable(true);
        board = new Board(col, row);
        pieces = new PieceStack();
        board.placePiece(0, 0, pieces.getPiece()[0]);
    }

    private void configurarEventos() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                onWindowResized();
            }
        });
    }

    private void onWindowResized() {
        // Lógica al redimensionar (si es necesaria)
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g, 0, 0, 50, 3);
    }
}
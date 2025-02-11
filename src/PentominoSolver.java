import javax.swing.*;  
import java.awt.*;
import java.awt.event.*;

public class PentominoSolver extends JPanel{

    public PentominoSolver() {
        setPreferredSize(new Dimension(800, 600));
        setLayout(null);
        setFocusable(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                onWindowResized();
            }
        });

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String os = System.getProperty("os.name").toLowerCase();
            boolean isMac = os.contains("mac");

            if (isMac) {
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Game of Life");
            }

            JFrame window = new JFrame("Pentomino Solver");
            PentominoSolver app = new PentominoSolver();
            window.add(app);

            ImageIcon icon = new ImageIcon("../media/icon.png");
            window.setIconImage(icon.getImage());

            JMenuBar menuBar = createMenuBar(app);
            window.setJMenuBar(menuBar);

            window.setResizable(true);
            window.pack();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setVisible(true);
        });
    }

    private void onWindowResized() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    }

    private static JMenuBar createMenuBar(PentominoSolver app) {

        JMenuBar menuBar = new JMenuBar();

        return menuBar;
    }
}
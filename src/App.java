import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            configLookAndFeel();
            JFrame window = createWindow();

            PentominoSolver app = new PentominoSolver(6, 10, 60);
            window.add(app);

            configWindow(window, app);
            
        });
    }

    private static void configLookAndFeel() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Game of Life");
        }
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JFrame createWindow() {
        return new JFrame("Pentomino Solver");
    }

    private static void configWindow(JFrame window, PentominoSolver app) {
        window.setJMenuBar(createMenuBar(app));
        window.setIconImage(new ImageIcon("../media/icon.png").getImage());
        window.setResizable(true);
        setLimits(window, app);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);

        window.setVisible(true);
    }

    private static JMenuBar createMenuBar(PentominoSolver app) {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu solveMenu = new JMenu("Resolver");
        JMenuItem startItem = new JMenuItem("Iniciar solución");
        JMenuItem stopItem = new JMenuItem("Parar solución");
        
        startItem.addActionListener(e -> app.startSolving());
        stopItem.addActionListener(e -> app.stopSolving());
        
        solveMenu.add(startItem);
        solveMenu.add(stopItem);
        menuBar.add(solveMenu);
        
        return menuBar;
    }

    public static void setLimits(JFrame window, PentominoSolver app) {
        int widthType = app.getBoardWidth();
        Dimension minSize = new Dimension();
        Dimension maxSize = new Dimension();

        switch (widthType) {
            case 12:
                minSize.setSize(500, 325);
                maxSize.setSize(920, 500);
                break;
            case 15:
                minSize.setSize(575, 300);
                maxSize.setSize(1100, 440);
                break;
            default:
                minSize.setSize(450, 350);
                maxSize.setSize(800, 560);
                break;
        }

        window.setMinimumSize(minSize);
        window.setMaximumSize(maxSize);
        window.pack();
    }
}
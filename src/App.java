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
        JFrame window = new JFrame("Pentomino Solver");
        return window;
    }

    private static void configWindow(JFrame window, PentominoSolver app) {
        window.setJMenuBar(createMenuBar(app));

        window.setIconImage(new ImageIcon("../media/icon.png").getImage());

        window.setResizable(true);
        setLimits(window, app);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setVisible(true);
    }

    private static JMenuBar createMenuBar(PentominoSolver app) {
        JMenuBar menuBar = new JMenuBar();
        // Añade aquí los elementos del menú
        return menuBar;
    }

    public static void setLimits(JFrame window, PentominoSolver app) {
        switch (app.getWidth()) {
            case 12:
                window.setMinimumSize(new Dimension(500, 325));
                window.setMaximumSize(new Dimension(920, 500));
                window.pack();
                
                break;

            case 15:
                window.setMinimumSize(new Dimension(575, 300));
                window.setMaximumSize(new Dimension(1100, 440));
                window.pack();
                
                break;
        
            default:
                window.setMinimumSize(new Dimension(450, 350));
                window.setMaximumSize(new Dimension(800, 560));
                window.pack();

                break;
        }
    }
}
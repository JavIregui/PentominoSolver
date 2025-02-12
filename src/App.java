import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            configLookAndFeel();
            JFrame window = createWindow();

            PentominoSolver app = new PentominoSolver(6, 10);
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
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    private static JMenuBar createMenuBar(PentominoSolver app) {
        JMenuBar menuBar = new JMenuBar();
        // Añade aquí los elementos del menú
        return menuBar;
    }
}
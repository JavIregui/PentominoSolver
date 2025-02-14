import java.util.Stack;
import java.awt.*;

public class Board {

    private int width = 10;
    private int height = 6;

    private char[][] grid; 

    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        grid = new char[this.height][this.width];
        reset();
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public char[][] getGrid() {
        return grid;
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    public boolean placePiece(int row, int col, char[][] piece) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] != '0') {
                    int gridRow = row + i;
                    int gridCol = col + j;
                    
                    if (gridRow < 0 || gridRow >= height || 
                        gridCol < 0 || gridCol >= width || 
                        grid[gridRow][gridCol] != '0') {
                        return false;
                    }
                }
            }
        }

        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] != '0') {
                    grid[row + i][col + j] = piece[i][j];
                }
            }
        }
        return true;
    }

    public void unplacePiece(int row, int col, char[][] piece) {
        for (int i = 0; i < piece.length; i++) {
            for (int j = 0; j < piece[i].length; j++) {
                if (piece[i][j] != '0') {
                    int gridRow = row + i;
                    int gridCol = col + j;
                    if (gridRow >= 0 && gridRow < height && 
                        gridCol >= 0 && gridCol < width) {
                        grid[gridRow][gridCol] = '0';
                    }
                }
            }
        }
    }

    public boolean solvable() {
        boolean[][] visited = new boolean[height][width];
        
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if (grid[i][j] == '0' && !visited[i][j]) {

                    if (getGapSize(i, j, visited) % 5 != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int getGapSize(int row, int col, boolean[][] visited) {
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{row, col});

        visited[row][col] = true;

        int size = 0;
        
        while (!stack.isEmpty()) {
            int[] cell = stack.pop();
            int r = cell[0], c = cell[1];
            size++;
            
            for (int d = 0; d < 4; d++) {
                int newRow = r + dRow[d];
                int newCol = c + dCol[d];
                
                if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width &&
                    grid[newRow][newCol] == '0' && !visited[newRow][newCol]) {
                    stack.push(new int[]{newRow, newCol});
                    visited[newRow][newCol] = true;
                }
            }
        }
        
        return size;
    }

    public void reset() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = '0';
            }
        }
    }

    public void draw(Graphics g, int posX, int posY, int cellSize, int gutter) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(gutter));

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int x = posX + col * cellSize;
                int y = posY + row * cellSize;
                
                setCellColor(g2d, grid[row][col]);
                g2d.fillRect(x, y, cellSize, cellSize);
                
                g2d.setColor(new Color(0x808080));
                g2d.drawRect(x, y, cellSize, cellSize);
            }
        }
    }

    private void setCellColor(Graphics2D g2d, char piece) {
        switch (piece) {
            case '0' -> g2d.setColor(new Color(0xFFFFFF));
            case 'i' -> g2d.setColor(new Color(0xEEAAAA));
            case 'f' -> g2d.setColor(new Color(0xDDBB99));
            case 'l' -> g2d.setColor(new Color(0xCCCC88));
            case 'p' -> g2d.setColor(new Color(0xBBDD99));
            case 'n' -> g2d.setColor(new Color(0xAAEEAA));
            case 't' -> g2d.setColor(new Color(0x99DDBB));
            case 'u' -> g2d.setColor(new Color(0x88CCCC));
            case 'v' -> g2d.setColor(new Color(0x99BBDD));
            case 'w' -> g2d.setColor(new Color(0xAAAAEE));
            case 'x' -> g2d.setColor(new Color(0xBB99DD));
            case 'y' -> g2d.setColor(new Color(0xCC88CC));
            case 'z' -> g2d.setColor(new Color(0xDD99BB));
            default -> g2d.setColor(Color.BLACK);
        }
    }
}

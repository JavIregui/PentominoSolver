public class Piece {
    public char[][][] variants;
    public int currentVariant;
    public int rotation;
    public char[][] piece;
    private int x, y;

    public Piece(char[][][] variants) {
        this.variants = variants;

        this.currentVariant = 0;
        this.piece = this.variants[currentVariant];

        this.rotation = 0;

        this.x = 0;
        this.y = 0;
    }


    public void nextRotation() {
        if (rotation < 3) {
            char[][] rotated = new char[piece[0].length][piece.length];
            for (int i = 0; i < piece.length; i++) {
                for (int j = 0; j < piece[i].length; j++) {
                    rotated[j][piece.length - 1 - i] = piece[i][j];
                }
            }
            piece = rotated;
            rotation++;
        } else {
            currentVariant = (currentVariant + 1) % variants.length;
            piece = variants[currentVariant];
            rotation = 0;
        }
    }

    public char[][] getShape() {
        return piece;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    
    public void reset() {
        currentVariant = 0;
        piece = variants[currentVariant];
        rotation = 0;
        x = 0;
        y = 0;
    }
}
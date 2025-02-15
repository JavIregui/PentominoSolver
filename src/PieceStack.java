import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class PieceStack {
    private ArrayList<char[][][]> pieces;
    private Random random;
    
    // Formas de las piezas con sus variantes (reflejos)
    private final char[][][][] shapes = {
        // F
        {
            {
                {'0','f','f'},
                {'f','f','0'},
                {'0','f','0'}
            },
            {
                {'f','f','0'},
                {'0','f','f'},
                {'0','f','0'}
            }
        },
        // I
        {
            {
                {'i'},
                {'i'},
                {'i'},
                {'i'},
                {'i'}
            }
        },
        // L
        {
            {
                {'l','0'},
                {'l','0'},
                {'l','0'},
                {'l','l'}
            },
            {
                {'0','l'},
                {'0','l'},
                {'0','l'},
                {'l','l'}
            }
        },
        // N
        {
            {
                {'0','n'},
                {'0','n'},
                {'n','n'},
                {'n','0'}
            },
            {
                {'n','0'},
                {'n','0'},
                {'n','n'},
                {'0','n'}
            }
        },
        // P
        {
            {
                {'p','p'},
                {'p','p'},
                {'0','p'}
            },
            {
                {'p','p'},
                {'p','p'},
                {'p','0'}
            }
        },
        // T
        {
            {
                {'t','t','t'},
                {'0','t','0'},
                {'0','t','0'}
            }
        },
        // U
        {
            {
                {'u','0','u'},
                {'u','u','u'}
            }
        },
        // V
        {
            {
                {'v','0','0'},
                {'v','0','0'},
                {'v','v','v'}
            }
        },
        // W
        {
            {
                {'w','0','0'},
                {'w','w','0'},
                {'0','w','w'}
            }
        },
        // X
        {
            {
                {'0','x','0'},
                {'x','x','x'},
                {'0','x','0'}
            }
        },
        // Y
        {
            {
                {'y','0'},
                {'y','y'},
                {'y','0'},
                {'y','0'}
            },
            {
                {'0','y'},
                {'y','y'},
                {'0','y'},
                {'0','y'}
            }
        },
        // Z
        {
            {
                {'0','z','z'},
                {'0','z','0'},
                {'z','z','0'}
            },
            {
                {'z','z','0'},
                {'0','z','0'},
                {'0','z','z'}
            }
        }
    };

    public PieceStack() {
        pieces = new ArrayList<>();
        random = new Random();
        reset();
    }

    public char[][][][] getAllShapes() {
        return shapes.clone();
    }

    public char[][][] getPiece() {
        return pieces.remove(random.nextInt(pieces.size()));
    }

    public void addPiece(char piece) {
        for (char[][][] shape : shapes) {
            for (char[][] variant : shape) {
                if (containsPiece(variant, piece)) {
                    pieces.add(shape);
                    return;
                }
            }
        }
    }

    private boolean containsPiece(char[][] variant, char target) {
        for (char[] row : variant) {
            for (char c : row) {
                if (c == target) return true;
            }
        }
        return false;
    }

    public char[][][] getRandomPiece() {
        if (pieces.isEmpty()) return null;
        return pieces.remove(random.nextInt(pieces.size()));
    }

    public void shufflePieces() {
        Collections.shuffle(Arrays.asList(shapes));
    }

    public void reset() {
        pieces.clear();
        for (char[][][] shape : shapes) {
            pieces.add(shape);
        }
    }
}
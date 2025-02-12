import java.util.ArrayList;
import java.util.Random;

public class PieceStack {

    private ArrayList<char[][][]> pieces;
    private Random random;

    private char[][][][] shapes = {
        {
            {
                {'0','f','f'},
                {'f','f','0'},
                {'0','f','0'},

            },
            {
                {'f','f','0'},
                {'0','f','f'},
                {'0','f','0'},
            }
        },
        {
            {
                {'i'},
                {'i'},
                {'i'},
                {'i'},
                {'i'}
            }
        },
        {
            {
                {'l','0'},
                {'l','0'},
                {'l','0'},
                {'l','l'},
            },
            {
                {'0','l'},
                {'0','l'},
                {'0','l'},
                {'l','l'},
            }
        },
        {
            {
                {'0','n'},
                {'0','n'},
                {'n','n'},
                {'n','0'},
            },
            {
                {'n','0'},
                {'n','0'},
                {'n','n'},
                {'0','n'},
            }
        },
        {
            {
                {'p','p'},
                {'p','p'},
                {'0','p'},

            },
            {
                {'p','p'},
                {'p','p'},
                {'p','0'},
            }
        },
        {
            {
                {'t','t','t'},
                {'0','t','0'},
                {'0','t','0'}
            }
        },
        {
            {
                {'u','0','u'},
                {'u','u','u'}
            }
        },
        {
            {
                {'v','0','0'},
                {'v','0','0'},
                {'v','v','v'}
            }
        },
        {
            {
                {'w','0','0'},
                {'w','w','0'},
                {'0','w','w'}
            }
        },
        {
            {
                {'0','x','0'},
                {'x','x','x'},
                {'0','x','0'}
            }
        },
        {
            {
                {'y','0'},
                {'y','y'},
                {'y','0'},
                {'y','0'},
            },
            {
                {'0','y'},
                {'y','y'},
                {'0','y'},
                {'0','y'},
            }
        },
        {
            {
                {'0','z','z'},
                {'0','z','0'},
                {'z','z','0'},

            },
            {
                {'z','z','0'},
                {'0','z','0'},
                {'0','z','z'},
            }
        }
    };

    public PieceStack() {
        pieces = new ArrayList<>();
        random = new Random();
        reset();
    }

    public char[][][] getPiece() {
        int index = random.nextInt(pieces.size());
        return pieces.remove(index);
    }

    public void addPiece(char piece) {
        for (char[][][] shape : shapes) {

            for (char[][] variant : shape) {

                for (char[] row : variant) {
                    for (char cell : row) {

                        if (cell == piece) {
                            pieces.add(shape);
                            return;
                        }
                    }
                }
            }
        }
    }

    public void reset() {
        for (char[][][] shapeVariants : shapes) {
            pieces.add(shapeVariants);
        }
    }
}

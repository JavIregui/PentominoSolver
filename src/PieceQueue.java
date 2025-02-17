import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PieceQueue {
    private Queue<Piece> pieces;
    
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

    public PieceQueue() {
        pieces = new LinkedList<>();
        reset();
    }

    public Piece getNextPiece() {
        return pieces.isEmpty() ? null : pieces.poll();
    }

    public void addPiece(Piece piece) {
        pieces.offer(piece);
    }

    public boolean isEmpty() {
        return pieces.isEmpty();
    }

    public int size() {
        return pieces.size();
    }

    public void reset() {
        pieces.clear();
        
        List<Piece> pieceList = new ArrayList<>();
        for (char[][][] shape : shapes) {
            pieceList.add(new Piece(shape));
        }
        Collections.shuffle(pieceList);

        pieces.addAll(pieceList);
    }
}
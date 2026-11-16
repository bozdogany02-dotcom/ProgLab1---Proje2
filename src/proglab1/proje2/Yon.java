package proglab1.proje2;

public enum Yon {
    E(1, 0),
    S(0, 1),
    W(-1, 0),
    N(0, -1);

    private final int dx;
    private final int dy;

    Yon(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() { return dx; }
    public int getDy() { return dy; }

    public Yon sag() {
        return values()[(ordinal() + 1) % values().length];
    }

    public Yon sol() {
        return values()[(ordinal() + values().length - 1) % values().length];
    }
}
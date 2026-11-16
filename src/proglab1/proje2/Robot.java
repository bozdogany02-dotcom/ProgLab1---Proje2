package proglab1.proje2;

import proglab1.proje2.govde.Govde;

public class Robot {

    private final int no;
    private final Govde govde;
    private int x;
    private int y;
    private Yon yon;
    private int enerji;
    private Durum durum;

    public Robot(int no, Govde govde, int x, int y, int enerji) {
        this.no = no;
        this.govde = govde;
        this.x = x;
        this.y = y;
        this.enerji = enerji;
        this.yon = Yon.E;
        this.durum = Durum.AKTIF;
    }

    public int getNo() { return no; }
    public Govde getGovde() { return govde; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Yon getYon() { return yon; }
    public int getEnerji() { return enerji; }
    public Durum getDurum() { return durum; }

    public boolean aktifMi() { return durum == Durum.AKTIF; }

    public void sagaDon() { yon = yon.sag(); }

    public void solaDon() { yon = yon.sol(); }

    public void konumaGit(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean enerjiHarca(int maliyet) {
        if (enerji < maliyet) {
            durum = Durum.ENERJISIZ;
            return false;
        }
        enerji -= maliyet;
        return true;
    }

    public void teslimEt() {
        durum = Durum.TESLIM;
    }

    public String ozet() {
        return "ROBOT " + no + " " + govde.getAd()
                + " KONUM " + x + " " + y
                + " YON " + yon
                + " ENERJI " + enerji
                + " DURUM " + durum;
    }
}
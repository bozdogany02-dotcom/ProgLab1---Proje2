package proglab1.proje2;

import proglab1.proje2.govde.Govde;

public class Robot {

    public static class DurumKaydi {
        private final int x;
        private final int y;
        private final Yon yon;
        private final int enerji;
        private final Durum durum;

        private DurumKaydi(int x, int y, Yon yon, int enerji, Durum durum) {
            this.x = x;
            this.y = y;
            this.yon = yon;
            this.enerji = enerji;
            this.durum = durum;
        }
    }

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

    public int getNo() {
        return no;
    }

    public Govde getGovde() {
        return govde;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Yon getYon() {
        return yon;
    }

    public int getEnerji() {
        return enerji;
    }

    public Durum getDurum() {
        return durum;
    }

    public boolean aktifMi() {
        return durum == Durum.AKTIF;
    }

    public void sagaDon() {
        if (aktifMi()) {
            yon = yon.sag();
        }
    }

    public void solaDon() {
        if (aktifMi()) {
            yon = yon.sol();
        }
    }

    public void konumaGit(int x, int y) {
        if (aktifMi()) {
            this.x = x;
            this.y = y;
        }
    }

    public boolean enerjiHarca(int maliyet) {
        if (!aktifMi()) {
            return false;
        }
        if (enerji < maliyet) {
            durum = Durum.ENERJISIZ;
            return false;
        }
        enerji -= maliyet;
        return true;
    }

    public void enerjiyiSifirla() {
        if (aktifMi()) {
            this.enerji = 0;
            this.durum = Durum.ENERJISIZ;
        }
    }

    public void teslimEt() {
        if (aktifMi()) {
            durum = Durum.TESLIM;
        }
    }

    public DurumKaydi kayitAl() {
        return new DurumKaydi(x, y, yon, enerji, durum);
    }

    public void kayittanYukle(DurumKaydi kayit) {
        this.x = kayit.x;
        this.y = kayit.y;
        this.yon = kayit.yon;
        this.enerji = kayit.enerji;
        this.durum = kayit.durum;
    }

    public String ozet() {
        return "ROBOT " + no + " " + govde.getAd()
                + " KONUM " + x + " " + y
                + " YON " + yon
                + " ENERJI " + enerji
                + " DURUM " + durum;
    }
}
package proglab1.proje2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import proglab1.proje2.komut.Komut;

public class Simulasyon {

    public static class KareKaydi {
        private final int kareNo;
        private final int komutNo; // 0: baslangic, 1..N: komut numarasi
        private final int aktifRobotNo; // -1: yok
        private final String aciklama;
        private final List<Robot.DurumKaydi> robotDurumlari;
        private final List<List<int[]>> robotIzleri;

        private KareKaydi(
                int kareNo,
                int komutNo,
                int aktifRobotNo,
                String aciklama,
                List<Robot.DurumKaydi> robotDurumlari,
                List<List<int[]>> robotIzleri) {
            this.kareNo = kareNo;
            this.komutNo = komutNo;
            this.aktifRobotNo = aktifRobotNo;
            this.aciklama = aciklama;
            this.robotDurumlari = robotDurumlari;
            this.robotIzleri = robotIzleri;
        }

        public int getKareNo() { return kareNo; }
        public int getKomutNo() { return komutNo; }
        public int getAktifRobotNo() { return aktifRobotNo; }
        public String getAciklama() { return aciklama; }
        public List<List<int[]>> getRobotIzleri() { return robotIzleri; }
    }

    private final Harita harita;
    private final List<Robot> robotlar;
    private final List<Komut> komutlar;
    private final int baslangicEnerjisi;
    private final List<KareKaydi> gecmis;
    private int aktifKareIndeks;

    public Simulasyon(Harita harita, List<Robot> robotlar, List<Komut> komutlar) {
        this.harita = harita;
        this.robotlar = new ArrayList<>(robotlar);
        this.komutlar = new ArrayList<>(komutlar);
        this.baslangicEnerjisi = this.robotlar.isEmpty() ? 1 : Math.max(1, this.robotlar.get(0).getEnerji());

        // Robotları numaralarına göre sırala
        this.robotlar.sort((a, b) -> Integer.compare(a.getNo(), b.getNo()));

        this.gecmis = new ArrayList<>();
        gecmisiOlustur();
        basaDon();
    }

    private void gecmisiOlustur() {
        List<List<int[]>> izler = new ArrayList<>();
        for (Robot r : robotlar) {
            List<int[]> rIz = new ArrayList<>();
            rIz.add(new int[]{r.getX(), r.getY()});
            izler.add(rIz);
        }

        gecmis.add(new KareKaydi(
                0, 0, -1,
                "Baslangic durumu. Robotlar doguya (E) bakiyor.",
                durumlariKopyala(),
                izleriKopyala(izler)));

        int kareSayaci = 0;
        for (int k = 0; k < komutlar.size(); k++) {
            Komut komut = komutlar.get(k);
            for (int rIdx = 0; rIdx < robotlar.size(); rIdx++) {
                Robot robot = robotlar.get(rIdx);
                kareSayaci++;

                int oncekiX = robot.getX();
                int oncekiY = robot.getY();
                Durum oncekiDurum = robot.getDurum();

                if (robot.aktifMi()) {
                    komut.uygula(robot, harita);
                    if (robot.getX() != oncekiX || robot.getY() != oncekiY) {
                        izler.get(rIdx).add(new int[]{robot.getX(), robot.getY()});
                    }
                }

                String aciklama = adimAciklamasiUret(
                        k + 1, komut, robot, oncekiDurum);

                gecmis.add(new KareKaydi(
                        kareSayaci,
                        k + 1,
                        robot.getNo(),
                        aciklama,
                        durumlariKopyala(),
                        izleriKopyala(izler)));
            }
        }
    }

    private String adimAciklamasiUret(
            int komutNo, Komut komut, Robot robot, Durum oncekiDurum) {
        String onEk = "Komut " + komutNo + " (" + komut.etiket() + "), R"
                + robot.getNo() + " " + robot.getGovde().getAd() + ": ";

        if (oncekiDurum == Durum.TESLIM) {
            return onEk + "daha once teslim etti.";
        }
        if (oncekiDurum == Durum.ENERJISIZ) {
            return onEk + "enerjisi tukendigi icin hareket etmedi.";
        }
        if (robot.getDurum() == Durum.TESLIM) {
            return onEk + "paketi teslim etti. Konum ("
                    + robot.getX() + ", " + robot.getY()
                    + "), enerji " + robot.getEnerji();
        }
        if (robot.getDurum() == Durum.ENERJISIZ) {
            return onEk + "enerjisi bitti! Konum ("
                    + robot.getX() + ", " + robot.getY() + ")";
        }
        return onEk + "konum (" + robot.getX() + ", " + robot.getY()
                + "), yon " + robot.getYon()
                + ", enerji " + robot.getEnerji();
    }

    private List<Robot.DurumKaydi> durumlariKopyala() {
        List<Robot.DurumKaydi> liste = new ArrayList<>();
        for (Robot r : robotlar) {
            liste.add(r.kayitAl());
        }
        return liste;
    }

    private List<List<int[]>> izleriKopyala(List<List<int[]>> kaynak) {
        List<List<int[]>> kopya = new ArrayList<>();
        for (List<int[]> yol : kaynak) {
            List<int[]> yolKopya = new ArrayList<>();
            for (int[] nokta : yol) {
                yolKopya.add(new int[]{nokta[0], nokta[1]});
            }
            kopya.add(yolKopya);
        }
        return kopya;
    }

    public void baslat() {
        sonaGit();
    }

    public void basaDon() {
        kareyeGit(0);
    }

    public void sonaGit() {
        kareyeGit(gecmis.size() - 1);
    }

    public boolean tekAdimIlerle() {
        if (aktifKareIndeks < gecmis.size() - 1) {
            kareyeGit(aktifKareIndeks + 1);
            return true;
        }
        return false;
    }

    public boolean geriGit() {
        if (aktifKareIndeks > 0) {
            kareyeGit(aktifKareIndeks - 1);
            return true;
        }
        return false;
    }

    public boolean tekKomutUygula() {
        if (aktifKareIndeks >= gecmis.size() - 1 || robotlar.isEmpty()) {
            return false;
        }
        int rSayisi = robotlar.size();
        int hedefKare = ((aktifKareIndeks / rSayisi) + 1) * rSayisi;
        kareyeGit(Math.min(hedefKare, gecmis.size() - 1));
        return true;
    }

    public void kareyeGit(int indeks) {
        if (indeks < 0 || indeks >= gecmis.size()) {
            return;
        }
        this.aktifKareIndeks = indeks;
        KareKaydi kayit = gecmis.get(indeks);
        for (int i = 0; i < robotlar.size(); i++) {
            robotlar.get(i).kayittanYukle(kayit.robotDurumlari.get(i));
        }
    }

    public void sonucuYazdir() {
        int teslimSayisi = 0;
        int kalanEnerji = 0;

        for (Robot robot : robotlar) {
            System.out.println(robot.ozet());
            if (robot.getDurum() == Durum.TESLIM) {
                teslimSayisi++;
            }
            kalanEnerji += robot.getEnerji();
        }

        System.out.println("TESLIM " + teslimSayisi);
        System.out.println("KALAN_ENERJI " + kalanEnerji);
    }

    public Harita getHarita() { return harita; }

    public List<Robot> getRobotlar() {
        return Collections.unmodifiableList(robotlar);
    }

    public List<Komut> getKomutlar() {
        return Collections.unmodifiableList(komutlar);
    }

    public int getBaslangicEnerjisi() { return baslangicEnerjisi; }
    public int getAktifKareIndeks() { return aktifKareIndeks; }
    public int getToplamKareSayisi() { return gecmis.size(); }
    public KareKaydi getAktifKare() { return gecmis.get(aktifKareIndeks); }

    public int getTeslimSayisi() {
        int sayac = 0;
        for (Robot r : robotlar) {
            if (r.getDurum() == Durum.TESLIM) {
                sayac++;
            }
        }
        return sayac;
    }

    public int getKalanEnerjiToplami() {
        int toplam = 0;
        for (Robot r : robotlar) {
            toplam += r.getEnerji();
        }
        return toplam;
    }
}
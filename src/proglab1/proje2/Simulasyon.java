package proglab1.proje2;

import java.util.ArrayList;
import java.util.List;
import proglab1.proje2.komut.Komut;

public class Simulasyon {

    private final Harita harita;
    private final List<Robot> robotlar;
    private final List<Komut> komutlar;

    public Simulasyon(Harita harita, List<Robot> robotlar,
            List<Komut> komutlar) {

        this.harita = harita;
        this.robotlar = new ArrayList<>(robotlar);
        this.komutlar = komutlar;

        // Robotları numaralarına göre sırala
        for (int i = 0; i < this.robotlar.size() - 1; i++) {
            for (int j = i + 1; j < this.robotlar.size(); j++) {

                if (this.robotlar.get(i).getNo()
                        > this.robotlar.get(j).getNo()) {

                    Robot gecici = this.robotlar.get(i);
                    this.robotlar.set(i, this.robotlar.get(j));
                    this.robotlar.set(j, gecici);
                }
            }
        }
    }

    public void baslat() {

        for (Komut komut : komutlar) {

            for (Robot robot : robotlar) {

                if (robot.aktifMi()) {
                    komut.uygula(robot, harita);
                }
            }
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

    public List<Robot> getRobotlar() {
        return robotlar;
    }
}

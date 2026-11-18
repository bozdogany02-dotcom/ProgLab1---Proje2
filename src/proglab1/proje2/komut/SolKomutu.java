package proglab1.proje2.komut;

import proglab1.proje2.Harita;
import proglab1.proje2.Robot;

public class SolKomutu implements Komut {

    @Override
    public void uygula(Robot robot, Harita harita) {
        if (robot.aktifMi()) {
            robot.solaDon();
        }
    }

    @Override
    public String etiket() {
        return "SOL";
    }
}
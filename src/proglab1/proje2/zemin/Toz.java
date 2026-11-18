package proglab1.proje2.zemin;

import proglab1.proje2.Harita;
import proglab1.proje2.Robot;
import proglab1.proje2.Sabitler;

public class Toz extends Zemin {

    public Toz() { super('T'); }

    @Override
    public boolean girisUygula(Robot robot, Harita harita, int x, int y) {
        if (robot.getGovde().ucabilir()) {
            robot.enerjiHarca(Sabitler.TOZ_HAVALI_MALIYET);   // savrulur, yerinde kalır
            return false;
        }
        return gir(robot, Sabitler.TOZ_MALIYET, x, y);
    }
}
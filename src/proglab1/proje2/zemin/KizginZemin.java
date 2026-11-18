package proglab1.proje2.zemin;

import proglab1.proje2.Harita;
import proglab1.proje2.Robot;

public class KizginZemin extends Zemin {

    public KizginZemin() {
        super('V');
    }

    @Override
    public boolean girisUygula(Robot robot, Harita harita, int x, int y) {

        if (robot.getGovde().atlayabilir()) {
            int maliyet = robot.getGovde().atlamaMaliyeti() + 1;
            return atla(robot, harita, x, y, maliyet);
        }

        if (robot.getGovde().ucabilir()) {
            int hedefX = x + robot.getYon().getDx();
            int hedefY = y + robot.getYon().getDy();

            Zemin hedef = harita.zeminAl(hedefX, hedefY);

            if (hedef == null || !hedef.inisaUygunMu()
                    || harita.doluMu(hedefX, hedefY)) {
                return false;
            }

            return gir(robot, 2, hedefX, hedefY);
        }

        if (gir(robot, 0, x, y)) {
            robot.enerjiyiSifirla();
            return true;
        }

        return false;
    }

    private boolean atla(Robot robot, Harita harita,
            int x, int y, int maliyet) {

        int inisX = x + robot.getYon().getDx();
        int inisY = y + robot.getYon().getDy();

        Zemin inis = harita.zeminAl(inisX, inisY);

        if (inis == null || !inis.inisaUygunMu()
                || harita.doluMu(inisX, inisY)) {
            return false;
        }

        return gir(robot, maliyet, inisX, inisY);
    }
}
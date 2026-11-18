package proglab1.proje2.zemin;

import proglab1.proje2.Harita;
import proglab1.proje2.Robot;
import proglab1.proje2.Sabitler;
import proglab1.proje2.govde.Govde;

public class Kaya extends Zemin {

    public Kaya() {
        super('K');
    }

    @Override
    public boolean girisUygula(Robot robot, Harita harita, int x, int y) {

        Govde govde = robot.getGovde();

        if (govde.ucabilir()) {
            return gir(robot, Sabitler.HAVALI_MALIYET, x, y);
        }

        if (govde.atlayabilir()) {
            return atla(robot, harita, x, y);
        }

        return gir(robot, Sabitler.KAYA_MALIYET, x, y);
    }

    private boolean atla(Robot robot, Harita harita, int x, int y) {

        int inisX = x + robot.getYon().getDx();
        int inisY = y + robot.getYon().getDy();

        Zemin inis = harita.zeminAl(inisX, inisY);

        if (inis == null || !inis.inisaUygunMu()
                || harita.doluMu(inisX, inisY)) {
            return false;
        }

        return gir(robot, robot.getGovde().atlamaMaliyeti(),
                inisX, inisY);
    }

    @Override
    public boolean inisaUygunMu() {
        return false;
    }
}
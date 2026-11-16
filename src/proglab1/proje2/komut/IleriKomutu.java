package proglab1.proje2.komut;

import proglab1.proje2.Harita;
import proglab1.proje2.Robot;
import proglab1.proje2.zemin.Zemin;

public class IleriKomutu implements Komut {
    private final int adim;
    
    public IleriKomutu(int adim) {
        this.adim = adim;
    }
    
    @Override
    public void uygula(Robot robot, Harita harita) {
        for (int i = 0; i < adim; i++) {
            if (!robot.aktifMi()) {
                return;
            }
            int hedefX = robot.getX() + robot.getYon().getDx();
            int hedefY = robot.getY() + robot.getYon().getDy();
            
            Zemin zemin = harita.zeminAl(hedefX, hedefY);
            if (zemin == null || harita.doluMu(hedefX, hedefY)) {
                return;     //harita kenarı ya da başka robot
            }
            if (!zemin.girisUygula(robot, harita, hedefX, hedefY)) {
                return;     //kalan adımlar iptal
            }
        }
    }
}
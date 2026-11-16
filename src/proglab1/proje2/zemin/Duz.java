package proglab1.proje2.zemin;

import proglab1.proje2.Harita;
import proglab1.proje2.Robot;
import proglab1.proje2.Sabitler;

public class Duz extends Zemin {
    public Duz() { super('.'); }
    
    @Override
    public boolean girisUygula(Robot robot, Harita harita, int x, int y) {
        return gir(robot, Sabitler.DUZ_MALIYET, x, y);
    }
}
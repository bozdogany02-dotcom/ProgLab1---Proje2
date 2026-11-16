package proglab1.proje2.zemin;

import proglab1.proje2.Harita;
import proglab1.proje2.Robot;
import proglab1.proje2.Sabitler;

public class Metan extends Zemin {
    public Metan() { super('~'); }
    
    @Override
    public boolean girisUygula(Robot robot, Harita harita, int x, int y) {
        if(robot.getGovde().ucabilir()) {
            return gir(robot, Sabitler.HAVALI_MALIYET, x, y);
        }
        return false;       //geçemez
    }
    
    @Override
    public boolean inisaUygunMu() {return false; }
}
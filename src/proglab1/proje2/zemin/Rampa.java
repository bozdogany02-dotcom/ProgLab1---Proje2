package proglab1.proje2.zemin;

import proglab1.proje2.Harita;
import proglab1.proje2.Robot;
import proglab1.proje2.Sabitler;

public class Rampa extends Zemin {
    public Rampa() {super('H'); }
    
    @Override
    public boolean girisUygula(Robot robot, Harita harita, int x, int y) {
        if(!robot.enerjiHarca(Sabitler.RAMPA_MALIYET)) {
            return false;
        }
        robot.konumaGit(x, y);
        robot.teslimEt();
        return false;
    }
}
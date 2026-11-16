package proglab1.proje2.zemin;

import proglab1.proje2.Harita;
import proglab1.proje2.Robot;

public class Engel extends Zemin {
    
    public Engel() {super('X'); }
    
    @Override
    public boolean girisUygula(Robot robot, Harita harita, int x, int y) {
        return false;
    }
    
    @Override
    public boolean inisaUygunMu() { return false; }
}
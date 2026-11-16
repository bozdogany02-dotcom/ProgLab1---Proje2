package proglab1.proje2.zemin;

import proglab1.proje2.Harita;
import proglab1.proje2.Robot;
import proglab1.proje2.Sabitler;
import proglab1.proje2.govde.Govde;

public class Buz extends Zemin {
    public Buz() { super('I'); }
    
    @Override
    public boolean girisUygula(Robot robot, Harita harita, int x, int y){
        Govde govde = robot.getGovde();
        if(govde.ucabilir()) {
            
            return gir(robot, Sabitler.HAVALI_MALIYET, x, y);
        }
        if(govde.kayabilir()) {
            return kay(robot, harita, x, y);
        }
        return gir(robot, Sabitler.BUZ_MALIYET, x, y);
    }
    
    private boolean kay(Robot robot, Harita harita, int x, int y) {
        if(!gir(robot, Sabitler.BUZ_KAYMA_MALIYET, x, y)){
            return false;
        }
        int dx = robot.getYon().getDx();
        int dy = robot.getYon().getDy();
        int sx = x + dx;
        int sy = y + dy;
        Zemin sonraki = harita.zeminAl(sx, sy);
        while (sonraki != null && sonraki.kaygan() && !harita.doluMu(sx, sy)){
            robot.konumaGit(sx, sy);
            sx += dx;
            sy += dy;
            sonraki = harita.zeminAl(sx, sy);
        }
        return true;
    }
    
    @Override
    public boolean kaygan() {return true; }
}
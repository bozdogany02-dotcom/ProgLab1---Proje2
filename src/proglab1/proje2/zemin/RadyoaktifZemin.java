package proglab1.proje2.zemin;

import proglab1.proje2.Harita;
import proglab1.proje2.Robot;
import proglab1.proje2.Sabitler;
import proglab1.proje2.govde.Govde;

public class RadyoaktifZemin extends Zemin {
    public RadyoaktifZemin() {
        super('R');
    }
    
    @Override
    public boolean inisUygula(Robot robot, Harita harita, int x, int y) {
        return girisUygula(robot, harita, x, y);
    }
    
    @Override
    public boolean girisUygula(Robot robot, Harita harita, int x, int y) {
        Govde govde = robot.getGovde();
        
        if (govde.ucabilir()) {
            return gir(robot, Sabitler.RADYOAKTIF_HAVALI_MALIYET, x, y);
        }
        
        if (govde.radyasyonaDayanabilir()) {
            return gir(robot, Sabitler.RADYOAKTIF_ZIRHLI_MALIYET, x, y);
        }
        
        // Diger govdeler: zemine girer, enerjisi sifirlanir, orada kalir
        robot.konumaGit(x, y);
        robot.enerjiyiSifirla();
        return false;
    }
}
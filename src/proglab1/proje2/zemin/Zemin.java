package proglab1.proje2.zemin;

import proglab1.proje2.Harita;
import proglab1.proje2.Robot;

public abstract class Zemin {

    private final char sembol;

    protected Zemin(char sembol) {
        this.sembol = sembol;
    }

    public char getSembol() { return sembol; }

    // true: robot ilerlemeye devam eder, false: komutun kalan adımları iptal olur
    public abstract boolean girisUygula(Robot robot, Harita harita, int x, int y);

    // Sıçrayan robot bu zemine inebilir mi?
    public boolean inisaUygunMu() { return true; }

    // Sıçrayan robot atlayıp bu zemine indiğinde uygulanacak kural
    public boolean inisUygula(Robot robot, Harita harita, int x, int y) {
        robot.konumaGit(x, y);
        return true;
    }

    // Kızaklı robot bu zeminde kayar mı?
    public boolean kaygan() { return false; }

    // Enerji öde, ödeyebildyse hücreye gir
    protected boolean gir(Robot robot, int maliyet, int x, int y) {
        if (!robot.enerjiHarca(maliyet)) {
            return false;
        }
        robot.konumaGit(x, y);
        return true;
    }
}
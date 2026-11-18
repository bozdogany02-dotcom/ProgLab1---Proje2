package proglab1.proje2.govde;

public class SirikliPaletli extends Govde {

    @Override
    public String getAd() {
        return "SIRIKLI_PALETLI";
    }

    @Override
    public boolean atlayabilir() {
        return true;
    }

    @Override
    public int atlamaMaliyeti() {
        return 4;
    }
}
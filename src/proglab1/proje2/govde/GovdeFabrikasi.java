package proglab1.proje2.govde;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GovdeFabrikasi {

    private static final Map<String, Supplier<Govde>> KAYITLAR = new HashMap<>();

    static {
        kaydet("PALETLI", Paletli::new);
        kaydet("SICRAYAN", Sicrayan::new);
        kaydet("HAVALI", Havali::new);
        kaydet("KIZAKLI", Kizakli::new);
        kaydet("ZIRHLI", Zirhli::new);
    }

    private GovdeFabrikasi() {
    }

    public static void kaydet(String ad, Supplier<Govde> uretici) {
        KAYITLAR.put(ad, uretici);
    }

    public static Govde olustur(String ad) {
        Supplier<Govde> uretici = KAYITLAR.get(ad);
        return (uretici != null) ? uretici.get() : null;
    }
} 
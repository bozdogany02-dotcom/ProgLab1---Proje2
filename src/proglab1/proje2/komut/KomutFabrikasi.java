package proglab1.proje2.komut;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import proglab1.proje2.GecersizSatirException;
import proglab1.proje2.Sabitler;

public class KomutFabrikasi {

    @FunctionalInterface
    public interface KomutUretici {
        Komut olustur(String[] parcalar, String dosyaAdi, int satirNo)
                throws GecersizSatirException;
    }

    private static final Map<String, KomutUretici> KAYITLAR = new HashMap<>();

    static {
        parametresizKaydet("SAG", SagKomutu::new);
        parametresizKaydet("SOL", SolKomutu::new);
        sayiliKaydet("ILERI", Sabitler.MIN_ILERI, Sabitler.MAX_ILERI, IleriKomutu::new);
    }

    private KomutFabrikasi() {
    }

    public static void kaydet(String ad, KomutUretici uretici) {
        KAYITLAR.put(ad, uretici);
    }

    public static void parametresizKaydet(String ad, Supplier<Komut> uretici) {
        kaydet(ad, (parcalar, dosyaAdi, satirNo) -> {
            if (parcalar.length != 1) {
                throw new GecersizSatirException(
                        dosyaAdi, satirNo, ad + " komutu parametre alamaz");
            }
            return uretici.get();
        });
    }

    public static void sayiliKaydet(
            String ad, int min, int max, IntFunction<Komut> uretici) {
        kaydet(ad, (parcalar, dosyaAdi, satirNo) -> {
            if (parcalar.length != 2) {
                throw new GecersizSatirException(
                        dosyaAdi, satirNo, ad + " komutu bir adim sayisi ister");
            }
            int adim;
            try {
                adim = Integer.parseInt(parcalar[1]);
            } catch (NumberFormatException e) {
                throw new GecersizSatirException(
                        dosyaAdi, satirNo, ad + " adim sayisi sayi olmalidir");
            }
            if (adim < min || adim > max) {
                throw new GecersizSatirException(
                        dosyaAdi, satirNo,
                        ad + " adim sayisi " + min + "-" + max + " arasinda olmali");
            }
            return uretici.apply(adim);
        });
    }

    public static Komut olustur(String[] parcalar, String dosyaAdi, int satirNo)
            throws GecersizSatirException {
        KomutUretici uretici = KAYITLAR.get(parcalar[0]);
        if (uretici == null) {
            throw new GecersizSatirException(
                    dosyaAdi, satirNo, "Bilinmeyen komut: " + parcalar[0]);
        }
        return uretici.olustur(parcalar, dosyaAdi, satirNo);
    }
}
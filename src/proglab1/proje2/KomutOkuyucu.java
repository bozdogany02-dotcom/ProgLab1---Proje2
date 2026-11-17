package proglab1.proje2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import proglab1.proje2.komut.Komut;
import proglab1.proje2.komut.IleriKomutu;
import proglab1.proje2.komut.SagKomutu;
import proglab1.proje2.komut.SolKomutu;

public class KomutOkuyucu {

    public static List<Komut> oku(String dosyaAdi)
            throws GecersizSatirException {

        List<Komut> komutlar = new ArrayList<>();
        int satirNo = 0;

        try (BufferedReader okuyucu =
                new BufferedReader(new FileReader(dosyaAdi))) {

            String satir;

            while ((satir = okuyucu.readLine()) != null) {
                satirNo++;

                // # işaretinden sonraki açıklamaları kaldır
                int yorum = satir.indexOf('#');

                if (yorum >= 0) {
                    satir = satir.substring(0, yorum);
                }

                satir = satir.trim();

                if (satir.isEmpty()) {
                    continue;
                }

                String[] parcalar = satir.split("\\s+");

                switch (parcalar[0]) {

                    case "SAG":
                        if (parcalar.length != 1) {
                            throw new GecersizSatirException(
                                    dosyaAdi, satirNo,
                                    "SAG komutu parametre alamaz");
                        }

                        komutlar.add(new SagKomutu());
                        break;

                    case "SOL":
                        if (parcalar.length != 1) {
                            throw new GecersizSatirException(
                                    dosyaAdi, satirNo,
                                    "SOL komutu parametre alamaz");
                        }

                        komutlar.add(new SolKomutu());
                        break;

                    case "ILERI":
                        if (parcalar.length != 2) {
                            throw new GecersizSatirException(
                                    dosyaAdi, satirNo,
                                    "ILERI komutu bir adim sayisi ister");
                        }

                        int adim;

                        try {
                            adim = Integer.parseInt(parcalar[1]);
                        } catch (NumberFormatException e) {
                            throw new GecersizSatirException(
                                    dosyaAdi, satirNo,
                                    "ILERI adim sayisi sayi olmalidir");
                        }

                        if (adim < Sabitler.MIN_ILERI
                                || adim > Sabitler.MAX_ILERI) {
                            throw new GecersizSatirException(
                                    dosyaAdi, satirNo,
                                    "ILERI adim sayisi 1-99 arasinda olmali");
                        }

                        komutlar.add(new IleriKomutu(adim));
                        break;

                    default:
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo,
                                "Bilinmeyen komut: " + parcalar[0]);
                }
            }

        } catch (IOException e) {
            throw new GecersizSatirException(
                    dosyaAdi, satirNo,
                    "Dosya okunamadi: " + e.getMessage());
        }

        return komutlar;
    }
}

package proglab1.proje2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import proglab1.proje2.komut.Komut;
import proglab1.proje2.komut.KomutFabrikasi;

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

                int yorum = satir.indexOf('#');
                if (yorum >= 0) {
                    satir = satir.substring(0, yorum);
                }

                satir = satir.trim();
                if (satir.isEmpty()) {
                    continue;
                }

                String[] parcalar = satir.split("\\s+");
                komutlar.add(KomutFabrikasi.olustur(parcalar, dosyaAdi, satirNo));
            }

        } catch (IOException e) {
            throw new GecersizSatirException(
                    dosyaAdi, satirNo,
                    "Dosya okunamadi: " + e.getMessage());
        }

        return komutlar;
    }
}
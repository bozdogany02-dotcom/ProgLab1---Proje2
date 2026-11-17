package proglab1.proje2;

import java.util.List;
import proglab1.proje2.komut.Komut;

public class ProgLab1Proje2 {

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println(
                "Kullanim: java ProgLab1Proje2 harita.txt komutlar.txt"
            );
            return;
        }

        try {
            // Haritayı ve robotları oku
            Harita harita = DosyaOkuyucu.oku(args[0]);

            // Komutları oku
            List<Komut> komutlar = KomutOkuyucu.oku(args[1]);

            // Simülasyonu oluştur
            Simulasyon simulasyon = new Simulasyon(
                harita,
                harita.getRobotlar(),
                komutlar
            );

            // Simülasyonu çalıştır
            simulasyon.baslat();

            // Sonuçları yazdır
            simulasyon.sonucuYazdir();

        } catch (GecersizSatirException e) {
            System.out.println("HATA: " + e.getMessage());
        }
    }
}
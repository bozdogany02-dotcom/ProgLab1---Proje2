package proglab1.proje2;

import java.util.List;
import proglab1.proje2.komut.Komut;

public class ProgLab1Proje2 {

    public static void main(String[] args) {

        try {
            String haritaDosyasi = "harita.txt";
            String komutDosyasi = "komutlar.txt";

            Harita harita = DosyaOkuyucu.oku(haritaDosyasi);

            List<Komut> komutlar =
                    KomutOkuyucu.oku(komutDosyasi);

            Simulasyon simulasyon = new Simulasyon(
                    harita,
                    harita.getRobotlar(),
                    komutlar
            );

            simulasyon.baslat();
            simulasyon.sonucuYazdir();

        } catch (GecersizSatirException e) {
            System.out.println("DOSYA HATASI: " + e.getMessage());
        }
    }
}

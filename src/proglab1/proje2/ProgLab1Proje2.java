package proglab1.proje2;

import java.awt.GraphicsEnvironment;
import java.util.List;
import javax.swing.SwingUtilities;
import proglab1.proje2.komut.Komut;
import proglab1.proje2.ui.SimulasyonArayuzu;

public class ProgLab1Proje2 {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println(
                "Kullanim: java ProgLab1Proje2 harita.txt komutlar.txt"
            );
            return;
        }

        boolean guiAc = true;
        for (int i = 2; i < args.length; i++) {
            if ("--nogui".equalsIgnoreCase(args[i])) {
                guiAc = false;
            }
        }

        try {
            Harita harita = DosyaOkuyucu.oku(args[0]);
            List<Komut> komutlar = KomutOkuyucu.oku(args[1]);

            Simulasyon simulasyon = new Simulasyon(
                harita,
                harita.getRobotlar(),
                komutlar
            );

            // Zorunlu konsol ciktisi
            simulasyon.baslat();
            simulasyon.sonucuYazdir();

            // Gorsel arayuz
            if (guiAc && !GraphicsEnvironment.isHeadless()) {
                simulasyon.basaDon();
                SwingUtilities.invokeLater(() -> {
                    new SimulasyonArayuzu(simulasyon).setVisible(true);
                });
            }

        } catch (GecersizSatirException e) {
            System.out.println("HATA: " + e.getMessage());
        }
    }
}
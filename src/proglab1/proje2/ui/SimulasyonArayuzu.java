package proglab1.proje2.ui;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import proglab1.proje2.*;
import proglab1.proje2.Robot;
import proglab1.proje2.komut.Komut;
import proglab1.proje2.zemin.Zemin;

public class SimulasyonArayuzu extends JFrame {

    private static final Color ARKA_PLAN = new Color(16, 22, 34);
    private static final Color PANEL_ARKA = new Color(24, 32, 48);
    private static final Color KART_ARKA = new Color(30, 40, 58);
    private static final Color KENARLIK = new Color(48, 62, 86);
    private static final Color METIN_ANA = new Color(236, 242, 250);
    private static final Color METIN_SOLUK = new Color(148, 163, 184);
    private static final Color VURGU_TURUNCU = new Color(245, 158, 11);
    private static final Color YESIL_TESLIM = new Color(52, 211, 153);
    private static final Color KIRMIZI_ENERJI = new Color(248, 113, 113);

    private static final Color[] ROBOT_RENKLERI = {
        new Color(239, 108, 77),
        new Color(168, 85, 247),
        new Color(52, 211, 153),
        new Color(56, 189, 248),
        new Color(250, 204, 21),
        new Color(244, 114, 182),
        new Color(129, 140, 248),
        new Color(45, 212, 191),
        new Color(251, 146, 60)
    };

    private final Simulasyon simulasyon;
    private final HaritaPaneli haritaPaneli;
    private final JButton btnBasa;
    private final JButton btnGeri;
    private final JButton btnOynat;
    private final JButton btnIleri;
    private final JButton btnSonrakiKomut;
    private final JButton btnSona;
    private final JSlider hizSlider;
    private final Timer oynatmaZamanlayici;

    private final JLabel lblKareBilgi;
    private final JLabel lblTeslimSayi;
    private final JLabel lblEnerjiSayi;
    private final JPanel komutPillPaneli;
    private final JLabel lblAdimAciklama;
    private final JPanel robotKartlariPaneli;

    public SimulasyonArayuzu(Simulasyon simulasyon) {
        this.simulasyon = simulasyon;
        this.simulasyon.basaDon();

        setTitle("Gezegen Postacilari");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(ARKA_PLAN);

        JPanel anaKapsayici = new JPanel(new BorderLayout(16, 12));
        anaKapsayici.setBackground(ARKA_PLAN);
        anaKapsayici.setBorder(new EmptyBorder(16, 18, 16, 18));

        // Ust Baslik
        JPanel ustBaslik = new JPanel();
        ustBaslik.setLayout(new BoxLayout(ustBaslik, BoxLayout.Y_AXIS));
        ustBaslik.setBackground(ARKA_PLAN);

        JLabel lblBaslik = new JLabel("Gezegen Postacilari");
        lblBaslik.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblBaslik.setForeground(METIN_ANA);

        JLabel lblAltBaslik = new JLabel(
                "Ayni komut listesi, farkli robot govdeleri. Amac: paketleri teslim rampasina ulastirmak.");
        lblAltBaslik.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblAltBaslik.setForeground(METIN_SOLUK);

        ustBaslik.add(lblBaslik);
        ustBaslik.add(Box.createVerticalStrut(4));
        ustBaslik.add(lblAltBaslik);
        anaKapsayici.add(ustBaslik, BorderLayout.NORTH);

        // Sol Bolum: Harita + Kontroller + Durum
        JPanel solBolum = new JPanel(new BorderLayout(0, 12));
        solBolum.setBackground(ARKA_PLAN);

        haritaPaneli = new HaritaPaneli();
        JPanel haritaCerceve = new JPanel(new BorderLayout());
        haritaCerceve.setBackground(PANEL_ARKA);
        haritaCerceve.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(KENARLIK, 1, true),
                new EmptyBorder(8, 8, 8, 8)));
        haritaCerceve.add(haritaPaneli, BorderLayout.CENTER);
        solBolum.add(haritaCerceve, BorderLayout.CENTER);

        // Alt Kontrol + Sayac + Komut Listesi
        JPanel solAlt = new JPanel();
        solAlt.setLayout(new BoxLayout(solAlt, BoxLayout.Y_AXIS));
        solAlt.setBackground(ARKA_PLAN);

        JPanel butonSatiri = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        butonSatiri.setBackground(ARKA_PLAN);
        butonSatiri.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnBasa = stilButon("Basa", false);
        btnGeri = stilButon("Geri", false);
        btnOynat = stilButon("Oynat", true);
        btnIleri = stilButon("Ileri", false);
        btnSonrakiKomut = stilButon("Sonraki komut", false);
        btnSona = stilButon("Sona", false);

        JLabel lblHiz = new JLabel("Hiz");
        lblHiz.setForeground(METIN_SOLUK);
        lblHiz.setFont(new Font("SansSerif", Font.PLAIN, 12));

        hizSlider = new JSlider(1, 10, 5);
        hizSlider.setPreferredSize(new Dimension(100, 24));
        hizSlider.setBackground(ARKA_PLAN);
        hizSlider.setForeground(VURGU_TURUNCU);

        butonSatiri.add(btnBasa);
        butonSatiri.add(btnGeri);
        butonSatiri.add(btnOynat);
        butonSatiri.add(btnIleri);
        butonSatiri.add(btnSonrakiKomut);
        butonSatiri.add(btnSona);
        butonSatiri.add(Box.createHorizontalStrut(8));
        butonSatiri.add(lblHiz);
        butonSatiri.add(hizSlider);

        lblKareBilgi = new JLabel("Kare 1 / 1 (komut 0 / 0)");
        lblKareBilgi.setForeground(METIN_SOLUK);
        lblKareBilgi.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblKareBilgi.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel altBilgiSatiri = new JPanel(new BorderLayout(16, 0));
        altBilgiSatiri.setBackground(ARKA_PLAN);
        altBilgiSatiri.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Sol Sayaclar
        JPanel sayacPaneli = new JPanel(new GridLayout(1, 2, 18, 0));
        sayacPaneli.setBackground(ARKA_PLAN);

        JPanel teslimKutu = new JPanel();
        teslimKutu.setLayout(new BoxLayout(teslimKutu, BoxLayout.Y_AXIS));
        teslimKutu.setBackground(ARKA_PLAN);
        lblTeslimSayi = new JLabel("0 / 4");
        lblTeslimSayi.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTeslimSayi.setForeground(YESIL_TESLIM);
        JLabel lblTeslimAlt = new JLabel("teslim edilen paket");
        lblTeslimAlt.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblTeslimAlt.setForeground(METIN_SOLUK);
        teslimKutu.add(lblTeslimSayi);
        teslimKutu.add(lblTeslimAlt);

        JPanel enerjiKutu = new JPanel();
        enerjiKutu.setLayout(new BoxLayout(enerjiKutu, BoxLayout.Y_AXIS));
        enerjiKutu.setBackground(ARKA_PLAN);
        lblEnerjiSayi = new JLabel("0");
        lblEnerjiSayi.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblEnerjiSayi.setForeground(METIN_ANA);
        JLabel lblEnerjiAlt = new JLabel("kalan enerji toplami");
        lblEnerjiAlt.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblEnerjiAlt.setForeground(METIN_SOLUK);
        enerjiKutu.add(lblEnerjiSayi);
        enerjiKutu.add(lblEnerjiAlt);

        sayacPaneli.add(teslimKutu);
        sayacPaneli.add(enerjiKutu);
        altBilgiSatiri.add(sayacPaneli, BorderLayout.WEST);

        // Sag Komut Listesi + Aciklama
        JPanel komutBolumu = new JPanel();
        komutBolumu.setLayout(new BoxLayout(komutBolumu, BoxLayout.Y_AXIS));
        komutBolumu.setBackground(ARKA_PLAN);

        JLabel lblKomutBaslik = new JLabel("Komut listesi");
        lblKomutBaslik.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblKomutBaslik.setForeground(METIN_SOLUK);
        lblKomutBaslik.setAlignmentX(Component.LEFT_ALIGNMENT);

        komutPillPaneli = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        komutPillPaneli.setBackground(ARKA_PLAN);
        komutPillPaneli.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblAdimAciklama = new JLabel(" ");
        lblAdimAciklama.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblAdimAciklama.setForeground(METIN_SOLUK);
        lblAdimAciklama.setAlignmentX(Component.LEFT_ALIGNMENT);

        komutBolumu.add(lblKomutBaslik);
        komutBolumu.add(komutPillPaneli);
        komutBolumu.add(Box.createVerticalStrut(4));
        komutBolumu.add(lblAdimAciklama);
        altBilgiSatiri.add(komutBolumu, BorderLayout.CENTER);

        solAlt.add(butonSatiri);
        solAlt.add(Box.createVerticalStrut(8));
        solAlt.add(lblKareBilgi);
        solAlt.add(Box.createVerticalStrut(6));
        solAlt.add(altBilgiSatiri);
        solBolum.add(solAlt, BorderLayout.SOUTH);

        anaKapsayici.add(solBolum, BorderLayout.CENTER);

        // Sag Panel: Zemin Rehberi + Robotlar
        JPanel sagPanel = new JPanel();
        sagPanel.setLayout(new BoxLayout(sagPanel, BoxLayout.Y_AXIS));
        sagPanel.setBackground(PANEL_ARKA);
        sagPanel.setPreferredSize(new Dimension(360, 620));
        sagPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(KENARLIK, 1, true),
                new EmptyBorder(14, 16, 14, 16)));

        JLabel lblRehber = new JLabel("Zemin rehberi");
        lblRehber.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblRehber.setForeground(METIN_ANA);
        lblRehber.setAlignmentX(Component.LEFT_ALIGNMENT);
        sagPanel.add(lblRehber);
        sagPanel.add(Box.createVerticalStrut(8));

        sagPanel.add(rehberSatiri('.', "Duz zemin", "Her govde icin 1 enerji."));
        sagPanel.add(rehberSatiri('T', "Toz firtinasi", "2 enerji. Havali 1 enerjiyle girer ama savrulur."));
        sagPanel.add(rehberSatiri('K', "Kraterli kaya", "Paletli/kizakli 3, havali 1. Sicrayan atlar (2 enerji)."));
        sagPanel.add(rehberSatiri('~', "Metan golu", "Yalnizca havali gecer (1 enerji). Digerleri durur."));
        sagPanel.add(rehberSatiri('I', "Buz kabugu", "Kizakli 1 enerjiyle kayar. Havali 1, digerleri 2 enerji."));
        sagPanel.add(rehberSatiri('X', "Engel", "Hicbir robot gecemez."));
        sagPanel.add(rehberSatiri('R', "Radyoaktif zemin", "Zirhli 3, havali 1 enerji. Digerlerinin enerjisi sifirlanir."));
        sagPanel.add(rehberSatiri('H', "Teslim rampasi", "1 enerjiyle paket teslim edilir ve robot kalkar."));

        sagPanel.add(Box.createVerticalStrut(12));
        JLabel lblRobotlar = new JLabel("Robotlar");
        lblRobotlar.setFont(new Font("SansSerif", Font.BOLD, 15));
        lblRobotlar.setForeground(METIN_ANA);
        lblRobotlar.setAlignmentX(Component.LEFT_ALIGNMENT);
        sagPanel.add(lblRobotlar);
        sagPanel.add(Box.createVerticalStrut(8));

        robotKartlariPaneli = new JPanel();
        robotKartlariPaneli.setLayout(new BoxLayout(robotKartlariPaneli, BoxLayout.Y_AXIS));
        robotKartlariPaneli.setBackground(PANEL_ARKA);
        robotKartlariPaneli.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane robotScroll = new JScrollPane(robotKartlariPaneli);
        robotScroll.setBorder(null);
        robotScroll.setBackground(PANEL_ARKA);
        robotScroll.getViewport().setBackground(PANEL_ARKA);
        robotScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        sagPanel.add(robotScroll);

        anaKapsayici.add(sagPanel, BorderLayout.EAST);
        setContentPane(anaKapsayici);

        // Oynatma Zamanlayicisi
        oynatmaZamanlayici = new Timer(450, e -> {
            if (!simulasyon.tekAdimIlerle()) {
                oynatmayiDurdur();
            }
            ekraniGuncelle();
        });

        hizSlider.addChangeListener(e -> {
            int gecikme = 950 - (hizSlider.getValue() * 85);
            oynatmaZamanlayici.setDelay(Math.max(80, gecikme));
        });

        btnBasa.addActionListener(e -> {
            oynatmayiDurdur();
            simulasyon.basaDon();
            ekraniGuncelle();
        });

        btnGeri.addActionListener(e -> {
            oynatmayiDurdur();
            simulasyon.geriGit();
            ekraniGuncelle();
        });

        btnOynat.addActionListener(e -> {
            if (oynatmaZamanlayici.isRunning()) {
                oynatmayiDurdur();
            } else {
                if (simulasyon.getAktifKareIndeks() >= simulasyon.getToplamKareSayisi() - 1) {
                    simulasyon.basaDon();
                }
                oynatmaZamanlayici.start();
                btnOynat.setText("Duraklat");
            }
        });

        btnIleri.addActionListener(e -> {
            oynatmayiDurdur();
            simulasyon.tekAdimIlerle();
            ekraniGuncelle();
        });

        btnSonrakiKomut.addActionListener(e -> {
            oynatmayiDurdur();
            simulasyon.tekKomutUygula();
            ekraniGuncelle();
        });

        btnSona.addActionListener(e -> {
            oynatmayiDurdur();
            simulasyon.sonaGit();
            ekraniGuncelle();
        });

        ekraniGuncelle();
        pack();
        setMinimumSize(new Dimension(1120, 680));
        setLocationRelativeTo(null);
    }

    private void oynatmayiDurdur() {
        if (oynatmaZamanlayici.isRunning()) {
            oynatmaZamanlayici.stop();
        }
        btnOynat.setText("Oynat");
    }

    private JButton stilButon(String yazi, boolean vurgulu) {
        JButton btn = new JButton(yazi);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (vurgulu) {
            btn.setBackground(VURGU_TURUNCU);
            btn.setForeground(new Color(20, 24, 34));
        } else {
            btn.setBackground(KART_ARKA);
            btn.setForeground(METIN_ANA);
        }
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(KENARLIK, 1, true),
                new EmptyBorder(6, 12, 6, 12)));
        return btn;
    }

    private JPanel rehberSatiri(char sembol, String ad, String aciklama) {
        JPanel satir = new JPanel(new BorderLayout(10, 0));
        satir.setBackground(PANEL_ARKA);
        satir.setMaximumSize(new Dimension(340, 38));
        satir.setAlignmentX(Component.LEFT_ALIGNMENT);
        satir.setBorder(new EmptyBorder(2, 0, 2, 0));

        JPanel ikon = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                zeminCiz(g2, sembol, 0, 0, getWidth(), getHeight());
            }
        };
        ikon.setPreferredSize(new Dimension(28, 28));
        ikon.setBackground(PANEL_ARKA);

        JPanel metinler = new JPanel();
        metinler.setLayout(new BoxLayout(metinler, BoxLayout.Y_AXIS));
        metinler.setBackground(PANEL_ARKA);

        JLabel lblAd = new JLabel(ad);
        lblAd.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblAd.setForeground(METIN_ANA);

        JLabel lblAcik = new JLabel("<html><body style='width:240px'>" + aciklama + "</body></html>");
        lblAcik.setFont(new Font("SansSerif", Font.PLAIN, 10));
        lblAcik.setForeground(METIN_SOLUK);

        metinler.add(lblAd);
        metinler.add(lblAcik);

        satir.add(ikon, BorderLayout.WEST);
        satir.add(metinler, BorderLayout.CENTER);
        return satir;
    }

    private void ekraniGuncelle() {
        Simulasyon.KareKaydi kare = simulasyon.getAktifKare();
        int aktifKare = simulasyon.getAktifKareIndeks() + 1;
        int toplamKare = simulasyon.getToplamKareSayisi();
        int toplamKomut = simulasyon.getKomutlar().size();

        lblKareBilgi.setText("Kare " + aktifKare + " / " + toplamKare
                + " (komut " + kare.getKomutNo() + " / " + toplamKomut + ")");

        List<Robot> robotlar = simulasyon.getRobotlar();
        lblTeslimSayi.setText(simulasyon.getTeslimSayisi() + " / " + robotlar.size());
        lblEnerjiSayi.setText(String.valueOf(simulasyon.getKalanEnerjiToplami()));
        lblAdimAciklama.setText(kare.getAciklama());

        // Komut listesi pill guncelleme
        komutPillPaneli.removeAll();
        List<Komut> komutlar = simulasyon.getKomutlar();
        for (int i = 0; i < komutlar.size(); i++) {
            boolean secili = (kare.getKomutNo() == i + 1);
            JLabel pill = new JLabel((i + 1) + " " + komutlar.get(i).etiket());
            pill.setFont(new Font("SansSerif", Font.BOLD, 11));
            pill.setOpaque(true);
            pill.setBorder(new EmptyBorder(4, 10, 4, 10));
            if (secili) {
                pill.setBackground(VURGU_TURUNCU);
                pill.setForeground(new Color(20, 24, 34));
            } else {
                pill.setBackground(KART_ARKA);
                pill.setForeground(METIN_SOLUK);
            }
            komutPillPaneli.add(pill);
        }
        komutPillPaneli.revalidate();
        komutPillPaneli.repaint();

        // Robot kartlari guncelleme
        robotKartlariPaneli.removeAll();
        int maxEnerji = simulasyon.getBaslangicEnerjisi();
        for (int i = 0; i < robotlar.size(); i++) {
            Robot r = robotlar.get(i);
            Color renk = ROBOT_RENKLERI[(r.getNo() - 1) % ROBOT_RENKLERI.length];
            robotKartlariPaneli.add(robotKartiOlustur(r, renk, maxEnerji));
            robotKartlariPaneli.add(Box.createVerticalStrut(6));
        }
        robotKartlariPaneli.revalidate();
        robotKartlariPaneli.repaint();

        btnBasa.setEnabled(simulasyon.getAktifKareIndeks() > 0);
        btnGeri.setEnabled(simulasyon.getAktifKareIndeks() > 0);
        btnIleri.setEnabled(simulasyon.getAktifKareIndeks() < toplamKare - 1);
        btnSonrakiKomut.setEnabled(simulasyon.getAktifKareIndeks() < toplamKare - 1);
        btnSona.setEnabled(simulasyon.getAktifKareIndeks() < toplamKare - 1);

        haritaPaneli.repaint();
    }

    private JPanel robotKartiOlustur(Robot r, Color renk, int maxEnerji) {
        JPanel kart = new JPanel(new BorderLayout(8, 4));
        kart.setBackground(KART_ARKA);
        kart.setMaximumSize(new Dimension(340, 54));
        kart.setAlignmentX(Component.LEFT_ALIGNMENT);
        kart.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(KENARLIK, 1, true),
                new EmptyBorder(6, 10, 6, 10)));

        JPanel ust = new JPanel(new BorderLayout());
        ust.setBackground(KART_ARKA);

        JLabel lblAd = new JLabel("R" + r.getNo() + " " + r.getGovde().getAd());
        lblAd.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblAd.setForeground(renk);

        JLabel lblYetenek = new JLabel(r.getGovde().getYetenekAciklamasi());
        lblYetenek.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblYetenek.setForeground(METIN_SOLUK);

        ust.add(lblAd, BorderLayout.WEST);
        ust.add(lblYetenek, BorderLayout.EAST);

        JPanel alt = new JPanel(new BorderLayout(8, 0));
        alt.setBackground(KART_ARKA);

        JProgressBar bar = new JProgressBar(0, Math.max(1, maxEnerji));
        bar.setValue(Math.max(0, Math.min(r.getEnerji(), maxEnerji)));
        bar.setForeground(renk);
        bar.setBackground(ARKA_PLAN);
        bar.setBorderPainted(false);
        bar.setPreferredSize(new Dimension(110, 8));

        String durumMetni;
        Color durumRenk;
        if (r.getDurum() == Durum.TESLIM) {
            durumMetni = "paketi teslim etti";
            durumRenk = YESIL_TESLIM;
        } else if (r.getDurum() == Durum.ENERJISIZ) {
            durumMetni = "enerjisi bitti";
            durumRenk = KIRMIZI_ENERJI;
        } else {
            durumMetni = "yolda (" + r.getX() + "," + r.getY() + ")";
            durumRenk = METIN_SOLUK;
        }

        JLabel lblSag = new JLabel("enerji " + r.getEnerji() + "  " + durumMetni);
        lblSag.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblSag.setForeground(durumRenk);

        alt.add(bar, BorderLayout.WEST);
        alt.add(lblSag, BorderLayout.EAST);

        kart.add(ust, BorderLayout.NORTH);
        kart.add(alt, BorderLayout.SOUTH);
        return kart;
    }

    private static void zeminCiz(Graphics2D g2, char sembol, int px, int py, int w, int h) {
        switch (sembol) {
            case 'T':
                g2.setColor(new Color(140, 98, 57));
                g2.fillRect(px, py, w, h);
                g2.setColor(new Color(92, 62, 34));
                g2.fillOval(px + w / 4, py + h / 4, 4, 4);
                g2.fillOval(px + (2 * w) / 3, py + (2 * h) / 3, 4, 4);
                break;
            case 'K':
                g2.setColor(new Color(45, 55, 72));
                g2.fillRect(px, py, w, h);
                g2.setColor(new Color(113, 128, 150));
                g2.fillOval(px + 4, py + 4, w - 8, h - 8);
                g2.setColor(new Color(45, 55, 72));
                g2.fillOval(px + w / 3, py + h / 3, w / 3, h / 3);
                break;
            case '~':
                g2.setColor(new Color(20, 83, 105));
                g2.fillRect(px, py, w, h);
                g2.setColor(new Color(56, 189, 248));
                g2.drawArc(px + 4, py + h / 3, w / 2, 6, 0, 180);
                g2.drawArc(px + w / 2 - 2, py + (2 * h) / 3 - 4, w / 2 - 2, 6, 180, 180);
                break;
            case 'I':
                g2.setColor(new Color(196, 228, 245));
                g2.fillRect(px, py, w, h);
                g2.setColor(Color.WHITE);
                g2.drawLine(px + 5, py + h - 5, px + w - 5, py + 5);
                break;
            case 'X':
                g2.setColor(new Color(28, 36, 52));
                g2.fillRect(px, py, w, h);
                g2.setColor(new Color(71, 85, 105));
                int[] xs = {px + w / 2, px + w - 5, px + w - 7, px + 7, px + 5};
                int[] ys = {py + 4, py + h / 2, py + h - 5, py + h - 5, py + h / 2};
                g2.fillPolygon(xs, ys, 5);
                break;
            case 'H':
                g2.setColor(new Color(161, 118, 28));
                g2.fillRect(px, py, w, h);
                g2.setColor(new Color(38, 34, 24));
                for (int d = -h; d < w; d += 10) {
                    g2.drawLine(px + d, py + h, px + d + h, py);
                }
                break;
            case 'R':
                g2.setColor(new Color(54, 83, 20));
                g2.fillRect(px, py, w, h);
                g2.setColor(new Color(163, 230, 53));
                g2.drawOval(px + 5, py + 5, w - 10, h - 10);
                g2.fillOval(px + w / 2 - 3, py + h / 2 - 3, 6, 6);
                break;
            default:
                g2.setColor(new Color(30, 38, 56));
                g2.fillRect(px, py, w, h);
                break;
        }
    }

    private class HaritaPaneli extends JPanel {

        HaritaPaneli() {
            setBackground(PANEL_ARKA);
            setPreferredSize(new Dimension(680, 440));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Harita harita = simulasyon.getHarita();
            int gen = harita.getGenislik();
            int yuk = harita.getYukseklik();

            int kenarBosluk = 28;
            int hucreBoyut = Math.max(24, Math.min(
                    (getWidth() - kenarBosluk - 10) / gen,
                    (getHeight() - kenarBosluk - 10) / yuk));

            int basX = kenarBosluk + (getWidth() - kenarBosluk - gen * hucreBoyut) / 2;
            int basY = kenarBosluk + (getHeight() - kenarBosluk - yuk * hucreBoyut) / 2;

            // Koordinat etiketleri
            g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            g2.setColor(METIN_SOLUK);
            for (int x = 0; x < gen; x++) {
                String s = String.valueOf(x);
                int sw = g2.getFontMetrics().stringWidth(s);
                g2.drawString(s, basX + x * hucreBoyut + (hucreBoyut - sw) / 2, basY - 8);
            }
            for (int y = 0; y < yuk; y++) {
                String s = String.valueOf(y);
                int sw = g2.getFontMetrics().stringWidth(s);
                g2.drawString(s, basX - sw - 8, basY + y * hucreBoyut + hucreBoyut / 2 + 4);
            }

            // Zemin hucreleri
            for (int y = 0; y < yuk; y++) {
                for (int x = 0; x < gen; x++) {
                    int px = basX + x * hucreBoyut;
                    int py = basY + y * hucreBoyut;
                    Zemin z = harita.zeminAl(x, y);
                    zeminCiz(g2, z.getSembol(), px, py, hucreBoyut, hucreBoyut);
                    g2.setColor(new Color(45, 58, 82));
                    g2.drawRect(px, py, hucreBoyut, hucreBoyut);
                }
            }

            // Robot izleri ve baslangic halkalari
            List<Robot> robotlar = simulasyon.getRobotlar();
            List<List<int[]>> izler = simulasyon.getAktifKare().getRobotIzleri();

            for (int i = 0; i < robotlar.size(); i++) {
                Robot r = robotlar.get(i);
                Color renk = ROBOT_RENKLERI[(r.getNo() - 1) % ROBOT_RENKLERI.length];
                List<int[]> yol = izler.get(i);

                if (!yol.isEmpty()) {
                    int[] ilk = yol.get(0);
                    int cx = basX + ilk[0] * hucreBoyut + hucreBoyut / 2;
                    int cy = basY + ilk[1] * hucreBoyut + hucreBoyut / 2;
                    Stroke eski = g2.getStroke();
                    g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER, 10f, new float[]{4f, 4f}, 0f));
                    g2.setColor(new Color(148, 163, 184, 150));
                    int cap = (int) (hucreBoyut * 0.65);
                    g2.drawOval(cx - cap / 2, cy - cap / 2, cap, cap);
                    g2.setStroke(eski);
                }

                if (yol.size() > 1) {
                    Stroke eski = g2.getStroke();
                    g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g2.setColor(new Color(renk.getRed(), renk.getGreen(), renk.getBlue(), 190));
                    for (int j = 0; j < yol.size() - 1; j++) {
                        int x1 = basX + yol.get(j)[0] * hucreBoyut + hucreBoyut / 2;
                        int y1 = basY + yol.get(j)[1] * hucreBoyut + hucreBoyut / 2;
                        int x2 = basX + yol.get(j + 1)[0] * hucreBoyut + hucreBoyut / 2;
                        int y2 = basY + yol.get(j + 1)[1] * hucreBoyut + hucreBoyut / 2;
                        g2.drawLine(x1, y1, x2, y2);
                    }
                    g2.setStroke(eski);
                }
            }

            // Robotlar
            for (Robot r : robotlar) {
                Color renk = ROBOT_RENKLERI[(r.getNo() - 1) % ROBOT_RENKLERI.length];
                int cx = basX + r.getX() * hucreBoyut + hucreBoyut / 2;
                int cy = basY + r.getY() * hucreBoyut + hucreBoyut / 2;
                int yaricap = (int) (hucreBoyut * 0.28);

                g2.setColor(renk);
                g2.fillOval(cx - yaricap, cy - yaricap, yaricap * 2, yaricap * 2);
                g2.setColor(Color.WHITE);
                g2.drawOval(cx - yaricap, cy - yaricap, yaricap * 2, yaricap * 2);

                // Yon oku
                yonOkuCiz(g2, cx, cy, yaricap + 5, r.getYon());

                // Robot numarasi rozeti
                g2.setColor(ARKA_PLAN);
                g2.fillOval(cx - yaricap - 5, cy - yaricap - 5, 16, 16);
                g2.setColor(renk);
                g2.drawOval(cx - yaricap - 5, cy - yaricap - 5, 16, 16);
                g2.setFont(new Font("SansSerif", Font.BOLD, 10));
                g2.drawString(String.valueOf(r.getNo()), cx - yaricap, cy - yaricap + 7);

                // Teslim rozeti
                if (r.getDurum() == Durum.TESLIM) {
                    g2.setColor(YESIL_TESLIM);
                    g2.fillOval(cx + yaricap - 6, cy - yaricap - 6, 14, 14);
                    g2.setColor(ARKA_PLAN);
                    g2.drawString("+", cx + yaricap - 2, cy - yaricap + 5);
                }
            }
        }

        private void yonOkuCiz(Graphics2D g2, int cx, int cy, int mesafe, Yon yon) {
            int ox = cx + yon.getDx() * mesafe;
            int oy = cy + yon.getDy() * mesafe;
            Path2D ucgen = new Path2D.Double();
            int boyut = 5;
            if (yon == Yon.E) {
                ucgen.moveTo(ox + boyut, oy);
                ucgen.lineTo(ox - boyut, oy - boyut);
                ucgen.lineTo(ox - boyut, oy + boyut);
            } else if (yon == Yon.W) {
                ucgen.moveTo(ox - boyut, oy);
                ucgen.lineTo(ox + boyut, oy - boyut);
                ucgen.lineTo(ox + boyut, oy + boyut);
            } else if (yon == Yon.S) {
                ucgen.moveTo(ox, oy + boyut);
                ucgen.lineTo(ox - boyut, oy - boyut);
                ucgen.lineTo(ox + boyut, oy - boyut);
            } else {
                ucgen.moveTo(ox, oy - boyut);
                ucgen.lineTo(ox - boyut, oy + boyut);
                ucgen.lineTo(ox + boyut, oy + boyut);
            }
            ucgen.closePath();
            g2.setColor(Color.WHITE);
            g2.fill(ucgen);
        }
    }
}
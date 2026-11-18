package proglab1.proje2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import proglab1.proje2.govde.Govde;
import proglab1.proje2.govde.GovdeFabrikasi;
import proglab1.proje2.zemin.Duz;
import proglab1.proje2.zemin.Zemin;
import proglab1.proje2.zemin.ZeminFabrikasi;

public class DosyaOkuyucu {

    public static Harita oku(String dosyaAdi)
            throws GecersizSatirException {

        int satirNo = 0;

        try (BufferedReader okuyucu
                = new BufferedReader(new FileReader(dosyaAdi))) {

            String satir;
            int genislik = 0;
            int yukseklik = 0;
            int enerji = 0;

            boolean boyutOkundu = false;
            boolean enerjiOkundu = false;
            boolean haritaBasladi = false;

            List<Robot> robotlar = new ArrayList<>();
            List<Integer> robotNolari = new ArrayList<>();
            List<Govde> govdeler = new ArrayList<>();

            List<String> haritaSatirlari = new ArrayList<>();
            List<Integer> haritaSatirNolari = new ArrayList<>();
            List<Integer> robotSatirNolari = new ArrayList<>();
            int haritaBaslikSatiri = 0;

            while ((satir = okuyucu.readLine()) != null) {
                satirNo++;
                satir = satir.trim();

                if (satir.isEmpty() || satir.startsWith("#")) {
                    continue;
                }

                if (haritaBasladi) {
                    haritaSatirlari.add(satir);
                    haritaSatirNolari.add(satirNo);
                    continue;
                }

                String[] parcalar = satir.split("\\s+");

                if (parcalar[0].equals("BOYUT")) {
                    if (parcalar.length != 3 || boyutOkundu) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo, "BOYUT satiri hatali");
                    }

                    try {
                        genislik = Integer.parseInt(parcalar[1]);
                        yukseklik = Integer.parseInt(parcalar[2]);
                    } catch (NumberFormatException e) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo, "Boyut sayilari gecersiz");
                    }

                    if (genislik <= 0 || yukseklik <= 0) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo, "Boyutlar pozitif olmali");
                    }

                    boyutOkundu = true;

                } else if (parcalar[0].equals("ENERJI")) {
                    if (parcalar.length != 2 || enerjiOkundu) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo, "ENERJI satiri hatali");
                    }

                    try {
                        enerji = Integer.parseInt(parcalar[1]);
                    } catch (NumberFormatException e) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo, "Enerji degeri gecersiz");
                    }

                    if (enerji < 0) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo, "Enerji negatif olamaz");
                    }

                    enerjiOkundu = true;

                } else if (parcalar[0].equals("ROBOT")) {
                    if (parcalar.length != 3) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo, "ROBOT satiri hatali");
                    }

                    int no;
                    try {
                        no = Integer.parseInt(parcalar[1]);
                    } catch (NumberFormatException e) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo, "Robot numarasi gecersiz");
                    }

                    if (no < Sabitler.MIN_ROBOT_NO
                            || no > Sabitler.MAX_ROBOT_NO) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo,
                                "Robot numarasi "
                                + Sabitler.MIN_ROBOT_NO + " ile "
                                + Sabitler.MAX_ROBOT_NO
                                + " arasinda olmali");
                    }

                    if (robotNolari.contains(no)) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo, "Robot numarasi tekrarli");
                    }

                    Govde govde = GovdeFabrikasi.olustur(parcalar[2]);
                    if (govde == null) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo, "Bilinmeyen robot govdesi");
                    }

                    robotNolari.add(no);
                    robotSatirNolari.add(satirNo);
                    govdeler.add(govde);

                } else if (parcalar[0].equals("HARITA")) {
                    if (parcalar.length != 1) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo, "HARITA satiri hatali");
                    }

                    if (!boyutOkundu || !enerjiOkundu) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo,
                                "BOYUT ve ENERJI once tanimlanmali");
                    }

                    if (robotNolari.isEmpty()) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo,
                                "En az bir robot tanimlanmali");
                    }

                    haritaBasladi = true;
                    haritaBaslikSatiri = satirNo;

                } else {
                    throw new GecersizSatirException(
                            dosyaAdi, satirNo, "Bilinmeyen satir");
                }
            }

            if (!haritaBasladi) {
                throw new GecersizSatirException(
                        dosyaAdi, satirNo, "HARITA bolumu bulunamadi");
            }

            if (haritaSatirlari.size() != yukseklik) {
                int hataSatiri;
                if (haritaSatirlari.size() > yukseklik) {
                    hataSatiri = haritaSatirNolari.get(yukseklik);
                } else if (haritaSatirNolari.isEmpty()) {
                    hataSatiri = haritaBaslikSatiri;
                } else {
                    hataSatiri = haritaSatirNolari.get(
                            haritaSatirNolari.size() - 1);
                }
                throw new GecersizSatirException(
                        dosyaAdi, hataSatiri,
                        "Harita satir sayisi yukseklikle uyusmuyor");
            }

            Zemin[][] zeminler = new Zemin[yukseklik][genislik];

            for (int y = 0; y < yukseklik; y++) {
                String satirHarita = haritaSatirlari.get(y);
                int haritaSatiri = haritaSatirNolari.get(y);

                if (satirHarita.length() != genislik) {
                    throw new GecersizSatirException(
                            dosyaAdi, haritaSatiri,
                            "Harita satirinin genisligi hatali");
                }

                for (int x = 0; x < genislik; x++) {
                    char sembol = satirHarita.charAt(x);
                    Zemin zemin = ZeminFabrikasi.olustur(sembol);

                    if (zemin != null) {
                        zeminler[y][x] = zemin;
                    } else if (sembol >= '0' + Sabitler.MIN_ROBOT_NO
                            && sembol <= '0' + Sabitler.MAX_ROBOT_NO) {

                        int no = sembol - '0';
                        int indeks = robotNolari.indexOf(no);

                        if (indeks == -1) {
                            throw new GecersizSatirException(
                                    dosyaAdi, haritaSatiri,
                                    "Tanimlanmamis robot haritada bulundu");
                        }

                        if (robotlar.stream().anyMatch(r -> r.getNo() == no)) {
                            throw new GecersizSatirException(
                                    dosyaAdi, haritaSatiri,
                                    "Robot haritada birden fazla bulunuyor");
                        }

                        robotlar.add(new Robot(
                                no, govdeler.get(indeks), x, y, enerji));
                        zeminler[y][x] = new Duz();

                    } else {
                        throw new GecersizSatirException(
                                dosyaAdi, haritaSatiri,
                                "Gecersiz harita karakteri: " + sembol);
                    }
                }
            }

            for (int i = 0; i < robotNolari.size(); i++) {
                int robotNo = robotNolari.get(i);
                boolean haritada = robotlar.stream()
                        .anyMatch(r -> r.getNo() == robotNo);
                if (!haritada) {
                    throw new GecersizSatirException(
                            dosyaAdi, robotSatirNolari.get(i),
                            robotNo + " numarali robot haritada bulunamadi");
                }
            }

            robotlar.sort((a, b) -> Integer.compare(a.getNo(), b.getNo()));

            return new Harita(genislik, yukseklik, zeminler, robotlar);

        } catch (IOException e) {
            throw new GecersizSatirException(
                    dosyaAdi, satirNo,
                    "Dosya okunamadi: " + e.getMessage());
        }
    }
}
package proglab1.proje2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import proglab1.proje2.govde.*;
import proglab1.proje2.zemin.*;

public class DosyaOkuyucu {

    public static Harita oku(String dosyaAdi)
            throws GecersizSatirException {

        int satirNo = 0;

        try (BufferedReader okuyucu =
                new BufferedReader(new FileReader(dosyaAdi))) {

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

            while ((satir = okuyucu.readLine()) != null) {
                satirNo++;

                satir = satir.trim();

                if (satir.isEmpty() || satir.startsWith("#")) {
                    continue;
                }

                if (haritaBasladi) {
                    haritaSatirlari.add(satir);
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

                    if (no < 1 || no > 9 || robotNolari.contains(no)) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo,
                                "Robot numarasi gecersiz veya tekrarli");
                    }

                    Govde govde;

                    switch (parcalar[2]) {
                        case "PALETLI":
                            govde = new Paletli();
                            break;
                        case "SICRAYAN":
                            govde = new Sicrayan();
                            break;
                        case "HAVALI":
                            govde = new Havali();
                            break;
                        case "KIZAKLI":
                            govde = new Kizakli();
                            break;
                        default:
                            throw new GecersizSatirException(
                                    dosyaAdi, satirNo, "Bilinmeyen robot govdesi");
                    }

                    robotNolari.add(no);
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

                    if (robotNolari.size() != 4) {
                        throw new GecersizSatirException(
                                dosyaAdi, satirNo,
                                "Tam olarak dort robot tanimlanmali");
                    }

                    haritaBasladi = true;

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
                throw new GecersizSatirException(
                        dosyaAdi, satirNo,
                        "Harita satir sayisi yukseklikle uyusmuyor");
            }

            Zemin[][] zeminler = new Zemin[yukseklik][genislik];

            for (int y = 0; y < yukseklik; y++) {

                String satirHarita = haritaSatirlari.get(y);

                if (satirHarita.length() != genislik) {
                    throw new GecersizSatirException(
                            dosyaAdi, satirNo,
                            "Harita satirinin genisligi hatali");
                }

                for (int x = 0; x < genislik; x++) {

                    char sembol = satirHarita.charAt(x);

                    switch (sembol) {
                        case '.':
                            zeminler[y][x] = new Duz();
                            break;

                        case 'T':
                            zeminler[y][x] = new Toz();
                            break;

                        case 'K':
                            zeminler[y][x] = new Kaya();
                            break;

                        case '~':
                            zeminler[y][x] = new Metan();
                            break;

                        case 'I':
                            zeminler[y][x] = new Buz();
                            break;

                        case 'X':
                            zeminler[y][x] = new Engel();
                            break;

                        case 'H':
                            zeminler[y][x] = new Rampa();
                            break;

                        default:
                            if (sembol >= '1' && sembol <= '9') {

                                int no = sembol - '0';
                                int indeks = robotNolari.indexOf(no);

                                if (indeks == -1) {
                                    throw new GecersizSatirException(
                                            dosyaAdi, satirNo,
                                            "Tanimlanmamis robot haritada bulundu");
                                }

                                if (robotlar.stream().anyMatch(
                                        r -> r.getNo() == no)) {
                                    throw new GecersizSatirException(
                                            dosyaAdi, satirNo,
                                            "Robot haritada birden fazla bulunuyor");
                                }

                                robotlar.add(new Robot(
                                        no, govdeler.get(indeks),
                                        x, y, enerji));

                                zeminler[y][x] = new Duz();

                            } else {
                                throw new GecersizSatirException(
                                        dosyaAdi, satirNo,
                                        "Gecersiz harita karakteri: " + sembol);
                            }
                    }
                }
            }

            if (robotlar.size() != 4) {
                throw new GecersizSatirException(
                        dosyaAdi, satirNo,
                        "Dort robotun tamami haritada bulunmali");
            }

            robotlar.sort((a, b) ->
                    Integer.compare(a.getNo(), b.getNo()));

            return new Harita(
                    genislik, yukseklik, zeminler, robotlar);

        } catch (IOException e) {
            throw new GecersizSatirException(
                    dosyaAdi, satirNo,
                    "Dosya okunamadi: " + e.getMessage());
        }
    }
}


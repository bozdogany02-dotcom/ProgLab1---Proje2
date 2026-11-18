package proglab1.proje2;

public class Sabitler {

    private Sabitler(){}

    // Duz zemin (tum govdeler)
    public static final int DUZ_MALIYET = 1;

    // Toz firtinasi: havali haric govdeler gecer
    public static final int TOZ_MALIYET = 2;
    public static final int TOZ_HAVALI_MALIYET = 1;

    // Kaya: sicrayan atlar, havali ucar
    public static final int KAYA_MALIYET = 3;
    public static final int KAYA_ATLAMA_MALIYET = 2;
    public static final int KAYA_HAVALI_MALIYET = 1;

    // Metan golu: sadece havali gecer
    public static final int METAN_HAVALI_MALIYET = 1;

    // Buz: kizakli kayar, havali ucar
    public static final int BUZ_MALIYET = 2;
    public static final int BUZ_KAYMA_MALIYET = 1;
    public static final int BUZ_HAVALI_MALIYET = 1;

    // Radyoaktif zemin (kendi zeminimiz): zirhli dayanir, havali ucar
    public static final int RADYOAKTIF_ZIRHLI_MALIYET = 3;
    public static final int RADYOAKTIF_HAVALI_MALIYET = 1;

    public static final int RAMPA_MALIYET = 1;          //teslim rampasi

    public static final int MIN_ILERI = 1;
    public static final int MAX_ILERI = 99;
    public static final int MIN_ROBOT_NO = 1;
    public static final int MAX_ROBOT_NO = 9;
}
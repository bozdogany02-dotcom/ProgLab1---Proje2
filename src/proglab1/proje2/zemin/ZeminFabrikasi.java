package proglab1.proje2.zemin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ZeminFabrikasi {

    private static final Map<Character, Supplier<Zemin>> KAYITLAR = new HashMap<>();

    static {
        kaydet('.', Duz::new);
        kaydet('T', Toz::new);
        kaydet('K', Kaya::new);
        kaydet('~', Metan::new);
        kaydet('I', Buz::new);
        kaydet('X', Engel::new);
        kaydet('R', RadyoaktifZemin::new);
        kaydet('H', Rampa::new);
    }

    private ZeminFabrikasi() {
    }

    public static void kaydet(char sembol, Supplier<Zemin> uretici) {
        KAYITLAR.put(sembol, uretici);
    }

    public static Zemin olustur(char sembol) {
        Supplier<Zemin> uretici = KAYITLAR.get(sembol);
        return (uretici != null) ? uretici.get() : null;
    }
}
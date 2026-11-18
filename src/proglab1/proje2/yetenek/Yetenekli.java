package proglab1.proje2.yetenek;

public interface Yetenekli {

    default boolean ucabilir() {
        return false;
    }

    default boolean atlayabilir() {
        return false;
    }

    default boolean kayabilir() {
        return false;
    }

    default int atlamaMaliyeti() {
        return -1;
    }
}
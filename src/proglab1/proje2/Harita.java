package proglab1.proje2;

import java.util.List;
import proglab1.proje2.zemin.Zemin;

public class Harita {
    private final int genislik;
    private final int yukseklik;
    private final Zemin[][] zeminler;     //zeminler[y][x]
    private final List<Robot> robotlar;
    
    public Harita(int genislik, int yukseklik, Zemin[][] zeminler, List<Robot> robotlar) {
        this.genislik = genislik;
        this.yukseklik = yukseklik;
        this.zeminler = zeminler;
        this.robotlar = robotlar;
    }
    
    public int getGenislik() {return genislik; }
    public int getYukseklik() {return yukseklik; }
    public List<Robot> getRobotlar() {return robotlar; }
    
    public boolean sinirIcindeMi(int x, int y) {
        return x >= 0 && x < genislik && y >= 0 && y < yukseklik;
    }
    
    //Harita dışındaysa null döner
    public  Zemin zeminAl(int x, int y) {
        if(!sinirIcindeMi(x, y)) {
            return null;
        }
        return zeminler[y][x];
    }
    
    //Hritada duran bir robot var mı? (teslim eden robot haritadan kalkar)
    public boolean doluMu(int x, int y) {
        for(Robot robot : robotlar) {
            if(robot.getDurum() != Durum.TESLIM 
                    && robot.getX() == x && robot.getY() == y){
                return true;
            }
        }
        return false;
    }
}
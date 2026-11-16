/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proglab1.proje2;

/**
 *
 * @author Yahya Bozdoğan
 */
public class GecersizSatirException extends Exception {
    
    private final String dosyaAdi;
    private final int satirNo;
    private final String neden;
    
    public GecersizSatirException(String dosyaAdi, int satirNo, String neden){
        super(dosyaAdi + "dosyasında satır" + satirNo + ": " + neden);
        this.dosyaAdi = dosyaAdi;
        this.satirNo = satirNo;
        this.neden = neden;
    }
    
    public String getDosyaAdi() {return dosyaAdi; }
    public int getSatirNo() {return satirNo; }
    public String getNeden() {return neden; }
}

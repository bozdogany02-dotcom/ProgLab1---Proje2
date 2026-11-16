/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package proglab1.proje2.yetenek;

/**
 *
 * @author Yahya Bozdoğan
 */
public interface Yetenekli {
    default boolean ucabilir() {return false; }
    default boolean atlayabilir() {return false; }
    default boolean kayabilir() {return false; }
}

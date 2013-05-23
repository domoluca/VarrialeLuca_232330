/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package varrialeluca_232330;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luca
 */
public class Uomo extends Thread{
    Bagno bagno;
    String sesso;
    int pid;
    int cicli;
    long attesa;
    long attesaTotale;
    long mediaAttesa;
    
    public Uomo(Bagno ba, String sesso, int pid){
    this.bagno = ba;
    this.pid = pid;
    this.sesso = sesso;
    this.cicli = 4;
    }
    
    public void run(){
        while (this.cicli > 0){
            
            int attendo = 100 + (int)(Math.random() * 400);
            try {
                Thread.sleep(attendo);
            } catch (InterruptedException ex) {
                Logger.getLogger(Uomo.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        System.out.println("[*][*][*][*]"
                          +" sono l'UOMO"+pid+
                           " e faccio richiesta per il bagno");  
        
        long temp = System.currentTimeMillis();
        this.bagno.accessoAlBagno(sesso, pid);
        long temp1 = System.currentTimeMillis();
        this.attesa = temp1-temp;
        this.attesaTotale = this.attesaTotale + this.attesa;
        
        this.cicli--;
        }
        this.mediaAttesa = this.attesaTotale / 4;
        System.out.println("###########################");
        System.out.println("L'uomo "+this.pid+" ha terminato. ");
        System.out.println("in media ha atteso: ");
        System.out.println(this.mediaAttesa+" millisecondi");
        System.out.println("###########################");
        System.out.println("");
        this.bagno.attesaMaschi(mediaAttesa);
        
    }
}

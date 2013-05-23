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
public class Donna extends Thread{
    Bagno bagno;
    String sesso;
    int pid;
    int cicli;
    long attesa;
    long attesaTotale;
    long mediaAttesa;
    
    public Donna(Bagno ba, String sesso, int pid){
    this.bagno = ba;
    this.pid = pid;
    this.sesso = sesso;
    this.cicli = 8;
    }
    
    public void run(){
        while(this.cicli > 0){
        int attendo = 100 + (int)(Math.random() * 100);
            try {
                Thread.sleep(attendo);
            } catch (InterruptedException ex) {
                Logger.getLogger(Uomo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        System.out.println("[*][*][*][*]"
                          +" sono la DONNA"+pid+
                           " e faccio richiesta per il bagno"); 
        
        long temp = System.currentTimeMillis();
        this.bagno.accessoAlBagno(sesso, pid);
        long temp1 = System.currentTimeMillis();
        
        attesa = (temp1 - temp);
        attesaTotale = attesaTotale + attesa;
        
        this.cicli--;
        }
        mediaAttesa = attesaTotale / 8;
        System.out.println("###########################");
        System.out.println("La donna "+pid+" ha terminato. ");
        System.out.println("in media ha atteso: ");
        System.out.println(mediaAttesa+" millisecondi");
        System.out.println("###########################");
        System.out.println("");
        this.bagno.attesaDonne(mediaAttesa);
        
    }
}

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
        this.bagno.accessoAlBagno(sesso, pid);
        System.out.println("sono la donna "+pid+
                " e ho appena finito di usare il bagno");
        this.cicli--;
        }
        
    }
}

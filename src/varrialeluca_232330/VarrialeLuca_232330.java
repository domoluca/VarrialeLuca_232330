/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package varrialeluca_232330;
import java.io.*;


/**
 *
 * @author Luca
 */
public class VarrialeLuca_232330 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numDonne = 0;
        int numUomini = 0;
        int posti = 0;
        BufferedReader readln = null;
        String input = "";
        try{
            readln = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Inserire il numero di donne: ");
            input = readln.readLine();
            numDonne = (new Integer(input)).intValue();
            input = "";
            
            System.out.println("Inserire il numero di uomini: ");
            input = readln.readLine();
            numUomini = (new Integer(input)).intValue();
            input = "";
            
            System.out.println("Inserire il numero di posti disponibili"
                             + " nel bagno "
                             +"(il numero inserito deve essere maggiore di 0): ");
            input = readln.readLine();
            posti  = (new Integer(input)).intValue();
            
        }catch(IOException | NumberFormatException e){
            System.out.println(e);
        }
        Bagno bagno = new Bagno(posti);
        
        Donna donna[] = new Donna[numDonne];
        for (int i = 0; i < numDonne; i++){
            donna[i] = new Donna(bagno, "f", i);
        }
        Uomo uomo[] = new Uomo[numUomini];
        for (int j = 0; j < numUomini; j++){
            uomo[j] = new Uomo(bagno, "m", j);
        }
        
        for (int i = 0; i < numDonne; i++){
            donna[i].start();
        }
        for (int j = 0; j < numUomini; j++){
            uomo[j].start();
        }
        
        try{
            for (int i = 0; i < numDonne; i++){
            donna[i].join();
        }
        for (int j = 0; j < numUomini; j++){
            uomo[j].join();
        }
            
        }catch(Exception e){
            System.out.println();
        }
        
        long attesaMas = bagno.attesaTotM / bagno.k;
        long attesaDon = bagno.attesaTotF /bagno.l;
        System.out.println("################################################");
        System.out.println("################################################");
        System.out.println("");
        System.out.println("PROGRAMMA TERMINATO");
        System.out.println("IN MEDIA GLI UOMINI HANNO ATTESO: "+attesaMas
                          +" millisecondi");
        System.out.println("");
        System.out.println("IN MEDIA LE DONNE HANNO ATTESO: "+attesaDon
                          +" millisecondi");
        System.out.println("");
        System.out.println("################################################");
        System.out.println("################################################");
    }
}
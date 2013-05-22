/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package varrialeluca_232330;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.*;


public class Bagno {
    int posti;
    int occupanti;
    int i=0;
    int j=0;
    Lock lockAB = null;
    Lock lockHashM;
    Lock lockHashF;
    //Lock lockListM;
    //Lock lockListF;
    Lock accessoVar;
    LinkedList<Integer> listM = new LinkedList();
    LinkedList<Integer> listF = new LinkedList();
    Hashtable<Integer, Semaphore> hashM = null;
    Hashtable<Integer, Semaphore> hashF = null;
    String sex;
    
    public Bagno(int posti){
        this.posti = posti;
        this.occupanti = 0;
        this.lockAB = new ReentrantLock();
        this.lockHashM = new ReentrantLock();
        this.lockHashF = new ReentrantLock();
        //this.lockListM = new ReentrantLock();
        //this.lockListF = new ReentrantLock();
        this.accessoVar = new ReentrantLock();
        this.hashM = new Hashtable<Integer, Semaphore>();
        this.hashF = new Hashtable<Integer, Semaphore>();
        this.sex = "u";
    }
    
    
        public void accessoAlBagno(String sesso, int pid){
            this.lockAB.lock();
            if("u".equals(this.sex)){
            this.sex = sesso;
            System.out.println("///////"
                              +"adesso il sesso del bagno è: "+this.sex);
            //this.occupanti++;
            this.lockAB.unlock();
            usaBagno(pid, sesso);
            }
            else if (sesso.equals(this.sex)){
                if(this.occupanti < this.posti){
                    //this.occupanti++;
                    this.lockAB.unlock();
                    usaBagno(pid, sesso);    
                }
                else{
                    this.lockAB.unlock();
                    mettiInAttesa(pid, sesso);
               }
            }
            else if (!sesso.equals(this.sex)){
                this.lockAB.unlock();
                mettiInAttesa(pid,sesso);
                //this.lockAB.unlock();
            }
    }
        public void usaBagno(int pid, String sesso){
            this.lockAB.lock();
            this.occupanti ++;
            this.lockAB.unlock();
            if("f".equals(sesso)){
                System.out.println("");
                System.out.println("sono la donna "+pid+
                                   " e sto usando il bagno");
                System.out.println("");
                int utilizzo = 200 + (int)(Math.random() * 400);
                try {
                    Thread.sleep(utilizzo);
                    } catch(Exception e){
                    System.out.println(e);
                    }
                notificaUscita(sesso, pid);
                }
             else if("m".equals(sesso)){
                System.out.println(""); 
                System.out.println("sono il maschio "+pid+
                                   " e sto usando il bagno");
                System.out.println("");
                int utilizzo = 100 + (int)(Math.random() * 100);
                    try {
                        Thread.sleep(utilizzo);
                        }catch(Exception e){
                        System.out.println(e);
                        } 
                 notificaUscita(sesso, pid);
                    }
            //notificaUscita(sesso);
        }
        
        public void mettiInAttesa(int pid, String sesso){
                if ("m".equals(sesso)){
                lockHashM.lock();
                //lockListM.lock();
                Semaphore sem = new Semaphore(0);
                this.hashM.put(pid, sem);
                this.listM.addFirst(pid);
                System.out.println("");
                System.out.println("XXXXXXho appena messo in attesa l'UOMO "+this.listM.getFirst());
                System.out.println("XXXXXdimensione ht "+sesso+": "+this.hashM.size());
                System.out.println("XXXXXdimensione lista: "+this.listM.size());
                System.out.println("");
                lockHashM.unlock();
                //lockListM.unlock();
                try {
                    //this.lockAB.unlock();
                    sem.acquire();
                } catch(Exception e){
                    System.out.println(e);
                }
                System.out.println("sono il maschio "+pid+" e sto per entrare in bagno!!!!!!!!!");
                usaBagno(pid, sesso);
                }
                else{
                lockHashF.lock();
                //lockListF.lock();
                Semaphore sem = new Semaphore(0);
                this.hashF.put(pid, sem);
                this.listF.addFirst(pid);
                System.out.println("");
                System.out.println("XXXXXXho appena messo in attesa la DONNA"+this.listF.getFirst());
                System.out.println("XXXXXdimensione ht "+sesso+": "+this.hashF.size());
                System.out.println("XXXXXdimensione lista: "+this.listF.size());
                System.out.println("");
                lockHashF.unlock();
                //lockListF.unlock();
                try {
                    //this.lockAB.unlock();
                    sem.acquire();
                } catch(Exception e){
                    System.out.println(e);
                }
                usaBagno(pid, sesso);
                }
                //usaBagno(pid, sesso);
        }
        
        public void notificaUscita(String sesso, int pid){
                this.lockAB.lock();
                this.lockHashF.lock();
                this.lockHashM.lock();
                this.i = 0;
                this.j = 0;
                //System.out.println("");
                System.out.println(sesso+" "+pid+" entro in notificaUscita");
                System.out.println("");
                this.occupanti--;
                if("m".equals(sesso)){
                    if(!this.listM.isEmpty()){
                        int pidLast = listM.getLast();
                        System.out.println("devo rimuovere questo:"+pidLast);
                        this.hashM.get(pidLast).release();
                        this.hashM.remove(pidLast);
                        this.listM.removeLast();
                    }
                    else if(this.hashM.isEmpty()){
                            if(this.occupanti > 0){
                            //this.occupanti--;
                                System.out.println(sesso+" "+pid+" vede coda vuota");
                                System.out.println("ma nel bagno c'è ancora qualcuno");
                                }
                            else{
                                if(this.listF.isEmpty()){
                                    //this.occupanti--;
                                    this.sex = "u";
                                    System.out.println("******************************");
                                    System.out.println("il sesso è cambiato in: "+sex);
                                    System.out.println("******************************");
                               }
                                else{
                                    this.sex = "f";
                                    System.out.println("******************************");
                                    System.out.println("il sesso è cambiato in "+sex);
                                    System.out.println("******************************");
                                    while(i <= this.posti && 
                                        !this.hashF.isEmpty()){
                                        i++;
                                        int pidLastF = listF.getLast();
                                        this.hashF.get(pidLastF).release();
                                        this.hashF.remove(pidLastF);
                                        this.listF.removeLast();
                                        }
                                     }    
                                }}
                    //this.lockHashM.unlock();
                    //this.lockListM.unlock();
                    //this.lockAB.unlock();
                }
                
                if("f".equals(sesso)){
                //lockHashF.lock();
                //lockListF.lock();
                    if(!this.listF.isEmpty()){
                        int pidLast = listF.getLast();
                        System.out.println("devo rimuovere questo: "+pidLast);
                        this.hashF.get(pidLast).release();
                        this.hashF.remove(pidLast);
                        this.listF.removeLast();
                        //this.lockAB.unlock();
                        //this.lockHashM.unlock();
                        //this.lockListM.unlock();
                        //System.out.println("FFFFFFFFFFFF");
                    }
                    else if(this.hashF.isEmpty()){
                            if(this.occupanti > 0){
                            //this.occupanti--;
                                System.out.println(sesso+" "+pid+" vede coda vuota");
                                System.out.println(" ma nel bagno c'è ancora qualcuno");
                            //this.lockAB.unlock();
                            //this.lockHashM.unlock();
                            //this.lockListM.unlock(); 
                            }
                            else{
                            //this.lockHashM.lock();
                            //this.lockListM.lock();
                                if(this.listM.isEmpty()){
                                    //this.occupanti--;
                                    this.sex = "u";
                                    System.out.println("******************************");
                                    System.out.println("il sesso è cambiato in: "+sex);
                                    System.out.println("******************************");
                                    //this.lockAB.unlock();
                                    //this.lockHashM.unlock();
                                    //this.lockListM.unlock();
                                    //this.lockHashM.unlock();
                                    //this.lockListM.unlock();ù
                                }
                                else{
                                    this.sex = "m";
                                    System.out.println("******************************");
                                    System.out.println("il sesso è cambiato in: "+sex);
                                    System.out.println("******************************");
                                    while(j <= this.posti && 
                                        !this.hashM.isEmpty()){
                                        j++;
                                        int pidLastM = listM.getLast();
                                        this.hashM.get(pidLastM).release();
                                        this.hashM.remove(pidLastM);
                                        this.listM.removeLast();
                                        }
                                     }    
                        }}
                    //this.lockHashF.unlock();
                    //this.lockListF.unlock();
                    //this.lockAB.unlock();
                }   this.lockHashF.unlock();
                    this.lockHashM.unlock();
                    this.lockAB.unlock();
        }
}

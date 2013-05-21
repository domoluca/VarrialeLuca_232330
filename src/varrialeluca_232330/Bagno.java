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
    int i =0;
    int j;
    Lock lockAB = null;
    Lock lockHashM;
    Lock lockHashF;
    Lock lockListM;
    Lock lockListF;
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
        this.lockListM = new ReentrantLock();
        this.lockListF = new ReentrantLock();
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
            //this.lockAB.lock();
            this.occupanti ++;
            //this.lockAB.unlock();
            if("f".equals(sesso)){
                System.out.println("sono la donna "+pid+
                                   "e sto usando il bagno");
                int utilizzo = 200 + (int)(Math.random() * 400);
                try {
                    Thread.sleep(utilizzo);
                    } catch(Exception e){
                    System.out.println(e);
                    }
                notificaUscita(sesso, pid);
                }
             else if("m".equals(sesso)){
                System.out.println("sono il maschio "+pid+
                                   " e sto usando il bagno");
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
                lockListM.lock();
                Semaphore sem = new Semaphore(0);
                this.hashM.put(pid, sem);
                this.listM.addFirst(pid);
                System.out.println("XXXXXXho appena messo in attesa l'UOMO "+this.listM.getFirst());
                System.out.println("XXXXXdimensione ht: "+this.hashM.size());
                System.out.println("XXXXXdimensione lista: "+this.listM.size());
                lockHashM.unlock();
                lockListM.unlock();
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
                lockListF.lock();
                Semaphore sem = new Semaphore(0);
                this.hashF.put(pid, sem);
                this.listF.addFirst(pid);
                System.out.println("XXXXXXho appena messo in attesa la DONNA"+this.listF.getFirst());
                lockHashF.unlock();
                lockListF.unlock();
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
                System.out.println(sesso+" "+pid+" entro in notificaUscita");
                this.occupanti--;
                if("m".equals(sesso)){
                lockHashM.lock();
                lockListM.lock();
                    if(!this.listM.isEmpty()){
                        int pidLast = listM.getLast();
                        System.out.println("devo rimuovere questo:"+pidLast);
                        this.hashM.get(pidLast).release();
                        this.hashM.remove(pidLast);
                        this.listM.removeLast();
                        //this.lockAB.unlock();
                        //this.lockHashM.unlock();
                        //this.lockListM.unlock();
                        System.out.println("111111111111");
                    }
                    else if(this.hashM.isEmpty()){
                            if(this.occupanti > 0){
                            //this.occupanti--;
                                System.out.println(sesso+" "+pid+" vede coda vuota");
                                System.out.println("ma nel bagno c'è ancora qualcuno");
                            //this.lockAB.unlock();
                            //this.lockHashM.unlock();
                            //this.lockListM.unlock(); 
                            }
                            else{
                            this.lockHashF.lock();
                            this.lockListF.lock();
                                if(this.listF.isEmpty()){
                                    //this.occupanti--;
                                    this.sex = "u";
                                    System.out.println("3333333333333");
                                    //this.lockAB.unlock();
                                    this.lockHashF.unlock();
                                    this.lockListF.unlock();
                                    //this.lockHashM.unlock();
                                    //this.lockListM.unlock();ù
                                }
                                else{
                                    this.sex = "f";
                                    System.out.println("il sesso è cambiato in donna");
                                    while(i <= this.posti || 
                                        !this.hashF.isEmpty()){
                                        i++;
                                        System.out.println("44444444");
                                        int pidLast = listF.getLast();
                                        this.hashF.get(pidLast).release();
                                        this.hashF.remove(pidLast);
                                        this.listF.remove(pidLast);
                                        //this.lockAB.unlock();
                                        this.lockHashF.unlock();
                                        this.lockListF.unlock();}
                                        }    
                        }}
                    this.lockHashM.unlock();
                    this.lockListM.unlock();
                    this.lockAB.unlock();
                }
                
                if("f".equals(sesso)){
                lockHashF.lock();
                lockListF.lock();
                    if(!this.listF.isEmpty()){
                        int pidLast = listF.getLast();
                        System.out.println("devo rimuovere questo:"+pidLast);
                        this.hashF.get(pidLast).release();
                        this.hashF.remove(pidLast);
                        this.listF.removeLast();
                        //this.lockAB.unlock();
                        //this.lockHashM.unlock();
                        //this.lockListM.unlock();
                        System.out.println("FFFFFFFFFFFF");
                    }
                    else if(this.hashF.isEmpty()){
                            if(this.occupanti > 0){
                            //this.occupanti--;
                                System.out.println(sesso+" "+pid+" vede coda vuota");
                                System.out.println("ma nel bagno c'è ancora qualcuno");
                            //this.lockAB.unlock();
                            //this.lockHashM.unlock();
                            //this.lockListM.unlock(); 
                            }
                            else{
                            this.lockHashM.lock();
                            this.lockListM.lock();
                                if(this.listM.isEmpty()){
                                    //this.occupanti--;
                                    this.sex = "u";
                                    System.out.println("3333333333333");
                                    //this.lockAB.unlock();
                                    this.lockHashM.unlock();
                                    this.lockListM.unlock();
                                    //this.lockHashM.unlock();
                                    //this.lockListM.unlock();ù
                                }
                                else{
                                    this.sex = "f";
                                    System.out.println("il sesso è cambiato in uomo");
                                    while(i <= this.posti || 
                                        !this.hashM.isEmpty()){
                                        j++;
                                        System.out.println("44444444");
                                        int pidLast = listM.getLast();
                                        this.hashM.get(pidLast).release();
                                        this.hashM.remove(pidLast);
                                        this.listM.remove(pidLast);
                                        //this.lockAB.unlock();
                                        this.lockHashM.unlock();
                                        this.lockListM.unlock();}
                                        }    
                        }}
                    this.lockHashF.unlock();
                    this.lockListF.unlock();
                    this.lockAB.unlock();
                }
        }
}

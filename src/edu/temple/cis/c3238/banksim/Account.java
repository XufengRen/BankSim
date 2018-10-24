package edu.temple.cis.c3238.banksim;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 * @author Modified by Charles Wang
 */
public class Account {

    private volatile int balance;
    private final int id;
    private final Bank myBank;
    //private final Lock fundsLock;
    //private final Condition waitFunds;
    //private boolean waiting;
    

    public Account(Bank myBank, int id, int initialBalance) {
        this.myBank = myBank;
        this.id = id;
        balance = initialBalance;
        /*fundsLock = new ReentrantLock();
        waitFunds = fundsLock.newCondition();
        waiting = false;
        */
    }

    //Sync -- takes care of mutual exclusion
    public synchronized int getBalance() {
        return balance;
    }

    // Sync
    public synchronized boolean withdraw(int amount) {
        if (amount <= balance /*&& !waiting*/) {
            int currentBalance = balance;
//            Thread.yield(); // Try to force collision
            int newBalance = currentBalance - amount;
            balance = newBalance;
            return true;
        } else {
            return false;
        }
    }

    //Sync
    public synchronized void deposit(int amount) {
        int currentBalance = balance;
//        Thread.yield();   // Try to force collision
        int newBalance = currentBalance + amount;
        balance = newBalance;
        //waitFunds.signalAll();
    }

    /*public void waitForFunds(int amount) {
        fundsLock.lock();
        try {
            waiting = true;
            while (balance < amount) {
                try {
                    waitFunds.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.withdraw(amount);
        } finally {
            waiting = false;
            fundsLock.unlock();
        }
    }*/

     
    @Override
    public String toString() {
        return String.format("Account[%d] balance %d", id, balance);
    }
}

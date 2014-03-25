package com.thread.freelock;

import java.math.BigInteger;
import java.sql.Array;
import java.text.Format;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by julia
 */
public class Main {

    public static void main(String... args){
        final PowersOfTwo power = new PowersOfTwo();
        BigInteger expSum;
        BigInteger actSum = BigInteger.ZERO;
        int n = 100, m = 10, lenth;

        BigInteger[] bigIntegers = new BigInteger[n * m];
        BigInteger[] future;
        FutureTask<BigInteger[]>[] futureTasks = new FutureTask[n];
        Thread[] threads = new Thread[n];

        for(int i = 0; i < n; ++i){
            futureTasks[i] = new FutureTask<BigInteger[]>(new MyCallable(power));
            threads[i] = new Thread(futureTasks[i]);
            threads[i].start();
        }

        for(int i = 0; i < n; ++i){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                future = futureTasks[i].get();
                for(int j = 0; j < m; ++j){
                    bigIntegers[i * m + j] = future[j];
                    System.out.println(future[j]);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }

        lenth = bigIntegers.length;
        for(int i = 0; i < lenth; ++i){
            actSum = actSum.add(bigIntegers[i]);
        }


        expSum = (new BigInteger("2")).pow(m * n).subtract(BigInteger.ONE);
        System.out.println();
        System.out.println("Actual sum is: " + actSum.toString());
        System.out.println("Expected sum is: " + expSum.toString());


    }

    public static class MyCallable implements Callable<BigInteger[]>{
        PowersOfTwo power;
        int n = 10;

        public MyCallable(PowersOfTwo power){
           this.power = power;
        }

        public void run() {
            for(int i = 0; i < n; ++i){
                System.out.println(power.next());
            }
        }

        @Override
        public BigInteger[] call() throws Exception {
            BigInteger[] bigIntegers = new BigInteger[n];
            for(int i = 0; i < n; ++i){
                bigIntegers[i] = power.next();
            }
            return bigIntegers;
        }
    }
}

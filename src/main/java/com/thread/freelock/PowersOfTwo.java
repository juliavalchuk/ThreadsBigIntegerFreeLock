package com.thread.freelock;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by julia
 */
public class PowersOfTwo {
  //  private volatile BigInteger numb;
    private AtomicReference<BigInteger> numb;
    private BigInteger TWO;

    public PowersOfTwo(){
        numb = new AtomicReference<BigInteger>(BigInteger.ONE);
        TWO = new BigInteger("2");
    }

    public BigInteger next(){
        for (; ; ) {
            BigInteger current = numb.get();
            BigInteger next = current.multiply(TWO);
            if (numb.compareAndSet(current, next)) {
                return current;
            }
        }
    }
}

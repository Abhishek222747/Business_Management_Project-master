package com.business.basiclogics;

public class Logic
{
    private Logic() {
        // Private constructor to prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }

 public static double countTotal(double price,int quantity)
 {
	 double res=price*quantity;
	 return res;
 }
	
}

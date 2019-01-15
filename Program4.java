/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicas;

import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author guille_pt
 */
public class Program4 {
    
    private static LinkedList<Integer> numberOfItems = new LinkedList<>(); 
    private static LinkedList<Integer> partSize = new LinkedList<>(); 
    private static LinkedList<Double> sizePerItem = new LinkedList<>(); 

    
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Total de datos ");
        int size = sc.nextInt();
        double num ;
        int n;
       
        
        for(int i=0; i<size; i++){
            System.out.println("Dame un numero para numberOfItems");
            n = sc.nextInt();
            numberOfItems.add(n);
        }
        
        for(int i=0; i<size; i++){
            System.out.println("Dame un numero para partSize");
            n = sc.nextInt();
            partSize.add(n);
        }
        
         for(int i=0; i<size; i++){
            num = numberOfItems.get(i).doubleValue() / partSize.get(i).doubleValue();
            sizePerItem.add(num);
        }
        
        double[] sizeRanges = calcular(sizePerItem);
        double vs = sizeRanges[0];
        double s = sizeRanges[1];
        double m = sizeRanges[2];
        double l = sizeRanges[3];
        double vl =  sizeRanges[4];
        System.out.printf("   %-9s%-9s%-9s%-9s%-9s\n","vs","s","m","l","vl");
        System.out.printf("%-9.4f %-8.4f %-8.4f %-8.4f %-8.4f\n",vs,s,m,l,vl);
        
    }
    //fin metodo

    //inicia metodo
   private static double[] calcular(LinkedList<Double> sizePerItem) {
    
        double avg= 0;
        double suma = 0;
        double suma_var = 0;
        
        double var;
        double dev;
        
        double ln_vs;
        double ln_s;
        double ln_m;
        double ln_l;
        double ln_vl;
        
        double vs;
        double s;
        double m;
        double l;
        double vl;
        
        for(int i=0; i<sizePerItem.size();i++)
            suma += Math.log(sizePerItem.get(i));
        
        avg = suma / (sizePerItem.size());
        
        for(int i=0; i < sizePerItem.size();i++)
             suma_var += Math.pow(Math.log(sizePerItem.get(i))-avg,2);
        
        
        var = suma_var / (sizePerItem.size() - 1);
        dev = Math.sqrt(var);
        
        ln_vs = avg - (2 * dev);
        ln_s = avg -  dev;
        ln_m = avg;
        ln_l = avg + dev;
        ln_vl = avg + (2 * dev);
        
        vs = Math.exp(ln_vs);
        s = Math.exp(ln_s);
        m = Math.exp(ln_m);
        l = Math.exp(ln_l);
        vl = Math.exp(ln_vl);

        double[] res = {vs, s, m, l ,vl};
        return res;
    
    }
    
}

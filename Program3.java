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
public class Program3 {
    
    private static LinkedList<Double> x = new LinkedList<>(); 
    private static LinkedList<Double> y = new LinkedList<>(); 

    //inicia metodo
    public static void main(String[] args) {
       
        Scanner sc = new Scanner(System.in);
        System.out.println("Cuantos datos serán? ");
        int n = sc.nextInt();
        double num ;
       
        
        for(int i=0; i<n; i++){
            System.out.println("Dame un numero para x");
            num = sc.nextDouble();
            x.add(num);
        }
        
        for(int i=0; i<n; i++){
            System.out.println("Dame un numero para y");
            num = sc.nextDouble();
            y.add(num);
        }
        
        double[] b = calcular(x, y);
        double b1 = b[0];
        double b0 = b[1];
        double rxy = b[2];
        double r = Math.pow(rxy, 2);
        double yk =  b0 + b1* 386;
        System.out.printf("   %-9s%-9s%-9s%-9s%-9s\n","B0","B1","rxy","r2","yk");
        System.out.printf("%-9.4f %-8.4f %-8.4f %-8.4f %-8.4f\n",b0,b1,rxy,r,yk);
        
    }
    //fin metodo

    //inicia metodo
    private static double[] calcular(LinkedList<Double> x, LinkedList<Double> y) {
        double suma = 0;
        double xavg;
        double sumax = 0;
        double sumax2 = 0;
        double yavg;
        double sumay = 0;
        double sumay2 = 0;
        
        //Variables Finales
        double b1;
        double b0;
        double rxy;
        
        for(int i=0; i<x.size();i++){
            //b0 y b1
            suma += x.get(i)* y.get(i);
            sumax += x.get(i);
            sumay += y.get(i);
            sumax2 += Math.pow(x.get(i), 2);
            
            //rxy
            sumay2 += Math.pow(y.get(i) , 2);
        }
        
        xavg = sumax / x.size();
        yavg = sumay / y.size();
        
        b1 = (suma - (x.size() * xavg * yavg)) / (sumax2 - (x.size() * Math.pow(xavg, 2)));
        b0 = yavg - (b1 * xavg);
        rxy = ((x.size()*suma) - (sumax * sumay)) /
        (Math.sqrt((x.size() * sumax2 - Math.pow(sumax, 2)) * (x.size() * sumay2 - Math.pow(sumay, 2))));
        double[] res = {b1, b0, rxy};

        return res;
    
    }
    //fin metodo
    
}

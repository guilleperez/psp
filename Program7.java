/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicas;

import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author Julio
 */
public class Program7 {
    
    //inicia metodo
    public static void main(String[] args) {
        
       Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);;
       int n;
       n = sc.nextInt();
       
       //1. Compute the value of x, such that
       double xk = 21.3;//386;
       double xs[] = new double[n];
       double y[] = new double[n];
       for(int i=0; i<n; i++)
           xs[i] = sc.nextDouble();
       
        for(int i=0; i<n; i++)
           y[i] = sc.nextDouble();
        
       double[] correlacion = calcularCorrelacion(xs, y); 
       double b1 = correlacion[0];
       double b0 = correlacion[1];
       double r = correlacion[2];

       double x = calcularX(r,n);
       
       /*2. Find the probability p by numerically integrating 
       the t distribution for n - 2 degrees of freedom, 
       from 0 to x.*/
       int dof = n - 2;
       int num_seg = 10; //any even number
       double E = 0.00001;
       double W = x / num_seg;
       double integral =  calcularIntegral(x, dof, W, num_seg);
       double new_res;
       double p;
        
       while (true){
           num_seg *= 2; 
           W = x / num_seg;
           new_res = calcularIntegral(x, dof, W, num_seg);
           
           if( Math.abs(integral - new_res) < E)
               break;
           
           integral = new_res;
        }
       
       p = new_res;
       
       /*3. Calculate the tail area as 1-2*p. (The area under the curve from –x to +x is twice the area from 0 to x, or 2*p;
       the remaining area in the upper and lower tails is 1-2*).*/
       double tail;
       tail = 1 - 2* p; 
       double rango;
       double UPI;
       double LPI;
       
       double yk = b0 + b1*xk;
       //1. Calculate the Range for a 70% interval.
       rango = calcularRango(n, xs, xk, y, b0, b1);
       x = 1.0;    
       num_seg = 10; //any even number
       E = 0.00001;
       W = x / num_seg;
        double res = calcularinversaIntegral(x, dof, W, num_seg);
        
        double d = 0.5;
        double old_error;
        p = 0.35;
        if (Math.abs(p - res) > E) {
            if (res < p) 
                x += d;
             else if (res > p) 
                x -= d;    

            while (true) {

                old_error = p - res;

                num_seg *= 2;
                W = x / num_seg;
                res = calcularinversaIntegral(x, dof, W, num_seg);

                if (Math.abs(p - res) <= E) 
                    break;
                
                if (old_error * (p - res) < 0) 
                    d /= 2;

                if (res < p) 
                    x += d;
                else if (res > p) 
                    x -= d;
                
            }
        }
        

       rango *= x;

 
       //2. Calculate the UPI as:
        UPI = yk + rango;
       //3. Calculate the LPI as:
       LPI = yk - rango;
       System.out.printf("%-9s %.9f\n","rxy",r);
       System.out.printf("%-9s %.8f\n","r2",Math.pow(r, 2));
       System.out.printf("%-9s %e\n","tail area",tail);
       System.out.printf("%-9s %.8f\n","b0", b0);
       System.out.printf("%-9s %.9f\n","b1",b1);
       System.out.printf("%-9s %.7f\n","yk",yk);
       System.out.printf("%-9s %.4f\n","Range",rango);
       System.out.printf("%-9s %7f\n","UPI(70%)-", UPI);
       System.out.printf("%-9s %6f\n","LPI(70%)", LPI);
       
        
     
              
    }
    //fin metodo

    //inicia metodo
    private static double calcularX(double r, int n) {
        double x;
        x = Math.abs(r) * Math.sqrt(n-2);
        x /= Math.sqrt(1 - Math.pow(r,2));
        
        return x;
    }
    //fin metodo
    
    //inicia metodo
    private static double[] calcularCorrelacion(double[] x, double[] y) {
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
        
        for(int i=0; i<x.length;i++){
            //b0 y b1
            suma += x[i]* y[i];
            sumax += x[i];
            sumay += y[i];
            sumax2 += Math.pow(x[i], 2);
            
            //rxy
            sumay2 += Math.pow(y[i] , 2);
        }
        
        xavg = sumax / x.length;
        yavg = sumay / y.length;
        
        b1 = (suma - (x.length * xavg * yavg)) / (sumax2 - (x.length * Math.pow(xavg, 2)));
        b0 = yavg - (b1 * xavg);
        rxy = ((x.length * suma) - (sumax * sumay)) /
        (Math.sqrt((x.length * sumax2 - Math.pow(sumax, 2)) * (x.length * sumay2 - Math.pow(sumay, 2))));
        double[] res = {b1, b0, rxy};

        return res;
    }
    //fin metodo
    
        //Inicia metodo
    private static double calcularIntegral(double x, int dof, double W, int num_seg) {   
        
        double xi = 0;
        double col_uno, col_dos, col_tres, fx,  multiplier = 1, terms = 0;
        double exponente;
        
        for(int i=0; i<= num_seg; i++){
            col_uno = 1 + (Math.pow(xi, 2) / dof);    
            
            exponente = -(double) (dof + 1)/2;
            col_dos = Math.pow(col_uno,exponente);
            
            col_tres = factorial((double)(dof+1)/2)/ 
                    (Math.sqrt(dof*Math.PI) * 
                    factorial((double)dof/2));
       
            fx = col_tres * col_dos;
            terms += W/3 * multiplier * fx;
        
            xi += W;
            if(xi < x){
                if(i%2 == 0)
                    multiplier = 4;
                else
                    multiplier = 2;  
            }else{
                multiplier = 1; 
            }
            
        }
        
        return terms;
       
    }
    //fin metodo

    //reused
    //Inicia metodo
    private static double factorial(double numero) {
        numero -= 1;
        //es diferente formula para un double y un int, verifica que es
       int num = (int)numero;
         double factorial;
       if(num == numero)
           factorial = 1;
       else
            factorial = Math.sqrt(Math.PI);
       
        
        while (numero>0) {
          factorial=factorial*numero;
         numero--;
        
       }
        
       return factorial;
    }

    //Fin metodo  
    
    //inicia metodo
    private static double calcularinversaIntegral(double x, int dof, double W, int num_seg) {

        double xi = 0;
        double col_uno, col_dos, col_tres, fx, multiplier = 1, terms = 0;
        double exponente;

        for (int i = 0; i <= num_seg; i++) {
            col_uno = 1 + (Math.pow(xi, 2) / dof);

            exponente = -(double) (dof + 1) / 2;
            col_dos = Math.pow(col_uno, exponente);

            col_tres = factorial((double) (dof + 1) / 2)
                    / (Math.sqrt(dof * Math.PI)
                    * factorial((double) dof / 2));

            fx = col_tres * col_dos;
            terms += W / 3 * multiplier * fx;

            xi += W;
            if (xi < x) {
                if (i % 2 == 0) {
                    multiplier = 4;
                } else {
                    multiplier = 2;
                }
            } else {
                multiplier = 1;
            }

        }

        return terms;

    }
    //fin metodo

    //inicia metodo
    private static double calcularRango(int n, double[] x, double xk, double[] y, double b0, double b1) {
        
        double xavg = 0;
        for(int i=0; i< x.length ; i++)
            xavg += x[i];
        
        xavg /= x.length;
            
        double sum = 0;
        for(int i=0; i< x.length ; i++)
            sum += Math.pow(x[i] - xavg,2);
        
        double sqr = 1 + (1/ (double) n) + (Math.pow(xk - xavg, 2) / sum);
        sqr = Math.sqrt(sqr);
        
        sum = 0;
        for(int i=0; i< x.length ; i++)
            sum += Math.pow(y[i] - b0 - b1*x[i],2);
                
        double sigma = Math.sqrt((1/(double)(n-2)) * sum);
      
      
        return sigma * sqr;
    }
    //fin metodo
}

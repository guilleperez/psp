/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicas;

import java.util.Scanner;

/**
 *
 * @author guill
 */
public class Program8 {
    //inicia metodo
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        double[] w = new double[n];
        double[] x = new double[n];
        double[] y = new double[n];
        double[] z = new double[n];
       
        for(int i=0; i<n; i++)
            w[i] = sc.nextDouble();
        
        for(int i=0; i<n; i++)
            x[i] = sc.nextDouble();
                
        for(int i=0; i<n; i++)
            y[i] = sc.nextDouble();
                        
        for(int i=0; i<n; i++)
            z[i] = sc.nextDouble();
        
        double wk = sc.nextDouble();
        double xk = sc.nextDouble();
        double yk = sc.nextDouble();
        double[][] linearValues = getLinearEquationValues(w,x,y,z);
        double[] gaussValues = calculateGauss(linearValues);
        
        double rango = calcularRango(n, w, x, y, z, gaussValues, wk, xk, yk);
        double zk = gaussValues[0] + wk*gaussValues[1] + xk*gaussValues[2] + yk*gaussValues[3];

         //2. Calculate the UPI as:
        double UPI = zk + rango;
        //3. Calculate the LPI as:
        double LPI = zk - rango;
       
        System.out.printf("%-15s %.5f\n","bo", gaussValues[0]);
        System.out.printf("%-15s %.5f\n","b1", gaussValues[1]);
        System.out.printf("%-15s %.6f\n","b2", gaussValues[2]);
        System.out.printf("%-15s %.5f\n","b3", gaussValues[3]);
        System.out.printf("%-15s %.2f\n","Projected Hours", zk );
        System.out.printf("%-15s %.2f\n","UPI(70%)-", UPI);
        System.out.printf("%-15s %.2f\n","LPI(70%)", LPI);
    }
    //fin metodo

    //inicia metodo
    private static double[][] getLinearEquationValues(double[] w, double[] x, double[] y, double[] z) {

        double[] indx_b0 = new double[4];
        double[] indx_b1 = new double[4];
        double[] indx_b2 = new double[4];
        double[] indx_b3 = new double[4];
        double[] indx_res = new double[4];
        
  
        indx_b0[0] = w.length;
        for(int i=0; i<w.length;i++){
            indx_b1[0] += w[i];
            indx_b2[0] += x[i];
            indx_b3[0] += y[i];
            indx_res[0] += z[i];
        }     
        
        for(int i=0; i<w.length;i++){
            indx_b0[1] += w[i];
            indx_b1[1] += Math.pow(w[i], 2);
            indx_b2[1] += w[i] * x[i];
            indx_b3[1] += w[i] * y[i];
            indx_res[1] += w[i] * z[i];
        }
        
        for(int i=0; i<w.length;i++){
            indx_b0[2] += x[i];
            indx_b1[2] += w[i] * x[i];
            indx_b2[2] += Math.pow(x[i], 2);
            indx_b3[2] += x[i] * y[i];
            indx_res[2] += x[i] * z[i];
        }
        
        for(int i=0; i<w.length;i++){
            indx_b0[3] += y[i];
            indx_b1[3] += w[i] * y[i];
            indx_b2[3] += x[i] * y[i];
            indx_b3[3] += Math.pow(y[i], 2);
            indx_res[3] += y[i] * z[i];
        }
        
        double[][] linearValues = {indx_b0, indx_b1,indx_b2,indx_b3,indx_res};
        
        return linearValues;
    }
    //fin metodo

    //inicia metodo
    private static double[] calculateGauss(double[][] linearValue) {
        double[] b = linearValue[linearValue.length - 1];
        double[][] linearValues = {linearValue[0], linearValue[1], linearValue[2], linearValue[3]};
        
        //double EPSILON = 1e-10;
        for(int i=0; i< b.length;i++){
            int max = i;
            for(int j = i +1; j< b.length;j++){
                if (Math.abs(linearValues[j][i]) > Math.abs(linearValues[max][i])) 
                    max = j;               
            }
         
            double[] temp = linearValues[i];
            linearValues[i] = linearValues[max]; 
            linearValues[max] = temp;
            double t = b[i]; 
            b[i] = b[max]; 
            b[max] = t;
            
            for (int j = i + 1; j < b.length; j++) {
                double alpha = linearValues[j][i] / linearValues[i][i];
                b[j] -= alpha * b[i];
                for (int k = i; k < b.length; k++) {
                    linearValues[j][k] -= alpha * linearValues[i][k];
                }
            }
        }
        
        // back substitution
        double[] x = new double[b.length];
        for (int i = b.length - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < b.length; j++) {
                sum += linearValues[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / linearValues[i][i];
        }
        
        return x;

    }
    //fin metodo

     //inicia metodo
    private static double calcularRango(int n, double[] w, double[] x, double[] y, double[] z, double[] gaussValues, double wk, double xk, double yk) {
        
        double wavg = 0;
        double xavg = 0;
        double yavg = 0;
        
        for(int i=0; i< w.length ; i++){
            wavg += w[i];
            xavg += x[i];
            yavg += y[i];
        }
        
        wavg /= w.length;
        xavg /= x.length; 
        yavg /= y.length;
            
        double sumw = 0;
        double sumx = 0;
        double sumy = 0;
        
        for(int i=0; i< x.length ; i++){
            sumw += Math.pow(w[i] - wavg,2);
            sumx += Math.pow(x[i] - xavg,2);
            sumy += Math.pow(y[i] - yavg,2);
        }
        
        double sqr = 1 + (1/ (double) n) + (Math.pow(wk - wavg, 2) / sumw)
                + (Math.pow(xk - xavg, 2) / sumx)
                + (Math.pow(yk - yavg, 2) / sumy);
        sqr = Math.sqrt(sqr);

        //sigma
        double sum = 0;
        for(int i=0; i< x.length ; i++)
            sum += Math.pow(z[i] - gaussValues[0] - gaussValues[1]*w[i] - gaussValues[2]*x[i] - gaussValues[3]*y[i],2);
 
        double sigma = Math.sqrt((1/(double)(n-4)) * sum);
        
        //t(0.35, dof)
        double value = 1.0;    
        int num_seg = 10; //any even number
        double E = 0.00001;
        double W = value / num_seg;
        int dof = n - 4;
        double res = calcularinversaIntegral(value, dof, W, num_seg);   
        double d = 0.5;
        double old_error;
        double p = 0.35;
        
        if (Math.abs(p - res) > E) {
            if (res < p) 
                value += d;
             else if (res > p) 
                value -= d; 
            
            while (true) {
                old_error = p - res;
                num_seg *= 2;
                W = value / num_seg;
                res = calcularinversaIntegral(value, dof, W, num_seg);

                if (Math.abs(p - res) <= E) 
                    break;
                
                if (old_error * (p - res) < 0) 
                    d /= 2;

                if (res < p) 
                    value += d;
                else if (res > p) 
                    value -= d;    
            }
        }
     
        return sigma * sqr * value;

    }
    //fin metodo
    
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

}
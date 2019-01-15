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
public class Program6 {

    //Inicia metodo
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);;
        double x = 1.0;
        double old_error;

        double p = sc.nextDouble();
        int dof = sc.nextInt();
        int num_seg = 10; //any even number
        double E = 0.00001;
        double W = x / num_seg;
        double res = calcularDev(x, dof, W, num_seg);
        double d = 0.5;

        if (Math.abs(p - res) > E) {
            if (res < p) 
                x += d;
             else if (res > p) 
                x -= d;    

            while (true) {

                old_error = p - res;

                num_seg *= 2;
                W = x / num_seg;
                res = calcularDev(x, dof, W, num_seg);

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

        System.out.printf("%.5f \n", x);

    }
    //fin metodo

    //Inicia metodo
    private static double calcularDev(double x, int dof, double W, int num_seg) {

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

    //reused
    //Inicia metodo
    private static double factorial(double numero) {
        numero -= 1;
        //es diferente formula para un double y un int, verifica que es
        int num = (int) numero;
        double factorial;
        if (num == numero) {
            factorial = 1;
        } else {
            factorial = Math.sqrt(Math.PI);
        }

        while (numero > 0) {
            factorial = factorial * numero;
            numero--;

        }

        return factorial;
    }
    //Fin metodo  
}

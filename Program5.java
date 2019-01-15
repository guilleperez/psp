package practicas;
import java.util.Locale;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Julio
 */
public class Program5 {
    //Inicia metodo
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);;
        double x;
        int dof;
        x = sc.nextDouble();
        dof = sc.nextInt();
        int num_seg = 10; //any even number
        double E =0.00001;
        double W = x / num_seg;
        double res =  calcularDev(x, dof, W, num_seg);
        double new_res;
        
        while (true){
           num_seg *= 2; 
           W = x / num_seg;
           new_res = calcularDev(x, dof, W, num_seg);
           
           if( Math.abs(res - new_res) < E)
               break;
           res = new_res;
            
        }

           System.out.printf("%.5f\n" ,new_res);
        
       
   
    }
    //fin metodo

    //Inicia metodo
    private static double calcularDev(double x, int dof, double W, int num_seg) {   
        
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
   
}


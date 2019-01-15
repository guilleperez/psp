package practicas;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author guille_pt
 */
public class Program2 {
    

    //Inicia metodo
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/practicas/Program8.java");
        Scanner sc = new Scanner(file);
        int lineaMetodo = 0;
        int totalMetodos = 0;
        int totalLineas = 0;
        boolean esUnMetodo = false;
        String nombreMetodo = "";
        System.out.printf("%-20s%-20s%-20s%-20s\n","Part Name","Number Of Items","Part Size","Total Size");
        while(sc.hasNextLine()){
            String linea  = sc.nextLine().trim();
            if(linea.equalsIgnoreCase("//Inicia metodo")){
                nombreMetodo = sc.nextLine().trim().split(" ")[3] ;
                nombreMetodo = nombreMetodo.substring(0, nombreMetodo.indexOf('('));
                totalMetodos ++;
                esUnMetodo = true;
            }else if(linea.equalsIgnoreCase("//fin metodo") ){
                totalLineas += lineaMetodo;
                System.out.printf("%-20s\t%-20s%-20s\n" ,nombreMetodo,"1",String.valueOf(lineaMetodo));
                lineaMetodo=0;
                esUnMetodo = false;         
            }else if(!linea.equalsIgnoreCase("")){
                if(esUnMetodo){

                    if(verificarComentario(linea) && !linea.equalsIgnoreCase("//fin metodo") ){                  
                        lineaMetodo ++;
                     }
                }else if(verificarComentario(linea)){
                    totalLineas++;
                }
            }   
        }
        System.out.printf("%-60s\t%s\n" ,"", String.valueOf(totalLineas));
        System.out.println("Total Parts: " + totalMetodos);
    }
               //Fin metodo  


    //Inicia metodo
    private static boolean verificarComentario(String linea) {
        //es un comentario
       if((linea.contains("/*")/**/ && !linea.contains("*/")) || linea.charAt(0) == '*' || linea.charAt(0) == '/' )
            return false;
       if(linea.charAt(0) == '*')
           return false;
       
   
       return true;
       
    }
               //Fin metodo  
 
}

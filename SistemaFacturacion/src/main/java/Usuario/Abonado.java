/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuario;

import Medidor.Medidor;
import Factura.Factura;
import Medidor.MedidorInteligente;

import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localDate;

/**
 *
 * @author cebor
 */
public class Abonado extends Usuario{
    private String nombre;  
    private String cedula;
    private String correo;
    private String direccion;
    private ArrayList<Medidor> medidores;
    
    
    public Abonado(String cedula, String contraseña, String correo, String direccion, String nombre, ArrayList<Medidor> medidores){
      super(cedula,contraseña);
      this.correo = correo;
      this.direccion = direccion;
      this.nombre = nombre;
      this.cedula = cedula;
      this.medidores = medidores;
    }
    
    public Abonado(String cedula, String contraseña, String correo, String direccion, String nombre){
        super(cedula, contraseña);
        this.correo = correo;
        this.direccion = direccion;
        this.nombre = nombre;
        this.cedula = cedula;
    }

    public String getCedula(){
      return cedula;
    }

    public String getCorreo(){
      return correo;
    }
    
    public String getDireccion(){
      return direccion;
    }
    
    public String getNombre(){
      return nombre;
    }

    public ArrayList<Medidor> getMedidores() {
        return medidores;
    }

    public void consultarFactura(Factura facturas){

    }
    ///(ab.getMedidores)
    public void consultarHistoricoFacturado(ArrayList<Medidor> medidores){
   

    Scanner sc = new Scanner(System.in);
      
       String titulo="Medidores Asosciados:",c1= "Codigo de Medidor" ,c2="Tipo de Medidor" ,c3="Nombre del Plan";

      System.out.println(titulo+"\n");////////////////
      System.out.printf("%-20s%-20s%-20s\n",c1,c2,c3);
      String b="";
    for(Medidor medidor:medidores){              ///////////0020 inteligente  comercial1
      if (medidor instanceof MedidorInteligente){
        b="inteligente";
      }else{
        b="analógico";
      }
      String a=medidor.getCodigoMedidor(),c=medidor.getPlan().getNombrePlan();
      System.out.printf("%-20s%-20s%-20s\n",a,b,c);   
    } 
    System.out.println("Ingrese código factura:");
    String codigoM = sc.nextLine();
     for (Medidor med:medidores){
        if(med.getCodigoMedidor().equals(codigoM)){
          med.calcularConsumo(med.getLecturas());

        }
     }

    }

    public void consumoPorHoras(Medidor medidores){
      Scanner sc = new Scanner(System.in);
      System.out.println("Ingrese código factura:");
    String codigoM = sc.nextLine();
        String titulo="Medidores Asosciados:",c1= "Codigo de Medidor" ,c2="Tipo de Medidor" ,c3="Nombre del Plan";

      System.out.println(titulo+"\n");////////////////
      System.out.printf("%-20s%-20s%-20s\n",c1,c2,c3);


    }

   
   public boolean equals(Object obj){
     if(obj !=null){
       if(obj instanceof Abonado){
         Abonado ab =(Abonado)obj;
         if(nombre.equals(ab.nombre)){
           return true;
         }
         }
       }return false;
     }

     public void consultarFactura2(ArrayList<Factura> facturas){
        
        
      String titulo="Facturas Asociadas",c1= "Numero de Factura" ,c2="Fecha de Emisión" ,c3="Codigo Medidor";
      double total =0;
      for(Factura factura:facturas){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-LLLL-yyyy");
        String s=factura.getFechaEmision().toLocalDate().format(formatter);
      String a=factura.getCodigoFactura(),b=s,c=factura.getMedidor().getCodigoMedidor();
      System.out.println(titulo+"\n");////////////////
      System.out.printf("%-20s%-20s%-20s\n",c1,c2,c3);
      System.out.printf("%-20s%-20s%-20s\n",a,b,c);
    
    }

      Scanner sc = new Scanner(System.in);
      System.out.println("Ingrese código factura: ");
      String codigo = sc.nextLine();


       for(Factura f:facturas){
      
      if(codigo.equals(f.getCodigoFactura())){
          total= f.getMedidor().calcularCosto(f.getMedidor().getLecturas(),f.getPlan()) + total;
      }
       }
    for(Factura f:facturas){
      
      if(codigo.equals(f.getCodigoFactura())){

        double consumoAct = facturas.get(facturas.size()-1).getConsumo() - f.getConsumo();
         long diasFacturados=ChronoUnit.DAYS.between(f.getFechaEmision(),facturas.get(facturas.size()-1).getFechaEmision() );
       System.out.println("Medidor: "+f.getMedidor().getCodigoMedidor());
        System.out.println("Nombre del Plan: "+f.getPlan().getNombrePlan());
        System.out.println("Desde: "+f.getFechaEmision());
        System.out.println("Hasta: "+facturas.get(facturas.size()-1).getFechaEmision());
        System.out.println("Días Facturados: "+ diasFacturados);
        System.out.println("Lectura Anterior: "+ f.getConsumo());
        System.out.println("Lectura Actual: "+facturas.get(facturas.size()-1).getConsumo());
        System.out.println("Consumo: "+ consumoAct);
        System.out.println("Cargo Fijo: "+ f.getPlan().getCargoBase());
        System.out.println("Total a pagar: "+ total);
      }
    }
    }    
}
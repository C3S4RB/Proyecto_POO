/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuario;

import Correo.Correo;
import Medidor.Medidor;
import Medidor.MedidorInteligente;
import Medidor.MedidorAnalogico;
import Factura.Plan;
import Factura.Factura;
import Factura.HorarioPico;
import java.time.*;
import java.util.Scanner;
import java.util.*;
import java.util.UUID;
import java.util.Arrays;
import java.util.Random;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author cebor
 */
public class Administrador extends Usuario {

    //private Abonado abonado;
    //private LocalDateTime fechaFin;
    //private LocalDateTime fechaIn;
    //private ArrayList<Factura> facturas;
    Scanner sc = new Scanner(System.in);

    public Administrador(String usuario, String contraseña) {
        super(usuario, contraseña);
    }

    public Abonado registrarAbonado(String cedula) {

        System.out.print("Ingrese correo nombre del abonado: ");
        String correo = sc.nextLine();
        System.out.print("Ingrese nombre del abonado: ");
        String nombre = sc.nextLine();

        //la contraseña es un valor al azar que contengan 8 caracteres al menos una letra mayúscula y un dígito
        //La contraseña es un valor unico contiene numeros y letras
        Random random = new Random();
        ArrayList<String> vocales = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));
        int x = random.nextInt(vocales.size());
        int y = random.nextInt(vocales.size());
        String l1 = vocales.get(x);
        String l2 = vocales.get(y).toLowerCase();
        String a = UUID.randomUUID().toString().toUpperCase().substring(0, 6);
        String contraseña = l1 + a + l2;

        System.out.println("Ingrese su direccion: ");
        String direccion = sc.nextLine();
        //Nombre de USUARIO como cedula
        String contenido = "Usuario: " + cedula + "\ncontraseña: " + contraseña;
        Correo.enviarEMail(correo, "Se registro en el sistema con las siguientes credenciales", contenido);

        return new Abonado(cedula, contraseña, correo, direccion, nombre);
    }

    public Plan registrarPlan(String nombrePlan, double costo, ArrayList<String> Lprovincia, double cargoBase, ArrayList<HorarioPico> horasPico) {
        return new Plan(nombrePlan, costo, Lprovincia, cargoBase, horasPico);
    }

    //Hay que registrar el medidor
    public Medidor registrarMedidor(String direccion, String tipo_medidor, Plan plan,Abonado abonado) {
        String correo = abonado.getCorreo();
        if (tipo_medidor.equals("inteligente")) {
            String contenido = "Dirección: " + direccion + "\nTipo de medidor: " + tipo_medidor+"\nTipo de plan: "+plan.getNombrePlan();
            Correo.enviarEMail(correo, "Los datos del medidor registrado son", contenido);
            return new MedidorInteligente(plan, direccion);
        }////////////estamal
        else {
            String contenido = "Dirección: " + direccion + "\nTipo de medidor: " + tipo_medidor+"\nTipo de plan: "+plan.getNombrePlan();
            Correo.enviarEMail(correo, "Los datos del medidor registrado son", contenido);
            return new MedidorAnalogico(plan, direccion);
        }
    }

///simularMedicion
    public void simularMedicion(LocalDateTime fechaInicio, LocalDateTime fechaFin, ArrayList<Medidor> medidores) {
        System.out.println("Fecha inicio:" + fechaInicio);
        System.out.println("Fecha Fin:" + fechaFin);
        ArrayList<MedidorInteligente> medidoresInteligentes = new ArrayList<>();

        for (Medidor medidor : medidores) {

            if (medidor instanceof MedidorInteligente) {
                MedidorInteligente mi = (MedidorInteligente) medidor;
                medidoresInteligentes.add(mi);
                //  ArrayList<Lectura> lecturas = medidor.getLecturas();
                //for(Lectura )         
            }
            for (MedidorInteligente medidorI : medidoresInteligentes) {
                System.out.println("Lecturas para el medidor con codigo" + medidorI.getCodigoMedidor() + "con valor actual" + medidorI.getConsumoUltimaFecha());
                for (int x = 0; x < medidorI.getLecturas().size(); x++) {
                    if ((fechaInicio.compareTo(medidorI.getLecturas().get(x).getFechaToma()) < 0 || fechaInicio.compareTo(medidorI.getLecturas().get(x).getFechaToma()) == 0) && fechaFin.compareTo(medidorI.getLecturas().get(x).getFechaToma()) > 0) {
                        System.out.println(medidorI.toString(x));
                    }
                }
            }
        } // int    n=abonado.getMedidores.size()-1;
    }
    //         (abonado.getMedidores.get(n),abonado)

    public void realizarFacturacion(Medidor medidor, Abonado abonado, ArrayList<Factura> facturas) {
        Factura factura = new Factura(medidor);

        double actualKw = factura.getMedidor().getLecturas().get(factura.getMedidor().getLecturas().size() - 1).getKilovatios();
        LocalDateTime fInicio = factura.getMedidor().getLecturas().get(0).getFechaToma();
        LocalDateTime fFin = factura.getMedidor().getLecturas().get(factura.getMedidor().getLecturas().size() - 1).getFechaToma();
        long difDias = ChronoUnit.DAYS.between(fInicio, fFin);

        System.out.println("Fecha de emisión: " + factura.getFechaEmision());
        System.out.println("Código del Medidor: " + factura.getMedidor().getCodigoMedidor());
        System.out.println("Nombre del Plan: " + factura.getMedidor().getPlan().getNombrePlan());
        System.out.println("Fecha lectura anterior: " + factura.getMedidor().getUltimaFechaCobrado());//
        System.out.println("Fecha lectura actual: " + fFin);
        System.out.println("Número de días Facturados: " + difDias + "días");///
        System.out.println("Kw lectura anterior: " + factura.getMedidor().getConsumoUltimaFecha() + " Kw");///falta
        System.out.println("Kw lectura actual: " + actualKw + "Kw");///falta
        System.out.println("Consumo en kilovatios: " + factura.getConsumo() + " Kw");
        System.out.println("Cargo Fijo del Plan: " + factura.getMedidor().getPlan().getCargoBase());
        System.out.println("Total a pagar: " + factura.getMedidor().calcularCosto(factura.getMedidor().getLecturas(), factura.getMedidor().getPlan()));
        facturas.add(factura);
        ///enviar al correo del Abonado
    }
}

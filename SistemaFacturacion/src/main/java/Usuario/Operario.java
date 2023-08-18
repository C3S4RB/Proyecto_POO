/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuario;

import Factura.Lectura;
import Medidor.Medidor;
import Medidor.MedidorAnalogico;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.*;

/**
 *
 * @author cebor
 *
 */
public class Operario extends Usuario {

    Scanner sc = new Scanner(System.in);

    public Operario(String usuario, String contraseña) {
        super(usuario, contraseña);
    }
    private String obtenerCodigoMedidor() {
        System.out.println("Ingrese el código del medidor: ");
        return sc.nextLine();
    }
    
    private void procesarMedicion(MedidorAnalogico medidor, Abonado abonado) {
        System.out.println("Medidor analógico a nombre de " + abonado.getNombre() + "\nÚltima lectura realizada " + medidor.getUltimaFechaCobrado() + "\nLectura anterior " + medidor.getConsumoUltimaFecha() + " Kw");
        System.out.println("Ingrese la lectura actual: ");
        double lecturaActual = sc.nextDouble();
        sc.nextLine();
        double kw = lecturaActual - medidor.getConsumoUltimaFecha();
        System.out.println("Kilovatios consumidos " + kw + " Kw."+"\nOperario: " + getUsuario());
        MedidorAnalogico medidorAnalogico = (MedidorAnalogico) medidor;
        medidorAnalogico.setOperario(this);
        Lectura lectura = new Lectura(LocalDateTime.now(), lecturaActual);
        medidorAnalogico.getLecturas().add(lectura);
    }

    private Abonado buscarAbonado(Medidor medidor, ArrayList<Usuario> usuarios) {
        if (medidor instanceof MedidorAnalogico) {
            String direccionMedidor = medidor.getDireccion();
            for (Usuario usuario : usuarios) {
                if (usuario instanceof Abonado) {
                    Abonado abonado = (Abonado) usuario;
                    if (abonado.getDireccion().equals(direccionMedidor)) {
                        return abonado;
                    }
                }
            }
        }
        return null;
    }
    public boolean encontrarMedidor(ArrayList<Medidor> medidores, ArrayList<Usuario> usuarios){
        for (Medidor medidor : medidores) {
            if (medidor.getCodigoMedidor().equals(obtenerCodigoMedidor())) {
                if (medidor instanceof MedidorAnalogico) {
                    Abonado abonado = buscarAbonado(medidor, usuarios);
                    if (abonado != null) {
                        procesarMedicion((MedidorAnalogico) medidor, abonado);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void registrarMedicion(ArrayList<Medidor> medidores, ArrayList<Usuario> usuarios) { 
        boolean medidorEncontrado;
        do {
            medidorEncontrado = encontrarMedidor(medidores, usuarios);
            if (!medidorEncontrado) {
                System.out.println("Código del medidor inválido. Ingrese uno válido.");
            }     
        }while(!medidorEncontrado);
    }
    
}

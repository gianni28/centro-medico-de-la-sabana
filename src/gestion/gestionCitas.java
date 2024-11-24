/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Citas;

/**
 *
 * @author Gianni
 */
public class gestionCitas {

    private String ruta;

    public gestionCitas() {
        this.ruta = "./ARCHIVOS/citas.txt";
        this.verificarArchivo();
    }

    private void verificarArchivo() {
        try {
            File filex = new File(this.ruta);
            if (!filex.exists()) {
                filex.createNewFile();
            }
        } catch (IOException ex) {
            System.out.println("Problemas con la ruta" + ex);
        }
    }

    public void guardarCita(String identificacionPac, String especialidadDoc, String identificacionDoc, String fechaCita, String diaCita, String horaCita) {
        // Generar código de cita aleatorio de 10 dígitos
        String codigoCita = generarCodigoCita();

        // Crear la cadena de texto con los datos de la cita
        String cita = String.format("%s,%s,%s,%s,%s,%s,%s", codigoCita, identificacionPac, especialidadDoc, identificacionDoc, fechaCita, diaCita, horaCita);

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(this.ruta, true))) {
            // Escribir la cita en el archivo
            writer.write(cita);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar la cita: " + e.getMessage());
        }
    }

    private String generarCodigoCita() {
        // Generar un número aleatorio de 10 dígitos como código de cita
        long codigo = (long) (Math.random() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(codigo);
    }

    public boolean existeCitaProgramada(String identificacionDoc, String fechaCita, String horaCita) {
        try {
            // Abrir el archivo de citas para lectura
            BufferedReader reader = new BufferedReader(new FileReader(this.ruta));
            String line;

            // Leer cada línea del archivo
            while ((line = reader.readLine()) != null) {
                // Separar los campos utilizando un separador (por ejemplo, coma)
                String[] fields = line.split(",");

                // Obtener los valores de los campos relevantes
                String doctor = fields[3];
                String fecha = fields[4];
                String hora = fields[6];

                // Comparar los valores con la nueva cita
                if (doctor.equals(identificacionDoc) && fecha.equals(fechaCita) && hora.equals(horaCita)) {
                    // Cita encontrada, cerrar el archivo y retornar true
                    reader.close();
                    return true;
                }
            }

            // No se encontró ninguna cita con los mismos valores, cerrar el archivo y retornar false
            reader.close();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
           
            return false; // En este caso, se retorna false como valor predeterminado
        }
    }
    public void eliminarCita(Citas cita) {
        List<Citas> citas = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader(new FileReader(this.ruta))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 7) {
                    String codigo = datos[0].trim();
                    if (!codigo.equals(cita.getCodigoCita())) {
                        String identificacionPac = datos[1].trim();
                        String especialidadDoc = datos[2].trim();
                        String identificacionDoc = datos[3].trim();
                        String fechaCita = datos[4].trim();
                        String diaCita = datos[5].trim();
                        String horaCita = datos[6].trim();
                        Citas c = new Citas(codigo, identificacionPac, especialidadDoc, identificacionDoc, fechaCita, diaCita, horaCita);
                        citas.add(c);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(this.ruta))) {
            for (Citas c : citas) {
                writer.write(c.getCodigoCita() + ","
                        + c.getIdentificacionPac() + ","
                        + c.getEspecialidadDoc() + ","
                        + c.getIdentificacionDoc() + ","
                        + c.getFechaCita() + ","
                        + c.getDiaCita() + ","
                        + c.getHoraCita());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    




}

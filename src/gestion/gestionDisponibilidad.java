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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Disponibilidad;

/**
 *
 * @author Gianni
 */
public class gestionDisponibilidad {
    private String ruta;

    public gestionDisponibilidad() {
        this.ruta = "./ARCHIVOS/disponibilidad.txt";
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
    
    public ObservableList<Disponibilidad> cargarDisponibilidades() {
        ObservableList<Disponibilidad> disponibilidades = FXCollections.observableArrayList();
        try ( BufferedReader reader = new BufferedReader(new FileReader(this.ruta))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 5) {
                    String codigo = datos[0].trim();
                    String identificacionMedico = datos[1].trim();
                    String dia = datos[2].trim();
                    String horaDesde = datos[3].trim();
                    String horaHasta = datos[4].trim();
                    Disponibilidad disponibilidad = new Disponibilidad(codigo, identificacionMedico, dia, horaDesde, horaHasta);
                    disponibilidades.add(disponibilidad);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return disponibilidades;
    }

    public void guardarDisponibilidad(Disponibilidad disponibilidad) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(this.ruta, true))) {
            writer.write(disponibilidad.getCodigoDispo() + ","
                    + disponibilidad.getIdentificacionDoc() + ","
                    + disponibilidad.getDia() + ","
                    + disponibilidad.getDesde()+ ","
                    + disponibilidad.getHasta());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void eliminarDisponibilidad(Disponibilidad disponibilidad) {
        List<Disponibilidad> disponibilidades = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader(new FileReader(this.ruta))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 5) {
                    String codigo = datos[0].trim();
                    if (!codigo.equals(disponibilidad.getCodigoDispo())) {
                        String identificacionMedico = datos[1].trim();
                        String dia = datos[2].trim();
                        String horaDesde = datos[3].trim();
                        String horaHasta = datos[4].trim();
                        Disponibilidad disp = new Disponibilidad(codigo, identificacionMedico, dia, horaDesde, horaHasta);
                        disponibilidades.add(disp);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(this.ruta))) {
            for (Disponibilidad disp : disponibilidades) {
                writer.write(disp.getCodigoDispo()+ ","
                        + disp.getIdentificacionDoc()+ ","
                        + disp.getDia() + ","
                        + disp.getDesde() + ","
                        + disp.getHasta());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public boolean existeDisponibilidad(String identificacionMedico, String dia, String horaDesde, String horaHasta) {
        try (BufferedReader reader = new BufferedReader(new FileReader(this.ruta))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 5) {
                    String codigo = datos[0].trim();
                    String idMedico = datos[1].trim();
                    String diaDispo = datos[2].trim();
                    String desdeDispo = datos[3].trim();
                    String hastaDispo = datos[4].trim();
                    if (idMedico.equals(identificacionMedico) && diaDispo.equals(dia) && desdeDispo.equals(horaDesde) && hastaDispo.equals(horaHasta)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public ObservableList<Disponibilidad> obtenerDisponibilidadesPorMedico(String identificacionMedico) {
        ObservableList<Disponibilidad> disponibilidadesMedico = FXCollections.observableArrayList();
        try ( BufferedReader reader = new BufferedReader(new FileReader(this.ruta))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 5) {
                    String codigo = datos[0].trim();
                    String idMedico = datos[1].trim();
                    String diaDispo = datos[2].trim();
                    String desdeDispo = datos[3].trim();
                    String hastaDispo = datos[4].trim();
                    if (idMedico.equals(identificacionMedico)) {
                        Disponibilidad disponibilidad = new Disponibilidad(codigo, idMedico, diaDispo, desdeDispo, hastaDispo);
                        disponibilidadesMedico.add(disponibilidad);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return disponibilidadesMedico;
    }
    
}

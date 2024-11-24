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
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import modelo.Especialidad;
import modelo.Medicos;

/**
 *
 * @author Gianni
 */
public class gestionMedicos {
    
    private String ruta;

    public gestionMedicos() {
        this.ruta = "./ARCHIVOS/medicos.txt";
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
    
    public void agregarMedico(Medicos medico) {
        ObservableList<Medicos> medicosActuales = cargarMedicos();

        // Verificar si el médico ya existe
        if (medicosActuales.contains(medico)) {
            showMessages("Ya existe un médico con esa identificación.", 1);
            return;
        }

        medicosActuales.add(medico);

        guardarMedicos(medicosActuales);
    }

    public ObservableList<Medicos> buscarMedicosPorEspecialidad(String especialidad) {
        ObservableList<Medicos> medicosEncontrados = FXCollections.observableArrayList();

        ObservableList<Medicos> medicosActuales = cargarMedicos();

        for (Medicos medico : medicosActuales) {
            if (medico.getEspecialidad().equals(especialidad)) {
                medicosEncontrados.add(medico);
            }
        }

        return medicosEncontrados;
    }

    public ObservableList<Especialidad> obtenerEspecialidades() {
        gestionEspecialidades gestionEspecialidades = new gestionEspecialidades();
        return gestionEspecialidades.cargarEspecialidades();
    }

    public void guardarMedicos(ObservableList<Medicos> medicos) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            for (Medicos medico : medicos) {
                writer.write(
                        medico.getIdentificacion() + ","
                        + medico.getNombre() + ","
                        + medico.getApellido() + ","
                        + medico.getEmail() + ","
                        + medico.getTelefono() + ","
                        + medico.getEspecialidad() + ","
                        + medico.getNombreFoto()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean guardarMedicos(Medicos medico) {
        ObservableList<Medicos> medicosActuales = cargarMedicos();

        // Verificar si el médico ya existe
        if (medicosActuales.contains(medico)) {
            showMessages("Ya existe un médico con esa identificación.", 1);
            return false;
        }

        medicosActuales.add(medico);

        guardarMedicos(medicosActuales);
        return true;
    }

    private ObservableList<Medicos> cargarMedicos() {
        ObservableList<Medicos> medicosCargados = FXCollections.observableArrayList();

        try ( BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 7) {
                    String identificacion = datos[0];
                    String nombre = datos[1];
                    String apellido = datos[2];
                    String email = datos[3];
                    String telefono = datos[4];
                    String especialidad = datos[5];
                    String nombreFoto = datos[6];
                    Medicos medico = new Medicos(identificacion, nombre, apellido, email, telefono, especialidad, nombreFoto);
                    medicosCargados.add(medico);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al cargar los médicos: " + ex.getMessage());
        }
        
        return medicosCargados;
    }

    //=====================================================================
    
    private boolean showMessages(String mesg, int caso) {
        Alert msg;
        boolean ok = false;

        if (caso == 1)//Error
        {
            msg = new Alert(Alert.AlertType.ERROR);
            msg.setTitle("ERROR");

            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.showAndWait();
        }
        if (caso == 2)//Notificacion
        {
            msg = new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle("LISTO");

            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.showAndWait();
        }
        if (caso == 3)//Confirmacion
        {
            msg = new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("Petición Eliminación");

            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.initStyle(StageStyle.UTILITY);

            Optional<ButtonType> result = msg.showAndWait();
            if (result.get() == ButtonType.OK) {
                ok = true;
            }
        }
        return ok;
    }
}

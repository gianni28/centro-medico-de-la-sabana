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
import modelo.Pacientes;

/**
 *
 * @author Gianni
 */
public class gestionPacientes {
    private String ruta;
    
    private ObservableList<Pacientes> listaPacientes;


    public gestionPacientes() {
        this.ruta = "./ARCHIVOS/pacientes.txt";
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
    
    public void agregarPaciente(Pacientes paciente) {
        ObservableList<Pacientes> pacientesActuales = cargarPacientes();

        // Verificar si el paciente ya existe
        if (pacientesActuales.contains(paciente)) {
            showMessages("Ya existe un paciente con esa identificación.", 1);
            return;
        }

        pacientesActuales.add(paciente);

        guardarPacientes(pacientesActuales);
    }
    
    public boolean eliminarPaciente(String identificacion) {
        ObservableList<Pacientes> pacientesActuales = cargarPacientes();

        Optional<Pacientes> pacienteEncontrado = pacientesActuales.stream()
                .filter(p -> p.getIdentificacion().equals(identificacion))
                .findFirst();

        if (pacienteEncontrado.isPresent()) {
            boolean confirmacion = showMessages("¿Estás seguro de eliminar al paciente?", 3);
            if (confirmacion) {
                pacientesActuales.remove(pacienteEncontrado.get());
                guardarPacientes(pacientesActuales);
                return true;
            }
        } else {
            showMessages("No se encontró un paciente con esa identificación.", 1);
        }

        return false;
    }

    
    public void modificarPaciente(Pacientes paciente) {
        ObservableList<Pacientes> pacientesActuales = cargarPacientes();

        // Verificar si el paciente existe
        int index = -1;
        for (int i = 0; i < pacientesActuales.size(); i++) {
            if (pacientesActuales.get(i).getIdentificacion().equals(paciente.getIdentificacion())) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            showMessages("No se encontró un paciente con esa identificación.", 1);
            return;
        }

        pacientesActuales.set(index, paciente);

        guardarPacientes(pacientesActuales);
    }


    public ObservableList<Pacientes> buscarPacientesPorIdentificacion(String identificacion) {
        ObservableList<Pacientes> pacientesEncontrados = FXCollections.observableArrayList();

        ObservableList<Pacientes> pacientesActuales = cargarPacientes();

        for (Pacientes paciente : pacientesActuales) {
            if (paciente.getIdentificacion().equals(identificacion)) {
                pacientesEncontrados.add(paciente);
            }
        }

        return pacientesEncontrados;
    }

    public void guardarPacientes(ObservableList<Pacientes> pacientes) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            for (Pacientes paciente : pacientes) {
                writer.write(
                        paciente.getIdentificacion() + ","
                        + paciente.getNombre() + ","
                        + paciente.getApellido() + ","
                        + paciente.getGenero() + ","
                        + paciente.getFechanacimiento() + ","
                        + paciente.getTelefono()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean guardarPaciente(Pacientes paciente) {
        ObservableList<Pacientes> pacientesActuales = cargarPacientes();

        // Verificar si el paciente ya existe
        if (pacientesActuales.contains(paciente)) {
            showMessages("Ya existe un paciente con esa identificación.", 1);
            return false;
        }

        pacientesActuales.add(paciente);

        guardarPacientes(pacientesActuales); // Guardar los pacientes en el archivo

        return true;
    }



    private ObservableList<Pacientes> cargarPacientes() {
        ObservableList<Pacientes> pacientesCargados = FXCollections.observableArrayList();

        try ( BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 6) {
                    String identificacion = datos[0];
                    String nombre = datos[1];
                    String apellido = datos[2];
                    String genero = datos[3];
                    String fechanacimiento = datos[4];
                    String telefono = datos[5];
                    Pacientes paciente = new Pacientes(identificacion, nombre, apellido, genero, fechanacimiento, telefono);
                    pacientesCargados.add(paciente);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al cargar los pacientes: " + ex.getMessage());
        }

        return pacientesCargados;
    }
    
    //===================================================================================
    
    public ObservableList<Pacientes> getPacientes() {
        return listaPacientes;
    }

    
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
            msg.setTitle("NOTIFICACIÓN");

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

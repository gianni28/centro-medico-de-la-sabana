/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Gianni
 */
public class AgendaController implements Initializable {

    @FXML
    private Label lblNombreDoc;
    @FXML
    private Label lblIdDoc;
    @FXML
    private DatePicker dprAgenda;
    @FXML
    private TableView<Cita> tblAgenda;
    @FXML
    private TableColumn<Cita, String> colHora;
    @FXML
    private TableColumn<Cita, String> colIdPaciente;
    @FXML
    private TableColumn<Cita, String> colNombre;
    @FXML
    private TableColumn<Cita, String> colApellido;
    @FXML
    private Button btnAtender;
    @FXML
    private Button btnHistoria;
    @FXML
    private Button btnRegresar;
    
    private String identificacionMedico;
    private String nombreCompletoMedico;
    private ObservableList<Cita> listaCitas;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dprAgenda.setOnAction(this::cargarCitas);
        modelaTablaAgenda();
    }    

    @FXML
    private void goConsulta(ActionEvent event) {
        // Verificar si hay una fila seleccionada
        Cita citaSeleccionada = tblAgenda.getSelectionModel().getSelectedItem();
        if (citaSeleccionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Consultas.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                // Obtener el controlador de la ventana "Consultas"
                ConsultasController consultasController = loader.getController();

                // Pasar los datos de la cita seleccionada al controlador
                consultasController.setCita(citaSeleccionada);
                String identificacionMedico = this.lblIdDoc.getText();
                String nombreMedico = this.lblNombreDoc.getText();
                consultasController.setNombreMedico(nombreMedico);
                consultasController.setIdentificacionMedico(identificacionMedico);

                String fechaSeleccionada = dprAgenda.getValue().toString();
                consultasController.setFechaSeleccionada(fechaSeleccionada);

                Stage stage = new Stage();
                stage.setResizable(false); // No permite redimensionar ventanas
                stage.setTitle("CONSULTA"); // Título de la ventana

                stage.setScene(scene);
                stage.show();

                Stage myStage = (Stage) this.btnAtender.getScene().getWindow();
                myStage.close();
            } catch (IOException ex) {
                Logger.getLogger(AgendaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Mostrar error si no hay fila seleccionada
            showMessages("Primero debe seleccionar una cita.", 1);
        }
    }



    @FXML
    private void goHistoria(ActionEvent event) {
        // Verificar si hay una fila seleccionada
        Cita citaSeleccionada = tblAgenda.getSelectionModel().getSelectedItem();
        if (citaSeleccionada != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Historia.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                // Obtener el controlador de la ventana "Consultas"
                HistoriaController historiaController = loader.getController();

                // Pasar los datos de la cita seleccionada al controlador
                historiaController.setCita(citaSeleccionada);
                //String identificacionMedico = this.lblIdDoc.getText();
                //String nombreMedico = this.lblNombreDoc.getText();
                //historiaController.setNombreMedico(nombreMedico);
                //historiaController.setIdentificacionMedico(identificacionMedico);

                //String fechaSeleccionada = dprAgenda.getValue().toString();
                //historiaController.setFechaSeleccionada(fechaSeleccionada);

                Stage stage = new Stage();
                stage.setResizable(false); // No permite redimensionar ventanas
                stage.setTitle("HISTORIA"); // Título de la ventana

                stage.setScene(scene);
                stage.show();

                Stage myStage = (Stage) this.btnHistoria.getScene().getWindow();
                myStage.close();
            } catch (IOException ex) {
                Logger.getLogger(AgendaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            // Mostrar error si no hay fila seleccionada
            showMessages("Primero debe seleccionar una cita.", 1);
        }
    }

    @FXML
    private void goMedicos(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Medicos.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            //EspecialidadesController ouControlador = loader.getController();
            //ouControlador.constructorNuevo();                                //metodo para inicializar y mandarle cosas
            Stage stage = new Stage();
            stage.setResizable(false);                         //no permite redimensionar ventanas
            stage.setTitle("MÉDICOS");                //titulo de la ventana   

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btnRegresar.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(AgendaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setIdentificacionMedico(String identificacionMedico) {
        this.identificacionMedico = identificacionMedico;
        this.lblIdDoc.setText(identificacionMedico);
    }

    void setNombreMedico(String nombreCompletoMedico) {
        this.nombreCompletoMedico = nombreCompletoMedico;
        this.lblNombreDoc.setText(nombreCompletoMedico);
    }
    
    @FXML
    private void cargarCitas(ActionEvent event) {
        tblAgenda.getItems().clear();
        listaCitas = FXCollections.observableArrayList();

        String fechaSeleccionada = dprAgenda.getValue().toString();
        String archivoCitas = "./ARCHIVOS/citas.txt";
        String archivoPacientes = "./ARCHIVOS/pacientes.txt";

        boolean citasEncontradas = false;

        try ( BufferedReader brCitas = new BufferedReader(new FileReader(archivoCitas));  BufferedReader brPacientes = new BufferedReader(new FileReader(archivoPacientes))) {

            List<String[]> datosPacientes = new ArrayList<>(); // Lista para almacenar los datos de pacientes

            String lineaPacientes;
            while ((lineaPacientes = brPacientes.readLine()) != null) {
                String[] datosPaciente = lineaPacientes.split(",");
                datosPacientes.add(datosPaciente); // Agregar los datos de paciente a la lista
            }

            String lineaCitas;
            while ((lineaCitas = brCitas.readLine()) != null) {
                String[] datosCita = lineaCitas.split(",");

                String idPaciente = datosCita[1];
                String fechaCita = datosCita[4];
                String horaCita = datosCita[6];

                if (fechaCita.equals(fechaSeleccionada)) {
                    boolean pacienteEncontrado = false;

                    for (String[] datosPaciente : datosPacientes) {
                        if (datosPaciente[0].equals(idPaciente)) {
                            String nombrePaciente = datosPaciente[1];
                            String apellidoPaciente = datosPaciente[2];

                            Cita cita = new Cita(horaCita, idPaciente, nombrePaciente, apellidoPaciente);
                            listaCitas.add(cita);
                            citasEncontradas = true;
                            pacienteEncontrado = true;
                            break; // Salir del bucle después de encontrar el paciente correspondiente
                        }
                    }

                    if (!pacienteEncontrado) {
                        // Si no se encuentra el paciente, se muestra un mensaje de error
                        showMessages("Error: No se encontró el paciente con ID " + idPaciente, 1);
                    }
                }
            }

            if (!citasEncontradas) {
                showMessages("No hay citas programadas para la fecha seleccionada.", 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        actualizarTablaCitas();
    }




    
//========================================================================

    public static class Cita {

        private String hora;
        private String idPaciente;
        private String nombrePaciente;
        private String apellidoPaciente;

        public Cita(String hora, String idPaciente, String nombrePaciente, String apellidoPaciente) {
            this.hora = hora;
            this.idPaciente = idPaciente;
            this.nombrePaciente = nombrePaciente;
            this.apellidoPaciente = apellidoPaciente;
        }

        public String getHora() {
            return hora;
        }

        public String getIdPaciente() {
            return idPaciente;
        }

        public String getNombrePaciente() {
            return nombrePaciente;
        }

        public String getApellidoPaciente() {
            return apellidoPaciente;
        }
    }
    
    private void modelaTablaAgenda() {
        this.listaCitas = FXCollections.observableArrayList();

        this.colHora.setCellValueFactory(new PropertyValueFactory("hora"));
        this.colIdPaciente.setCellValueFactory(new PropertyValueFactory("idPaciente"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombrePaciente"));
        this.colApellido.setCellValueFactory(new PropertyValueFactory("apellidoPaciente"));
    }
    
    private void actualizarTablaCitas() {
        ObservableList<Cita> listaCitasObservable = FXCollections.observableArrayList(listaCitas);
        this.tblAgenda.setItems(listaCitasObservable);
    }

    
    //===================================================================
    
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
    


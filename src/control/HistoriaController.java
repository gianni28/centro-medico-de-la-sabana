/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import control.AgendaController.Cita;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modelo.CitaHistoria;

/**
 * FXML Controller class
 *
 * @author Gianni
 */
public class HistoriaController implements Initializable {

    
    @FXML
    private Label lblNombrePaciente;
    @FXML
    private ComboBox<?> cbxFiltrarMedicos;
    @FXML
    private Button btnRegresar;
    @FXML
    private TableView<CitaHistoria> tblCita;
    @FXML
    private TextArea txtDiagnostico;
    @FXML
    private TextArea txtPrescripcion;
    @FXML
    private TextArea txtExamenes;
    
    private AgendaController.Cita cita;
    @FXML
    private Label lblIdPaciente;
    @FXML
    private TableColumn<CitaHistoria, String> colFecha;
    @FXML
    private TableColumn<CitaHistoria, String> colDoctor;
    @FXML
    private TableColumn<CitaHistoria, String> colEspecialidad;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        modelaTablaHistoria();

    }    

    @FXML
    private void doRegresarAgenda(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Agenda.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);


            Stage stage = new Stage();
            stage.setOnCloseRequest(e -> {
                e.consume();
            });          //deshabilita la X de cerrar ventana
            stage.setResizable(false);                         //no permite redimensionar ventanas
            stage.setTitle("AGENDA");                //titulo de la ventana   

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btnRegresar.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(HistoriaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setCita(AgendaController.Cita citaSeleccionada) {
        this.cita = citaSeleccionada;
        String idPaciente = cita.getIdPaciente();
        String nombrePac = cita.getNombrePaciente() + " " + cita.getApellidoPaciente();
        this.lblNombrePaciente.setText(nombrePac);
        this.lblIdPaciente.setText(idPaciente);

        // Leer el archivo citas.txt y filtrar las citas del paciente
        ObservableList<CitaHistoria> citas = FXCollections.observableArrayList();
        try ( BufferedReader br = new BufferedReader(new FileReader("./ARCHIVOS/citas.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datosCita = line.split(",");
                String codigoCita = datosCita[0];
                String pacienteCita = datosCita[1];
                String especialidadMedico = datosCita[2];
                String idMedico = datosCita[3];
                String fecha = datosCita[4];
                String dia = datosCita[5];
                String hora = datosCita[6];

                if (pacienteCita.equals(idPaciente)) {
                    // Buscar el nombre y especialidad del médico en el archivo medicos.txt
                    String nombreMedico = buscarNombreMedico(idMedico);
                   

                    // Crear objeto CitaHistoria y agregarlo a la lista
                    CitaHistoria cita = new CitaHistoria(codigoCita, pacienteCita, especialidadMedico, idMedico, fecha, dia, hora, nombreMedico);
                    citas.add(cita);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Cargar las citas en la tabla
        tblCita.setItems(citas);
    }

    

    
    private void modelaTablaHistoria() {
        this.colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        this.colDoctor.setCellValueFactory(new PropertyValueFactory<>("nombreDoctor"));
        this.colEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidadMedico"));
    }

    private String buscarNombreMedico(String idMedico) {
        try ( BufferedReader br = new BufferedReader(new FileReader("./ARCHIVOS/medicos.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] datosMedico = line.split(",");
                String id = datosMedico[0];
                String nombre = datosMedico[1];
                String apellido = datosMedico[2];

                if (id.equals(idMedico)) {
                    return nombre + " " + apellido;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    @FXML
    private void doMostrarConsulta(MouseEvent event) {
        CitaHistoria citaSeleccionada = tblCita.getSelectionModel().getSelectedItem();
        if (citaSeleccionada != null) {
            String nombreDoctor = citaSeleccionada.getNombreDoctor();
            String fecha = citaSeleccionada.getFecha();

            // Restablecer los valores de los campos de texto
            txtDiagnostico.setText("");
            txtPrescripcion.setText("");
            txtExamenes.setText("");

            try ( BufferedReader br = new BufferedReader(new FileReader("./ARCHIVOS/consultas.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] datosConsulta = line.split(",");
                    String nombreDoctorConsulta = datosConsulta[0];
                    String fechaConsulta = datosConsulta[2];
                    String diagnostico = datosConsulta[4];
                    String prescripcion = datosConsulta[5];
                    String examenes = datosConsulta[6];

                    if (nombreDoctorConsulta.equals(nombreDoctor) && fechaConsulta.equals(fecha)) {
                        txtDiagnostico.setText(diagnostico);
                        txtPrescripcion.setText(prescripcion);
                        txtExamenes.setText(examenes);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}

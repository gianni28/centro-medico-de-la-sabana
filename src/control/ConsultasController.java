/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import control.AgendaController.Cita;
import gestion.gestionConsultas;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Gianni
 */
public class ConsultasController implements Initializable {

    @FXML
    private Label lblNombreDoc;
    @FXML
    private Label lblNombrePac;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblHora;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnModificar;
    @FXML
    private TextArea txtDiagnostico;
    @FXML
    private TextArea txtPrescripcion;
    @FXML
    private TextArea txtExamenes;
    @FXML
    private Button btnRegresar;

    private Cita cita;
    private String identificacionMedico;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void doGuardar(ActionEvent event) {
        String nombreDoc = lblNombreDoc.getText();
        String nombrePac = lblNombrePac.getText();
        String fecha = lblFecha.getText();
        String hora = lblHora.getText();
        String diagnostico = txtDiagnostico.getText();
        String prescripcion = txtPrescripcion.getText();
        String examenes = txtExamenes.getText();

        gestionConsultas gestionConsultas = new gestionConsultas();
        // Llamar al método para guardar los datos en el archivo
        gestionConsultas.guardarConsulta(nombreDoc, nombrePac, fecha, hora, diagnostico, prescripcion, examenes);
        showMessages("La consulta se ha guardado exitosamente.",2);
    }


    @FXML
    private void doModificar(ActionEvent event) {
    }

    @FXML
    private void doGoAgenda(ActionEvent event) {
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Agenda.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            AgendaController agendaController = loader.getController();
            String identificacionMedico = this.identificacionMedico;
            String nombreDoc = this.lblNombreDoc.getText();
            agendaController.setNombreMedico(nombreDoc);
            agendaController.setIdentificacionMedico(identificacionMedico);                                //metodo para inicializar y mandarle cosas

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
            Logger.getLogger(ConsultasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    void setCita(AgendaController.Cita citaSeleccionada) {
        this.cita = citaSeleccionada;
        this.lblHora.setText(cita.getHora());
        String nombrePac = cita.getNombrePaciente() + " " + cita.getApellidoPaciente();
        this.lblNombrePac.setText(nombrePac);

        String nombreDoc = lblNombreDoc.getText(); // Agrega esta línea
        this.setLeerCita(nombreDoc, nombrePac); // Llama a setLeerCita() con los valores adecuados
    }


    void setNombreMedico(String nombreMedico) {
        this.lblNombreDoc.setText(nombreMedico);
    }

    void setIdentificacionMedico(String identificacionMedico) {
        this.identificacionMedico = identificacionMedico;
    }

    void setFechaSeleccionada(String fechaSeleccionada) {
        this.lblFecha.setText(fechaSeleccionada);
    }

    private void setLeerCita(String nombreDoc, String nombrePac) {
        String consultaArchivo;
        try ( BufferedReader br = new BufferedReader(new FileReader("./ARCHIVOS/consultas.txt"))) {
            while ((consultaArchivo = br.readLine()) != null) {
                String[] datosConsulta = consultaArchivo.split(",");
                
                String nombrePacienteArchivo = datosConsulta[1].trim().replaceAll("\\s+", " ").toLowerCase();
                
                String nombrePacNormalizado = nombrePac.trim().replaceAll("\\s+", " ").toLowerCase();

                if (nombrePacienteArchivo.equalsIgnoreCase(nombrePacNormalizado)) 
                {
                    String horaArchivo = datosConsulta[3];
                    String diagnosticoArchivo = datosConsulta[4];
                    String prescripcionArchivo = datosConsulta[5];
                    String examenesArchivo = datosConsulta[6];

                    lblHora.setText(horaArchivo);
                    txtDiagnostico.setText(diagnosticoArchivo);
                    txtPrescripcion.setText(prescripcionArchivo);
                    txtExamenes.setText(examenesArchivo);

                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

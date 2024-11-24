/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.gestionDisponibilidad;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Disponibilidad;
import modelo.Medicos;



/**
 * FXML Controller class
 *
 * @author Gianni
 */
public class DisponibilidadController implements Initializable {

    @FXML
    private Label lblIdMedDispo;
    @FXML
    private Button btnGuardarDispo;
    @FXML
    private Button btnEliminarDispo;
    @FXML
    private Button btnRegresarDispo;
    @FXML
    private TableView<Disponibilidad> tblDispo;
    @FXML
    private TableColumn<Disponibilidad, String> colDiaDispo;
    @FXML
    private TableColumn<Disponibilidad, String> colDesdeDispo;
    @FXML
    private TableColumn<Disponibilidad, String> colHastaDispo;
    @FXML
    private ComboBox<String> cbxDiaDispo;
    @FXML
    private ComboBox<String> cbxDesdeDispo;
    @FXML
    private ComboBox<String> cbxHastaDispo;

    private ObservableList<Disponibilidad> disponibilidades;
    
    private gestionDisponibilidad gestionDisponibilidad;
    
    private String identificacionMedico;
    
    public void setIdentificacionMedico(String identificacionMedico) {
        this.identificacionMedico = identificacionMedico;
        this.lblIdMedDispo.setText(identificacionMedico);
        cargarDisponibilidades();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        gestionDisponibilidad = new gestionDisponibilidad();
        modelaTabla();
        cargarComboboxes();
        cargarDisponibilidades();
    }    

    @FXML
    private void doGuardarDispo(ActionEvent event) {
        String identificacionMedico = this.lblIdMedDispo.getText();
        String dia = this.cbxDiaDispo.getValue();
        String horaDesde = this.cbxDesdeDispo.getValue();
        String horaHasta = this.cbxHastaDispo.getValue();

        if (identificacionMedico.isEmpty() || dia == null || horaDesde == null || horaHasta == null) {
            showMessages("Por favor, complete todos los campos antes de guardar.", 1);
            return;
        }

        LocalTime desde = LocalTime.parse(horaDesde);
        LocalTime hasta = LocalTime.parse(horaHasta);

        if (desde.isBefore(hasta)) {
            if (!gestionDisponibilidad.existeDisponibilidad(identificacionMedico, dia, desde.toString(), hasta.toString())) {
                String codigo = generarCodigoAleatorio();
                Disponibilidad disponibilidad = new Disponibilidad(codigo, identificacionMedico, dia, desde.toString(), hasta.toString());
                gestionDisponibilidad.guardarDisponibilidad(disponibilidad);
                this.tblDispo.getItems().add(disponibilidad);
                limpiarCampos();
                showMessages("La disponibilidad se ha guardado exitosamente.", 2); // Mostrar mensaje de notificación
            } else {
                showMessages("Ya existe una disponibilidad para el médico en el día y horario especificado", 1);
            }
        } else {
            showMessages("La hora de inicio debe ser anterior a la hora de fin", 1);
        }
    }

    @FXML
    private void doEliminarDispo(ActionEvent event) {
        Disponibilidad disponibilidadSeleccionada = this.tblDispo.getSelectionModel().getSelectedItem();
        if (disponibilidadSeleccionada == null) {
            showMessages("Para eliminar, primero debes seleccionar una disponibilidad.", 1);
            return;
        }

        boolean confirmacion = showMessages("¿Estás seguro de eliminar la disponibilidad?", 3);
        if (confirmacion) {
            gestionDisponibilidad.eliminarDisponibilidad(disponibilidadSeleccionada);
            this.tblDispo.getItems().remove(disponibilidadSeleccionada);
            showMessages("La disponibilidad se ha eliminado exitosamente.", 2);
        }
    }

    @FXML
    private void doRegresarMed(ActionEvent event) 
    {
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

            Stage myStage = (Stage) this.btnRegresarDispo.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(DisponibilidadController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


//======================================================================

    private void modelaTabla()
    {
        this.disponibilidades=FXCollections.observableArrayList();
        
        this.colDiaDispo.setCellValueFactory(new PropertyValueFactory("dia"));
        this.colDesdeDispo.setCellValueFactory(new PropertyValueFactory("desde"));
        this.colHastaDispo.setCellValueFactory(new PropertyValueFactory("hasta"));
    }
    
    private void cargarDisponibilidades() {
        // Obtener la lista de disponibilidades del médico desde el archivo de texto
        List<Disponibilidad> disponibilidadesCargadas = gestionDisponibilidad.obtenerDisponibilidadesPorMedico(identificacionMedico);

        // Limpiar la lista actual de disponibilidades
        disponibilidades.clear();

        // Agregar las disponibilidades cargadas del médico a la lista observable
        disponibilidades.addAll(disponibilidadesCargadas);

        this.tblDispo.setItems(disponibilidades);
    }
    
    private void limpiarCampos() {
        this.cbxDiaDispo.setValue(null);
        this.cbxDesdeDispo.setValue(null);
        this.cbxHastaDispo.setValue(null);
    }

    private String generarCodigoAleatorio() {
        String codigo = "DIS";
        int numeroAleatorio = (int) (Math.random() * 10000);
        codigo += String.format("%04d", numeroAleatorio);
        return codigo;
    }
    
    private void cargarComboboxes() {
        ObservableList<String> dias = FXCollections.observableArrayList(
                "Lunes", "Martes", "Miércoles", "Jueves", "Viernes","Sábado");
        this.cbxDiaDispo.setItems(dias);

        ObservableList<String> horasDesde = FXCollections.observableArrayList(
                "07:00","07:30", "08:00","08:30", "09:00","09:30", "10:00","10:30", "11:00","11:30", "12:00","12:30","13:00",
                "13:30","14:00","14:30", "15:00","15:30", "16:00","16:30", "17:00","17:30", "18:00","18:30", "19:00");
        
        ObservableList<String> horasHasta = FXCollections.observableArrayList(
                "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00",
                "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00","19:30","20:00");
        
        this.cbxDesdeDispo.setItems(horasDesde);
        this.cbxHastaDispo.setItems(horasHasta);
    }
   
    private boolean showMessages(String mesg,int caso)
    {
        Alert msg;
        boolean ok=false;
        
        if(caso==1)//Error
        {
            msg=new Alert(Alert.AlertType.ERROR);
            msg.setTitle("ERROR");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.showAndWait();
        }
        if(caso==2)//Notificacion
        {
            msg=new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle("NOTIFICACIÓN");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.showAndWait();
        }
        if(caso==3)//Confirmacion
        {
            msg=new Alert(Alert.AlertType.CONFIRMATION);
            msg.setTitle("Petición Eliminación");
            
            msg.setHeaderText(null);
            msg.setContentText(mesg);
            msg.initStyle(StageStyle.UTILITY);
            
            Optional<ButtonType> result = msg.showAndWait();
            if(result.get()==ButtonType.OK)
                ok=true;
        }
        return ok;
    }
}

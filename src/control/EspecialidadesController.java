package control;

import gestion.gestionEspecialidades; // Importa la clase GestionEspecialidades correctamente
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Especialidad;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.stage.StageStyle;

public class EspecialidadesController implements Initializable {

    @FXML
    private Button btnRegresarEsp;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField txtEspecialidad;
    @FXML
    private Button btnOkAgregar;
    @FXML
    private Label lblCodigo;
    @FXML
    private ComboBox<Especialidad> cbxEspecialidades;
    @FXML
    private Button btnModificar;
    @FXML
    private TextField txtModificar;
    @FXML
    private Button btnOkModificar;
    @FXML
    private Button btnEliminar;

    private gestionEspecialidades gestion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gestion = new gestionEspecialidades();
        lblCodigo.setText("");
        cargarEspecialidades();
        
        txtEspecialidad.setDisable(true);
        btnOkAgregar.setDisable(true);
        txtModificar.setDisable(true);
        btnOkModificar.setDisable(true);
        
        // Verificar si no hay especialidades guardadas
        if (cbxEspecialidades.getItems().isEmpty()) {
            showMessages("Aún no hay especialidades guardadas.", 2);
        }
    }

    @FXML
    private void backHome(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Entrada.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage.setOnCloseRequest(e -> e.consume()); // deshabilita la X de cerrar ventana
            stage.setResizable(false); // no permite redimensionar ventanas
            stage.setTitle("CENTRO MÉDICO DE LA SABANA"); // titulo de la ventana

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btnRegresarEsp.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(EspecialidadesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void doHabilitar(ActionEvent event) {
        txtEspecialidad.setDisable(false);
        btnOkAgregar.setDisable(false);
    }

    @FXML
    private void doAgregar(ActionEvent event) {
        String nombre = txtEspecialidad.getText();

        // Validar si el nombre contiene solo letras
        if (!nombre.matches("[a-zA-Z]+")) {
            showMessages("El nombre de la especialidad debe contener solo letras.", 1);
            return; // Salir del método si no es válido
        }

        Especialidad especialidad = new Especialidad(nombre);

        gestion.guardaEspecialidad(especialidad);

        lblCodigo.setText(especialidad.getCodigo());
        txtEspecialidad.setText("");
        txtEspecialidad.setDisable(true);
        btnOkAgregar.setDisable(true);

        cargarEspecialidades();
        // Seleccionar la nueva especialidad en el ComboBox
        cbxEspecialidades.getSelectionModel().select(especialidad);

        lblCodigo.setText(especialidad.getCodigo());

        showMessages("Se ha guardado exitosamente la especialidad.", 2);
    }

    @FXML
    private void doHabilitarMod(ActionEvent event) {
        txtModificar.setDisable(false);
        btnOkModificar.setDisable(false);
    }

    @FXML
    private void doModificar(ActionEvent event) {
        Especialidad especialidadSeleccionada = cbxEspecialidades.getValue();
        if (especialidadSeleccionada == null) {
            showMessages("Para modificar, primero debes seleccionar una especialidad.", 1);
            return;
        }

        String nuevoNombre = txtModificar.getText();

        // Validar si el nuevo nombre contiene solo letras
        if (!nuevoNombre.matches("[a-zA-Z]+")) {
            showMessages("El nuevo nombre de la especialidad debe contener solo letras.", 1);
            return; // Salir del método si no es válido
        }

        especialidadSeleccionada.setNombre(nuevoNombre);

        gestion.actualizarEspecialidad(especialidadSeleccionada);

        cargarEspecialidades();
        cbxEspecialidades.getSelectionModel().select(especialidadSeleccionada);

        txtModificar.setText("");
        txtModificar.setDisable(true);
        btnOkModificar.setDisable(true);

        showMessages("Se ha modificado exitosamente la especialidad.", 2);

        // Actualizar el título del ComboBox con el nuevo nombre de la especialidad modificada
        cbxEspecialidades.setPromptText(especialidadSeleccionada.getNombre());
    }

    @FXML
    private void doEliminar(ActionEvent event) {
        Especialidad especialidadSeleccionada = cbxEspecialidades.getValue();
        if (especialidadSeleccionada == null) {
            showMessages("Para eliminar, primero debes seleccionar una especialidad.", 1);
            return;
        }

        boolean confirmacion = showMessages("¿Estás seguro de que deseas eliminar la especialidad?", 3);
        if (confirmacion) {
            gestion.eliminarEspecialidad(especialidadSeleccionada);
            showMessages("Se ha eliminado exitosamente la especialidad.", 2);
            lblCodigo.setText("");
            cargarEspecialidades();
            cbxEspecialidades.setPromptText(""); // Establece el título del ComboBox en blanco
            cbxEspecialidades.setValue(null); // Establece el valor del ComboBox en null
        }
    }


//====================================================================================

    private void cargarEspecialidades() {
        ObservableList<Especialidad> especialidades = gestion.cargarEspecialidades();
        cbxEspecialidades.setItems(especialidades);

        // Agregar el listener para detectar cambios en la selección del ComboBox
        cbxEspecialidades.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Obtener la especialidad seleccionada
                Especialidad especialidadSeleccionada = cbxEspecialidades.getSelectionModel().getSelectedItem();

                // Actualizar el texto del Label con el código de la especialidad seleccionada
                lblCodigo.setText(especialidadSeleccionada.getCodigo());

                // Actualizar el título del ComboBox con el nombre de la especialidad seleccionada
                cbxEspecialidades.setButtonCell(new ListCell<>() {
                    @Override
                    protected void updateItem(Especialidad item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getNombre());
                        }
                    }
                });

                // Actualizar el título del ComboBox con el nombre de la especialidad seleccionada
                cbxEspecialidades.setPromptText(especialidadSeleccionada.getNombre());
            }
        });
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import gestion.gestionEspecialidades;
import gestion.gestionMedicos;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import modelo.Especialidad;
import modelo.Medicos;

/**
 * FXML Controller class
 *
 * @author Gianni
 */
public class MedicosController implements Initializable {

    @FXML
    private RadioButton radbtnVistaTodos;
    @FXML
    private RadioButton radbtnVistaMed;
    @FXML
    private RadioButton radbtnRegresar;
    @FXML
    private Pane paneVistaMed;
    @FXML
    private TextField txtIdeMed;
    @FXML
    private TextField txtNombreMed;
    @FXML
    private TextField txtApellidoMed;
    @FXML
    private TextField txtMailMed;
    @FXML
    private TextField txtTelefMed;
    @FXML
    private ImageView imgFotoMed;
    @FXML
    private Button btnDisponibilidad;
    @FXML
    private Button btnAgenda;
    @FXML
    private Button btnBuscarFoto;
    @FXML
    private Button btnEliminarMed;
    @FXML
    private Button btnModificarMed;
    @FXML
    private Button btnBuscarMed;
    @FXML
    private Button btnSiguiente;
    @FXML
    private Button btnAnterior;
    @FXML
    private ComboBox<String> cbxFiltrarEsp;
    @FXML
    private TableView<Medicos> tblMedicos;
    @FXML
    private TableColumn<Medicos, String> colIdentificacion;
    @FXML
    private TableColumn<Medicos, String> colNombre;
    @FXML
    private TableColumn<Medicos, String> colApellido;
    @FXML
    private TableColumn<Medicos, String> colTelefono;
    @FXML
    private Pane paneVistaTodos;
    
    @FXML
    private ComboBox<String> cbxEspecialidadesMed;
    
    private gestionEspecialidades gestionEspecialidades;
    private gestionMedicos gestionMedicos;
    
    @FXML
    private RadioButton radbtnNuevo;
    @FXML
    private RadioButton radbtnBuscar;
    @FXML
    private Button btnGuardarMed;
    @FXML
    private ToggleGroup menu;

    private String nombreFoto;
    
    private ObservableList<Medicos> listaMedicos; // Variable para almacenar la lista de médicos del archivo
    private ObservableList<Medicos> medicosFiltrados = FXCollections.observableArrayList();

    
    private int indiceMedicoActual; // Variable para realizar el seguimiento del médico actualmente mostrado

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.paneVistaMed.setVisible(true);
        this.paneVistaTodos.setVisible(false);
        this.btnGuardarMed.setVisible(false);
        this.btnBuscarMed.setVisible(false);
        
                
        gestionEspecialidades = new gestionEspecialidades();
        cargarEspecialidades();
                       
        modelaTabla();
        
        cargarMedicos();
        indiceMedicoActual = 0;
        mostrarMedicoActual();
        if (listaMedicos.isEmpty()) {
            doHabilitarNuevo(null);
            showMessages("No hay médicos guardados.", 2);
        }
        
        
    }  

    @FXML
    private void goPaneVistaTodos(ActionEvent event) {
        this.paneVistaTodos.setVisible(true);
        this.paneVistaTodos.toFront();
    }

    @FXML
    private void goPaneVistaMed(ActionEvent event) {
        this.btnBuscarMed.setVisible(false);
        this.btnGuardarMed.setVisible(false);
        this.btnEliminarMed.setVisible(true);
        this.btnModificarMed.setVisible(true);
        this.btnAnterior.setVisible(true);
        this.btnSiguiente.setVisible(true);
        this.btnDisponibilidad.setVisible(true);
        this.btnAgenda.setVisible(true);
        
        this.paneVistaMed.toFront();
        mostrarMedicoActual();
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

            Stage myStage = (Stage) this.radbtnRegresar.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(EspecialidadesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goDisponibilidad(ActionEvent event) 
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Disponibilidad.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            DisponibilidadController disponibilidadController = loader.getController();
            String identificacionMedico = txtIdeMed.getText();
            disponibilidadController.setIdentificacionMedico(identificacionMedico);                                //metodo para inicializar y mandarle cosas
            
            Stage stage = new Stage();
            stage.setOnCloseRequest(e -> {
                e.consume();
            });          //deshabilita la X de cerrar ventana
            stage.setResizable(false);                         //no permite redimensionar ventanas
            stage.setTitle("DISPONIBILIDAD");                //titulo de la ventana   

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btnDisponibilidad.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(MedicosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goAgenda(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Agenda.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            AgendaController agendaController = loader.getController();
            String identificacionMedico = txtIdeMed.getText();
            String nombreMedico = this.txtNombreMed.getText();
            String apellidoMedico = this.txtApellidoMed.getText();
            String nombreCompletoMedico = nombreMedico + " " + apellidoMedico;
            agendaController.setNombreMedico(nombreCompletoMedico);
            agendaController.setIdentificacionMedico(identificacionMedico);                                //metodo para inicializar y mandarle cosas

            Stage stage = new Stage();
            stage.setOnCloseRequest(e -> {
                e.consume();
            });          //deshabilita la X de cerrar ventana
            stage.setResizable(false);                         //no permite redimensionar ventanas
            stage.setTitle("AGENDA");                //titulo de la ventana   

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btnAgenda.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(MedicosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void doBuscarFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.setInitialDirectory(new File("./imagenes/"));

        // Obtener el archivo seleccionado
        File imgFile = fileChooser.showOpenDialog(new Stage());
        this.nombreFoto=imgFile.getName();
        
        if (this.nombreFoto != null) 
        {
            Image image = new Image("file:" + imgFile.getAbsolutePath());
            this.imgFotoMed.setImage(image);
        }
    }

    @FXML
    private void doEliminarMed(ActionEvent event) {
        if (listaMedicos.isEmpty()) {
            return;
        }

        int indiceMedicoActual = Math.min(this.indiceMedicoActual, listaMedicos.size() - 1);
        Medicos medicoActual = listaMedicos.get(indiceMedicoActual);
        String identificacion = medicoActual.getIdentificacion().trim();

        boolean confirmacion = showMessages("¿Estás seguro de eliminar este médico?", 3);
        if (confirmacion) {
            listaMedicos.remove(indiceMedicoActual);
            guardarMedicos();

            showMessages("Médico eliminado exitosamente.", 2);

            // Mostrar el médico actualizado
            if (listaMedicos.isEmpty()) {
                txtIdeMed.setText("");
                txtNombreMed.setText("");
                txtApellidoMed.setText("");
                txtMailMed.setText("");
                txtTelefMed.setText("");
                cbxEspecialidadesMed.setValue(null);
                Image image = new Image("file:./imagenes/sinImagen.jpg");
                imgFotoMed.setImage(image);
                // Restablecer el índice actual a -1
                this.indiceMedicoActual = -1;
            } else {
                // Verificar si el índice actual está fuera de los límites de la lista actualizada
                if (indiceMedicoActual >= listaMedicos.size()) {
                    indiceMedicoActual = listaMedicos.size() - 1;
                    this.indiceMedicoActual = indiceMedicoActual;
                }
                mostrarMedicoActual();
            }
        }
        actualizarTablaMedicos();
    }

    
    private void guardarMedicos() {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter("./ARCHIVOS/medicos.txt"))) {
            for (Medicos medico : listaMedicos) {
                String[] datosMedico = {
                    medico.getIdentificacion(),
                    medico.getNombre(),
                    medico.getApellido(),
                    medico.getEmail(),
                    medico.getTelefono(),
                    medico.getEspecialidad(),
                    medico.getNombreFoto()
                };
                String linea = String.join(",", datosMedico);
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        actualizarTablaMedicos();
    }


    @FXML
    private void doModificarMed(ActionEvent event) {
        String identificacion = txtIdeMed.getText();

        if (identificacion.isEmpty()) {
            showMessages("Ingrese una identificación para modificar.", 1);
            return;
        }

        // Verificar si ya existe un médico con la identificación ingresada
        boolean existeMedico = false;
        int indiceMedicoExistente = -1;

        for (int i = 0; i < listaMedicos.size(); i++) {
            Medicos medico = listaMedicos.get(i);
            if (medico.getIdentificacion().equals(identificacion)) {
                existeMedico = true;
                indiceMedicoExistente = i;
                break;
            }
        }

        if (!existeMedico) {
            showMessages("No existe un médico con esa identificación. No se puede modificar.", 1);
            return;
        }

        String nombre = txtNombreMed.getText();
        String apellido = txtApellidoMed.getText();
        String email = txtMailMed.getText();
        String telefono = txtTelefMed.getText();
        String especialidad = cbxEspecialidadesMed.getValue();
        String imagen = nombreFoto; // Obtén el nombre de la imagen seleccionada

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty() || especialidad == null) {
            showMessages("Todos los campos son requeridos.", 1);
            return;
        }

        // Validación de nombre y apellido
        if (!nombre.matches("[a-zA-Z]+") || !apellido.matches("[a-zA-Z]+")) {
            showMessages("El nombre y apellido solo pueden contener letras.", 1);
            return;
        }

        // Validación de identificación (6 dígitos numéricos)
        if (!identificacion.matches("\\d{6}")) {
            showMessages("La identificación debe contener 6 dígitos numéricos.", 1);
            return;
        }

        // Validación de número de teléfono (10 dígitos numéricos)
        if (!telefono.matches("\\d{10}")) {
            showMessages("El número de teléfono debe contener 10 dígitos numéricos.", 1);
            return;
        }
        
        Medicos medicoExistente = listaMedicos.get(indiceMedicoExistente);
        medicoExistente.setNombre(nombre);
        medicoExistente.setApellido(apellido);
        medicoExistente.setEmail(email);
        medicoExistente.setTelefono(telefono);
        medicoExistente.setEspecialidad(especialidad);
        medicoExistente.setNombreFoto(imagen);

        guardarMedicos();

        showMessages("Médico modificado exitosamente.", 2);

        tblMedicos.refresh();
    }


    @FXML
    private void doBuscarMed(ActionEvent event) {
        String identificacion = txtIdeMed.getText();

        if (identificacion.isEmpty()) {
            showMessages("Ingrese una identificación para buscar.", 1);
            return;
        }

        boolean encontrado = false;
        int indiceMedicoEncontrado = -1;

        for (int i = 0; i < listaMedicos.size(); i++) {
            Medicos medico = listaMedicos.get(i);
            if (medico.getIdentificacion().equals(identificacion)) {
                encontrado = true;
                indiceMedicoEncontrado = i;
                break;
            }
        }

        if (encontrado) {
            // Mostrar el médico encontrado
            Medicos medicoEncontrado = listaMedicos.get(indiceMedicoEncontrado);
            // Aquí puedes utilizar los datos para mostrarlos en los campos correspondientes o hacer lo que necesites
            txtNombreMed.setText(medicoEncontrado.getNombre());
            txtApellidoMed.setText(medicoEncontrado.getApellido());
            txtMailMed.setText(medicoEncontrado.getEmail());
            txtTelefMed.setText(medicoEncontrado.getTelefono());
            cbxEspecialidadesMed.setValue(medicoEncontrado.getEspecialidad());
            String rutaFoto = "./imagenes/" + medicoEncontrado.getNombreFoto(); // Ruta de la imagen del médico
            Image image = new Image("file:" + rutaFoto);
            imgFotoMed.setImage(image);

            showMessages("Médico encontrado.", 2);
        } else {
            showMessages("No se encontró ningún médico con esa identificación.", 1);
        }
    }


    @FXML
    private void doSiguienteMed(ActionEvent event) 
    {
        if (indiceMedicoActual < listaMedicos.size() - 1) {
            indiceMedicoActual++;
            mostrarMedicoActual();
        }
    }

    @FXML
    private void doRegresarMed(ActionEvent event) 
    {
        if (indiceMedicoActual > 0) {
            indiceMedicoActual--;
            mostrarMedicoActual();
        }
    }

    @FXML
    private void doFiltrarEsp(ActionEvent event) {
        String especialidadSeleccionada = cbxFiltrarEsp.getValue();

        if (especialidadSeleccionada.equals("TODAS")) {
            medicosFiltrados.clear();
            medicosFiltrados.addAll(listaMedicos);
        } else {
            medicosFiltrados.clear();

            for (Medicos medico : listaMedicos) {
                if (medico.getEspecialidad().equals(especialidadSeleccionada)) {
                    medicosFiltrados.add(medico);
                }
            }
        }

        tblMedicos.setItems(medicosFiltrados);
    }


    @FXML
    private void doHabilitarNuevo(ActionEvent event) {
        this.paneVistaMed.toFront();
        this.btnBuscarMed.setVisible(false);
        this.btnBuscarMed.setDisable(true);
       
        this.btnEliminarMed.setVisible(false);
        this.btnModificarMed.setVisible(false);
        this.btnAnterior.setVisible(false);
        this.btnSiguiente.setVisible(false);
        this.btnDisponibilidad.setVisible(false);
        this.btnAgenda.setVisible(false);

        
        this.btnGuardarMed.setDisable(false);
        this.btnGuardarMed.setVisible(true);
        this.btnGuardarMed.toFront();
        
        txtIdeMed.setText("");
        txtNombreMed.setText("");
        txtApellidoMed.setText("");
        txtMailMed.setText("");
        txtTelefMed.setText("");
        cbxEspecialidadesMed.setValue(null);
        Image image = new Image("file:" + ".\\imagenes\\sinImagen.jpg");
        this.imgFotoMed.setImage(image);
    }

    @FXML
    private void doHabilitatBuscar(ActionEvent event) {
        this.paneVistaMed.toFront();
        this.btnGuardarMed.setVisible(false);
        this.btnGuardarMed.setDisable(true);
        
        this.btnEliminarMed.setVisible(true);
        this.btnModificarMed.setVisible(true);
        this.btnAnterior.setVisible(true);
        this.btnSiguiente.setVisible(true);
        this.btnDisponibilidad.setVisible(true);
        this.btnAgenda.setVisible(true);
        
        this.btnBuscarMed.setDisable(false);
        this.btnBuscarMed.setVisible(true);
        this.btnBuscarMed.toFront();

        
        txtIdeMed.setText("");
        txtNombreMed.setText("");
        txtApellidoMed.setText("");
        txtMailMed.setText("");
        txtTelefMed.setText("");
        cbxEspecialidadesMed.setValue(null);
        Image image = new Image("file:" + ".\\imagenes\\sinImagen.jpg");
        this.imgFotoMed.setImage(image);
        
        showMessages("Digite la identificación del médico que desea buscar.", 2);
    }

    @FXML
    private void doGuardarMed(ActionEvent event) {
        String identificacion = txtIdeMed.getText();
        String nombre = txtNombreMed.getText();
        String apellido = txtApellidoMed.getText();
        String email = txtMailMed.getText();
        String telefono = txtTelefMed.getText();
        String especialidad = cbxEspecialidadesMed.getValue();
        String imagen = ""; // Obtén la ruta de la imagen seleccionada

        if (identificacion.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || telefono.isEmpty() || especialidad == null) {
            showMessages("Todos los campos son requeridos.", 1);
            return;
        }

        // Validación de nombre y apellido
        if (!nombre.matches("[a-zA-Z]+") || !apellido.matches("[a-zA-Z]+")) {
            showMessages("El nombre y apellido solo pueden contener letras.", 1);
            return;
        }

        // Validación de identificación (6 dígitos numéricos)
        if (!identificacion.matches("\\d{6}")) {
            showMessages("La identificación debe contener 6 dígitos numéricos.", 1);
            return;
        }

        // Validación de número de teléfono (10 dígitos numéricos)
        if (!telefono.matches("\\d{10}")) {
            showMessages("El número de teléfono debe contener 10 dígitos numéricos.", 1);
            return;
        }

        Medicos medico = new Medicos(identificacion, nombre, apellido, email, telefono, especialidad, nombreFoto);

        gestionMedicos gestionMedicos = new gestionMedicos();
        // Llama al método para guardar el médico en la gestión de médicos
        boolean guardadoExitoso = gestionMedicos.guardarMedicos(medico);

        if (guardadoExitoso) {
            showMessages("Médico guardado exitosamente.", 2);
            // Restablece los campos de entrada
            txtIdeMed.setText("");
            txtNombreMed.setText("");
            txtApellidoMed.setText("");
            txtMailMed.setText("");
            txtTelefMed.setText("");
            cbxEspecialidadesMed.setValue(null);

            Image image = new Image("file:" + "SinImagen.jpeg");
            this.imgFotoMed.setImage(image);

        } else {
            showMessages("Error al guardar el médico.", 1);
        }
        cargarMedicos();
    }
     
    //======================================================================
    
    private void modelaTabla()
    {
        this.listaMedicos=FXCollections.observableArrayList();
        
        this.colIdentificacion.setCellValueFactory(new PropertyValueFactory("identificacion"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colApellido.setCellValueFactory(new PropertyValueFactory("apellido"));
        this.colTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
    }
    
    private void actualizarTablaMedicos() {
        ObservableList<Medicos> listaMedicosObservable = FXCollections.observableArrayList(listaMedicos);
        tblMedicos.setItems(listaMedicosObservable);

        if (medicosFiltrados.isEmpty()) {
            tblMedicos.setItems(listaMedicos);
        } else {
            tblMedicos.setItems(medicosFiltrados);
        }
    }
    
    private void filtrarMedicosPorEspecialidad(String especialidad) {
        medicosFiltrados.clear();
        if (especialidad != null) {
            for (Medicos medico : listaMedicos) {
                if (medico.getEspecialidad().equals(especialidad)) {
                    medicosFiltrados.add(medico);
                }
            }
        } else {
            medicosFiltrados.addAll(listaMedicos);
        }
        tblMedicos.setItems(medicosFiltrados);
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
   
    private void cargarMedicos() {
        String archivoMedicos = "./ARCHIVOS/medicos.txt"; // Reemplaza con la ruta de tu archivo de médicos
        listaMedicos.clear();

        try ( BufferedReader br = new BufferedReader(new FileReader(archivoMedicos))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datosMedico = linea.split(",");
                Medicos medico = new Medicos(datosMedico[0].trim(), datosMedico[1].trim(), datosMedico[2].trim(), datosMedico[3].trim(), datosMedico[4].trim(), datosMedico[5].trim(), datosMedico[6].trim());
                listaMedicos.add(medico);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        actualizarTablaMedicos();
    }

    private void mostrarMedicoActual() {
        if (listaMedicos.isEmpty()) {
            return;
        }

        Medicos medicoActual = listaMedicos.get(indiceMedicoActual);

        txtIdeMed.setText(medicoActual.getIdentificacion().trim());
        txtNombreMed.setText(medicoActual.getNombre().trim());
        txtApellidoMed.setText(medicoActual.getApellido().trim());
        txtMailMed.setText(medicoActual.getEmail().trim());
        txtTelefMed.setText(medicoActual.getTelefono().trim());
        cbxEspecialidadesMed.setValue(medicoActual.getEspecialidad().trim());
        nombreFoto = medicoActual.getNombreFoto().trim();

        if (!nombreFoto.isEmpty()) {
            String rutaFoto = "./imagenes/" + nombreFoto; // Reemplaza con la ruta de tus fotos de médicos
            Image image = new Image("file:" + rutaFoto);
            imgFotoMed.setImage(image);
        }
    }

    private void cargarEspecialidades() {
        ObservableList<Especialidad> especialidades = gestionEspecialidades.cargarEspecialidades();

        // Limpiar ComboBox de especialidades
        cbxEspecialidadesMed.getItems().clear();
        cbxFiltrarEsp.getItems().clear();

        // Agregar las especialidades al ComboBox cbxEspecialidadesMed
        for (Especialidad especialidad : especialidades) {
            cbxEspecialidadesMed.getItems().add(especialidad.getNombre());
        }

        // Agregar las especialidades al ComboBox cbxFiltrarEsp
        cbxFiltrarEsp.getItems().add("TODAS");
        for (Especialidad especialidad : especialidades) {
            cbxFiltrarEsp.getItems().add(especialidad.getNombre());
        }
    }
}
    
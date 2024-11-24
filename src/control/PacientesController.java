/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.gestionPacientes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import modelo.Medicos;
import modelo.Pacientes;

/**
 * FXML Controller class
 *
 * @author Gianni
 */
public class PacientesController implements Initializable {

    @FXML
    private Button btnHabilitarNuevo;
    @FXML
    private Button btnHabilitarBuscar;
    @FXML
    private Button btnVistaTodos;
    @FXML
    private Button btnVistaPaciente;
    @FXML
    private Button btnRegresar;
    @FXML
    private TextField txtIdentificacion;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtTelefono;
    @FXML
    private ComboBox<String> cbxGenero;
    @FXML
    private DatePicker dprFechaNac;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnAnterior;
    @FXML
    private Button btnSiguiente;
    @FXML
    private Button btnBuscar;
    @FXML
    private TableView<Pacientes> tblPacientes;
    @FXML
    private TableColumn<Pacientes, String> colIdentificacionPac;
    @FXML
    private TableColumn<Pacientes, String> colNombrePac;
    @FXML
    private TableColumn<Pacientes, String> colApellidoPac;
    @FXML
    private TableColumn<Pacientes, String> colTelefonoPac;
    @FXML
    private Pane paneVistaPaciente;
    @FXML
    private Pane paneVistaTodos;
    
    private gestionPacientes gestionPacientes;
    
    ObservableList<Pacientes> listaPacientes; // Variable para almacenar la lista de médicos del archivo

    private int indicePacienteActual;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        gestionPacientes = new gestionPacientes();
        this.btnGuardar.setVisible(false);
        
       
        inicializarComboBoxGenero();
        modelaTabla();
        
        cargarPacientes();
        indicePacienteActual = 0;
        mostrarPacienteActual();
        if (listaPacientes.isEmpty()) {
            this.doHabilitarGuardar(null);
            showMessages("No hay pacientes guardados.", 2);
        }
    }    

    @FXML
    private void doHabilitarGuardar(ActionEvent event) {
        this.paneVistaPaciente.toFront();
        this.btnGuardar.setVisible(true);
        this.btnGuardar.toFront();
        
        this.btnBuscar.setVisible(false);
        this.btnModificar.setVisible(false);
        this.btnEliminar.setVisible(false);
        
        this.txtIdentificacion.setText("");
        this.txtNombre.setText("");
        this.txtApellido.setText("");
        this.cbxGenero.setValue(null);
        this.dprFechaNac.setValue(null);
        this.txtTelefono.setText("");
    }

    @FXML
    private void doHabilitarBuscar(ActionEvent event) {
        this.paneVistaPaciente.toFront();
        this.btnBuscar.toFront();
        this.btnBuscar.setVisible(true);
        this.btnModificar.setVisible(true);
        this.btnEliminar.setVisible(true);
        
        this.txtIdentificacion.setText("");
        this.txtNombre.setText("");
        this.txtApellido.setText("");
        this.cbxGenero.setValue(null);
        this.dprFechaNac.setValue(null);
        this.txtTelefono.setText("");
        
        showMessages("Digite la identificación del paciente que desea buscar.", 2);
    }

    @FXML
    private void goVistaTodos(ActionEvent event) {
        this.paneVistaTodos.toFront();
        this.paneVistaTodos.setVisible(true);
    }

    @FXML
    private void goVistaPaciente(ActionEvent event) {
        this.paneVistaPaciente.toFront();
        this.btnBuscar.setVisible(true);
        this.btnBuscar.toFront();
        
        this.btnEliminar.setVisible(true);
        this.btnModificar.setVisible(true);
        mostrarPacienteActual();
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

            Stage myStage = (Stage) this.btnRegresar.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(EspecialidadesController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @FXML
    private void doGuardar(ActionEvent event) {
        String identificacion = this.txtIdentificacion.getText();
        String nombre = this.txtNombre.getText();
        String apellido = this.txtApellido.getText();
        String genero = this.cbxGenero.getValue();
        LocalDate fechanacimiento = this.dprFechaNac.getValue();
        String telefono = this.txtTelefono.getText();
        
        if (identificacion.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || genero == null || fechanacimiento == null || telefono.isEmpty()) {
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
        
        // Convertir LocalDate a String en formato deseado
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaNacimientoStr = fechanacimiento.format(formatter);

        Pacientes paciente = new Pacientes(identificacion, nombre, apellido, genero, fechaNacimientoStr, telefono);

        gestionPacientes gestionPacientes = new gestionPacientes();
        // Llama al método para guardar el médico en la gestión de médicos
        boolean guardadoExitoso = gestionPacientes.guardarPaciente(paciente);

        if (guardadoExitoso) {
            showMessages("Paciente guardado exitosamente.", 2);
            // Restablece los campos de entrada
            this.txtIdentificacion.setText("");
            this.txtNombre.setText("");
            this.txtApellido.setText("");
            this.cbxGenero.setValue(null);
            this.dprFechaNac.setValue(null);
            this.txtTelefono.setText("");

        } else {
            showMessages("Error al guardar el paciente.", 1);
        }
        cargarPacientes();
    }

    @FXML
    private void doModificar(ActionEvent event) {
        String identificacion = this.txtIdentificacion.getText();

        if (identificacion.isEmpty()) {
            showMessages("Ingrese una identificación para modificar.", 1);
            return;
        }

        // Verificar si ya existe un paciente con la identificación ingresada
        boolean existePaciente = false;
        int indicePacienteExistente = -1;

        for (int i = 0; i < listaPacientes.size(); i++) {
            Pacientes paciente = listaPacientes.get(i);
            if (paciente.getIdentificacion().equals(identificacion)) {
                existePaciente = true;
                indicePacienteExistente = i;
                break;
            }
        }

        if (!existePaciente) {
            showMessages("No existe un paciente con esa identificación. No se puede modificar.", 1);
            return;
        }
        
        String nombre = this.txtNombre.getText();
        String apellido = this.txtApellido.getText();
        String genero = this.cbxGenero.getValue();
        LocalDate fechanacimiento = this.dprFechaNac.getValue();
        String telefono = this.txtTelefono.getText();

        if (nombre.isEmpty() || apellido.isEmpty() || genero == null || fechanacimiento == null || telefono.isEmpty()) {
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaNacimientoStr = fechanacimiento.format(formatter);

        Pacientes pacienteExistente = listaPacientes.get(indicePacienteExistente);
        pacienteExistente.setNombre(nombre);
        pacienteExistente.setApellido(apellido);
        pacienteExistente.setGenero(genero);
        pacienteExistente.setFechanacimiento(fechaNacimientoStr);
        pacienteExistente.setTelefono(telefono);

        guardarPacientes();

        showMessages("Paciente modificado exitosamente.", 2);

        this.tblPacientes.refresh();
    }


    @FXML
    private void doEliminar(ActionEvent event) {
        if (listaPacientes.isEmpty()) {
            return;
        }

        int indiceMedicoActual = Math.min(this.indicePacienteActual, listaPacientes.size() - 1);
        Pacientes pacienteActual = listaPacientes.get(indicePacienteActual);
        String identificacion = pacienteActual.getIdentificacion().trim();

        boolean confirmacion = showMessages("¿Estás seguro de eliminar este paciente?", 3);
        if (confirmacion) {
            listaPacientes.remove(indicePacienteActual);
            guardarPacientes();

            showMessages("Paciente eliminado exitosamente.", 2);

            // Mostrar el médico actualizado
            if (listaPacientes.isEmpty()) {
                this.txtIdentificacion.setText("");
                this.txtNombre.setText("");
                this.txtApellido.setText("");
                this.cbxGenero.setValue(null);
                this.dprFechaNac.setValue(null);
                this.txtTelefono.setText("");
                // Restablecer el índice actual a -1
                this.indicePacienteActual = -1;
            } else {
                // Verificar si el índice actual está fuera de los límites de la lista actualizada
                if (indicePacienteActual >= listaPacientes.size()) {
                    indicePacienteActual = listaPacientes.size() - 1;
                    this.indicePacienteActual = indicePacienteActual;
                }
                mostrarPacienteActual();
            }
        }
        actualizarTablaPacientes();
    }

    @FXML
    private void doAnterior(ActionEvent event) {
        if (indicePacienteActual > 0) {
            indicePacienteActual--;
            mostrarPacienteActual();
        }
    }

    @FXML
    private void doAvanzar(ActionEvent event) {
        if (indicePacienteActual < listaPacientes.size() - 1) {
            indicePacienteActual++;
            mostrarPacienteActual();
        }
    }

    @FXML
    private void doBuscar(ActionEvent event) {
        String identificacion = this.txtIdentificacion.getText();

        if (identificacion.isEmpty()) {
            showMessages("Ingrese una identificación para buscar.", 1);
            return;
        }

        boolean encontrado = false;
        int indicePacienteEncontrado = -1;

        for (int i = 0; i < listaPacientes.size(); i++) {
            Pacientes paciente = listaPacientes.get(i);
            if (paciente.getIdentificacion().equals(identificacion)) {
                encontrado = true;
                indicePacienteEncontrado = i;
                break;
            }
        }

        if (encontrado) {
            // Mostrar el médico encontrado
            Pacientes pacienteEncontrado = listaPacientes.get(indicePacienteEncontrado);
            // Aquí puedes utilizar los datos para mostrarlos en los campos correspondientes o hacer lo que necesites
            this.txtNombre.setText(pacienteEncontrado.getNombre());
            this.txtApellido.setText(pacienteEncontrado.getApellido());
            this.cbxGenero.setValue(pacienteEncontrado.getGenero());
            
            String fechaNacimientoStr = pacienteEncontrado.getFechanacimiento();
            LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
            this.dprFechaNac.setValue(fechaNacimiento);
            
            this.txtTelefono.setText(pacienteEncontrado.getTelefono());

            showMessages("Paciente encontrado.", 2);
        } else {
            showMessages("No se encontró ningún paciente con esa identificación.", 1);
        }
    }
    
    //===================================================================
    
    private Pacientes obtenerDatosPaciente() {
        String identificacion = txtIdentificacion.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String genero = cbxGenero.getSelectionModel().getSelectedItem();
        String fechaNacimiento = dprFechaNac.getValue() != null ? dprFechaNac.getValue().toString() : "";
        String telefono = txtTelefono.getText().trim();

        if (identificacion.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || genero == null || fechaNacimiento.isEmpty() || telefono.isEmpty()) {
            showMessages("Todos los campos son requeridos.", 1);
            return null;
        }

        return new Pacientes(identificacion, nombre, apellido, genero, fechaNacimiento, telefono);
    }
    
    private void inicializarComboBoxGenero() {
        ObservableList<String> generos = FXCollections.observableArrayList(
                "F", "M");
        cbxGenero.setItems(generos);
    }
    
    private void mostrarDatosPaciente(Pacientes paciente) {
        txtIdentificacion.setText(paciente.getIdentificacion());
        txtNombre.setText(paciente.getNombre());
        txtApellido.setText(paciente.getApellido());
        cbxGenero.getSelectionModel().select(paciente.getGenero());
        dprFechaNac.setValue(LocalDate.parse(paciente.getFechanacimiento()));
        txtTelefono.setText(paciente.getTelefono());
    }
    
    private void modelaTabla() {
        this.listaPacientes = FXCollections.observableArrayList();

        this.colIdentificacionPac.setCellValueFactory(new PropertyValueFactory("identificacion"));
        this.colNombrePac.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colApellidoPac.setCellValueFactory(new PropertyValueFactory("apellido"));
        this.colTelefonoPac.setCellValueFactory(new PropertyValueFactory("telefono"));
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


    private void cargarPacientes() {
        String archivoMedicos = "./ARCHIVOS/pacientes.txt"; // Reemplaza con la ruta de tu archivo de pacientes
        listaPacientes.clear();

        try ( BufferedReader br = new BufferedReader(new FileReader(archivoMedicos))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datosPaciente = linea.split(",");
                if (datosPaciente.length >= 6) {
                    Pacientes paciente = new Pacientes(datosPaciente[0].trim(), datosPaciente[1].trim(), datosPaciente[2].trim(), datosPaciente[3].trim(), datosPaciente[4].trim(), datosPaciente[5].trim());
                    this.listaPacientes.add(paciente);
                } else {
                    // Mostrar mensaje de error o ignorar la línea incorrecta
                    System.out.println("Error al leer la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        actualizarTablaPacientes();
    }


    private void mostrarPacienteActual() {
        if (listaPacientes.isEmpty()) {
            return;
        }

        Pacientes pacienteActual = listaPacientes.get(indicePacienteActual);

        this.txtIdentificacion.setText(pacienteActual.getIdentificacion().trim());
        this.txtNombre.setText(pacienteActual.getNombre().trim());
        this.txtApellido.setText(pacienteActual.getApellido().trim());
        this.cbxGenero.setValue(pacienteActual.getGenero().trim());

        String fechaNacimientoStr = pacienteActual.getFechanacimiento().trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr, formatter);
        this.dprFechaNac.setValue(fechaNacimiento);

        this.txtTelefono.setText(pacienteActual.getTelefono().trim());
    }


    private void actualizarTablaPacientes() {
        ObservableList<Pacientes> listaMedicosObservable = FXCollections.observableArrayList(listaPacientes);
        this.tblPacientes.setItems(listaMedicosObservable);
    }
   
    private void guardarPacientes() {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter("./ARCHIVOS/pacientes.txt"))) {
            for (Pacientes paciente : listaPacientes) {
                String[] datosPaciente = {
                    paciente.getIdentificacion(),
                    paciente.getNombre(),
                    paciente.getApellido(),
                    paciente.getGenero(),
                    paciente.getFechanacimiento(),
                    paciente.getTelefono(),
                };
                String linea = String.join(",", datosPaciente);
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        actualizarTablaPacientes();
    }
}


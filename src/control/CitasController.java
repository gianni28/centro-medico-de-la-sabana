/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package control;

import gestion.gestionCitas;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.Disponibilidad;
import modelo.Medicos;
import modelo.Pacientes;
import modelo.Disponibilidad;
import javafx.scene.control.DateCell;
import javafx.util.Callback;
import gestion.gestionCitas;
import java.time.format.TextStyle;
import java.util.Locale;
import modelo.Citas;


/**
 * FXML Controller class
 *
 * @author Gianni
 */
public class CitasController implements Initializable {

    @FXML
    private TextField txtIdPacCitas;
    @FXML
    private Button btnBuscar;
    @FXML
    private Label lblNombrePac;
    @FXML
    private ComboBox<String> cbxEspecialidadesCitas;
    @FXML
    private ComboBox<String> cbxDoctores;
    @FXML
    private TableView<Disponibilidad> tblDispoCitas;
    @FXML
    private TableColumn<Disponibilidad, String> colDiaDispo;
    @FXML
    private TableColumn<Disponibilidad, String> colDesdeDispo;
    @FXML
    private TableColumn<Disponibilidad, String> colHastaDispo;
    @FXML
    private TableView<Citas> tblCitas;
    @FXML
    private TableColumn<Citas, String> colEspecialidadCita;
    @FXML
    private TableColumn<Citas, String> colDoctorCita;
    @FXML
    private TableColumn<Citas, String> colFechaCita;
    @FXML
    private TableColumn<Citas, String> colHoraCita;
    @FXML
    private Button btnProgramar;
    @FXML
    private ComboBox<String> cbxHoraCita;
    @FXML
    private DatePicker dprCita;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnCancelarCita;

    private ObservableList<Citas> listaCitas;
    private ObservableList<Disponibilidad> disponibilidades;
    private ObservableList<LocalDate> diasDisponibles;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CargarEspecialidades();

        // Agregar evento de selección al ComboBox de especialidades
        cbxEspecialidadesCitas.setOnAction(this::filtrarDoctoresPorEspecialidad);
        cbxDoctores.setOnAction(this::cargarDisponibilidades);
        
        
        modelaTablaDispo();
    }
    

    @FXML
    private void doBuscarPac(ActionEvent event) {
        String pacienteId = txtIdPacCitas.getText();

        try {
            // Abrir el archivo de pacientes.txt para lectura
            FileReader fileReader = new FileReader("./ARCHIVOS/pacientes.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            boolean pacienteEncontrado = false;

            // Leer línea por línea del archivo
            while ((line = bufferedReader.readLine()) != null) {
                // Dividir la línea en partes separadas por coma (suponiendo un formato de CSV)
                String[] parts = line.split(",");

                // Verificar si el número de identificación coincide
                if (parts[0].equals(pacienteId)) {
                    pacienteEncontrado = true;
                    String nombre = parts[1];
                    String apellido = parts[2];

                    // Mostrar el nombre y apellido del paciente encontrado
                    lblNombrePac.setText(nombre + " " + apellido);
                    modelaTablaCitas();
                    cargarCitas();
                    break;
                }
            }

            // Cerrar el archivo
            bufferedReader.close();

            // Mostrar un mensaje si el paciente no se encuentra
            if (!pacienteEncontrado) {
                showMessages("Paciente no encontrado", 1);
            }

        } catch (IOException e) {
            // Mostrar un mensaje de error si ocurre un error de lectura
            showMessages("Error al leer el archivo de pacientes", 1);
        }
    }

    @FXML
    private void doProgramar(ActionEvent event) {
        String identificacionPac = txtIdPacCitas.getText();
        String especialidadDoc = cbxEspecialidadesCitas.getValue();
        String medicoSeleccionado = cbxDoctores.getValue();
        String identificacionDoc = obtenerIdentificacionMedico(medicoSeleccionado);

        LocalDate fechaSeleccionada = dprCita.getValue();
        if (fechaSeleccionada == null) {
            showMessages("Debe seleccionar una fecha.", 1);
            return;
        }
        String fechaCita = fechaSeleccionada.toString();

        String diaCitaF = fechaSeleccionada.toString();
        LocalDate diaCitaFF = LocalDate.parse(diaCitaF);
        DayOfWeek diaSemana = diaCitaFF.getDayOfWeek();
        String diaCita = diaSemana.getDisplayName(TextStyle.FULL, Locale.getDefault());
        String horaCita = cbxHoraCita.getValue();

        // Verificar que todos los campos estén seleccionados
        if (identificacionPac.isEmpty() || especialidadDoc == null || medicoSeleccionado == null
                || fechaCita.isEmpty() || diaCita.isEmpty() || horaCita == null) {
            showMessages("Debe llenar todos los campos.", 1);
            return;
        }

        // Crear una instancia de la clase gestionCitas
        gestionCitas gestionCitasInstance = new gestionCitas();

        // Verificar si ya existe una cita programada con el mismo doctor, fecha y hora
        if (gestionCitasInstance.existeCitaProgramada(identificacionDoc, fechaCita, horaCita)) {
            showMessages("El doctor ya tiene una cita programada a esa hora y fecha.", 1);
            return;
        }

        // Guardar la cita en el archivo
        gestionCitasInstance.guardarCita(identificacionPac, especialidadDoc, identificacionDoc, fechaCita, diaCita, horaCita);
        cargarCitas();

        showMessages("La cita se programó exitosamente.", 2);
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
    private void doCancelarCita(ActionEvent event) {
        // Verificar si hay una fila seleccionada en la tabla
        Citas citaSeleccionada = tblCitas.getSelectionModel().getSelectedItem();
        if (citaSeleccionada == null) {
            showMessages("Primero seleccione una cita para cancelar.", 1);
            return;
        }

        // Mostrar confirmación antes de eliminar la cita
        boolean confirmacion = showMessages("¿Está seguro de cancelar la cita?", 3);
        if (!confirmacion) {
            return;
        }

        // Obtener la instancia de gestionCitas
        gestionCitas gestionCitas = new gestionCitas();

        // Eliminar la cita del archivo citas.txt
        gestionCitas.eliminarCita(citaSeleccionada);

        showMessages("La cita ha sido cancelada correctamente.", 1);
        // Actualizar la tabla tblCitas
        cargarCitas();
    }

 
    //==================================================================
    
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


//======================================================================
    private void CargarEspecialidades(){
        // Obtener la lista de especialidades desde el archivo
        ObservableList<String> especialidades = FXCollections.observableArrayList();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("./ARCHIVOS/especialidades.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1) {
                    especialidades.add(parts[1]);
                }
            }
        } catch (IOException e) {
            showMessages("Error al leer el archivo de especialidades", 1);
        }

        // Establecer las especialidades en el ComboBox
        this.cbxEspecialidadesCitas.setItems(especialidades);

    }
    
    private void cargarDisponibilidades(ActionEvent event) {
        // Obtener el médico seleccionado del ComboBox
        String medicoSeleccionado = cbxDoctores.getValue();

        // Verificar si se ha seleccionado un médico
        if (medicoSeleccionado != null) {
            // Obtener el identificador del médico seleccionado
            String identificacionMedico = obtenerIdentificacionMedico(medicoSeleccionado);

            // Leer el archivo de disponibilidades.txt y filtrar las disponibilidades del médico seleccionado
            ObservableList<Disponibilidad> disponibilidades = obtenerDisponibilidades(identificacionMedico);
        
            // Establecer las disponibilidades en la tabla tblDispoCitas
            tblDispoCitas.setItems(disponibilidades);

            // Obtener los días disponibles
            diasDisponibles = obtenerDiasDisponibles(disponibilidades);
            
            // Obtener las horas disponibles del médico seleccionado
            ObservableList<String> horasDisponibles = obtenerHorasDisponibles(disponibilidades);

            // Establecer las horas disponibles en el ComboBox cbxHoraCita
            cbxHoraCita.setItems(horasDisponibles);

            // Deshabilitar los días no disponibles en el DatePicker
            Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(!diasDisponibles.contains(item));
                    if (!empty && !isDisabled()) {
                        setStyle("-fx-background-color: white;");
                    }
                }
            };
            dprCita.setDayCellFactory(dayCellFactory);
            
        }
    }
    
    private ObservableList<String> obtenerHorasDisponibles(ObservableList<Disponibilidad> disponibilidades) {
        ObservableList<String> horasDisponibles = FXCollections.observableArrayList();

        for (Disponibilidad disponibilidad : disponibilidades) {
            LocalTime desde = LocalTime.parse(disponibilidad.getDesde());
            LocalTime hasta = LocalTime.parse(disponibilidad.getHasta());

            while (desde.isBefore(hasta)) {
                horasDisponibles.add(desde.format(DateTimeFormatter.ofPattern("HH:mm")));
                desde = desde.plusMinutes(30); // Intervalo de 15 minutos, ajusta según tu necesidad
            }
        }

        return horasDisponibles;
    }

    
    
    
    private ObservableList<LocalDate> obtenerDiasDisponibles(ObservableList<Disponibilidad> disponibilidades) {
        ObservableList<LocalDate> diasDisponibles = FXCollections.observableArrayList();
        for (Disponibilidad disponibilidad : disponibilidades) {
            DayOfWeek diaSemana = obtenerDiaSemana(disponibilidad.getDia());
            if (diaSemana != null) {
                LocalDate fechaInicio = LocalDate.now().with(TemporalAdjusters.nextOrSame(diaSemana));
                LocalDate fechaFin = LocalDate.now().plusMonths(2).with(TemporalAdjusters.firstDayOfNextMonth()).with(TemporalAdjusters.previousOrSame(diaSemana));
                while (fechaInicio.isBefore(fechaFin)) {
                    diasDisponibles.add(fechaInicio);
                    fechaInicio = fechaInicio.plusWeeks(1);
                }
            }
        }
        return diasDisponibles;
    }

    
    private DayOfWeek obtenerDiaSemana(String dia) {
        switch (dia.toLowerCase()) {
            case "lunes":
                return DayOfWeek.MONDAY;
            case "martes":
                return DayOfWeek.TUESDAY;
            case "miércoles":
                return DayOfWeek.WEDNESDAY;
            case "jueves":
                return DayOfWeek.THURSDAY;
            case "viernes":
                return DayOfWeek.FRIDAY;
            case "sábado":
                return DayOfWeek.SATURDAY;
            case "domingo":
                return DayOfWeek.SUNDAY;
            default:
                return null;
        }
    }



    private void filtrarDoctoresPorEspecialidad(ActionEvent event) {
        String especialidadSeleccionada = cbxEspecialidadesCitas.getValue();

        if (especialidadSeleccionada != null) {
            // Obtener la lista de médicos filtrada por especialidad
            ObservableList<String> doctoresFiltrados = FXCollections.observableArrayList();
            try ( BufferedReader bufferedReader = new BufferedReader(new FileReader("./ARCHIVOS/medicos.txt"))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length > 5 && parts[5].equalsIgnoreCase(especialidadSeleccionada)) {
                        String nombre = parts[1];
                        String apellido = parts[2];

                        // Construir la cadena con nombre y apellido del médico
                        String nombreCompleto = nombre + " " + apellido;

                        // Agregar la cadena a la lista de médicos filtrados
                        doctoresFiltrados.add(nombreCompleto);
                    }
                }
            } catch (IOException e) {
                showMessages("Error al leer el archivo de médicos", 1);
            }

            // Establecer los médicos filtrados en el ComboBox de doctores
            cbxDoctores.setItems(doctoresFiltrados);

            // Llamar al método cargarDisponibilidades para mostrar las disponibilidades del primer médico en la lista
            if (!doctoresFiltrados.isEmpty()) {
                String primerMedico = doctoresFiltrados.get(0);
                cargarDisponibilidades(null);
            }
        }
    }

    
    private String obtenerIdentificacionMedico(String nombreCompletoMedico) {
        String identificacionMedico = "";

        try ( BufferedReader bufferedReader = new BufferedReader(new FileReader("./ARCHIVOS/medicos.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 2) {
                    String nombre = parts[1];
                    String apellido = parts[2];
                    if ((nombre + " " + apellido).equals(nombreCompletoMedico)) {
                        identificacionMedico = parts[0];
                        break;
                    }
                }
            }
        } catch (IOException e) {
            showMessages("Error al leer el archivo de médicos", 1);
        }

        return identificacionMedico;
    }

    private ObservableList<Disponibilidad> obtenerDisponibilidades(String identificacionMedico) {
        ObservableList<Disponibilidad> disponibilidades = FXCollections.observableArrayList();

        try ( BufferedReader bufferedReader = new BufferedReader(new FileReader("./ARCHIVOS/disponibilidad.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 4 && parts[1].equals(identificacionMedico)) {
                    String codigoDispo = parts[0];
                    String identificacionDoc = parts[1];
                    String dia = parts[2];
                    String desde = parts[3];
                    String hasta = parts[4];

                    Disponibilidad disponibilidad = new Disponibilidad(codigoDispo, identificacionDoc, dia, desde, hasta);
                    disponibilidades.add(disponibilidad);
                }
            }
        } catch (IOException e) {
            showMessages("Error al leer el archivo de disponibilidades", 1);
        }

        return disponibilidades;
    }
   
    private void modelaTablaDispo(){
        colDiaDispo.setCellValueFactory(new PropertyValueFactory<>("dia"));
        colDesdeDispo.setCellValueFactory(new PropertyValueFactory<>("desde"));
        colHastaDispo.setCellValueFactory(new PropertyValueFactory<>("hasta"));
    }
    
    private void modelaTablaCitas() {
        this.listaCitas = FXCollections.observableArrayList();

        this.colEspecialidadCita.setCellValueFactory(new PropertyValueFactory("especialidadDoc"));
        this.colDoctorCita.setCellValueFactory(new PropertyValueFactory("identificacionDoc"));
        this.colFechaCita.setCellValueFactory(new PropertyValueFactory("fechaCita"));
        this.colHoraCita.setCellValueFactory(new PropertyValueFactory("horaCita"));
    }
    
    private void cargarCitas() {
        String archivoCitas = "./ARCHIVOS/citas.txt";
        listaCitas.clear();
        tblCitas.getItems().clear(); // Limpiar la tabla de citas

        String pacienteId = txtIdPacCitas.getText().trim(); // Obtener el ID del paciente actual

        try ( BufferedReader br = new BufferedReader(new FileReader(archivoCitas))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datosCita = linea.split(",");
                String codigoCita = datosCita[0].trim();
                String identificacionPac = datosCita[1].trim();
                String especialidadDoc = datosCita[2].trim();
                String identificacionDoc = datosCita[3].trim();
                String fechaCita = datosCita[4].trim();
                String diaCita = datosCita[5].trim();
                String horaCita = datosCita[6].trim();

                if (identificacionPac.equals(pacienteId)) {
                    // Obtener el nombre y apellido del médico a partir de su código
                    String nombreMedico = obtenerNombreMedico(identificacionDoc);

                    // Crear una instancia de la clase Citas
                    Citas cita = new Citas(codigoCita, identificacionPac, especialidadDoc, nombreMedico, fechaCita, diaCita, horaCita);
                    listaCitas.add(cita);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        actualizarTablaCitas();
    }


    private String obtenerNombreMedico(String identificacionDoc) {
        String nombreMedico = "";

        try ( BufferedReader br = new BufferedReader(new FileReader("./ARCHIVOS/medicos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datosMedico = linea.split(",");
                String identificacion = datosMedico[0].trim();
                String nombre = datosMedico[1].trim();
                String apellido = datosMedico[2].trim();

                if (identificacion.equals(identificacionDoc)) {
                    nombreMedico = nombre + " " + apellido;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nombreMedico;
    }

    
    private void actualizarTablaCitas() {
        ObservableList<Citas> listaCitasObservable = FXCollections.observableArrayList(listaCitas);
        this.tblCitas.setItems(listaCitasObservable);        
    }

    @FXML
    private void doCargarCbxHora(ActionEvent event) {
        
    }
}
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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modelo.Pacientes;
import control.PacientesController;

/**
 * FXML Controller class
 *
 * @author Gianni
 */
public class EntradaController implements Initializable {

    @FXML
    private MenuItem menItemEspecialidades;
    @FXML
    private MenuItem menItemMedicos;
    @FXML
    private MenuItem menItemPacientes;
    @FXML
    private MenuItem menItemCitas;
    @FXML
    private MenuItem menItemSalir;
    @FXML
    private Pane panImagLogo;
    
    private PacientesController pacientesController;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
    }    

    @FXML
    private void goEspecialidades(ActionEvent event) {
        try
        {
           FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/Especialidades.fxml"));
           Parent root=loader.load();
           Scene scene = new Scene(root);
            
           //EspecialidadesController ouControlador = loader.getController();
           //ouControlador.constructorNuevo();                                //metodo para inicializar y mandarle cosas
            
           Stage stage= new Stage();
           stage.setOnCloseRequest(e -> {e.consume();});          //deshabilita la X de cerrar ventana
           stage.setResizable(false);                         //no permite redimensionar ventanas
           stage.setTitle("ESPECIALIDADES");                //titulo de la ventana   
           
           stage.setScene(scene);
           stage.show();
           
           Stage myStage=(Stage)this.panImagLogo.getScene().getWindow();
           myStage.close();
           
        } catch (IOException ex) {
            Logger.getLogger(EntradaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goMedicos(ActionEvent event) {
        try
        {
           FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/Medicos.fxml"));
           Parent root=loader.load();
           Scene scene = new Scene(root);
            
           //EspecialidadesController ouControlador = loader.getController();
           //ouControlador.constructorNuevo();                                //metodo para inicializar y mandarle cosas
            
           Stage stage= new Stage();
           stage.setResizable(false);                         //no permite redimensionar ventanas
           stage.setTitle("MÉDICOS");                //titulo de la ventana   
           
           stage.setScene(scene);
           stage.show();
           
           Stage myStage=(Stage)this.panImagLogo.getScene().getWindow();
           myStage.close();
           
        } catch (IOException ex) {
            Logger.getLogger(EntradaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goPacientes(ActionEvent event) {
        try
        {
           FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/Pacientes.fxml"));
           Parent root=loader.load();
           Scene scene = new Scene(root);
            
           //EspecialidadesController ouControlador = loader.getController();
           //ouControlador.constructorNuevo();                                //metodo para inicializar y mandarle cosas
            
           Stage stage= new Stage();
           stage.setResizable(false);                         //no permite redimensionar ventanas
           stage.setTitle("PACIENTES");                //titulo de la ventana   
           
           stage.setScene(scene);
           stage.show();
           
           Stage myStage=(Stage)this.panImagLogo.getScene().getWindow();
           myStage.close();
           
        } catch (IOException ex) {
            Logger.getLogger(EntradaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void goCitas(ActionEvent event) {
        try
        {
           FXMLLoader loader=new FXMLLoader(getClass().getResource("/vista/Citas.fxml"));
           Parent root=loader.load();
           Scene scene = new Scene(root);
           
  
            
           //EspecialidadesController ouControlador = loader.getController();
           //ouControlador.constructorNuevo();                                //metodo para inicializar y mandarle cosas
            
           Stage stage= new Stage();
           stage.setOnCloseRequest(e -> {e.consume();});          //deshabilita la X de cerrar ventana
           stage.setResizable(false);                         //no permite redimensionar ventanas
           stage.setTitle("CITAS");                //titulo de la ventana   
           
           stage.setScene(scene);
           stage.show();
           
           Stage myStage=(Stage)this.panImagLogo.getScene().getWindow();
           myStage.close();
           
        } catch (IOException ex) {
            Logger.getLogger(EntradaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void doSalir(ActionEvent event) {
        var stage = (Stage) this.panImagLogo.getScene().getWindow();
        stage.close();
        System.exit(0);
    }
}

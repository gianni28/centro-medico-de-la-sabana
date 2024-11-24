/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Gianni
 */
public class Entrada extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws IOException
    {  
        stage.setOnCloseRequest(event -> {event.consume();});  //deshabilita la X de cerrar
        stage.setResizable(false);                         //no permite redimensionar ventanas
        stage.setTitle("CENTRO MÉDICO DE LA SABANA");    //titulo de la ventana

        //Arma la ventana con el codigo XML
        Parent root = FXMLLoader.load(getClass().getResource("/vista/Entrada.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
         
        stage.show();                  //la muestra
    }
}

package gestion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class gestionConsultas {

    private String ruta;

    public gestionConsultas() {
        this.ruta = "./ARCHIVOS/consultas.txt";
        this.verificarArchivo();
    }

    private void verificarArchivo() {
        try {
            File filex = new File(this.ruta);
            if (!filex.exists()) {
                filex.createNewFile();
            }
        } catch (IOException ex) {
            System.out.println("Problemas con la ruta: " + ex.getMessage());
        }
    }

    public void guardarConsulta(String nombreDoc, String nombrePac, String fecha, String hora, String diagnostico, String prescripcion, String examenes) {
        String consulta = nombreDoc + "," + nombrePac + "," + fecha + "," + hora + "," + diagnostico + "," + prescripcion + "," + examenes + "\n";

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(this.ruta, true))) {
            writer.write(consulta);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

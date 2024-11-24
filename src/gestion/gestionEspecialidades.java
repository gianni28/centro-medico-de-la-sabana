package gestion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modelo.Especialidad;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class gestionEspecialidades {

    private String ruta;

    public gestionEspecialidades() {
        this.ruta = "./ARCHIVOS/especialidades.txt";
        this.verificarArchivo();
    }

    private void verificarArchivo() {
        try {
            File filex = new File(this.ruta);
            if (!filex.exists()) {
                filex.createNewFile();
            }
        } catch (IOException ex) {
            System.out.println("Problemas con la ruta" + ex);
        }
    }

    public void guardaEspecialidad(Especialidad especialidad) {
        File file;
        FileWriter fr;
        PrintWriter pw;

        try {
            file = new File(this.ruta);
            fr = new FileWriter(file, true); // Añadir al archivo existente
            pw = new PrintWriter(fr);

            pw.println(especialidad.getCodigo() + "," + especialidad.getNombre());

            pw.close();
        } catch (IOException ex) {
            System.out.println("No se pudo guardar la especialidad: " + ex.getMessage());
        }
    }

    public ObservableList<Especialidad> cargarEspecialidades() {
        ObservableList<Especialidad> especialidadesCargadas = FXCollections.observableArrayList();

        try ( BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    String codigo = datos[0];
                    String nombre = datos[1];
                    Especialidad especialidad = new Especialidad(nombre);
                    especialidad.setCodigo(codigo);
                    especialidadesCargadas.add(especialidad);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al cargar las especialidades: " + ex.getMessage());
        }

        return especialidadesCargadas;
    }

    public void actualizarEspecialidad(Especialidad especialidad) {
        ObservableList<Especialidad> especialidadesActuales = cargarEspecialidades();

        for (int i = 0; i < especialidadesActuales.size(); i++) {
            Especialidad esp = especialidadesActuales.get(i);
            if (esp.getCodigo().equals(especialidad.getCodigo())) {
                especialidadesActuales.set(i, especialidad);
                break;
            }
        }

        guardarEspecialidades(especialidadesActuales);
    }

    public void eliminarEspecialidad(Especialidad especialidad) {
        ObservableList<Especialidad> especialidadesActuales = cargarEspecialidades();

        especialidadesActuales.removeIf(esp -> esp.getCodigo().equals(especialidad.getCodigo()));

        guardarEspecialidades(especialidadesActuales);
    }

    private void guardarEspecialidades(ObservableList<Especialidad> especialidades) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            for (Especialidad especialidad : especialidades) {
                writer.write(especialidad.getCodigo() + "," + especialidad.getNombre());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

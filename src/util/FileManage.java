package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManage {

    // Método para garantir que o diretório existe antes de salvar arquivos
    private void ensureDirectoryExists(String filePath) {
        File file = new File(filePath);
        File directory = file.getParentFile(); // Obtém o diretório do arquivo
        if (directory != null && !directory.exists()) {
            boolean created = directory.mkdirs(); // Cria o diretório, se necessário
            if (created) {
                System.out.println("Diretório criado: " + directory.getAbsolutePath());
            } else {
                System.out.println("Falha ao criar o diretório: " + directory.getAbsolutePath());
            }
        }
    }

    // Método para ler objetos do arquivo
    public <T> List<T> readObjectFile(String filePath) {
        List<T> objects = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            objects = (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return objects;
    }

    // Método para escrever objetos no arquivo
    public <T> void writeObjectFile(String filePath, List<T> objects) {
        ensureDirectoryExists(filePath); // Garante que o diretório existe
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para ler dados do arquivo como String
    public List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    // Método para escrever dados no arquivo como String
    public void writeFile(String filePath, String data) {
        ensureDirectoryExists(filePath); // Garante que o diretório existe
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManage {

    private void ensureDirectoryExists(String filePath) {
        File file = new File(filePath);
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Diretório criado: " + directory.getAbsolutePath());
            } else {
                System.out.println("Falha ao criar o diretório: " + directory.getAbsolutePath());
            }
        }
    }

    public <T> List<T> readObjectFile(String filePath) {
        List<T> objects = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            objects = (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return objects;
    }

    public <T> void writeObjectFile(String filePath, List<T> objects) {
        ensureDirectoryExists(filePath);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(objects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void writeFile(String filePath, String data) {
        ensureDirectoryExists(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<String>> loadFilesToMap(String directoryPath) {
        ensureDirectoryExists(directoryPath);

        Map<String, List<String>> fileContents = new HashMap<>();
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Erro: O diretório não existe ou não é válido -> " + directoryPath);
            return fileContents;
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("Nenhum arquivo encontrado no diretório -> " + directoryPath);
            return fileContents;
        }

        for (File file : files) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                System.out.println("Lendo arquivo: " + file.getName());
                fileContents.put(file.getName(), readFile(file.getAbsolutePath()));
            }
        }

        return fileContents;
    }

    public File[] getFileNames(String directoryPath) {
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Erro: O diretório não existe ou não é válido -> " + directoryPath);
            return new File[0];
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("Nenhum arquivo encontrado no diretório -> " + directoryPath);
            return files;
        }

        System.out.println("Arquivos encontrados no diretório:");
        for (File file : files) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                System.out.println(file.getName());
            }
        }
        return files;
    }
}

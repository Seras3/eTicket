package service;

import graphic.GUI;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Database {
    private static Database database_instance = null;
    private final String path;
    private HashMap<String, File> name_to_file;

    private Database() {
        this.path = System.getProperty("user.dir") + "\\data\\";
        name_to_file = new HashMap<String, File>();

        for (File file : Objects.requireNonNull(new File(path).listFiles())) {
            if (file.isFile()) {
                name_to_file.put(file.getName(), file);
            }
        }
    }

    public static Database getInstance() {
        if(database_instance == null) { database_instance =  new Database(); }

        return database_instance;
    }

    public List<String[]> parse(String file_name) {
        List<String[]> payload = new ArrayList();
        String[] row;
        File file = name_to_file.get(file_name);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String row_string;
            while((row_string = reader.readLine()) != null) {
                row = row_string.split(",");
                payload.add(row.clone());
            }

        } catch (IOException e) {
            GUI.exception(e);
        } finally {

            try {
                reader.close();
            } catch (IOException e) {
                GUI.exception(e);
            }
        }

        return payload;
    }

    public void saveCSV(String file_name, String[] header, List<String[]> file_rows) {
        StringBuilder file_content = new StringBuilder();
        File file = name_to_file.get(file_name);
        BufferedWriter writer = null;
        file_rows.add(0, header);

        for(String[] row : file_rows) {
            file_content.append(String.join(",", row)).append('\n');
        }
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(file_content.toString());

        } catch(Exception e) {
            GUI.exception(e);
        } finally {

            try {
                writer.close();
            } catch (IOException e) {
                GUI.exception(e);
            }
        }
    }
}

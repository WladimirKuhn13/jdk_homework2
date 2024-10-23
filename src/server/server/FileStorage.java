package server.server;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileStorage implements Repository<String> {
    private static final String PATH = "src/server/log file.txt";

    @Override
    public void save(String message) {
        try (FileWriter writer = new FileWriter(PATH, true)) {
            writer.write(message);
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String load() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(PATH);) {
            int c;
            while ((c = reader.read()) != -1) {
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

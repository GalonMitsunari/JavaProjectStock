package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVGenerator {

    /**
     * G�n�re un fichier CSV.
     *
     * @param filePath Le chemin du fichier CSV � g�n�rer.
     * @param data     Les donn�es � inclure dans le CSV (liste de tableaux de cha�nes).
     * @param header   Les en-t�tes des colonnes.
     * @throws IOException En cas d'erreur d'�criture du fichier.
     */
    public static void generateCSV(String filePath, List<String[]> data, String[] header) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            if (header != null && header.length > 0) {
                writer.append(String.join(",", header));
                writer.append("\n");
            }

            for (String[] row : data) {
                writer.append(String.join(",", row));
                writer.append("\n");
            }

            System.out.println("Fichier CSV g�n�r� : " + filePath);
        }
    }
}

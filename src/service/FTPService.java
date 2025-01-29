package service;

import org.apache.commons.net.ftp.FTPClient;

import java.io.FileOutputStream;
import java.io.IOException;

public class FTPService {

    private FTPClient ftpClient;

    public FTPService() {
        ftpClient = new FTPClient();
    }

    private boolean checkFTPConnection() {
        try {
            if (!ftpClient.isConnected()) {
                ftpClient.connect("ftp.example.com");
                ftpClient.login("user", "password");
            }
            return ftpClient.isConnected();
        } catch (IOException e) {
            return false;
        }
    }

    public String downloadCSV(String remoteFile, String localFile) {
        try {
            if (!checkFTPConnection()) {
                return "Échec de la connexion FTP.";
            }
            ftpClient.retrieveFile(remoteFile, new FileOutputStream(localFile));
            return "Succès : Fichier téléchargé.";
        } catch (IOException e) {
            return "Erreur lors du téléchargement du fichier : " + e.getMessage();
        }
    }
}

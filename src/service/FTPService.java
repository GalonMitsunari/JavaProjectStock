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
                return "�chec de la connexion FTP.";
            }
            ftpClient.retrieveFile(remoteFile, new FileOutputStream(localFile));
            return "Succ�s : Fichier t�l�charg�.";
        } catch (IOException e) {
            return "Erreur lors du t�l�chargement du fichier : " + e.getMessage();
        }
    }
}

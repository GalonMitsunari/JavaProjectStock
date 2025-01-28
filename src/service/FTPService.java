package service;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FTPService {

    private final String server = "ftpperso.free.fr";
    private final int port = 21; 
    private final String user = "pottarn";
    private final String password = "cydeaxch0";

    public String downloadCSV(String remoteFilePath, String localFilePath) {
        FTPClient ftpClient = new FTPClient();

        if (!fileExists(localFilePath)) {
            createEmptyCSV(localFilePath);
        }

        try {
            ftpClient.connect(server, port);
            boolean login = ftpClient.login(user, password);

            if (!login) {
                return "Connexion FTP échouée.";
            }

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            if (!remoteFileExists(ftpClient, remoteFilePath)) {
                createEmptyCSVOnFTP(ftpClient, remoteFilePath);
            }

            try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
                boolean success = ftpClient.retrieveFile(remoteFilePath, fos);
                if (success) {
                    return "Fichier téléchargé avec succès.";
                } else {
                    return "Échec du téléchargement.";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Erreur lors du téléchargement : " + e.getMessage();
        } finally {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    private void createEmptyCSV(String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            String header = "Colonne1,Colonne2,Colonne3\n"; 
            fos.write(header.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean remoteFileExists(FTPClient ftpClient, String remoteFilePath) {
        try {
            return ftpClient.listFiles(remoteFilePath).length > 0;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createEmptyCSVOnFTP(FTPClient ftpClient, String remoteFilePath) {
        try {
            String emptyCSV = "Colonne1,Colonne2,Colonne3\n";
            byte[] data = emptyCSV.getBytes();
            ftpClient.storeFile(remoteFilePath, new java.io.ByteArrayInputStream(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

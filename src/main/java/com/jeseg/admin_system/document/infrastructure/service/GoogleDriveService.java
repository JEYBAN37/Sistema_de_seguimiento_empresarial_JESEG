package com.jeseg.admin_system.document.infrastructure.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.jeseg.admin_system.application.ex.TechnicalException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;

@Service
public class GoogleDriveService {

    private static final String FOLDER_ID = "1bII_QlaIgGxYAK504uSbyDte549L18Ef";

    /**
     * Inicializa y autentica la conexión con la API de Google Drive
     */
    private Drive getDriveService() throws Exception {
        // Lee el archivo JSON de credenciales desde src/main/resources/credentials.json
        InputStream in = GoogleDriveService.class.getResourceAsStream("/credentials.json");

        if (in == null) {
            throw TechnicalException.Type.ERROR_NO_ENCONTRADO_ARCHIVO_CREDENCIALES.build();
        }

        // Configura los permisos de acceso (Scope) para poder subir y leer archivos en Drive
        GoogleCredentials credentials = GoogleCredentials.fromStream(in)
                .createScoped(Collections.singleton(DriveScopes.DRIVE));

        // Retorna la instancia de Drive conectada y autenticada
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("Sistema-APS")
                .build();
    }

    /**
     * Recibe el archivo desde tu controlador (Next.js) y lo sube a la carpeta compartida de Google Drive
     *
     * @param multipartFile Archivo proveniente del formulario
     * @return El ID único del archivo en Drive (o la URL de visualización web)
     */
    public String subirArchivo(MultipartFile multipartFile) {
        try {
            // 1. Obtener el cliente conectado a Drive
            Drive driveService = getDriveService();

            // 2. Configurar los metadatos del archivo
            File metadata = new File();
            metadata.setName(multipartFile.getOriginalFilename());
            metadata.setParents(Collections.singletonList(FOLDER_ID));

// 3. Preparar el contenido binario del archivo
            com.google.api.client.http.InputStreamContent content =
                    new com.google.api.client.http.InputStreamContent(
                            multipartFile.getContentType(),
                            multipartFile.getInputStream()
                    );

// 4. Ejecutar la subida a los servidores de Google (AQUÍ ESTÁ EL CAMBIO)
            File uploadedFile = driveService.files().create(metadata, content)
                    .setFields("id, webViewLink")
                    .setSupportsAllDrives(true) // 🛠️ OBLIGATORIO: Le dice a Google que use los permisos de la carpeta contenedora
                    .execute();

            // 5. Retornar el ID o el Enlace para que lo guardes en tu base de datos de MySQL
            return uploadedFile.getWebViewLink();

        } catch (Exception e) {
            System.err.println("Error al subir archivo a Google Drive: " + e.getMessage());
            throw TechnicalException.Type.ERROR_CLOUD_FILES_CONECTED.build();
        }
    }
}
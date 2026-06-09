package com.jeseg.admin_system.document.infrastructure.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.jeseg.admin_system.application.ex.TechnicalException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
@Service
public class CloudinaryService {

    @Value("${app.cloudinary.url}")
    private String cloudinaryUrl;

    private Cloudinary cloudinary;

    @PostConstruct
    public void init() {
        // El SDK configura automáticamente el cloud_name "aps" junto con su llave y secreto
        this.cloudinary = new Cloudinary(cloudinaryUrl.trim());
    }

    public String subirArchivo(MultipartFile multipartFile) {
        File fileToUpload = null;
        try {
            fileToUpload = convertMultiPartToFile(multipartFile);

            // Configuramos el destino en tu contenedor
            Map options = ObjectUtils.asMap(
                    "folder", "anexos_aps",
                    "resource_type", "auto"
            );

            // Subida firmada estándar
            Map uploadResult = cloudinary.uploader().upload(fileToUpload, options);

            return (String) uploadResult.get("secure_url");

        } catch (Exception e) {
            throw TechnicalException.Type.ERROR_CLOUD_FILES_CONECTED.build();
        } finally {
            if (fileToUpload != null && fileToUpload.exists()) {
                fileToUpload.delete();
            }
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }
}
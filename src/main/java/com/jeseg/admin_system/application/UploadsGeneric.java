package com.jeseg.admin_system.application;

import com.jeseg.admin_system.application.ex.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadsGeneric {
    public static String uploadedFiles(MultipartFile file, String uploadDir) {

        if (file == null || file.isEmpty()) {
            throw BusinessException.Type.ERROR_GUARDAR_COMPANIA_LOGO_VACIO.build();
        }

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);

            // 2. Guardar el archivo físicamente
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            // 3. Crear la URL para la base de datos
            return "uploads/csv/" + fileName;
        } catch (Exception e) {
            throw BusinessException.Type.ERROR_GUARDAR_COMPANIA.build(e);
        }
    }
}

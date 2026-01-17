package com.jeseg.admin_system.application;

import com.jeseg.admin_system.application.ex.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class UploadsGeneric {

    private static final List<String> formats = List.of("M/d/yyyy", "d/M/yyyy", "yyyy-MM-dd","dd-MM-yyyy");
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

    public static LocalDate parseFecha(String fechaStr) {
        if (fechaStr == null || fechaStr.isBlank()) return null;

        // Lista de formatos posibles
        for (String format : formats) {
            try {
                return LocalDate.parse(fechaStr.trim(), DateTimeFormatter.ofPattern(format));
            } catch (DateTimeParseException ignored) {
                // Intenta el siguiente formato
                throw BusinessException.Type.ERROR_FORMATO_FECHA_INVALIDO.build();
            }
        }
        throw new RuntimeException("No se pudo parsear la fecha: " + fechaStr);
    }
}

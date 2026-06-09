package com.jeseg.admin_system.document.infrastructure.adapter;

import com.drew.metadata.exif.GpsDirectory;
import com.jeseg.admin_system.application.ex.BusinessException;
import com.jeseg.admin_system.document.domain.dto.CoordenadasGps;
import com.jeseg.admin_system.document.domain.dto.DocumentCreateRequest;
import com.jeseg.admin_system.document.domain.dto.DocumentResponse;
import com.jeseg.admin_system.document.domain.intreface.DocumentInterface;
import com.jeseg.admin_system.document.infrastructure.entity.DocumentEntity;
import com.jeseg.admin_system.document.infrastructure.repository.DocumentRepository;
import com.jeseg.admin_system.document.infrastructure.service.CloudinaryService;
import com.jeseg.admin_system.task.infrastructure.entity.TaskEntity;
import com.jeseg.admin_system.task.infrastructure.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.jeseg.admin_system.application.UploadsGeneric.uploadedFiles;

@Repository
@AllArgsConstructor
public class DocumentAdapter implements DocumentInterface {

    private final DocumentRepository documentRepository;
    private final TaskRepository taskRepository;
    private CloudinaryService cloudinaryService;

    @Override
    public void createDocument(DocumentCreateRequest documentCreateRequest) {
        documentRepository.save(DocumentEntity.builder().build());
    }

    @Override
    public void loadAnexos(DocumentCreateRequest documentCreateRequest) {
        TaskEntity taskId = taskRepository.findById(documentCreateRequest.getId())
                .orElseThrow(BusinessException.Type.ERROR_GUARDAR_HIERARCHY_COMPANIA_NO_EXISTE::build);


        List<MultipartFile> anexos = documentCreateRequest.getFiles();


        List<DocumentEntity> documentos = anexos.stream().map(anexo -> {
            // Extraemos la fecha real en la que se tomó la foto
            LocalDateTime fechaOriginal = getFechaCapturaFoto(anexo) != null ? getFechaCapturaFoto(anexo) : LocalDateTime.now();

            return DocumentEntity.builder()
                    .task(taskId)
                    .url(cloudinaryService.subirArchivo(anexo))
                    .typeAttachment(anexo.getContentType())
                    .filename(anexo.getOriginalFilename())
                    .extension(getFileExtension(anexo.getOriginalFilename()))
                    .extension(getFileExtension(anexo.getOriginalFilename()))
                    .timezone(fechaOriginal)
                    .createdAt(LocalDateTime.now())
                    .located(getCoordenadasFoto(anexo) != null
                            ? getCoordenadasFoto(anexo).toString() : documentCreateRequest.getLocation())
                    .build();
        }).toList();

        documentRepository.saveAll(documentos);
    }

    @Override
    public List<DocumentResponse> getDocumentsByTaskId(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(BusinessException.Type.ERROR_GUARDAR_HIERARCHY_COMPANIA_NO_EXISTE::build);

        List<DocumentEntity> documentEntities = documentRepository.findByTask(task.getId());
        if (!documentEntities.isEmpty()) {
            return documentEntities.stream().map(documentEntity -> DocumentResponse.builder()
                    .url(documentEntity.getUrl())
                    .timeSregistered(documentEntity.getTimezone() != null ? documentEntity.getTimezone().toString() : null)
                    .location(documentEntity.getLocated())
                    .build()).toList();
        }

        return List.of();
    }

    private LocalDateTime getFechaCapturaFoto(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            // Lee todos los metadatos del archivo binario
            Metadata metadata = ImageMetadataReader.readMetadata(is);

            // Buscamos el directorio EXIF específico donde los celulares guardan la fecha de captura
            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

            if (directory != null) {
                // TAG_DATETIME_ORIGINAL corresponde exactamente al momento del disparo de la cámara
                Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                if (date != null) {
                    // Convertimos el Date clásico de Java al LocalDateTime moderno
                    return date.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                }
            }
        } catch (Exception e) {
            // Si no es una imagen o no tiene metadatos (ej. fue enviada por WhatsApp que los borra), fallará silenciosamente
            System.err.println("No se pudieron extraer los metadatos EXIF de la imagen: " + e.getMessage());
        }
        // Si no tiene metadatos EXIF, retornamos null o la fecha actual del sistema como plan B
        return null;
    }

    private CoordenadasGps getCoordenadasFoto(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            Metadata metadata = ImageMetadataReader.readMetadata(is);

            // 2. Apuntamos al directorio GPS de los metadatos EXIF
            GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);

            if (gpsDirectory != null && gpsDirectory.getGeoLocation() != null) {
                // 3. La librería procesa internamente los grados, minutos y segundos
                // y nos entrega la ubicación en formato decimal listo para guardar o mapear
                double latitud = gpsDirectory.getGeoLocation().getLatitude();
                double longitud = gpsDirectory.getGeoLocation().getLongitude();

                return new CoordenadasGps(latitud, longitud);
            }
        } catch (Exception e) {
            System.err.println("No se pudieron extraer las coordenadas GPS: " + e.getMessage());
        }
        return null; // Retorna null si la foto no tenía el GPS encendido al tomarse
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

}

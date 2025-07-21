package com.DYShunyaev.LearningWeb.services;

import com.DYShunyaev.LearningWeb.dto.ImageDTO;
import com.DYShunyaev.LearningWeb.models.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class ImageService {
    @Value("${upload.path}")
    private String uploadPath;

    public ImageDTO toEntity(MultipartFile file) {
        try {
            return ImageDTO.builder()
                    .name(file.getName())
                    .originalName(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .bytes(file.getBytes())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String saveImage(ImageDTO image, String upload) {
        File uploadDir = new File(upload);
//        Charset charset = StandardCharsets.UTF_8;
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + image.getOriginalName() + ".txt";
//        file.transferTo(new File(upload + "/" + resultFileName));
        try (FileOutputStream writer = new FileOutputStream(resultFileName)){
            ObjectOutputStream oos = new ObjectOutputStream(writer);
            oos.writeInt(1);
            oos.writeObject(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultFileName;
    }
    public void push() {}

    public ResponseEntity<?> getImage(String photoName) {
        ImageDTO image = new ImageDTO();
        try(FileInputStream inputStream = new FileInputStream(photoName)) {
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            int length = ois.readInt();
            for (int i = 0; i < length; i++) {
                image = (ImageDTO) ois.readObject();
            }
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }
}

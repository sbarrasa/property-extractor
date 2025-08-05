package com.sbarrasa.extractor.service;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class TextExtractService {

    private final Tika tika = new Tika();

    public String extract(MultipartFile file) throws IOException {
        if (file == null)
            throw new IOException("No se ha recibido ningún archivo");

        if (file.isEmpty())
            throw new IOException("El archivo está vacío");

        try (InputStream input = file.getInputStream()) {
            return tika.parseToString(input);
        } catch (TikaException e) {
            throw new IOException("Error extrayendo texto del archivo", e);
        }
    }
}
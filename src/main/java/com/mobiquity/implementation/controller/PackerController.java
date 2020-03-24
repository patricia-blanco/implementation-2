package com.mobiquity.implementation.controller;


import com.mobiquity.implementation.exception.ApiException;
import com.mobiquity.implementation.packer.Packer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@RestController
public class PackerController {
    private Logger logger = LoggerFactory.getLogger(PackerController.class);

    /** Example api call http://localhost:8080/pack?filePath=/myPath/myFile*/
    @GetMapping("/pack")
    public @ResponseBody String pack(@RequestParam("filePath") @NotBlank String filePath) throws ApiException {
        try {
            logger.info("Reading file content " + filePath);
            Stream<String> content = Files.lines(Paths.get(filePath));
            return Packer.pack(content);
        } catch (IOException e) {
            //TODO - Implement the error handler
            logger.error("Error occurred trying to process file");
            throw new ApiException(e.getMessage(), e);
        }
    }
}

package com.wordpress.nikitapavlenko.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ImageBanner;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    @Autowired
    private Environment environment;

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();

            try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                PrintStream p = new PrintStream(byteArrayOutputStream);)
            {
                Resource resource = new ByteArrayResource(bytes);
                ImageBanner imageBanner = new ImageBanner(resource);
                imageBanner.printBanner(environment, getClass(), p);

                byte[] asciiBytes = byteArrayOutputStream.toByteArray();

                redirectAttributes.addFlashAttribute("message", new String(asciiBytes));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

}
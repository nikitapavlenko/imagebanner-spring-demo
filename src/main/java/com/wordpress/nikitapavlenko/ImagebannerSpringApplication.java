package com.wordpress.nikitapavlenko;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ImageBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class ImagebannerSpringApplication implements CommandLineRunner {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(ImagebannerSpringApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		Resource imageResource = new ClassPathResource("9gag.png");
		ImageBanner imageBanner = new ImageBanner(imageResource);
		imageBanner.printBanner(environment, getClass(), System.out);
	}
}

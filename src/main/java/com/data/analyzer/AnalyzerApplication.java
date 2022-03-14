package com.data.analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.Normalizer;
import java.util.regex.Pattern;

@SpringBootApplication
@EnableScheduling
public class AnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyzerApplication.class, args);
	}

}

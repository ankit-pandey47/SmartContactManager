package com.project.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class Cloudiniary_Image {

	@Bean
	public Cloudinary getCloudinary() {
		Map config = new HashMap<>();
		config.put("cloud_name" , "djehk2p5d");
		config.put("api_key", "957963864294951");
		config.put("api_secret", "Fs-T68JPawv2KeMcaMKgIHdk9BI");
		config.put("api_secure", true);
		
		return new Cloudinary(config);
	}
	
}

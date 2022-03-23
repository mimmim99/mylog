package com.smstudy.mylog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final String baseFilePath;
	
	public WebConfig(@Value("${custom.config.filepath}") String baseFilePath) {
		this.baseFilePath = baseFilePath;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//외부 리소스 경로 매핑
		// /files/** -> C:/dev/eclipse-workspace-private/mylog/files/**
		registry.addResourceHandler("/files/**").addResourceLocations("file:///" + baseFilePath + "/");
	}
}

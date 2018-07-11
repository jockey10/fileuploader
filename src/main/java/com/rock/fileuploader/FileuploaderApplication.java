package com.rock.fileuploader;

import com.rock.fileuploader.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.rock.fileuploader.api","com.rock.fileuploader.service"})
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class FileuploaderApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FileuploaderApplication.class, args);
    }
}

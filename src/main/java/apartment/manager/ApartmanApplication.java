package apartment.manager;

import apartment.manager.business.ApartmentService;
import apartment.manager.business.BuildingService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class ApartmanApplication {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private BuildingService buildingService;

    public static void main(String[] args) {
        SpringApplication.run(ApartmanApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // Set default timezone to Asia/Gaza which adjusts for daylight saving automatically
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Gaza"));
        System.out.println("Spring Boot application running in Asia/Gaza timezone :" + new java.util.Date());
    }
}

package apartment.manager;

import apartment.manager.Utilities.ExceptionToHttpStatusMapper;
import apartment.manager.Utilities.mappers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringBeans {
    @Bean
    public ExceptionToHttpStatusMapper getExceptionToHttpStatusMapper() {
        return new ExceptionToHttpStatusMapper();
    }

    @Bean
    public BuildingMapper getBuildingMapperImpl() {
        return new BuildingMapperImpl();
    }

    @Bean
    public ApartmentMapper getApartmentMapper() {
        return new ApartmentMapperImpl();
    }

    @Bean
    public PaymentMapper getPaymentMapper() {
        return new PaymentMapperImpl();
    }

    @Bean
    public RentalDetailsMapper getRentalDetailsMapper() {
        return new RentalDetailsMapperImpl();
    }
    /**
     * Creates a PasswordEncoder bean that uses BCrypt hashing algorithm.
     * <p>
     * This encoder is used to hash passwords before storing them and
     * to verify passwords during authentication.
     * BCrypt is a strong hashing algorithm that uses an adaptive approach,
     * which makes it resistant to brute-force attacks.
     *
     * @return an instance of BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package apartment.manager;

import apartment.manager.Utilities.ExceptionToHttpStatusMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans {
    @Bean
    public ExceptionToHttpStatusMapper getExceptionToHttpStatusMapper() {
        return new ExceptionToHttpStatusMapper();
    }
}

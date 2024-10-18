package apartment.manager;

import apartment.manager.Utilities.ExceptionToHttpStatusMapper;
import apartment.manager.Utilities.mappers.ApartmentMapper;
import apartment.manager.Utilities.mappers.ApartmentMapperImpl;
import apartment.manager.Utilities.mappers.BuildingMapper;
import apartment.manager.Utilities.mappers.BuildingMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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


}

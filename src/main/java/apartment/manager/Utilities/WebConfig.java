package apartment.manager.Utilities;

import apartment.manager.business.UserService;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebConfig {
    public static final String[] PUBLIC_URLS = {"/swagger-ui/**", "/authentication/**"};
    public static String TIMEZONE;
    @Autowired
    UserService userService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Value("${spring.jpa.properties.hibernate.jdbc.time_zone}")
    private String timeZoneInitialHolder; // Gets the value of the timezone from the properties file to add it later to the static variable

    @PostConstruct
    public void init() {
        TIMEZONE = timeZoneInitialHolder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_URLS).permitAll() // Permit the authentication endpoint
                        .anyRequest().authenticated() // Require authentication for all other requests
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Use stateless session management
                )
                .cors(Customizer.withDefaults())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /**
     * Creates an AuthenticationManager bean using the default Spring Security configuration.
     * <p>
     * The AuthenticationManager is responsible for processing authentication requests.
     * By retrieving it from the AuthenticationConfiguration, it leverages Spring Boot's
     * autoconfiguration to use the defined AuthenticationProviders or UserDetailsService.
     * <p>
     * This bean is typically used to authenticate users programmatically.
     *
     * @param authenticationConfiguration the configuration object that provides the AuthenticationManager.
     * @return an instance of AuthenticationManager.
     * @throws Exception if an error occurs while creating the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**") // Allow CORS for all endpoints
                        .allowedOriginPatterns("*") // Allow all origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specific HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow credentials
            }
        };
    }

    /**
     * Defines a Swagger group for all APIs in the application, excluding
     * certain paths (like disabled APIs) from being documented.
     */
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("All APIs")                  // Defines the group name for Swagger UI
                .pathsToExclude("/disabled/**")     // Excludes the specified paths from documentation
                .build();
    }

    /**
     * A bean used to register the cleanup filter
     */
    public FilterRegistrationBean<UserFilterCleanupFilter> userFilterCleanupFilter() {
        FilterRegistrationBean<UserFilterCleanupFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserFilterCleanupFilter());
        registrationBean.setOrder(Ordered.LOWEST_PRECEDENCE); // Run after all filters
        return registrationBean;
    }
}

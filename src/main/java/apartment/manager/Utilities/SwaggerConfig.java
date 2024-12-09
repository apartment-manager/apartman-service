/**
 * Copyright © 2024 Khaled Asfour
 */
package apartment.manager.Utilities;

import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig class is responsible for configuring and managing
 * Swagger API documentation in the application. This configuration allows
 * customization of API groups and the exclusion of certain paths from
 * the API documentation.
 */
@Configuration
public class SwaggerConfig {

//    /**
//     * Defines a Swagger group for all APIs in the application, excluding
//     * certain paths (like disabled APIs) from being documented.
//     */
//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("All APIs")                  // Defines the group name for Swagger UI
//                .pathsToExclude("/disabled/**")     // Excludes the specified paths from documentation
//                .build();
//    }
}

package darko.merli.Configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                description = "Documentation for Spring Boot project",
                title = "Channels and Users"
        )
)
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info().title("YouMerli"))
                .addSecurityItem(new SecurityRequirement().addList("MerliSecurityScheme"))
                .components(new Components().addSecuritySchemes("MerliSecurityScheme", new SecurityScheme().name("MerliSecurityScheme").type(SecurityScheme.Type.HTTP).scheme("basic")));
    }
}

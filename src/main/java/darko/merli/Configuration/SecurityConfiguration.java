package darko.merli.Configuration;

import darko.merli.Service.Implementation.UserFromDBImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration{

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    private static final String[] WHITE_LIST_URL = {
            "/v3/api-docs", "/resources/**", "/static/**", "/css/**", "/js/**", "/images/**","/vendor/**","/fonts/**", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/swagger-ui/**",
    };

    @Autowired
    public SecurityConfiguration(RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder());
        provider.setUserDetailsService(users());
        return provider;
    }

    @Bean
    public UserDetailsService users(){
        return new UserFromDBImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        .requestMatchers(HttpMethod.GET, "/channel/{name}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/create").permitAll()
                        .requestMatchers(HttpMethod.GET, "users/search/{name}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/videos/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "users/try").permitAll()
                        .requestMatchers(HttpMethod.GET, "/index.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/register.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/login.html").permitAll()
                        .requestMatchers(HttpMethod.GET,"/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer.loginPage("/login").permitAll();
                })
                .httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint);
        return http.build();
    }
}

//"/v3/api-docs/**",
//        "/swagger-ui/**",
//        "/swagger-ui.html"
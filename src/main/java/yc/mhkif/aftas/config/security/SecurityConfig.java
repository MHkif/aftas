package yc.mhkif.aftas.config.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import yc.mhkif.aftas.config.security.jwt.JwtAuthenticationFilter;
import yc.mhkif.aftas.enums.Role;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .requestMatchers(
                                        "aftas/api/v1/auth/login",
                                        "aftas/api/v1/auth/register"

                                ).permitAll()
                                .requestMatchers("aftas/api/v1/members").hasAnyRole(Role.MANAGER.name(), Role.JURY.name())
                                .requestMatchers(
                                        "aftas/api/v1/levels/*",
                                        "aftas/api/v1/levels",
                                        "aftas/api/v1/fishes",
                                        "aftas/api/v1/fishes/*"
                                ).hasRole(Role.MANAGER.name())
                                .requestMatchers(
                                        "aftas/api/v1/competitions",
                                        "aftas/api/v1/competitions/*",
                                        "aftas/api/v1/rankings/**",
                                        "aftas/api/v1/rankings"
                                ).hasAnyRole(Role.MANAGER.name(), Role.JURY.name(), Role.MEMBER.name())

                                .anyRequest().authenticated())

                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT));
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "PUT", "PATCH", "DELETE"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }













    /*
   // @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(adminAuthenticationProvider(), companyAuthenticationProvider());
    }

    //@Bean
    public AdminAuthenticationProvider adminAuthenticationProvider() {
        return new AdminAuthenticationProvider(passwordEncoder());
    }

    //@Bean
    public CompanyAuthenticationProvider companyAuthenticationProvider() {
        return new CompanyAuthenticationProvider(passwordEncoder());
    }


     */
}

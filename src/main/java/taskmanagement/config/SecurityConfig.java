package taskmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(Customizer.withDefaults())
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers(HttpMethod.POST,"/api/accounts").permitAll()
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/actuator/shutdown").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated())
                        .csrf(AbstractHttpConfigurer::disable)
                        .sessionManagement(sessions ->
                                sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                        .build();
    }
}

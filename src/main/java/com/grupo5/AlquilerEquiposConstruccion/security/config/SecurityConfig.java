package com.grupo5.AlquilerEquiposConstruccion.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers(antMatcher("/**")).permitAll()
//                        .anyRequest()
//                        .authenticated()
                        .requestMatchers(antMatcher("/user/create")).permitAll()
                        .requestMatchers(antMatcher("/api/auth/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET,"/swagger-ui/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET,"/categories/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET,"/products/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET,"/cities/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/images/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/reservations")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET,"/reservations/by-product/*")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET,"/user/confirmation/by-email/*")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/user")).hasRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/user/by-email/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/user/update/*")).hasRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/images/create/*")).hasRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/images/delete/*")).hasRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/images/delete/all/*")).hasRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/categories/create")).hasRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/categories/update")).hasRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/categories/delete/*")).hasRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.GET,"/reservations/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.GET,"/reservations/by-user/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.POST,"/reservations/create")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.PUT,"/reservations/update")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.DELETE,"/reservations/delete/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/favorites")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/favorites/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/favorites/create")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/favorites/update")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/favorites/delete/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.GET,"/favorites/by-product/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.GET,"/favorites/by-user/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.GET,"/favorites/by-user-and-product/**")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.DELETE,"/favorites/by-user-and-product/**")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/characteristics/create/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/characteristics/update/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/characteristics/delete/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/reviews")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/reviews/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/reviews/create")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/reviews/update")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/reviews/delete/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.GET,"/reviews/by-product/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.GET,"/reviews/by-user/*")).hasAnyRole("ADMIN", "USER")
                        .requestMatchers(antMatcher(HttpMethod.GET,"/reviews/by-user-and-product/**")).hasAnyRole("ADMIN", "USER")
//                        .requestMatchers(antMatcher(HttpMethod.GET, "/**")).hasAnyRole("ADMIN", "USER")
//                        .requestMatchers(antMatcher(HttpMethod.POST, "/**")).hasRole("ADMIN")
//                        .requestMatchers(antMatcher(HttpMethod.PUT, "/**")).hasRole("ADMIN")
//                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/**")).hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}

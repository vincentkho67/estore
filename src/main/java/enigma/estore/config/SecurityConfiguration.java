package enigma.estore.config;

import enigma.estore.security.JwtAuthenticationFilter;
import enigma.estore.utils.strings.ApiUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private static final String AUTH = ApiUrl.BASE_URL + "/auth/**";
    private static final String CATEGORIES = ApiUrl.BASE_URL + ApiUrl.CATEGORIES;
    private static final String PRODUCTS = ApiUrl.BASE_URL + ApiUrl.PRODUCTS;
    private static final String TRANSACTIONS = ApiUrl.BASE_URL + ApiUrl.TRANSACTION;
    private static final String USERS = ApiUrl.BASE_URL + ApiUrl.USERS;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH).permitAll()
                        .requestMatchers(HttpMethod.GET, CATEGORIES + "/**", PRODUCTS + "/**").permitAll()
                        .requestMatchers(HttpMethod.GET, TRANSACTIONS).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, CATEGORIES, PRODUCTS).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, TRANSACTIONS).hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, CATEGORIES + "/**", PRODUCTS + "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, CATEGORIES + "/**", PRODUCTS + "/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
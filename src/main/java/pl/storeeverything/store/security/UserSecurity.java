package pl.storeeverything.store.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pl.storeeverything.store.service.UserService;

import java.util.List;

@Configuration
@EnableWebSecurity
public class UserSecurity{
    private final UserService userService;
    private UserSecurityService userSecurityService;
    public UserSecurity(UserService userService) {
        this.userService = userService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.GET,"/notes/filter").permitAll()
                        .requestMatchers(HttpMethod.POST,"/notes/filter").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/styles/**").permitAll()
                        .requestMatchers("/register").permitAll() // all users
                        .requestMatchers("/notes/display/{id}").permitAll()
                        .requestMatchers("/notes/category/*").hasRole("ADMIN") // admin
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form //login page
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler())
                        .permitAll()
                )
                .logout((logout) -> logout.logoutSuccessUrl("/").permitAll());

        return http.build();
    }
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationManager authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        List<AuthenticationProvider> providerManager = List.of(daoAuthenticationProvider);

        return new ProviderManager(providerManager);
    }
}


package pl.storeeverything.store.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//
//        @Bean
//        public UserDetailsService userDetailsService() {
//            UserDetails user =
//                    User.withUsername("userr")
//                            .password("userr")
//                            .roles("USER")
//                            .build();
//
//            UserDetails weak_user =
//                    User.withUsername("usero")
//                            .password("usero")
//                            .roles("USER_WEAK")
//                            .build();
//
//            UserDetails admin =
//                    User.withUsername("admin")
//                            .password("admin")
//                            .roles("ADMIN")
//                            .build();
//
//            System.out.println(user.getUsername()+"" + user.getPassword()+
//                    "" +user.getAuthorities());
//
//            System.out.println(weak_user.getUsername()+"" + weak_user.getPassword()+
//                    "" +weak_user.getAuthorities());
//
//            System.out.println(admin.getUsername()+"" + admin.getPassword()+
//                    "" +admin.getAuthorities());
//
//            return new InMemoryUserDetailsManager(user, weak_user, admin);
//        }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/styles/**").permitAll()
                        .requestMatchers("/register").permitAll() // all users
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


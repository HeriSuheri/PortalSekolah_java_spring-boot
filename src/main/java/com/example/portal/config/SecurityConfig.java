// package com.example.portal.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// import org.springframework.http.HttpMethod;

// @Configuration
// public class SecurityConfig {

//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//         http
//                 .csrf(csrf -> csrf.disable())
//                 .cors(Customizer.withDefaults()) // ✅ perbaikan di sini
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // ✅ izinkan preflight
//                         .requestMatchers("/api/auth/login").permitAll()
//                         .anyRequest().authenticated())
//                 .httpBasic(basic -> basic.disable())
//                 .formLogin(login -> login.disable());

//         return http.build();
//     }

//     @Bean
//     public WebMvcConfigurer corsConfigurer() {
//         return new WebMvcConfigurer() {
//             @Override
//             public void addCorsMappings(CorsRegistry registry) {
//                 registry.addMapping("/**")
//                         .allowedOrigins("http://localhost:3000")
//                         .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                         .allowedHeaders("*")
//                         .allowCredentials(true);
//             }
//         };
//     }
// }

package com.example.portal.config;

import com.example.portal.config.helper.CustomUserDetailService;
import com.example.portal.security.JwtAuthenticationFilter;
import com.example.portal.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

    @Bean
    // start tanpa CustomerUserDetails
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //             .addFilterBefore(new JwtAuthenticationFilter(jwtUtil()), UsernamePasswordAuthenticationFilter.class)
    // end tanpa CustomerUserDetails
    public SecurityFilterChain filterChain(HttpSecurity http,
                                       JwtUtil jwtUtil,
                                       CustomUserDetailService customUserDetailService) throws Exception {
    http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, customUserDetailService),
                         UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/forgot-password").permitAll()
                        .requestMatchers("/api/auth/reset-password").permitAll()
                        .requestMatchers("/uploads/**").permitAll() // ⬅️ ini penting!
                        .requestMatchers(HttpMethod.POST, "/api/ppdb/register").permitAll() // calon siswa daftar
                        .requestMatchers(HttpMethod.GET, "/api/ppdb/**").permitAll() // cek status tanpa login
                        .requestMatchers(HttpMethod.PUT, "/api/ppdb/**").hasRole("ADMIN")
                        // Guru endpoints
                        .requestMatchers(HttpMethod.GET, "/api/guru/**").hasAnyRole("ADMIN", "GURU", "SISWA") // admin/guru
                        .requestMatchers(HttpMethod.POST, "/api/guru/**").hasRole("ADMIN") // create hanya admin
                        .requestMatchers(HttpMethod.PUT, "/api/guru/**").hasRole("ADMIN") // update hanya admin
                        .requestMatchers(HttpMethod.DELETE, "/api/guru/**").hasRole("ADMIN")
                        // .requestMatchers("/api/users/**").hasRole("ADMIN")
                        // .requestMatchers(HttpMethod.GET, "/api/users/roles").hasAnyRole("ADMIN",
                        // "GURU")
                        // .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // Siswa Endpoints
                        .requestMatchers(HttpMethod.POST, "/api/siswa/**").hasAnyRole("ADMIN", "GURU")
                        .requestMatchers(HttpMethod.PUT, "/api/siswa/**").hasAnyRole("ADMIN", "GURU")
                        .requestMatchers(HttpMethod.DELETE, "/api/siswa/**").hasAnyRole("ADMIN", "GURU")
                        .requestMatchers(HttpMethod.GET, "/api/siswa/**").hasAnyRole("ADMIN", "GURU", "SISWA")
                        .anyRequest().authenticated())
                .httpBasic(basic -> basic.disable())
                .formLogin(login -> login.disable());

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(CustomUserDetailService customUserDetailService) {
        return customUserDetailService;
    }


}
// package com.example.portal.security;

// import io.jsonwebtoken.Claims;
// import jakarta.servlet.*;
// import jakarta.servlet.http.*;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.*;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;
// import java.util.List;

// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final JwtUtil jwtUtil;

//     public JwtAuthenticationFilter(JwtUtil jwtUtil) {
//         this.jwtUtil = jwtUtil;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,
//             HttpServletResponse response,
//             FilterChain filterChain)
//             throws ServletException, IOException {
//         // String path = request.getServletPath();
//         // if (path.equals("/api/auth/forgot-password") || path.equals("/api/auth/reset-password")) {
//         //     filterChain.doFilter(request, response);
//         //     return;
//         // }

//         String authHeader = request.getHeader("Authorization");
//         if (authHeader != null && authHeader.startsWith("Bearer ")) {
//             String token = authHeader.substring(7);
//             try {
//                 Claims claims = jwtUtil.validateToken(token);
//                 String nomorInduk = claims.getSubject();
//                 String role = claims.get("role", String.class);

//                 List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
//                 Authentication auth = new UsernamePasswordAuthenticationToken(nomorInduk, null, authorities);
//                 SecurityContextHolder.getContext().setAuthentication(auth);
//                 System.out.println("Nomor Induk: " + nomorInduk);
//                 System.out.println("Role: " + role);
//                 System.out.println("Authorities: " + authorities);
//             } catch (Exception e) {
//                 // Token invalid, skip auth
//             }
//         }
//         filterChain.doFilter(request, response);
//     }
// }



// dengan CustomerUserDetails & CustomerUserDetailSrvice
package com.example.portal.security;

import com.example.portal.config.helper.CustomUserDetailService;
import com.example.portal.config.helper.CustomUserDetails;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailService customUserDetailService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = jwtUtil.validateToken(token);
                String nomorInduk = claims.getSubject();

                // ✅ load user dari DB → principal = CustomUserDetails
                CustomUserDetails userDetails =
                        (CustomUserDetails) customUserDetailService.loadUserByUsername(nomorInduk);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                // Token invalid, skip auth
            }
        }
        filterChain.doFilter(request, response);
    }
}
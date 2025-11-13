// package com.example.portal.dto;

// import com.example.portal.model.Role;

// public class RegisterUserRequest {
//     private String username;
//     private String password;
//     private Role role;

//     // Getters & Setters
//     public String getUsername() {
//         return username;
//     }

//     public void setUsername(String username) {
//         this.username = username;
//     }

//     public String getPassword() {
//         return password;
//     }

//     public void setPassword(String password) {
//         this.password = password;
//     }

//     public Role getRole() {
//         return role;
//     }

//     public void setRole(Role role) {
//         this.role = role;
//     }
// }

package com.example.portal.dto;

import com.example.portal.model.Role;
import java.time.LocalDate;

public class RegisterUserRequest {
    private String nomorInduk;
    private LocalDate tanggalLahir;
    private String password;
    private Role role;

    // Getters & Setters
    public String getNomorInduk() {
        return nomorInduk;
    }

    public void setNomorInduk(String nomorInduk) {
        this.nomorInduk = nomorInduk;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
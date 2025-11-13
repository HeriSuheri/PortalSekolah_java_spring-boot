// package com.example.portal.model;

// import java.time.LocalDate;

// import jakarta.persistence.*;

// @Entity
// @Table(name = "siswa")
// public class Siswa {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String nama;

//     @Column(unique = true, nullable = false)
//     private String nis;

//     @ManyToOne
//     @JoinColumn(name = "classroom_id")
//     private Classroom classroom;

//     @OneToOne
//     @JoinColumn(name = "user_id", unique = true)
//     private User user;

//     // Constructors
//     public Siswa() {
//     }

//     public Siswa(String nama, String nis, Classroom classroom, User user) {
//         this.nama = nama;
//         this.nis = nis;
//         this.classroom = classroom;
//         this.user = user;
//     }

//     // Getters and Setters
//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getNama() {
//         return nama;
//     }

//     public void setNama(String nama) {
//         this.nama = nama;
//     }

//     public String getNis() {
//         return nis;
//     }

//     public void setNis(String nis) {
//         this.nis = nis;
//     }

//     public Classroom getClassroom() {
//         return classroom;
//     }

//     public void setClassroom(Classroom classroom) {
//         this.classroom = classroom;
//     }

//     public User getUser() {
//         return user;
//     }

//     public void setUser(User user) {
//         this.user = user;
//     }
// }


package com.example.portal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "siswa")
public class Siswa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nama;

    @Column(unique = true, nullable = false)
    private String nis;

    @Column(name = "tanggal_lahir", nullable = false)
    private LocalDate tanggalLahir;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    // Constructors
    public Siswa() {}

    public Siswa(String nama, String nis, LocalDate tanggalLahir, Classroom classroom, User user) {
        this.nama = nama;
        this.nis = nis;
        this.tanggalLahir = tanggalLahir;
        this.classroom = classroom;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
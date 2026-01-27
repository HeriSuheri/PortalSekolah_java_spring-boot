package com.example.portal.service.auth;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.portal.model.PpdbRegistration;
import com.example.portal.model.Siswa;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendResetLink(String toEmail, String token) {
        String resetUrl = "http://localhost:3000/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Reset Password Portal Sekolah");
        message.setText(
                "Klik link berikut untuk reset password Anda:\n" + resetUrl + "\n\nLink berlaku selama 2 Menit.");

        mailSender.send(message);
    }

    // email ppdb
    public void sendRegistrationEmail(String to, String noPendaftaran, String nama) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Bukti Registrasi PPDB");
        message.setText("Halo " + nama + ",\n\n"
                + "Terima kasih sudah mendaftar PPDB.\n"
                + "Nomor pendaftaran Anda adalah: " + noPendaftaran + "\n\n"
                + "Silakan datang ke sekolah dengan membawa berkas sesuai ketentuan.\n\n"
                + "Salam,\nAdmin PPDB");
        mailSender.send(message);
    }

    // notif berhasil diterima
    public void sendAcceptanceEmail(Siswa siswa) {
        String to = siswa.getUser().getEmail();
        String subject = "Penerimaan Siswa Baru - " + siswa.getNama();
        String body = """
                Halo %s,

                Selamat! Pendaftaran Anda telah diterima.

                Detail siswa:
                - Nama: %s
                - NIS: %s
                - Kelas: %s
                - Status: %s
                - Status Pembayaran: %s
                - Jumlah Dibayar: %s

                Silakan login ke portal sekolah untuk informasi lebih lanjut.

                Salam,
                Admin Sekolah
                """.formatted(
                siswa.getNama(),
                siswa.getNama(),
                siswa.getNis(),
                siswa.getClassroom() != null ? siswa.getClassroom().getName() : "-",
                siswa.getStatus(),
                siswa.getStatusPembayaran(),
                siswa.getJumlahBayar());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendAcceptanceEmailPpdb(PpdbRegistration siswa) {
        String to = siswa.getEmail();
        String subject = "Penerimaan Siswa Baru - " + siswa.getNama();
        String body = """
                Halo %s,

                Selamat! Pendaftaran Anda telah diterima.

                Detail siswa:
                - Nama: %s
                - No Pendaftaran: %s
                - Alamat: %s
                - Tanggal Lahir: %s
                - No HP: %s
                - Status: %s
                - Status Pembayaran: %s
                - Jumlah Dibayar: %s

                Tunggu Informasi selanjutnya untuk pembagian kelas.

                Salam,
                Admin Sekolah
                """.formatted(
                siswa.getNama(),
                siswa.getNama(),
                siswa.getNoPendaftaran(),
                siswa.getAlamat(),
                siswa.getTanggalLahir(),
                siswa.getNoHandphone(),
                siswa.getStatus(),
                siswa.getStatusPembayaran(),
                siswa.getJumlahDibayar());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

}

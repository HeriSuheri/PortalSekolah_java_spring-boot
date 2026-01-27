package com.example.portal.service.auth;

import com.example.portal.model.PpdbRegistration;
import com.example.portal.model.Siswa;

public interface EmailService {
    void sendResetLink(String toEmail, String token);

    void sendRegistrationEmail(String to, String noPendaftaran, String nama);

    void sendAcceptanceEmail(Siswa siswa);

    void sendAcceptanceEmailPpdb(PpdbRegistration ppdb);
}

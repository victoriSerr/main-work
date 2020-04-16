package ru.itis.services;

import ru.itis.models.Mail;

public interface MailService {
    void sendEmail(Mail mail);
}

package com.example.MovieRentalSystem.service;

import com.example.MovieRentalSystem.model.RentalRecord;
import com.example.MovieRentalSystem.repository.RentalRecordRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderService {

    private final RentalRecordRepository rentalRecordRepository;

    private final JavaMailSender mailSender;

    public ReminderService(RentalRecordRepository rentalRecordRepository, JavaMailSender mailSender) {
        this.rentalRecordRepository = rentalRecordRepository;
        this.mailSender = mailSender;
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendReminderEmails() {
        LocalDate reminderDate = LocalDate.now().plusDays(2);
        List<RentalRecord> rentals = rentalRecordRepository.findAll();

        for (RentalRecord rental : rentals) {
            if (rental.getReturnDate().equals(reminderDate)) {
                sendEmail(rental.getUserEmail(), rental.getMovie().getTitle(), rental.getReturnDate());
            }
        }
    }

    private void sendEmail(String to, String movieTitle, LocalDate returnDate) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Movie Return Reminder");
        message.setText("Reminder: Your rental for the movie '" + movieTitle + "' is due for return on " + returnDate + ".");
        mailSender.send(message);
    }
}


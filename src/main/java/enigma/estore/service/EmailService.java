package enigma.estore.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Async("emailExecutor")
    public void sendTransactionConfirmationEmail(String to, String transactionDetails) {
        // Simulate email sending process
        try {
            Thread.sleep(5000); // Simulate a time-consuming process
            System.out.println("Email sent to " + to + " with transaction details: " + transactionDetails);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Email sending was interrupted");
        }
    }
}

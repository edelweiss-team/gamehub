package model.bean;

import javax.mail.MessagingException;

public class SendMailTest {
    public static void main(String[] args) throws MessagingException {
        System.out.println("\n\nProvo a mandare la mail...");

        EmailUtility.sendEmail(
                "smtp.gmail.com", "" + 587, "atatbj.22@gmail.com",
                "Billjobs22", "robyesposito2000@gmail.com",
                "Ordine #130 ricevuto",
                "L'ordine #130 è stato ricevuto!"
        );
        System.out.println("\n\nMessaggio mail mandato!\n\n");
    }
}

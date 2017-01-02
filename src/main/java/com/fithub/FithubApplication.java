package com.fithub;

/**
 * Entry point for the Spring boot application
 *
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FithubApplication {

	public static void main(String[] args) {
		SpringApplication.run(FithubApplication.class, args);

		// Email from = new Email("sharmamohits.ms@gmail.com");
		// String subject = "Sending with SendGrid is Fun";
		// Email to = new Email("mohitshsh@yahoo.co.in");
		// Content content = new Content("text/plain", "and easy to do anywhere,
		// even with Java");
		// Mail mail = new Mail(from, subject, to, content);
		//
		// SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
		// Request request = new Request();
		// try {
		// request.method = Method.POST;
		// request.endpoint = "mail/send";
		// request.body = mail.build();
		// Response response = sg.api(request);
		// System.out.println(response.statusCode);
		// System.out.println(response.body);
		// System.out.println(response.headers);
		// } catch (IOException ex) {
		// ex.printStackTrace();
		// }

	}
}

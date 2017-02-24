package com.fithub.service.email;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fithub.domain.SalesOrder;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

/**
 * Client for consuming email services for sending out transactional emails
 * 
 *
 */
@Service
public class MailServiceImpl implements MailService {

	private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);
	private final String emailSenderAddress;
	private final SendGrid sendGridClient;
	private static final String MAIL_SEND_ENDPOINT = "mail/send";

	@Autowired
	public MailServiceImpl(@Value("${application.emailSenderAddress}") final String emailSenderAddress) {
		this.emailSenderAddress = emailSenderAddress;
		this.sendGridClient = new SendGrid(System.getenv("SENDGRID_API_KEY"));
	}

	public void sendOrderReceiptMail(SalesOrder salesOrder) {
		// Reference:
		// https://github.com/sendgrid/sendgrid-java

		String orderReceiptTemplateID = "f458bc00-6465-4081-86d8-2393f8106754";
		Email senderAddress = new Email(emailSenderAddress);
		String subject = "Order Receipt";
		Email receiverAddress = new Email(salesOrder.getUser().getEmail());
		Content content = new Content("text/html", "Transactional Mail");
		Mail mail = new Mail(senderAddress, subject, receiverAddress, content);
		mail.personalization.get(0).addSubstitution("-totalOrderCost-", salesOrder.getSalesOrderTotalCost().toString());
		mail.personalization.get(0).addSubstitution("-orderId-", new Integer(salesOrder.getSalesOrderId()).toString());
		mail.personalization.get(0).addSubstitution("-userName-", salesOrder.getUser().getGivenName());
		mail.personalization.get(0).addSubstitution("-trackingNumber-", salesOrder.getTrackingNumber());
		mail.personalization.get(0).addSubstitution("-orderCreationDate-",
				salesOrder.getSalesOrderCreationDate().toString());
		mail.setTemplateId(orderReceiptTemplateID);

		sendEmail(mail, subject);

	}

	public void sendOrderCancellationMail(SalesOrder salesOrder) {
		// Reference:
		// https://github.com/sendgrid/sendgrid-java

		String orderCancellationTemplateId = "30dd2f58-3c81-41e2-b2b2-79fe4fffeb12";
		Email senderAddress = new Email(emailSenderAddress);
		String subject = "Order Cancellation";
		Email receiverAddress = new Email(salesOrder.getUser().getEmail());
		Content content = new Content("text/html", "Transactional Mail");
		Mail mail = new Mail(senderAddress, subject, receiverAddress, content);
		mail.personalization.get(0).addSubstitution("-totalOrderCost-", salesOrder.getSalesOrderTotalCost().toString());
		mail.personalization.get(0).addSubstitution("-orderId-", new Integer(salesOrder.getSalesOrderId()).toString());
		mail.personalization.get(0).addSubstitution("-userName-", salesOrder.getUser().getGivenName());
		mail.personalization.get(0).addSubstitution("-refundAmount-",
				salesOrder.getSalesOrderRefundAmount().toString());
		mail.personalization.get(0).addSubstitution("-orderCancellationDate-",
				salesOrder.getSalesOrderEditDate().toString());
		mail.personalization.get(0).addSubstitution("-refundId-", salesOrder.getStripeRefundId().toString());
		mail.setTemplateId(orderCancellationTemplateId);

		sendEmail(mail, subject);

	}

	public void sendWelcomeMail(String givenName, String email) {
		// Reference:
		// https://github.com/sendgrid/sendgrid-java

		String welcomeMailTemplateId = "8f77c455-39bc-49b1-9ec2-f165c4d3c09b";
		Email from = new Email(emailSenderAddress);
		String subject = "Welcome to FitHub";
		Email to = new Email(email);
		Content content = new Content("text/html", "Transactional Mail");
		Mail mail = new Mail(from, subject, to, content);

		mail.personalization.get(0).addSubstitution("-userName-", givenName);
		mail.setTemplateId(welcomeMailTemplateId);

		sendEmail(mail, subject);
	}

	public void sendPasswordResetMail(String givenName, String email, String resetPassword) {
		// Reference:
		// https://github.com/sendgrid/sendgrid-java

		String resetPasswordMailTemplateId = "51eafcf2-f8a9-4f85-b3a5-6e8cc30103fd";
		Email from = new Email(emailSenderAddress);
		String subject = "Password Reset Requested";
		Email to = new Email(email);
		Content content = new Content("text/html", "Password Reset Mail");
		Mail mail = new Mail(from, subject, to, content);

		mail.personalization.get(0).addSubstitution("-userName-", givenName);
		mail.personalization.get(0).addSubstitution("-resetPassword-", resetPassword);
		mail.setTemplateId(resetPasswordMailTemplateId);

		sendEmail(mail, subject);

	}

	/**
	 * Method sends the mail to the destination
	 * 
	 * @param mail
	 *            SendGrid mail object holding mail pertinent information
	 * @param emailSubject
	 *            Email Subject
	 */
	private void sendEmail(Mail mail, String emailSubject) {
		Request request = new Request();
		try {
			request.method = Method.POST;
			request.endpoint = MAIL_SEND_ENDPOINT;
			request.body = mail.build();
			Response response = sendGridClient.api(request);
			if (response.statusCode == 202)
				LOG.debug("{} email sent successfully", emailSubject);
			else
				LOG.debug("Problems encountered with sending {} email", emailSubject);
		}

		catch (IOException ex) {
			LOG.error("errors", ex.toString());
		}

	}

}

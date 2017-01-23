package com.fithub.restmailclient;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
@Component
public class RestMailClient {

	private static final Logger LOG = LoggerFactory.getLogger(RestMailClient.class);
	private final String emailSenderAddress;
	private final String sendGridApiKey;

	@Autowired
	public RestMailClient(@Value("${application.emailSenderAddress}") final String emailSenderAddress,
			@Value("${sendGrid.apiKey}") final String sendGridApiKey) {
		this.emailSenderAddress = emailSenderAddress;
		this.sendGridApiKey = sendGridApiKey;
	}

	public void sendOrderReceiptMail(SalesOrder salesOrder) {
		// Reference:
		// https://github.com/sendgrid/sendgrid-java

		String orderReceiptTemplateID = "f458bc00-6465-4081-86d8-2393f8106754";
		Email from = new Email(emailSenderAddress);
		String subject = "Order Receipt";
		Email to = new Email(salesOrder.getUser().getEmail());
		Content content = new Content("text/html", "Transactional Mail");
		Mail mail = new Mail(from, subject, to, content);
		mail.personalization.get(0).addSubstitution("-totalOrderCost-", salesOrder.getSalesOrderTotalCost().toString());
		mail.personalization.get(0).addSubstitution("-orderId-", new Integer(salesOrder.getSalesOrderId()).toString());
		mail.personalization.get(0).addSubstitution("-userName-", salesOrder.getUser().getGivenName());
		mail.personalization.get(0).addSubstitution("-trackingNumber-", salesOrder.getTrackingNumber());
		mail.personalization.get(0).addSubstitution("-orderCreationDate-",
				salesOrder.getSalesOrderCreationDate().toString());
		mail.setTemplateId(orderReceiptTemplateID);

		SendGrid sg = new SendGrid(sendGridApiKey);
		Request request = new Request();
		try {
			request.method = Method.POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			Response response = sg.api(request);
			if (response.statusCode == 202)
				LOG.debug("Order receipt sent successfully");
			else
				LOG.debug("Problems encountered with sending order receipt");
		}

		catch (IOException ex) {
			LOG.error("errors", ex.toString());
		}

	}

	public void sendOrderCancellationMail(SalesOrder salesOrder) {
		// Reference:
		// https://github.com/sendgrid/sendgrid-java

		String orderCancellationTemplateId = "30dd2f58-3c81-41e2-b2b2-79fe4fffeb12";
		Email from = new Email(emailSenderAddress);
		String subject = "Order Cancellation";
		Email to = new Email(salesOrder.getUser().getEmail());
		Content content = new Content("text/html", "Transactional Mail");
		Mail mail = new Mail(from, subject, to, content);
		mail.personalization.get(0).addSubstitution("-totalOrderCost-", salesOrder.getSalesOrderTotalCost().toString());
		mail.personalization.get(0).addSubstitution("-orderId-", new Integer(salesOrder.getSalesOrderId()).toString());
		mail.personalization.get(0).addSubstitution("-userName-", salesOrder.getUser().getGivenName());
		mail.personalization.get(0).addSubstitution("-refundAmount-",
				salesOrder.getSalesOrderRefundAmount().toString());
		mail.personalization.get(0).addSubstitution("-orderCancellationDate-",
				salesOrder.getSalesOrderEditDate().toString());
		mail.personalization.get(0).addSubstitution("-refundId-", salesOrder.getStripeRefundId().toString());
		mail.setTemplateId(orderCancellationTemplateId);

		SendGrid sg = new SendGrid(sendGridApiKey);
		Request request = new Request();
		try {
			request.method = Method.POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			Response response = sg.api(request);
			if (response.statusCode == 202)
				LOG.debug("Order cancellation mail sent successfully");
			else
				LOG.debug("Problems encountered with sending order cancellation mail");
		}

		catch (IOException ex) {
			LOG.error("errors", ex.toString());
		}

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

		SendGrid sg = new SendGrid(sendGridApiKey);
		Request request = new Request();
		try {
			request.method = Method.POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			Response response = sg.api(request);
			if (response.statusCode == 202)
				LOG.debug("User welcome mail sent successfully");
			else
				LOG.debug("Problems encountered with sending User welcome mail");
		}

		catch (IOException ex) {
			LOG.error("errors", ex.toString());
		}
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

		SendGrid sg = new SendGrid(sendGridApiKey);
		Request request = new Request();
		try {
			request.method = Method.POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			Response response = sg.api(request);
			if (response.statusCode == 202)
				LOG.debug("Password reset mail sent successfully");
			else
				LOG.debug("Problems encountered with sending Password reset mail");
		}

		catch (IOException ex) {
			LOG.error("errors", ex.toString());
		}

	}

}

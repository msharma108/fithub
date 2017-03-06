package com.fithub.integrationtests;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fithub.domain.SalesOrder;
import com.fithub.domain.User;
import com.fithub.service.email.MailService;

public class MailServiceIntegrationTest extends AbstractFithubApplicationIntegrationTest {

	String testReceipentName = "TestUser";
	String testRecipientEmailAddress = "befithubproject@gmail.com";
	String testRecipientInvalidEmailAddress = "TestUser";

	@Autowired
	private MailService mailService;

	@Test
	public void mailServiceSuccessfullySendsOrderReceiptMail() {

		int expectedSuccessHttpStatusCode = 202;
		SalesOrder testSalesOrder = prepareTestSalesOrder(testRecipientEmailAddress);

		int actualHttpStatusCode = mailService.sendOrderReceiptMail(testSalesOrder);

		assertEquals("Problems sending order receipt mail", expectedSuccessHttpStatusCode, actualHttpStatusCode);
	}

	@Test
	public void mailServiceDoesNotSendOrderReceiptMailWhenRecipientAddressIsInvalid() {

		int expectedInvalidHttpStatusCode = 0;
		SalesOrder testSalesOrder = prepareTestSalesOrder(testRecipientInvalidEmailAddress);

		int actualHttpStatusCode = mailService.sendOrderReceiptMail(testSalesOrder);

		assertEquals("Mail Service shouldn't send mail", expectedInvalidHttpStatusCode, actualHttpStatusCode);
	}

	@Test
	public void mailServiceSuccessfullySendsWelcomeMail() {

		int expectedSuccessHttpStatusCode = 202;
		int actualHttpStatusCode = mailService.sendWelcomeMail(testReceipentName, testRecipientEmailAddress);
		assertEquals("Problems with sending welcome mail", expectedSuccessHttpStatusCode, actualHttpStatusCode);
	}

	@Test
	public void mailServiceDoesNotSendWelcomeMailWhenRecipientAddressIsInvalid() {

		int expectedSuccessHttpStatusCode = 0;
		int actualHttpStatusCode = mailService.sendWelcomeMail(testReceipentName, testRecipientInvalidEmailAddress);
		assertEquals("Mail Service shouldn't send mail", expectedSuccessHttpStatusCode, actualHttpStatusCode);
	}

	@Test
	public void mailServiceSuccessfullySendsPasswordResetMail() {
		String testResetPasswordString = "testResetPassword";
		int expectedSuccessHttpStatusCode = 202;
		int actualHttpStatusCode = mailService.sendPasswordResetMail(testReceipentName, testRecipientEmailAddress,
				testResetPasswordString);
		assertEquals("Problems with sending password reset mail", expectedSuccessHttpStatusCode, actualHttpStatusCode);
	}

	@Test
	public void mailServiceDoesNotSendPasswordResetMailWhenRecipientAddressIsInvalid() {
		String testResetPasswordString = "testResetPassword";
		int expectedSuccessHttpStatusCode = 0;
		int actualHttpStatusCode = mailService.sendPasswordResetMail(testReceipentName,
				testRecipientInvalidEmailAddress, testResetPasswordString);
		assertEquals("Mail Service shouldn't send mail", expectedSuccessHttpStatusCode, actualHttpStatusCode);
	}

	@Test
	public void mailServiceSuccessfullySendsOrderCancellationMail() {

		int expectedSuccessHttpStatusCode = 202;
		SalesOrder testSalesOrder = prepareTestSalesOrder(testRecipientEmailAddress);

		int actualHttpStatusCode = mailService.sendOrderCancellationMail(testSalesOrder);

		assertEquals("Problems sending order cancellation mail", expectedSuccessHttpStatusCode, actualHttpStatusCode);
	}

	@Test
	public void mailServiceDoesNotSendOrderCancellationMailWhenRecipientAddressIsInvalid() {

		int expectedInvalidHttpStatusCode = 0;
		SalesOrder testSalesOrder = prepareTestSalesOrder(testRecipientInvalidEmailAddress);

		int actualHttpStatusCode = mailService.sendOrderCancellationMail(testSalesOrder);

		assertEquals("Mail Service shouldn't send mail", expectedInvalidHttpStatusCode, actualHttpStatusCode);
	}

	private SalesOrder prepareTestSalesOrder(String recipientEmailAddress) {

		// mock user details for test sales order
		User testUser = new User();
		testUser.setEmail(recipientEmailAddress);
		testUser.setGivenName(testReceipentName);

		SalesOrder salesOrder = new SalesOrder();
		salesOrder.setUser(testUser);
		salesOrder.setSalesOrderId(123);
		salesOrder.setSalesOrderCreationDate(new Date());
		salesOrder.setTrackingNumber("testTrackingNumer");
		salesOrder.setSalesOrderTotalCost(BigDecimal.ONE);
		salesOrder.setStripeRefundId("testRefundId");
		salesOrder.setSalesOrderEditDate(new Date());
		salesOrder.setSalesOrderRefundAmount(BigDecimal.ONE);
		return salesOrder;
	}

}

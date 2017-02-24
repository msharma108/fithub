package com.fithub.service.email;

import com.fithub.domain.SalesOrder;

/**
 * Interface for mail service that in turn consumes transactional email services
 *
 */
public interface MailService {

	/**
	 * Method sends an email with the order receipt
	 * 
	 * @param salesOrder
	 *            Sales Order which has been booked
	 */
	void sendOrderReceiptMail(SalesOrder salesOrder);

	/**
	 * Method sends an order cancellation mail
	 * 
	 * @param salesOrder
	 *            Sales Order being cancelled
	 */
	void sendOrderCancellationMail(SalesOrder salesOrder);

	/**
	 * Method sends a welcome mail
	 * 
	 * @param givenName
	 *            Given Name for the individual who has created an account on
	 *            the application
	 * @param email
	 *            Email for the individual who has created an account on the
	 *            application
	 */
	void sendWelcomeMail(String givenName, String email);

	/**
	 * Method sends password reset mail
	 * 
	 * @param givenName
	 *            Given Name for the individual whose password is being reset
	 * @param email
	 *            Email for the individual whose password is being reset
	 * @param resetPassword
	 *            Reset password which will be sent to the user
	 */
	void sendPasswordResetMail(String givenName, String email, String resetPassword);

}

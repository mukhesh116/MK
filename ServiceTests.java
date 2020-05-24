/*package com.goomo.offline.payment.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.goomo.offline.assembler.OfflinePaymentAssembler;
import com.goomo.offline.constants.APPConstants;
import com.goomo.offline.constants.MessageCodes;
import com.goomo.offline.constants.PaymentStatus;
import com.goomo.offline.entity.OfflinePaymentMaster;
import com.goomo.offline.entity.OfflineTransactionMaster;
import com.goomo.offline.io.OfflinePaymentModel;
import com.goomo.offline.io.OfflinePaymentRequest;
import com.goomo.offline.io.OfflinePaymentResponse;
import com.goomo.offline.io.OfflineTransactionModel;
import com.goomo.offline.repository.OfflinePaymentRepository;
import com.goomo.offline.repository.OfflineTransactionRepository;
import com.goomo.offline.service.OfflinePaymentService;
import com.goomo.offline.util.Mailer;
import com.goomo.offline.util.PhonOnSMS;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(value = OfflinePaymentService.class)
@ContextConfiguration(classes= {OfflinePaymentRepository.class})
public class OfflinePaymentServiceTest {

	@InjectMocks
	OfflinePaymentService service;
	
	@MockBean
	OfflinePaymentRepository repository ;
	
	@MockBean
	OfflineTransactionRepository transactionRepository ;
	
	@MockBean
	ModelMapper modelMapper ;
	
	@MockBean
	private VelocityEngine velocityEngine;
	
	@MockBean
	private Mailer mailer;

	@MockBean
	private PhonOnSMS sms;

	
	@Spy
	OfflinePaymentAssembler assembler = new OfflinePaymentAssembler();	
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
//	@Test
//	public void create_payment_link_success() throws Exception {
//		OfflinePaymentRequest createRequest = OfflinePaymentFactory.createPaymentLinkRequest();
//		OfflinePaymentMaster responseEntity = OfflinePaymentFactory.createResponseEntity();
//		//mock response
//		when(repository.save(Mockito.any(OfflinePaymentMaster.class))).thenReturn(responseEntity);
//		//actual response
//		OfflinePaymentResponse  actualResponse = service.createPaymentLink(createRequest);
//		assertEquals(MessageCodes.SUCCESS,actualResponse.getStatus());
//		assertEquals(MessageCodes.OFFLINE_CREATE_LINK_SUCCESS,actualResponse.getStatusMessage().getDescription());
//		
//	}
		
	@Test
	public void create_payment_link_reference_null_success() throws Exception {
		OfflinePaymentRequest createRequest = OfflinePaymentFactory.createPaymentLinkRequest();
		createRequest.setBookingValue(null);
		OfflinePaymentMaster responseEntity = OfflinePaymentFactory.createResponseEntity();
		//mock response
		when(repository.save(Mockito.any(OfflinePaymentMaster.class))).thenReturn(responseEntity);
		//actual response
		try {
			service.createPaymentLink(createRequest);
		} catch (Exception e) {
			assertEquals(MessageCodes.INVALID_BOOKING_VALUE,e.getLocalizedMessage());
		}
	}
	
	
//	@Test
//	public void cancel_payment_link_findbyid_throw_exception() throws Exception {
//		OfflinePaymentRequest createRequest = OfflinePaymentFactory.cancelPaymentLinkRequest();
//		//mock response
//		when(repository.findByofflineReference(createRequest.getOfflineReference())).thenReturn(Collections.emptyList());
//		//actual response
//		try {
//			service.cancelPaymentLink(createRequest);
//		} catch (IllegalArgumentException e) {
//			assertEquals(MessageCodes.NO_RECORDS_FOUND,e.getLocalizedMessage());
//		}
//	}
		
//	@Test
//	public void cancel_payment_link_cancelled_entry_throw_exception() throws Exception {
//		OfflinePaymentRequest createRequest = OfflinePaymentFactory.cancelPaymentLinkRequest();
//		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.findByResponseEntity();
//		List<OfflinePaymentMaster> entities = Arrays.asList(findByResponseEntity);
//		entities.get(0).setStatus(PaymentStatus.CANCELLED.getStatus());
//		//mock response
//		when(repository.findByofflineReference(Mockito.any(String.class))).thenReturn(entities);
//		//actual response
//		try {
//			service.cancelPaymentLink(createRequest);
//		} catch (IllegalArgumentException e) {
//			assertEquals(MessageCodes.PAYMENT_LINK_ALREDAY_CANCELLED,e.getLocalizedMessage());
//		}
//	}
	
	@Test
	public void cancel_payment_link_success() throws Exception {
		OfflinePaymentRequest cancelRequest = OfflinePaymentFactory.cancelPaymentLinkRequest();
		OfflinePaymentMaster responseEntity = OfflinePaymentFactory.createResponseEntity();
		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.findByResponseEntity();
		List<OfflinePaymentMaster> entities = Arrays.asList(findByResponseEntity);
		//mock response
		entities.get(0).setStatus(PaymentStatus.PENDING.getStatus());
		when(repository.findByofflineReference(Mockito.any(String.class))).thenReturn(entities);
		//mock response
		when(repository.save(Mockito.any(OfflinePaymentMaster.class))).thenReturn(responseEntity);
		//actual response
		OfflinePaymentResponse  actualResponse = service.cancelPaymentLink(cancelRequest);
		assertEquals(MessageCodes.SUCCESS,actualResponse.getStatus());
		assertEquals(MessageCodes.OFFLINE_CANCEL_LINK_SUCCESS,actualResponse.getStatusMessage().getDescription());
	}
	
//	@Test
//	public void resend_payment_link_findbyid_throw_exception() throws Exception {
//		OfflinePaymentRequest createRequest = OfflinePaymentFactory.cancelPaymentLinkRequest();
//		//mock response
//		when(repository.findByofflineReference(createRequest.getOfflineReference())).thenReturn(null);
//		//actual response
//		try {
//			service.resendPaymentLink(createRequest);
//		} catch (IllegalArgumentException e) {
//			assertEquals(MessageCodes.NO_RECORDS_FOUND,e.getLocalizedMessage());
//		}
//	}
//		
//	@Test
//	public void resend_payment_link_cancelled_entry_throw_exception() throws Exception {
//		OfflinePaymentRequest resendRequest = OfflinePaymentFactory.resendPaymentLinkRequest();
//		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.findByResponseEntity();
//		List<OfflinePaymentMaster> entities = Arrays.asList(findByResponseEntity);
//		//mock response
//		when(repository.findByemailReference(Mockito.any(String.class))).thenReturn(entities);
//		//actual response
//		try {
//			service.resendPaymentLink(resendRequest);
//		} catch (IllegalArgumentException e) {
//			assertEquals(MessageCodes.PAYMENT_LINK_ALREDAY_CANCELLED,e.getLocalizedMessage());
//		}
//	}
	
	
//	@Test
//	public void resend_payment_link_success() throws Exception {
//		OfflinePaymentRequest cancelRequest = OfflinePaymentFactory.cancelPaymentLinkRequest();
//		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.findByResponseEntity();
//		List<OfflinePaymentMaster> entities = Arrays.asList(findByResponseEntity);
//		//mock response
//		entities.get(0).setStatus(PaymentStatus.PENDING.getStatus());
//		when(repository.findByemailReference(Mockito.any(String.class))).thenReturn(entities);
//		//actual response
//		OfflinePaymentResponse  actualResponse = service.resendPaymentLink(cancelRequest);
//		assertEquals(MessageCodes.OFFLINE_RESEND_LINK_SUCCESS,actualResponse.getStatusMessage().getDescription());
//	}
	
	@Test
	public void payment_mode_link_request__null_throw_exception() throws Exception {
		try {
			service.paymentMode(null);
		} catch (IllegalArgumentException e) {
			assertEquals(MessageCodes.INVALID_REQUEST,e.getMessage());
		}
	}
	
	@Test
	public void payment_mode_link_findbyid_throw_exception() throws Exception {
		OfflinePaymentRequest paymentModeRequest = OfflinePaymentFactory.paymentModeRequest();
		//mock response
		when(repository.findByofflineReference(Mockito.any(String.class))).thenReturn( Collections.emptyList());
		//actual response
		try {
			service.paymentMode(paymentModeRequest);
		} catch (IllegalArgumentException e) {
			assertEquals(MessageCodes.NO_RECORDS_FOUND,e.getLocalizedMessage());
		}
	}
	
	@Test
	public void payment_mode_expired_error() throws Exception {
		OfflinePaymentRequest paymentModeRequest = OfflinePaymentFactory.paymentModeRequest();
		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.findByResponseEntity();
		findByResponseEntity.setStatus(MessageCodes.EXIPIRED_MSG);
		List<OfflinePaymentMaster> entities = Arrays.asList(findByResponseEntity);
		//mock response
		when(repository.findByofflineReference(Mockito.any(String.class))).thenReturn(entities);
		//actual response
		try {
			service.paymentMode(paymentModeRequest);
		} catch (Exception e) {
			assertEquals(MessageCodes.PAYMENT_LINK_ALREDAY_EXPIRED,e.getLocalizedMessage());
		}
		
	}
	
	@Test
	public void payment_mode_cancelled_error() throws Exception {
		OfflinePaymentRequest paymentModeRequest = OfflinePaymentFactory.paymentModeRequest();
		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.findByResponseEntity();
		findByResponseEntity.setStatus(PaymentStatus.CANCELLED.getStatus());
		List<OfflinePaymentMaster> entities = Arrays.asList(findByResponseEntity);
		//mock response
		when(repository.findByofflineReference(Mockito.any(String.class))).thenReturn(entities);
		//actual response
		try {
			service.paymentMode(paymentModeRequest);
		} catch (Exception e) {
			assertEquals(MessageCodes.PAYMENT_LINK_ALREDAY_CANCELLED,e.getLocalizedMessage());
		}
		
	}
	
	@Test
	public void payment_mode_success() throws Exception {
		OfflinePaymentRequest paymentModeRequest = OfflinePaymentFactory.paymentModeRequest();
		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.findByResponseEntity();
		List<OfflinePaymentMaster> entities = Arrays.asList(findByResponseEntity);
		//mock response
		when(repository.findByofflineReference(Mockito.any(String.class))).thenReturn(entities);
		//actual response
		OfflinePaymentResponse  actualResponse = service.paymentMode(paymentModeRequest);
		assertEquals("NB",actualResponse.getMode());
	}
	
	
	
	
	@Test
	public void modifyPaymentDetails_null_throw_exception() throws Exception {
		try {
			service.modifyPaymentDetails(null);
		} catch (IllegalArgumentException e) {
			assertEquals(MessageCodes.INVALID_REQUEST,e.getMessage());
		}
	}
	
	@Test
	public void modifyPaymentDetails_findbyid_throw_exception() throws Exception {
		OfflinePaymentRequest paymentModeRequest = OfflinePaymentFactory.paymentModeRequest();
		//mock response
		when(repository.findByofflineReference(Mockito.any(String.class))).thenReturn( Collections.emptyList());
		//actual response
		try {
			service.modifyPaymentDetails(paymentModeRequest);
		} catch (IllegalArgumentException e) {
			assertEquals(MessageCodes.NO_RECORDS_FOUND,e.getLocalizedMessage());
		}
	}
	
	@Test
	public void modifyPaymentDetails_success() throws Exception {
		OfflinePaymentRequest paymentModeRequest = OfflinePaymentFactory.paymentModeRequest();
		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.createResponseEntity();
		List<OfflinePaymentMaster> entities = Arrays.asList(findByResponseEntity);
		//mock response
		when(repository.findByofflineReference(Mockito.any(String.class))).thenReturn(entities);
		OfflinePaymentMaster paymetResponseEntity = OfflinePaymentFactory.updatePaymentDetailsResponseEntity();
		//mock response
		when(repository.save(Mockito.any(OfflinePaymentMaster.class))).thenReturn(paymetResponseEntity);
		when(transactionRepository.save(Mockito.any(OfflineTransactionMaster.class))).thenReturn(OfflinePaymentFactory.createTransactionResponseEntity());;
		//actual response
		OfflinePaymentResponse  actualResponse = service.modifyPaymentDetails(paymentModeRequest);
		assertEquals("2000", actualResponse.getAmountPaid());
	}
	
	
	
	
	
	@Test
	public void refundDetails_receiptId_null_throw_exception() throws Exception {
		OfflinePaymentRequest paymentModeRequest = OfflinePaymentFactory.paymentModeRequest();
		paymentModeRequest.setReceiptNo(null);
		try {
			service.processCheckReceipt(paymentModeRequest);
		} catch (IllegalArgumentException e) {
			assertEquals(MessageCodes.INVALID_RECEIPTNO,e.getMessage());
		}
	}
	
	@Test
	public void processCheckReceipt_success() throws Exception {
		OfflinePaymentRequest paymentModeRequest = OfflinePaymentFactory.paymentModeRequest();
		//mock response
		when(repository.findByreceiptNo(Mockito.any(String.class))).thenReturn(Collections.emptyList());
		//actual response
		OfflinePaymentResponse  actualResponse = service.processCheckReceipt(paymentModeRequest);
		assertEquals(MessageCodes.SUCCESS,actualResponse.getStatus());
		assertEquals(MessageCodes.SUCCESS_MSG,actualResponse.getStatusMessage().getCode());
		assertEquals(MessageCodes.RECEIPT_NOT_FOUND,actualResponse.getStatusMessage().getDescription());
	}
	
	@Test
	public void processCheckReceipt_failure() throws Exception {
		OfflinePaymentRequest paymentModeRequest =  OfflinePaymentRequest.builder().
				receiptNo("612cxac689").
				build();
		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.createResponseEntity();
		List<OfflinePaymentMaster> entities = Arrays.asList(findByResponseEntity);
		//mock response
		when(repository.findByreceiptNo(Mockito.any(String.class))).thenReturn(entities);
		//actual response
		OfflinePaymentResponse  actualResponse = service.processCheckReceipt(paymentModeRequest);
		assertEquals(MessageCodes.BAD_REQUEST,actualResponse.getStatus());
		assertEquals(MessageCodes.ERROR,actualResponse.getStatusMessage().getCode());
		assertEquals(MessageCodes.RECEIPT_ALREADY_EXSIT,actualResponse.getStatusMessage().getDescription());
	}
	
	//processTxnDetails test cases
	
	@Test
	public void processTxnDetails_invalid_request_null_throw_exception() throws Exception {
		try {
			service.processTxnDetails(null);
		} catch (IllegalArgumentException e) {
			assertEquals(MessageCodes.INVALID_REQUEST,e.getMessage());
		}
	}
	//offline response
	@Test
	public void processTxnDetails_offline_reference_empty_list_success() throws Exception {
		OfflinePaymentRequest offlineTxnDetailsRequest = OfflinePaymentRequest.builder().requestedBy("ADMIN").channelType("B2C").
				productType("OFFLINE").offlineReference("21jkd21323").build();
		//mock response
		when(repository.findByofflineReference(Mockito.any(String.class))).thenReturn(Collections.emptyList());
		//actual response
		try {
			service.processTxnDetails(offlineTxnDetailsRequest);
		} catch (Exception e) {
			assertEquals(MessageCodes.NO_RECORDS_FOUND,e.getMessage());
		}
	}
		
		
	//offline response
	@Test
	public void processTxnDetails_offline_reference_success() throws Exception {
		OfflinePaymentRequest offlineTxnDetailsRequest = OfflinePaymentRequest.builder().requestedBy("ADMIN").channelType("B2C").
				productType("OFFLINE").offlineReference("21jkd21323").
				build();
		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.createResponseEntity();
		List<OfflinePaymentMaster> entities = Arrays.asList(findByResponseEntity);
		OfflinePaymentModel model = OfflinePaymentFactory.createResponseModel();
		//mock response
		when(repository.findByofflineReference(Mockito.any(String.class))).thenReturn(entities);
		when(modelMapper.map(entities.get(0), OfflinePaymentModel.class)).thenReturn(model);
		//actual response
		OfflinePaymentResponse  actualResponse = service.processTxnDetails(offlineTxnDetailsRequest);
		assertEquals(MessageCodes.SUCCESS,actualResponse.getStatus());
		assertEquals(MessageCodes.SUCCESS_MSG,actualResponse.getStatusMessage().getCode());
		assertEquals(MessageCodes.RECORDS_FOUND_MSG_DESC,actualResponse.getStatusMessage().getDescription());
		
	}
	
	//bookingId response
	@Test
	public void processTxnDetails_bookingId_empty_success() throws Exception {
		OfflinePaymentRequest offlineTxnDetailsRequest = OfflinePaymentRequest.builder().requestedBy("ADMIN").channelType("B2C").
				productType("OFFLINE").bookingId("21jkd21323").
				build();
		//mock response
		when(transactionRepository.findBybookingId(Mockito.any(String.class))).thenReturn(Collections.emptyList());
		//actual response
		OfflinePaymentResponse  actualResponse = service.processTxnDetails(offlineTxnDetailsRequest);
		assertEquals(MessageCodes.BAD_REQUEST,actualResponse.getStatus());
		assertEquals(MessageCodes.ERROR,actualResponse.getStatusMessage().getCode());
		assertEquals(MessageCodes.NO_RECORDS_FOUND,actualResponse.getStatusMessage().getDescription());
		
	}
	
	
	//bookingId response
	@Test
	public void processTxnDetails_bookingId_success() throws Exception {
		OfflinePaymentRequest offlineTxnDetailsRequest = OfflinePaymentRequest.builder().requestedBy("ADMIN").channelType("B2C").
				productType("OFFLINE").bookingId("21jkd21323").build();
		OfflineTransactionMaster findByTransactionResponseEntity = OfflinePaymentFactory.createTransactionWithDataResponseEntity();
		List<OfflineTransactionMaster> transactionEntities = Arrays.asList(findByTransactionResponseEntity);
		OfflineTransactionModel  transactionResponseModel = OfflinePaymentFactory.createTransactionWithDataResponseModel();
		//mock response
		when(transactionRepository.findBybookingId(Mockito.any(String.class))).thenReturn(transactionEntities);
		when(modelMapper.map(transactionEntities.get(0), OfflineTransactionModel.class)).thenReturn(transactionResponseModel);
		//actual response
		OfflinePaymentResponse  actualResponse = service.processTxnDetails(offlineTxnDetailsRequest);
		assertEquals(MessageCodes.SUCCESS,actualResponse.getStatus());
		assertEquals(MessageCodes.SUCCESS_MSG,actualResponse.getStatusMessage().getCode());
		assertEquals(MessageCodes.RECORDS_FOUND_MSG_DESC,actualResponse.getStatusMessage().getDescription());
		assertTrue(actualResponse.getTransactions().size() > 0);;
	}
	
	//email response
	@Test
	public void processTxnDetails_email_reference_success() throws Exception {
		OfflinePaymentRequest offlineTxnDetailsRequest = OfflinePaymentRequest.builder().requestedBy("ADMIN").channelType("B2C").
				productType("OFFLINE").emailReference("deqwue123213").
				build();
		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.createResponseEntity();
		List<OfflinePaymentMaster> entities = Arrays.asList(findByResponseEntity);
		OfflinePaymentModel model = OfflinePaymentFactory.createResponseModel();
		//mock response
		when(repository.findByemailReference(Mockito.any(String.class))).thenReturn(entities);
		when(modelMapper.map(entities.get(0), OfflinePaymentModel.class)).thenReturn(model);
		//actual response
		OfflinePaymentResponse  actualResponse = service.processTxnDetails(offlineTxnDetailsRequest);
		assertEquals(MessageCodes.SUCCESS,actualResponse.getStatus());
		assertEquals(MessageCodes.SUCCESS_MSG,actualResponse.getStatusMessage().getCode());
		assertEquals(MessageCodes.RECORDS_FOUND_MSG_DESC,actualResponse.getStatusMessage().getDescription());
		
	}
	
	//paymentStatus "ALL" response
	@Test
	public void processTxnDetails_status_without_pagination_all_success() throws Exception {
		OfflinePaymentRequest offlineTxnDetailsRequest = OfflinePaymentRequest.builder().requestedBy("ADMIN").channelType("B2C").
				productType("OFFLINE").paymentStatus("ALL").
				build();
		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.createResponseEntity();
		List<OfflinePaymentMaster> entities = Arrays.asList(findByResponseEntity);
		OfflinePaymentModel model = OfflinePaymentFactory.createResponseModel();
		//mock response
		when(repository.findAll()).thenReturn(entities);
		when(modelMapper.map(entities.get(0), OfflinePaymentModel.class)).thenReturn(model);
		//actual response
		OfflinePaymentResponse  actualResponse = service.processTxnDetails(offlineTxnDetailsRequest);
		assertEquals(MessageCodes.SUCCESS,actualResponse.getStatus());
		assertEquals(MessageCodes.SUCCESS_MSG,actualResponse.getStatusMessage().getCode());
		assertEquals(MessageCodes.RECORDS_FOUND_MSG_DESC,actualResponse.getStatusMessage().getDescription());
		
	}

	//paymentStatus "ALL" response
	@Test
	public void processTxnDetails_status_with_pagination_all_success() throws Exception {
		OfflinePaymentRequest offlineTxnDetailsRequest = OfflinePaymentRequest.builder().requestedBy("ADMIN").channelType("B2C").
				productType("OFFLINE").paymentStatus("ALL").
				start("0").
				end("2").
				build();
		Pageable firstPageWithTwoElements = PageRequest.of(APPConstants.PAGE_SIZE_DEFAULT,Integer.parseInt("2"));
		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.createResponseEntity();
		OfflinePaymentMaster findByResponseEntity2 = OfflinePaymentFactory.createResponseEntity();
		OfflinePaymentMaster findByResponseEntity3 = OfflinePaymentFactory.createResponseEntity();
		OfflinePaymentMaster findByResponseEntity4 = OfflinePaymentFactory.createResponseEntity();
		List<OfflinePaymentMaster> entities = new ArrayList<>();
		entities.add(findByResponseEntity);
		entities.add(findByResponseEntity2);
		entities.add(findByResponseEntity3);
		entities.add(findByResponseEntity4);
		
		OfflinePaymentModel model = OfflinePaymentFactory.createResponseModel();
		//mock response
		when(repository.findAll(firstPageWithTwoElements)).thenReturn(entities);
		when(modelMapper.map(entities.get(0), OfflinePaymentModel.class)).thenReturn(model);
		when(modelMapper.map(entities.get(1), OfflinePaymentModel.class)).thenReturn(model);
		when(modelMapper.map(entities.get(2), OfflinePaymentModel.class)).thenReturn(model);
		when(modelMapper.map(entities.get(3), OfflinePaymentModel.class)).thenReturn(model);
		//actual response
		OfflinePaymentResponse  actualResponse = service.processTxnDetails(offlineTxnDetailsRequest);
		assertEquals(MessageCodes.SUCCESS,actualResponse.getStatus());
		assertEquals(MessageCodes.SUCCESS_MSG,actualResponse.getStatusMessage().getCode());
		assertEquals(MessageCodes.RECORDS_FOUND_MSG_DESC,actualResponse.getStatusMessage().getDescription());
	}
		
	//paymentStatus "APPROVED" response
	@Test
	public void processTxnDetails_status_without_pagination_APPROVED_success() throws Exception {
		OfflinePaymentRequest offlineTxnDetailsRequest = OfflinePaymentRequest.builder().requestedBy("ADMIN").channelType("B2C").
				productType("OFFLINE").paymentStatus("APPROVED").
				build();
		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.createResponseEntity();
		List<OfflinePaymentMaster> entities = Arrays.asList(findByResponseEntity);
		OfflinePaymentModel model = OfflinePaymentFactory.createResponseModel();
		//mock response
		when(repository.findBypaymentStatus(Mockito.anyString())).thenReturn(entities);
		when(modelMapper.map(entities.get(0), OfflinePaymentModel.class)).thenReturn(model);
		//actual response
		OfflinePaymentResponse  actualResponse = service.processTxnDetails(offlineTxnDetailsRequest);
		assertEquals(MessageCodes.SUCCESS,actualResponse.getStatus());
		assertEquals(MessageCodes.SUCCESS_MSG,actualResponse.getStatusMessage().getCode());
		assertEquals(MessageCodes.RECORDS_FOUND_MSG_DESC,actualResponse.getStatusMessage().getDescription());
	}
	
	//paymentStatus "ALL" response
	@Test
	public void processTxnDetails_status_with_pagination_success() throws Exception {
		OfflinePaymentRequest offlineTxnDetailsRequest = OfflinePaymentRequest.builder().requestedBy("ADMIN").channelType("B2C").
				productType("OFFLINE").paymentStatus("APPROVED").
				start("0").
				end("2").
				build();
		OfflinePaymentMaster findByResponseEntity = OfflinePaymentFactory.createResponseEntity();
		OfflinePaymentMaster findByResponseEntity2 = OfflinePaymentFactory.createResponseEntity();
		OfflinePaymentMaster findByResponseEntity3 = OfflinePaymentFactory.createResponseEntity();
		OfflinePaymentMaster findByResponseEntity4 = OfflinePaymentFactory.createResponseEntity();
		List<OfflinePaymentMaster> entities = new ArrayList<>();
		entities.add(findByResponseEntity);
		entities.add(findByResponseEntity2);
		entities.add(findByResponseEntity3);
		entities.add(findByResponseEntity4);
		
		OfflinePaymentModel model = OfflinePaymentFactory.createResponseModel();
		//mock response
		when(repository.findBypaymentStatus(Mockito.anyString())).thenReturn(entities);
		when(modelMapper.map(entities.get(0), OfflinePaymentModel.class)).thenReturn(model);
		when(modelMapper.map(entities.get(1), OfflinePaymentModel.class)).thenReturn(model);
		when(modelMapper.map(entities.get(2), OfflinePaymentModel.class)).thenReturn(model);
		when(modelMapper.map(entities.get(3), OfflinePaymentModel.class)).thenReturn(model);
		//actual response
		OfflinePaymentResponse  actualResponse = service.processTxnDetails(offlineTxnDetailsRequest);
		assertEquals(MessageCodes.SUCCESS,actualResponse.getStatus());
		assertEquals(MessageCodes.SUCCESS_MSG,actualResponse.getStatusMessage().getCode());
		assertEquals(MessageCodes.RECORDS_FOUND_MSG_DESC,actualResponse.getStatusMessage().getDescription());
	}
	
	
	
}
*/

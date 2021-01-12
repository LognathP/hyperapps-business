package com.hyperapps.model;

import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
	
	@Qualifier("postBackParamId")
    @JsonProperty("postBackParamId")
    public Integer postBackParamId;
    @Qualifier("mihpayid")
    @JsonProperty("mihpayid")
    public String mihpayid;
    @Qualifier("paymentId")
    @JsonProperty("paymentId")
    public Integer paymentId;
    @Qualifier("mode")
    @JsonProperty("mode")
    public String mode;
    @Qualifier("status")
    @JsonProperty("status")
    public String status;
    @Qualifier("unmappedstatus")
    @JsonProperty("unmappedstatus")
    public String unmappedstatus;
    @Qualifier("key")
    @JsonProperty("key")
    public String key;
    @Qualifier("txnid")
    @JsonProperty("txnid")
    public String txnid;
    @Qualifier("amount")
    @JsonProperty("amount")
    public String amount;
    @Qualifier("additionalCharges")
    @JsonProperty("additionalCharges")
    public String additionalCharges;
    @Qualifier("addedon")
    @JsonProperty("addedon")
    public String addedon;
    @Qualifier("createdOn")
    @JsonProperty("createdOn")
    public long createdOn;
    @Qualifier("productinfo")
    @JsonProperty("productinfo")
    public String productinfo;
    @Qualifier("firstname")
    @JsonProperty("firstname")
    public String firstname;
    @Qualifier("lastname")
    @JsonProperty("lastname")
    public String lastname;
    @Qualifier("address1")
    @JsonProperty("address1")
    public String address1;
    @Qualifier("address2")
    @JsonProperty("address2")
    public String address2;
    @Qualifier("city")
    @JsonProperty("city")
    public String city;
    @Qualifier("state")
    @JsonProperty("state")
    public String state;
    @Qualifier("country")
    @JsonProperty("country")
    public String country;
    @Qualifier("zipcode")
    @JsonProperty("zipcode")
    public String zipcode;
    @Qualifier("email")
    @JsonProperty("email")
    public String email;
    @Qualifier("phone")
    @JsonProperty("phone")
    public String phone;
    @Qualifier("udf1")
    @JsonProperty("udf1")
    public String udf1;
    @Qualifier("udf2")
    @JsonProperty("udf2")
    public String udf2;
    @Qualifier("udf3")
    @JsonProperty("udf3")
    public String udf3;
    @Qualifier("udf4")
    @JsonProperty("udf4")
    public String udf4;
    @Qualifier("udf5")
    @JsonProperty("udf5")
    public String udf5;
    @Qualifier("udf6")
    @JsonProperty("udf6")
    public String udf6;
    @Qualifier("udf7")
    @JsonProperty("udf7")
    public String udf7;
    @Qualifier("udf8")
    @JsonProperty("udf8")
    public String udf8;
    @Qualifier("udf9")
    @JsonProperty("udf9")
    public String udf9;
    @Qualifier("udf10")
    @JsonProperty("udf10")
    public String udf10;
    @Qualifier("hash")
    @JsonProperty("hash")
    public String hash;
    @Qualifier("field1")
    @JsonProperty("field1")
    public String field1;
    @Qualifier("field2")
    @JsonProperty("field2")
    public String field2;
    @Qualifier("field3")
    @JsonProperty("field3")
    public String field3;
    @Qualifier("field4")
    @JsonProperty("field4")
    public String field4;
    @Qualifier("field5")
    @JsonProperty("field5")
    public String field5;
    @Qualifier("field6")
    @JsonProperty("field6")
    public String field6;
    @Qualifier("field7")
    @JsonProperty("field7")
    public String field7;
    @Qualifier("field8")
    @JsonProperty("field8")
    public String field8;
    @Qualifier("field9")
    @JsonProperty("field9")
    public String field9;
    @Qualifier("bank_ref_num")
    @JsonProperty("bank_ref_num")
    public String bankRefNum;
    @Qualifier("bankcode")
    @JsonProperty("bankcode")
    public String bankcode;
    @Qualifier("error")
    @JsonProperty("error")
    public String error;
    @Qualifier("error_Message")
    @JsonProperty("error_Message")
    public String errorMessage;
    @Qualifier("cardToken")
    @JsonProperty("cardToken")
    public String cardToken;
    @Qualifier("offer_key")
    @JsonProperty("offerKey")
    public String offerKey;
    @Qualifier("offer_type")
    @JsonProperty("offer_type")
    public String offerType;
    @Qualifier("offer_availed")
    @JsonProperty("offer_availed")
    public String offerAvailed;
    @Qualifier("pg_ref_no")
    @JsonProperty("pg_ref_no")
    public String pgRefNo;
    @Qualifier("offer_failure_reason")
    @JsonProperty("offer_failure_reason")
    public String offerFailureReason;
    @Qualifier("name_on_card")
    @JsonProperty("name_on_card")
    public String nameOnCard;
    @Qualifier("cardnum")
    @JsonProperty("cardnum")
    public String cardnum;
    @Qualifier("cardhash")
    @JsonProperty("cardhash")
    public String cardhash;
    @Qualifier("card_type")
    @JsonProperty("card_type")
    public String cardType;
    @Qualifier("card_merchant_param")
    @JsonProperty("card_merchant_param")
    public Object cardMerchantParam;
    @Qualifier("version")
    @JsonProperty("version")
    public String version;
    @Qualifier("postUrl")
    @JsonProperty("postUrl")
    public String postUrl;
    @Qualifier("calledStatus")
    @JsonProperty("calledStatus")
    public Boolean calledStatus;
    @Qualifier("additional_param")
    @JsonProperty("additional_param")
    public String additionalParam;
    @Qualifier("amount_split")
    @JsonProperty("amount_split")
    public String amountSplit;
    @Qualifier("discount")
    @JsonProperty("discount")
    public String discount;
    @Qualifier("net_amount_debit")
    @JsonProperty("net_amount_debit")
    public String netAmountDebit;
    @Qualifier("fetchAPI")
    @JsonProperty("fetchAPI")
    public Object fetchAPI;
    @Qualifier("paisa_mecode")
    @JsonProperty("paisa_mecode")
    public String paisaMecode;
    @Qualifier("meCode")
    @JsonProperty("meCode")
    public String meCode;
    @Qualifier("payuMoneyId")
    @JsonProperty("payuMoneyId")
    public String payuMoneyId;
    @Qualifier("encryptedPaymentId")
    @JsonProperty("encryptedPaymentId")
    public Object encryptedPaymentId;
    @Qualifier("id")
    @JsonProperty("id")
    public Object id;
    @Qualifier("surl")
    @JsonProperty("surl")
    public Object surl;
    @Qualifier("furl")
    @JsonProperty("furl")
    public Object furl;
    @Qualifier("baseUrl")
    @JsonProperty("baseUrl")
    public Object baseUrl;
    @Qualifier("retryCount")
    @JsonProperty("retryCount")
    public Integer retryCount;
    @Qualifier("merchantid")
    @JsonProperty("merchantid")
    public Object merchantid;
    @Qualifier("payment_source")
    @JsonProperty("payment_source")
    public Object paymentSource;
    @Qualifier("pg_TYPE")
    @JsonProperty("pg_TYPE")
    public String pgTYPE;
}

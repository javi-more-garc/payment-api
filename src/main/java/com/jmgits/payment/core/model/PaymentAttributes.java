package com.jmgits.payment.core.model;

import lombok.Getter;
import lombok.Setter;
import org.mongodb.morphia.annotations.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by javi.more.garc on 12-11-17.
 */
@Entity
@Getter
@Setter
public class PaymentAttributes {

    private BigDecimal amount;

    private Party party;

    private ChargesInformation chargesInformation;

    private String currency;

    private Party debtorParty;

    private String endToEndReference;

    private Fx fx;

    private String numericReference;
    private String paymentId;
    private String paymentPurpose;
    private String paymentScheme;
    private String paymentType;

    private LocalDate processingDate;

    private String reference;
    private String schemePaymentSubType;
    private String schemePaymentType;

    private Party sponsorParty;

    @Getter
    @Setter
    @Entity
    public static class Party {

        private String accountName;
        private String accountNumber;
        private String accountNumberCode;

        private Integer accountType;

        private String address;
        private String bankId;
        private String bankIdCode;
        private String name;
    }

    @Getter
    @Setter
    public static class ChargesInformation {
        private String bearerCode;

        private List<SenderCharge> senderCharges;

        private BigDecimal receiverChargesAmount;
        private String receiverChargesCurrency;

        @Getter
        @Setter
        public static class SenderCharge{
            private BigDecimal amount;
            private String currency;
        }
    }

    @Getter
    @Setter
    public static class Fx {

        private String contractReference;
        private BigDecimal exchangeRate;
        private BigDecimal originalAmount;
        private String originalCurrency;
    }
}

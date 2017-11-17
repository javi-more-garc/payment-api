package com.jmgits.payment.core.view;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

/**
 * Created by javi.more.garc on 12-11-17.
 */
@Getter
@Setter
public class PaymentSearch {

    private String organisationId;

    private String currency;

    @DateTimeFormat(iso = DATE)
    private LocalDate processingDate;

    private String reference;

    private String accountNumber;
}

package com.jmgits.payment.core.model;

import lombok.Getter;
import lombok.Setter;
import org.mongodb.morphia.annotations.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by javi.more.garc on 12-11-17.
 */
@Entity
@Document
@Getter
@Setter
public class Payment extends AbstractDocument {

    private String organisationId;
    private String type;

    private PaymentAttributes attributes;
}

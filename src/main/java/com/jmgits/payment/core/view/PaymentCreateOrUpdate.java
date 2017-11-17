package com.jmgits.payment.core.view;

import com.jmgits.payment.core.model.PaymentAttributes;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by javi.more.garc on 12-11-17.
 */
@Getter
@Setter
public class PaymentCreateOrUpdate {

    @NotBlank
    private String organisationId;

    @NotBlank
    private String type;

    private PaymentAttributes attributes;
}

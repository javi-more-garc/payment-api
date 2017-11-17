package com.jmgits.payment.web.view;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.jmgits.payment.core.model.Payment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by javi.more.garc on 12-11-17.
 */
@Getter
@RequiredArgsConstructor
public class PaymentResource extends ResourceSupport {

    @JsonUnwrapped
    private final Payment payment;
}

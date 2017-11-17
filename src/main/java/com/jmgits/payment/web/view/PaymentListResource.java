package com.jmgits.payment.web.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

/**
 * Created by javi.more.garc on 12-11-17.
 */
@Getter
@RequiredArgsConstructor
public class PaymentListResource extends ResourceSupport {

    private final List<PaymentResource> data;
}

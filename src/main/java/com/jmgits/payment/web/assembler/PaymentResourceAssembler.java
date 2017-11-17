package com.jmgits.payment.web.assembler;

import com.jmgits.payment.core.model.Payment;
import com.jmgits.payment.web.rest.PaymentRestController;
import com.jmgits.payment.web.view.PaymentListResource;
import com.jmgits.payment.web.view.PaymentPageResource;
import com.jmgits.payment.web.view.PaymentResource;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedResources.PageMetadata;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by javi.more.garc on 12-11-17.
 */
@Component
public class PaymentResourceAssembler {

    public PaymentResource toResource(Payment payment) {

        PaymentResource resource = new PaymentResource(payment);

        resource.add(linkTo(methodOn(PaymentRestController.class).getById(payment.getId())).withSelfRel());

        return resource;
    }

    public PaymentListResource toResource(List<Payment> payments) {

        PaymentListResource resource = new PaymentListResource(
                payments.stream().map(this::toResource).collect(Collectors.toList())
        );

        resource.add(linkTo(methodOn(PaymentRestController.class).getLatest()).withSelfRel());

        return resource;
    }

    public PaymentPageResource toResource(Page<Payment> payments) {

        PaymentPageResource resource = new PaymentPageResource(
                payments.getContent().stream().map(this::toResource).collect(Collectors.toList()),
                new PageMetadata(payments.getSize(), payments.getNumber(), payments.getTotalElements(), payments.getTotalPages())
        );

        ControllerLinkBuilder controllerLinkBuilder = linkTo(methodOn(PaymentRestController.class).search(null, null));

        resource.add(controllerLinkBuilder.withSelfRel());

        return resource;
    }
}

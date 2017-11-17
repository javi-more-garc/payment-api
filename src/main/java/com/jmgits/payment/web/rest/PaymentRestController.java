package com.jmgits.payment.web.rest;

import com.jmgits.payment.core.model.Payment;
import com.jmgits.payment.core.service.PaymentService;
import com.jmgits.payment.core.view.PaymentCreateOrUpdate;
import com.jmgits.payment.core.view.PaymentSearch;
import com.jmgits.payment.web.assembler.PaymentResourceAssembler;
import com.jmgits.payment.web.view.PaymentListResource;
import com.jmgits.payment.web.view.PaymentPageResource;
import com.jmgits.payment.web.view.PaymentResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * Created by javi.more.garc on 12-11-17.
 */
@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
@Api(tags = "Payment", description = "Payment endpoints")
public class PaymentRestController {

    private final PaymentService paymentService;

    private final PaymentResourceAssembler paymentResourceAssembler;

    @GetMapping("/search")
    @ApiOperation(value = "Searches for payments")
    public PaymentPageResource search(@ModelAttribute PaymentSearch criteria, Pageable page) {

        Page<Payment> payments = paymentService.search(criteria, page);

        return paymentResourceAssembler.toResource(payments);
    }

    @GetMapping
    @ApiOperation(value = "Gets latest payments")
    public PaymentListResource getLatest() {

        List<Payment> payments = paymentService.getLatest();

        return paymentResourceAssembler.toResource(payments);
    }

    @GetMapping(("/{id}"))
    @ApiOperation(value = "Gets a payment identified by its id")
    public PaymentResource getById(@PathVariable String id) {

        Payment payment = paymentService.getById(id);

        return paymentResourceAssembler.toResource(payment);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation(value = "Creates a payment")
    public PaymentResource create(@Validated @RequestBody PaymentCreateOrUpdate criteria) {

        Payment payment = paymentService.create(criteria);

        return paymentResourceAssembler.toResource(payment);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Updates a payment identified by its id")
    public PaymentResource update(@PathVariable String id, @Validated @RequestBody PaymentCreateOrUpdate criteria) {

        Payment payment = paymentService.update(id, criteria);

        return paymentResourceAssembler.toResource(payment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation(value = "Deletes a payment identified by its id")
    public void delete(@PathVariable String id) {
        paymentService.delete(id);
    }

}

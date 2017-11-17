package com.jmgits.payment.core.service;

import com.jmgits.payment.core.model.Payment;
import com.jmgits.payment.core.view.PaymentCreateOrUpdate;
import com.jmgits.payment.core.view.PaymentSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by javi.more.garc on 12-11-17.
 */
public interface PaymentService {

    Page<Payment> search(PaymentSearch criteria, Pageable page);

    List<Payment> getLatest();

    Payment getById(String id);

    Payment create(PaymentCreateOrUpdate criteria);

    Payment update(String id, PaymentCreateOrUpdate criteria);

    void delete(String id);
}

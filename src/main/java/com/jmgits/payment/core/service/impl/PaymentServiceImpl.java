package com.jmgits.payment.core.service.impl;

import com.jmgits.payment.core.model.Payment;
import com.jmgits.payment.core.repository.PaymentRepository;
import com.jmgits.payment.core.service.PaymentService;
import com.jmgits.payment.core.view.PaymentCreateOrUpdate;
import com.jmgits.payment.core.view.PaymentSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Created by javi.more.garc on 12-11-17.
 */
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final PageRequest LATEST = new PageRequest(0, 10, DESC, "id");

    private final PaymentRepository paymentRepository;

    @Override
    public Page<Payment> search(PaymentSearch criteria, Pageable page) {

        return paymentRepository.search(criteria, page);
    }

    @Override
    public List<Payment> getLatest() {
        return paymentRepository.findAll(LATEST).getContent();
    }

    @Override
    public Payment getById(String id) {

        return paymentRepository.getById(id);
    }

    @Override
    public Payment create(PaymentCreateOrUpdate criteria) {

        Payment document = transform(new Payment(), criteria);

        return paymentRepository.save(document);
    }

    @Override
    public Payment update(String id, PaymentCreateOrUpdate criteria) {

        Payment document = transform(getById(id), criteria);

        return paymentRepository.save(document);
    }

    @Override
    public void delete(String id) {

        Payment document = getById(id);

        paymentRepository.delete(document);
    }

    //
    // private methods

    private Payment transform(Payment document, PaymentCreateOrUpdate criteria) {

        document.setOrganisationId(criteria.getOrganisationId());
        document.setType(criteria.getType());
        document.setAttributes(criteria.getAttributes());

        return document;
    }
}

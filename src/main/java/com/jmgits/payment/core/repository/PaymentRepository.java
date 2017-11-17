package com.jmgits.payment.core.repository;

import com.jmgits.payment.base.exception.NotFoundException;
import com.jmgits.payment.core.model.Payment;
import com.jmgits.payment.core.model.QPayment;
import com.jmgits.payment.core.view.PaymentSearch;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Created by javi.more.garc on 12-11-17.
 */
public interface PaymentRepository extends MongoRepository<Payment, String>, QueryDslPredicateExecutor<Payment> {

    Optional<Payment> findById(String id);

    //
    //

    default Payment getById(String id){

        return findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Payment with id '%s' not found", id))
        );
    }

    default Page<Payment> search(PaymentSearch criteria, Pageable page){

        BooleanBuilder query = new BooleanBuilder();

        QPayment qPayment = QPayment.payment;

        ofNullable(criteria.getOrganisationId()).ifPresent(organisationId ->
                query.and(qPayment.organisationId.containsIgnoreCase(organisationId))
        );

        ofNullable(criteria.getCurrency()).ifPresent(currency ->
                query.and(qPayment.attributes.currency.containsIgnoreCase(currency))
        );

        ofNullable(criteria.getProcessingDate()).ifPresent(processingDate ->
                query.and(qPayment.attributes.processingDate.eq(processingDate))
        );

        ofNullable(criteria.getReference()).ifPresent(reference ->
                query.and(qPayment.attributes.reference.containsIgnoreCase(reference))
        );

        ofNullable(criteria.getAccountNumber()).ifPresent(accountNumber -> {

            BooleanBuilder subQuery = new BooleanBuilder();

            subQuery.or(qPayment.attributes.party.accountNumber.containsIgnoreCase(accountNumber));
            subQuery.or(qPayment.attributes.sponsorParty.accountNumber.containsIgnoreCase(accountNumber));

            query.and(subQuery);
        });

        return findAll(query, page);
    }
}

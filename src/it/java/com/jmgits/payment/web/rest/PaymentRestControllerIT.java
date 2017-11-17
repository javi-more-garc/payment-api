package com.jmgits.payment.web.rest;

import com.jmgits.payment.TestApplication;
import com.jmgits.payment.core.model.Payment;
import com.jmgits.payment.core.model.PaymentAttributes;
import com.jmgits.payment.core.repository.PaymentRepository;
import com.jmgits.payment.core.view.PaymentCreateOrUpdate;
import com.jmgits.payment.core.view.PaymentSearch;
import com.jmgits.payment.web.rest.step.PaymentSteps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import java.util.List;

import static java.math.BigDecimal.TEN;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Created by javi.more.garc on 12-11-17.
 * <p>
 * Integration testing for {@link PaymentRestController}
 */
@RunWith(SerenityRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = TestApplication.class)
@ActiveProfiles("it")
public class PaymentRestControllerIT {

    @ClassRule
    public static final SpringClassRule SCR = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Value("${local.server.port}")
    protected int port;

    @Autowired
    private PaymentRepository paymentRepository;

    @Steps
    private PaymentSteps steps;

    @Before
    public void before() {
        steps.setServerUrl("http://localhost:" + port);

        paymentRepository.deleteAll();
    }

    @Test
    public void testSearch() throws Exception {

        //
        // create 1

        PaymentCreateOrUpdate criteria1 = new PaymentCreateOrUpdate();
        criteria1.setType("PAYMENT");
        criteria1.setOrganisationId("myOrganisation1");

        steps.create(criteria1);

        String id1 = steps.checkCreatedOk();

        //
        // get 1

        steps.getById(id1);

        Payment payment1 = new Payment();

        payment1.setId(id1);
        payment1.setType(criteria1.getType());
        payment1.setOrganisationId(criteria1.getOrganisationId());

        steps.checkGotByIdOk(payment1);

        //
        // create 2

        PaymentCreateOrUpdate criteria2 = new PaymentCreateOrUpdate();
        criteria2.setType("PAYMENT");
        criteria2.setOrganisationId("myOrganisation2");

        steps.create(criteria2);

        String id2 = steps.checkCreatedOk();

        //
        // get 2

        steps.getById(id2);

        Payment payment2 = new Payment();

        payment2.setId(id2);
        payment2.setType(criteria2.getType());
        payment2.setOrganisationId(criteria2.getOrganisationId());

        steps.checkGotByIdOk(payment2);

        //
        // search

        PaymentSearch criteriaSearch = new PaymentSearch();
        criteriaSearch.setOrganisationId("myOrganisation2");

        SearchResult expected = new SearchResult(singletonList(payment2));

        steps.search(criteriaSearch);
        steps.checkSearchedOk(expected);
    }

    @Test
    public void testGetLatest() throws Exception {

        //
        // create 1

        PaymentCreateOrUpdate criteria1 = new PaymentCreateOrUpdate();
        criteria1.setType("PAYMENT");
        criteria1.setOrganisationId("myOrganisation1");

        steps.create(criteria1);

        String id1 = steps.checkCreatedOk();

        //
        // get 1

        steps.getById(id1);

        Payment payment1 = new Payment();

        payment1.setId(id1);
        payment1.setType(criteria1.getType());
        payment1.setOrganisationId(criteria1.getOrganisationId());

        steps.checkGotByIdOk(payment1);

        //
        // create 2

        PaymentCreateOrUpdate criteria2 = new PaymentCreateOrUpdate();
        criteria2.setType("PAYMENT");
        criteria2.setOrganisationId("myOrganisation2");

        steps.create(criteria2);

        String id2 = steps.checkCreatedOk();

        //
        // get 2

        steps.getById(id2);

        Payment payment2 = new Payment();

        payment2.setId(id2);
        payment2.setType(criteria2.getType());
        payment2.setOrganisationId(criteria2.getOrganisationId());

        steps.checkGotByIdOk(payment2);

        //
        // retrieve latest

        SearchResult expected = new SearchResult(asList(payment2, payment1));

        steps.getLatest();
        steps.checkGotLatestOk(expected);
    }

    @Test
    public void testCreate() throws Exception {

        PaymentCreateOrUpdate criteria = new PaymentCreateOrUpdate();
        criteria.setType("PAYMENT");
        criteria.setOrganisationId("myOrganisation");

        PaymentAttributes paymentAttributes = new PaymentAttributes();
        paymentAttributes.setAmount(TEN);
        paymentAttributes.setCurrency("GBP");

        criteria.setAttributes(paymentAttributes);

        //
        // create

        steps.create(criteria);

        String id = steps.checkCreatedOk();

        //
        // get

        steps.getById(id);

        Payment payment = new Payment();

        payment.setId(id);
        payment.setType(criteria.getType());
        payment.setOrganisationId(criteria.getOrganisationId());
        payment.setVersion(0);
        payment.setAttributes(paymentAttributes);

        steps.checkGotByIdOk(payment);
    }

    @Test
    public void testUpdate() throws Exception {

        PaymentCreateOrUpdate criteria = new PaymentCreateOrUpdate();
        criteria.setType("PAYMENT");
        criteria.setOrganisationId("myOrganisation");

        //
        // create

        steps.create(criteria);

        String id = steps.checkCreatedOk();

        //
        // update

        criteria.setOrganisationId("myNewOrganisation");

        steps.update(id, criteria);
        steps.checkUpdatedOk(id);

        //
        // get

        steps.getById(id);

        Payment payment = new Payment();

        payment.setId(id);
        payment.setType(criteria.getType());
        payment.setOrganisationId("myNewOrganisation");
        payment.setVersion(1);

        steps.checkGotByIdOk(payment);
    }

    @Test
    public void testDelete() throws Exception {

        //
        // delete

        steps.delete("unknown");
        steps.checkNotFound();

        //
        // create

        PaymentCreateOrUpdate criteria = new PaymentCreateOrUpdate();
        criteria.setType("PAYMENT");
        criteria.setOrganisationId("myOrganisation");

        steps.create(criteria);

        String id = steps.checkCreatedOk();

        //
        // delete

        steps.delete(id);
        steps.checkDeletedOk();

        //
        // get

        steps.getById(id);
        steps.checkNotFound();
    }

    @RequiredArgsConstructor
    @Getter
    public static class SearchResult {

        private final List<Payment> data;
    }
}

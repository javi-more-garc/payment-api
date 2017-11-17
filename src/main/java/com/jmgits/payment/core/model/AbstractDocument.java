package com.jmgits.payment.core.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

/**
 * Created by javi.more.garc on 12-11-17.
 */
@Getter
@Setter
public class AbstractDocument {

    @Id
    protected String id;

    @Version
    protected Integer version;
}

package com.eventmanager.coreservicediploma.model.entity;

import java.util.Date;

public interface Evaluable {
    Double getPrice(Date date);
    Double getPrice(Date from, Date to);
}

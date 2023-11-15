package io.shodo.domain;

import java.util.Date;

public record ApplicationPeriod(Date startDate, Date deadline) {

    public boolean isOngoing(Date today) {
        return startDate.before(today) && deadline.after(today);
    }
}

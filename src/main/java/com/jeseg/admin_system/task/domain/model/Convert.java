package com.jeseg.admin_system.task.domain.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class Convert {
    public static LocalDateTime convertTime(String a) {
        if (a == null || a.length()< 3) return null;

        return LocalDateTime.parse(a);
    }

    public static List<Integer> convertTimeToList(LocalDateTime dateTime) {
        if (dateTime == null) return null;

        ZonedDateTime zdt = dateTime.atZone(ZoneId.of("America/Bogota"));
        LocalDateTime ldt = zdt.toLocalDateTime();

        return List.of(ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute());
    }
}

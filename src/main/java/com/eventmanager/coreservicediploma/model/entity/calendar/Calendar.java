package com.eventmanager.coreservicediploma.model.entity.calendar;

import com.eventmanager.coreservicediploma.model.entity.event.Event;
import com.eventmanager.coreservicediploma.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "calendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "eventid", referencedColumnName = "id")
    private Event event;
    @Column(name = "busydate")
    private Date date;
    @Column(name = "price")
    private Double price;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "calenderstatus")
    private CalendarStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Calendar calendar = (Calendar) o;
        return getId() != null && Objects.equals(getId(), calendar.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

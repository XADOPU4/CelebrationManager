package com.eventmanager.coreservicediploma.model.entity.userevent;

import com.eventmanager.coreservicediploma.model.entity.event.Event;
import com.eventmanager.coreservicediploma.model.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usertoevent")
public class UserEvent
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
    @ManyToOne
    @JoinColumn(name = "eventid")
    private Event event;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserEventStatus status;


    public UserEvent(User user, Event event, UserEventStatus status)
    {
        this.user = user;
        this.event = event;
        this.status = status;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEvent userEvent = (UserEvent) o;
        return getId() != null && Objects.equals(getId(), userEvent.getId());
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }
}

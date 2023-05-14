package com.eventmanager.coreservicediploma.model.entity.resource;

import com.eventmanager.coreservicediploma.model.entity.event.Event;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "stufffact")
public class ResourceFact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "eventid")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "usertostuffid")
    private UserResource userResource;

    @Column(name = "factprice")
    private Double factPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @CreationTimestamp
    @Column(name = "usagedate")
    private Date usageDate;

    @Setter(value = AccessLevel.NONE)
    private Double totalPrice;

    @Column(name = "filename")
    private String fileName;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ResourceFact that = (ResourceFact) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

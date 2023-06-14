package com.eventmanager.coreservicediploma.model.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "userinfo")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "userid")
    @ToString.Exclude
    private User user;
    @Column(name = "name")
    private String name;
    @Column(name = "contactphonenumber")
    private String contactPhoneNumber;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "status")
    private String status;
    @Column(name = "description")
    private String description;
    @Embedded
    private Location location;
    @Column(name = "dateofbirth")
    private Date birthDate;

    @ManyToMany
    @JoinTable(name = "userinfospecification",
            joinColumns = {
                    @JoinColumn(name = "userinfoid")
            }, inverseJoinColumns = {
            @JoinColumn(name = "specid")
    })
    @ToString.Exclude
    private List<Specification> specifications;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserInfo userInfo = (UserInfo) o;
        return getId() != null && Objects.equals(getId(), userInfo.getId());
    }

    @Override
    public int hashCode()
    {
        return getClass().hashCode();
    }
}


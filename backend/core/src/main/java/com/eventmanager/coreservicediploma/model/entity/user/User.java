package com.eventmanager.coreservicediploma.model.entity.user;

import com.eventmanager.coreservicediploma.model.entity.calendar.Calendar;
import com.eventmanager.coreservicediploma.model.entity.userevent.UserEvent;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "`user`")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NamedEntityGraphs(value = {
        @NamedEntityGraph(name = "auth",
                attributeNodes = {
                        @NamedAttributeNode(value = "login"),
                        @NamedAttributeNode(value = "password"),
                        @NamedAttributeNode(value = "role", subgraph = "role-subgraph"),
                        @NamedAttributeNode(value = "phoneNumber"),
                        @NamedAttributeNode(value = "email"),
                        @NamedAttributeNode(value = "isActive")
                },
                subgraphs = {
                        @NamedSubgraph(name = "role-subgraph", attributeNodes = {
                                @NamedAttributeNode(value = "name")
                        })
                }),
        @NamedEntityGraph(name = "business", attributeNodes = {
                @NamedAttributeNode(value = "login"),
                @NamedAttributeNode(value = "role"),
                @NamedAttributeNode(value = "userInfo"),
                @NamedAttributeNode(value = "events"),
                @NamedAttributeNode(value = "isActive")
        })
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code", unique = true)
    private String code;
    @Column(name = "login", unique = true)
    private String login;
    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private UserInfo userInfo;
    @Column(name = "password")
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleid")
    @ToString.Exclude
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<UserEvent> events;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Calendar> calendars;

    @Column(name = "phonenumber", unique = true)
    private String phoneNumber;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "isactive")
    private Boolean isActive;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

package com.eventmanager.coreservicediploma.model.entity.user;

import com.eventmanager.coreservicediploma.model.entity.userevent.UserEvent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

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
                @NamedAttributeNode(value = "phoneNumber"),
                @NamedAttributeNode(value = "email"),
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
    @JoinColumn(name = "id")
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

    @Column(name = "phonenumber", unique = true)
    @Pattern(regexp = "^[+]\\d{1,3}-\\d{3}-\\d{3}-\\d{4}$")
    private String phoneNumber;
    @Column(name = "email", unique = true)
    @Email
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

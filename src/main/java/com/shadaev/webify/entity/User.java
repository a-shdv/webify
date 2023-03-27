package com.shadaev.webify.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Builder
@Data
@EqualsAndHashCode(exclude = {"id", "userRoles", "cart"})
@ToString(exclude = {"id", "userRoles", "cart", "posts"})
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean active;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "user")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Post> posts = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUserRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}

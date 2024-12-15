package org.example.bank.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.Timestamp;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "users_id_seq", allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String name;
    private String password;
    @Column(columnDefinition = "TIMESTAMP(2)")
    @CreationTimestamp
    private Timestamp created_at;
    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

    public User(String name, String password, Collection<Role> roles) {
        this.name = name;
        this.password = password;
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.roles = roles;
    }
}

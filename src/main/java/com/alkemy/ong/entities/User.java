package com.alkemy.ong.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@SQLDelete(sql = "UPDATE users SET deleted_at = now() WHERE id=?")
@Where(clause = "deleted_at is null")
public class User implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NonNull
    @Column(nullable = false)
    private String firstName;
    
    @NonNull
    @Column(nullable = false)
    private String lastName;
    
    @NonNull
    @Column(nullable = false)
    @Email
    private String email;
    
    @NonNull
    @Column(nullable = false)
    private String password;
    
    private String photo;
    
    @ManyToOne
    @NonNull
    private Role role;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date deletedAt;


}

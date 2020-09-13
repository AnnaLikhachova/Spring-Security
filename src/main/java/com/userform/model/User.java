package com.userform.model;

import javax.persistence.*;
import java.util.Set;
import org.jboss.aerogear.security.otp.api.Base32;

/**
 * @author Anna Likhachova
 */

@Entity
@Table(name = "user")
public class User {
  private Long id;
  private String username;
  private String password;
  private String passwordConfirm;
  private Set<Role> roles;
  private String email;
  private VerificationToken token;
  private Boolean green;
  private String secret;

    public User() {
        super();
        this.green = false;
        this.secret = Base32.random();
    }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Transient
  public String getPasswordConfirm() {
    return passwordConfirm;
  }

  public void setPasswordConfirm(String passwordConfirm) {
    this.passwordConfirm = passwordConfirm;
  }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToOne(mappedBy = "user")
    public VerificationToken getToken() {
        return token;
    }

    public void setToken(VerificationToken token) {
        this.token = token;
    }

    public Boolean isGreen() {
        return green;
    }

    public void setGreen(Boolean green) {
        this.green = green;
    }

    @Transient
    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = Base32.random();;
    }
}

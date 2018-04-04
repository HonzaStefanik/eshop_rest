package org.hsnr.rest.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

  @Id
  @GenericGenerator(name = "id", strategy = "increment")
  @GeneratedValue(generator = "id")
  private int id;

  @Column(nullable = false)
  @NotEmpty
  @Email
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private boolean passwordChangeNeeded = false;

  @Column(nullable = false)
  private Date lastPasswordChange = new Date();

  @Column(nullable = false)
  @ElementCollection(targetClass=Role.class, fetch = FetchType.EAGER)
  private Set<Role> roles = new HashSet<>();

  public User() {
    roles.add(Role.USER);
  }

  public User(String email, String password) {
    this.email = email;
    this.password = password;
    roles.add(Role.USER);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isPasswordChangeNeeded() {
    return passwordChangeNeeded;
  }

  public void setPasswordChangeNeeded(boolean passwordChangeNeeded) {
    this.passwordChangeNeeded = passwordChangeNeeded;
  }

  public Date getLastPasswordChange() {
    return lastPasswordChange;
  }

  public void setLastPasswordChange(Date lastPasswordChange) {
    this.lastPasswordChange = lastPasswordChange;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}

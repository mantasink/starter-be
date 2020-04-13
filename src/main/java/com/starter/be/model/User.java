package com.starter.be.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user_account")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends TimestampEntity {

  @Size(min = 3, max = 30)
  @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Letters and numbers only.")
  private String username;

  private String email;

  @Size(min = 6, max = 255)
  private String password;

  private Boolean enabled = true;

  @ManyToMany(cascade = {CascadeType.MERGE})
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public boolean isEnabled() {
    return this.enabled;
  }

}

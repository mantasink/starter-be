package com.starter.be.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role extends IdEntity {
  @Column
  private String role;

  @JsonIgnore
  @ManyToMany(mappedBy = "roles")
  private Set<User> users = new HashSet<>();

  @Override
  public String toString() {
    return "Role{" + "role='" + role + '\'' + ", id=" + id + '}';
  }
}

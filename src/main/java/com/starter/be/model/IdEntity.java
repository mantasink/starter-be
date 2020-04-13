package com.starter.be.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class IdEntity {
  @Id @GeneratedValue protected Long id;
}

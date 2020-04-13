package com.starter.be.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class TimestampEntity extends IdEntity {

  @Column(name = "created", nullable = false)
  protected Date created;

  @Column(name = "updated", nullable = false)
  protected Date updated;

  @PrePersist
  protected void onCreate() {
    created = updated = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    updated = new Date();
  }
}

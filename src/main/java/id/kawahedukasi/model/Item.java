package id.kawahedukasi.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Table(name = "items")
public class Item extends PanacheEntityBase {

  @Id
  @SequenceGenerator(name = "itemSequence", sequenceName = "item_sequence", allocationSize = 1, initialValue = 1)
  @GeneratedValue(generator = "itemSequence", strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  public Long id;

  @Column(name = "name")
  public String name;

  @Column(name = "count")
  public Integer count;

  @Column(name = "price")
  public Double price;

  @Column(name = "type")
  public String type;

  @Column(name = "description", columnDefinition = "TEXT")
  public String description;

  @Column(name = "created_at")
  public LocalDateTime createdAt;

  @Column(name = "updated_at")
  public LocalDateTime updatedAt;

  @PrePersist
  public void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
}

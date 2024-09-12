package dev.limhm.domain;

import dev.limhm.study.StudyStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;


@Entity
public class Study {

  @Id
  @GeneratedValue
  private Long id;
  private StudyStatus status = StudyStatus.DRAFT;
  private int limitCount;
  private String name;
  private LocalDateTime openedDateTime;
  @ManyToOne
  private Member owner;

  public Study(int limitCount, String name) {
    this.limitCount = limitCount;
    this.name = name;
  }

  public Study() {
  }

  public Study(int limitCount) {
    if (limitCount < 0) {
      throw new IllegalArgumentException("limit은 0보다 커야 한다.");
    }
    this.limitCount = limitCount;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public StudyStatus getStatus() {
    return status;
  }

  public void setStatus(StudyStatus status) {
    this.status = status;
  }

  public int getLimitCount() {
    return limitCount;
  }

  public void setLimitCount(int limitCount) {
    this.limitCount = limitCount;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getOpenedDateTime() {
    return openedDateTime;
  }

  public void setOpenedDateTime(LocalDateTime openedDateTime) {
    this.openedDateTime = openedDateTime;
  }

  public Member getOwner() {
    return owner;
  }

  public void setOwner(Member owner) {
    this.owner = owner;
  }

  public void publish() {
    this.openedDateTime = LocalDateTime.now();
    this.status = StudyStatus.OPENED;
  }

  public void open() {
    this.openedDateTime = LocalDateTime.now();
    this.status = StudyStatus.OPENED;
  }
}

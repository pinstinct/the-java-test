package dev.limhm.start;

import dev.limhm.domain.StudyStatus;

public class Study {

  private StudyStatus status = StudyStatus.DRAFT;

  private int limit;

  private String name;

  public Study(int limit, String name) {
    this.limit = limit;
    this.name = name;
  }

  @Override
  public String toString() {
    return "Study{" + "status=" + status + ", limit=" + limit + ", name='" + name + '\'' + '}';
  }

  public String getName() {
    return name;
  }

  public Study(int limit) {
    if (limit < 0) {
      throw new IllegalArgumentException("limit은 0보다 커야 한다.");
    }
    this.limit = limit;
  }

  public int getLimit() {
    return limit;
  }

  public StudyStatus getStatus() {
    return this.status;
  }
}

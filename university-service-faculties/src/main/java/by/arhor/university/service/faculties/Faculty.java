package by.arhor.university.service.faculties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "faculties")
@NoArgsConstructor
public class Faculty {

  @Id private Long id;

  @Column(name = "default_title", nullable = false, unique = true, length = 64)
  private String defaultTitle;

  @Column(name = "seats_paid", nullable = false)
  private Short seatsPaid;

  @Column(name = "seats_budget", nullable = false)
  private Short seatsBudget;
}

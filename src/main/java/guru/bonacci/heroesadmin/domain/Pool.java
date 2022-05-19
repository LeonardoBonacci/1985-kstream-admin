package guru.bonacci.heroesadmin.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@EqualsAndHashCode
@ToString(exclude = "accounts")
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pool {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Builder.Default
  @OneToMany(mappedBy = "pool")
  private List<AccountDetails> accounts = new ArrayList<>(); // can grow very large

  @Column(nullable = false)
  private String poolType; // enum

  @ManyToOne
  @JoinColumn(name = "admin_id", nullable = false)
  private AdminUser admin;
}
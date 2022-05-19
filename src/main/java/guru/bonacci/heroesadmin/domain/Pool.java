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

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * TODO debezium
 */
@Data
@ToString(exclude = "accounts")
@Table
@Entity
@Where(clause = "active = true")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pool {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false, updatable = false, unique = true)
  private String name;
  
  @Builder.Default
  @OneToMany(mappedBy = "pool")
  private List<AccountDetails> accounts = new ArrayList<>(); // can grow very large

//  @Enumerated(EnumType.STRING)
  @Column(nullable = false, updatable = false)
  private String poolType; //TODO enum
  
  @ManyToOne
  @JoinColumn(name = "admin_id")
  private AdminUser admin;
  
  @Column 
  @Builder.Default
  private boolean active = true;
}
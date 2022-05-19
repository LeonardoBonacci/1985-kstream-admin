package guru.bonacci.heroesadmin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO debezium
 */
@Data
@Entity
@Where(clause = "active = true")
@Table(uniqueConstraints={
  @UniqueConstraint(columnNames = {"user_id", "pool_id"}),
  @UniqueConstraint(columnNames = {"name", "pool_id"})
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, updatable = false)
  private String name; //TODO validate regex

  @Column
  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false) 
  private UserInfo user;

  @ManyToOne
  @JoinColumn(name = "pool_id", nullable = false, updatable = false)
  private Pool pool;
  
  @Column 
  @Builder.Default
  private boolean active = true;
  
  @Override
  public String toString() {
    return "AccountDetails(id="+id
            +", name="+name
            +", description="+description
            +", user.name="+user.getName()
            +", pool.name="+pool.getType()+")";
  }
}
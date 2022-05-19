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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
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

  @Column(nullable = false)
  private String name;

  @Column
  private String description;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private UserInfo user;

  @ManyToOne
  @JoinColumn(name = "pool_id", nullable = false)
  private Pool pool;
  
  @Override
  public String toString() {
    return "AccountDetails(id="+id
            +", name="+name
            +", description="+description
            +", user.name="+user.getName()
            +", pool.name="+pool.getPoolType()+")";
  }
}
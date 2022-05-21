package guru.bonacci.heroesadmin.domain;

import java.math.BigDecimal;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AccountDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, updatable = false)
  private String name;

  @Column
  private String description;

  @Column(name = "start_amount")
  @Builder.Default
  private BigDecimal startAmount = BigDecimal.ZERO;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false) 
  private UserInfo user;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "pool_id", nullable = false, updatable = false)
  private Pool pool;

  @JsonIgnore
  @Column 
  @Builder.Default
  private boolean active = true;

  // to facilitate cdc
  @Column(name = "pool_name", nullable = false, updatable = false)
  private String poolName;

  // to facilitate cdc
  @Column(name = "pool_account_id", nullable = false, updatable = false)
  private String poolAccountId;

  @Override
  public String toString() {
    return "AccountDetails(id="+id
            +", name="+name
            +", description="+description
            +", user.name="+user.getName()
            +", pool.name="+pool.getType()+")";
  }
}
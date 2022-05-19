package guru.bonacci.heroesadmin.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(optional = false)
  @PrimaryKeyJoinColumn
  private UserInfo user;
  
  @Builder.Default
  @OneToMany(mappedBy = "admin", fetch = FetchType.EAGER)
  private List<Pool> pools = new ArrayList<>();
  
  @Override
  public String toString() {
    return "AdminUser(id="+id
        +", user.name="+user.getName()
        +", pools.name="+pools.stream().map(Pool::getPoolType).collect(Collectors.toList()).toString();
  }
}
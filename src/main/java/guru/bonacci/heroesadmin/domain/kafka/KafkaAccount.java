package guru.bonacci.heroesadmin.domain.kafka;

import java.math.BigDecimal;

import guru.bonacci.heroesadmin.domain.AccountDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaAccount {

  private String accountId; 
  private String poolId;
  private BigDecimal startAmount; 

  public String identifier() {
    return this.poolId + "." + this.accountId;
  }
  
  
  public static KafkaAccount from(AccountDetails account) {
    return new KafkaAccount(account.getName(), account.getPoolName(), account.getStartAmount());
  }
}
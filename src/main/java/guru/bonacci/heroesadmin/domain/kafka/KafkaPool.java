package guru.bonacci.heroesadmin.domain.kafka;

import guru.bonacci.heroesadmin.PoolType;
import guru.bonacci.heroesadmin.domain.Pool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaPool {

  private String poolId;
  private PoolType type;
  
  
  public static KafkaPool from(Pool pool) {
    return new KafkaPool(pool.getName(), pool.getType());
  }
}
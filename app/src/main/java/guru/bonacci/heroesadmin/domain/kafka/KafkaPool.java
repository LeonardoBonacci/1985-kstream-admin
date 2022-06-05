package guru.bonacci.heroesadmin.domain.kafka;

import guru.bonacci.heroesadmin.PoolType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaPool {

  private String name;
  private PoolType type;
}
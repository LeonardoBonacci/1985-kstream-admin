package guru.bonacci.heroesadmin.jpa;


import java.util.concurrent.ExecutionException;

import javax.persistence.PostPersist;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import guru.bonacci.heroesadmin.domain.Pool;
import guru.bonacci.heroesadmin.domain.kafka.KafkaPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class PoolListener {

  private final KafkaTemplate<String, KafkaPool> template;
  

  @PostPersist
  public void persist(Pool pool) {
    try {
      var kafkaPool = KafkaPool.builder().name(pool.getName()).type(pool.getType()).build();
      log.info("sending pool to broker '{}'", kafkaPool);
      this.template.send("pool", kafkaPool.getName(), kafkaPool).get();
    } catch (InterruptedException | ExecutionException e) {
      log.error(e.getMessage());
      throw new RuntimeException("pool creation failed");
    }
  }
  
//  @PostRemove 
//  public void remove(KafkaPool stuff) {
//    try {
//      log.info("sending tombstone to broker for key '{}'", stuff.getFoo());
//      this.template.send(KafkaTopicConfig.TOPIC, stuff.getFoo(), null).get();
//    } catch (InterruptedException | ExecutionException e) {
//      e.printStackTrace();
//    }
//  }

}

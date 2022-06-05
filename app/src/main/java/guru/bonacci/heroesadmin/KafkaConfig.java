package guru.bonacci.heroesadmin;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import guru.bonacci.heroesadmin.domain.kafka.KafkaPool;

@Configuration
public class KafkaConfig {

  
  @Bean
  public ProducerFactory<String, KafkaPool> producerFactory() {
    DefaultKafkaProducerFactory<String, KafkaPool> f = new DefaultKafkaProducerFactory<>(senderProps());
    f.setTransactionIdPrefix("tx-");
    return f;
  }

  private Map<String, Object> senderProps() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "me-again");
    return props;
  }

  @Bean
  public KafkaTemplate<String, KafkaPool> kafkaTemplateServingDetailsListener() {
    return new KafkaTemplate<String, KafkaPool>(producerFactory());
  }

  @Bean("kafkaTxs")
  public KafkaTransactionManager<String, KafkaPool> kafkaTransactionManager() {
    KafkaTransactionManager<String, KafkaPool> ktm = new KafkaTransactionManager<>(producerFactory());
    ktm.setTransactionSynchronization(AbstractPlatformTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION);
    return ktm;
  }

  @Bean(name = "unusedTxs")
  public JpaTransactionManager transactionManager() {
    return new JpaTransactionManager();
  }

  // https://itecnote.com/tecnote/r-working-with-spring-data-jpa-hibernate-and-multiple-transaction-manager-no-bean-named-transactionmanager-is-defined/
  @Bean("transactionManager")
  public ChainedTransactionManager chainedTxM(JpaTransactionManager jpa, KafkaTransactionManager<?, ?> kafka) {
    return new ChainedTransactionManager(jpa, kafka);
  }
}
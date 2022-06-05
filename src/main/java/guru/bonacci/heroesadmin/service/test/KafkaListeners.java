package guru.bonacci.heroesadmin.service.test;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaListeners {

  @KafkaListener( id = "dummy", 
                  topics = {"pool", "account"},
                  properties = {"isolation.level=read_committed"})
  public void listen(@Payload(required = false) String message,
                    @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key) {
      log.info("INCOMING {}<>{}" , key, message);
  }
}

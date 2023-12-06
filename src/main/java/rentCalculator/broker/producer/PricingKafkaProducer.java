package rentCalculator.broker.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import rentCalculator.broker.model.PricingEvent;

@Service
public class PricingKafkaProducer {

    private final KafkaTemplate<String, PricingEvent> kafkaTemplate;

    public PricingKafkaProducer(final KafkaTemplate<String, PricingEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCompletePricing(final PricingEvent pricingEvent) {
        // Publish event to Kafka
        kafkaTemplate.send("pricing-events", pricingEvent);
    }

}

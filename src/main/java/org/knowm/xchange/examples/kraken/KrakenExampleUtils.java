package org.knowm.xchange.examples.kraken;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.kraken.KrakenExchange;

public class KrakenExampleUtils {

  private KrakenExampleUtils() {

  }

  public static Exchange createTestExchange() {

    Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());
    krakenExchange.getExchangeSpecification().setApiKey("0L0xzNc3/4vKSPJu19CLRsu6HHZ1e1ppFyXB0j+CSx+RkRSpAz07dmwy");
    krakenExchange.getExchangeSpecification().setSecretKey("8jSPJQGZN1yuj9RnePbJErRI94gtHGrHTs5rBVQtjSlIrYBL+rFzrlsBGxv3u630FDdZpWfVxyQr8MLU5KhHpg==");
    krakenExchange.getExchangeSpecification().setUserName("walllzzz");
    krakenExchange.applySpecification(krakenExchange.getExchangeSpecification());
    return krakenExchange;
  }
}

package org.knowm.xchange.examples.kraken;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.kraken.KrakenExchange;

public class KrakenExampleUtils {

  private KrakenExampleUtils() {

  }

  public static Exchange createTestExchange() {

    Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());
    krakenExchange.getExchangeSpecification().setApiKey("");
    krakenExchange.getExchangeSpecification().setSecretKey("");
    krakenExchange.getExchangeSpecification().setUserName("walllzzz");
    krakenExchange.applySpecification(krakenExchange.getExchangeSpecification());
    return krakenExchange;
  }
}

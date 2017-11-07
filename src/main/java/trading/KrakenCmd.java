package trading;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.kraken.KrakenExampleUtils;
import org.knowm.xchange.kraken.dto.marketdata.KrakenTicker;
import org.knowm.xchange.kraken.dto.trade.KrakenOrder;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderResponse;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;
import org.knowm.xchange.kraken.service.KrakenTradeServiceRaw;

public class KrakenCmd {
	
	private Exchange krakenExchange = KrakenExampleUtils.createTestExchange();
	private KrakenTradeServiceRaw tradeService = (KrakenTradeServiceRaw) krakenExchange.getTradeService();
	private KrakenMarketDataServiceRaw krakenMarketDataService = (KrakenMarketDataServiceRaw) krakenExchange
			.getMarketDataService();
	
	public KrakenTicker getTicker() throws IOException {
		// Get the latest ticker data showing BTC to EUR
		KrakenTicker ticker = krakenMarketDataService.getKrakenTicker(CurrencyPair.BTC_EUR);
		return ticker;
	}

	private void updateBalance() {

	}

	public void placeBuyOrder(BigDecimal tradeableAmount, BigDecimal price) throws IOException {
		OrderType orderType = (OrderType.BID);
		LimitOrder limitOrder = new LimitOrder(orderType, tradeableAmount, CurrencyPair.BTC_EUR, "", null, price);
		KrakenOrderResponse orderResponse = tradeService.placeKrakenLimitOrder(limitOrder);
		System.out.println("Limit Order response: " + orderResponse);
	}

	public void placeSellOrder(BigDecimal tradeableAmount, BigDecimal price) throws IOException {
		OrderType orderType = (OrderType.ASK);
		LimitOrder limitOrder = new LimitOrder(orderType, tradeableAmount, CurrencyPair.BTC_EUR, "", null, price);
		KrakenOrderResponse orderResponse = tradeService.placeKrakenLimitOrder(limitOrder);
		System.out.println("Limit Order response: " + orderResponse);
	}
	public boolean checkOpenOrder() throws IOException{
		 // Get the open orders
	    Map<String, KrakenOrder> openOrders = tradeService.getKrakenOpenOrders();
	    if(openOrders.size()>0){
	    	return true;
	    };
	   return false; 
	}
	
	public Map<String, KrakenOrder> getOpenOrders() throws IOException {
		    // Get the open orders
		    Map<String, KrakenOrder> openOrders = tradeService.getKrakenOpenOrders();
		    return openOrders;
	}
}

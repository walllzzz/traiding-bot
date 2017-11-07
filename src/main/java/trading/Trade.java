package trading;

import java.io.IOException;
import java.math.RoundingMode;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.examples.kraken.KrakenExampleUtils;
import org.knowm.xchange.kraken.dto.marketdata.KrakenTicker;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderResponse;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;
import org.knowm.xchange.kraken.service.KrakenTradeServiceRaw;

public class Trade {

	private final static Logger LOG = Logger.getLogger(Trade.class.getName());

	private static MyBigDecimal percent = new MyBigDecimal("100");// 5 sec
	private static MyBigDecimal period = new MyBigDecimal("5");// 5 sec
	private static MyBigDecimal duration;
	private static MyBigDecimal sell_threshold_max = new MyBigDecimal("5");// 5%
	private static MyBigDecimal sell_min = new MyBigDecimal("0.1");// 5%
	private static MyBigDecimal buy_threshold = new MyBigDecimal("0.1");// 5%
	private static MyBigDecimal greed = new MyBigDecimal("5");// 5%
	private static MyBigDecimal trust_distrust_highest;
	private static MyBigDecimal trust_distrust_lowest;
	private static MyBigDecimal startBalanceCurrency=new MyBigDecimal("200");
	private static MyBigDecimal startBalanceAsset=new MyBigDecimal("0");
	private static MyBigDecimal balanceAsset=new MyBigDecimal("0");
	private static MyBigDecimal balanceCurrency=new MyBigDecimal("200");
	private static MyBigDecimal lastOpPrice=new MyBigDecimal("0");
	private static MyBigDecimal gainTotalBuy=new MyBigDecimal("0");
	private static MyBigDecimal gainTotalSell=new MyBigDecimal("0");
	
	private static String lastAction = "sell";
	private OutputWriter writer;
	private KrakenCmd kraken = new KrakenCmd();

	public Trade(OutputWriter writer) throws IOException  {
		this.writer=writer;
		this.balanceCurrency=startBalanceCurrency;
		this.balanceAsset=startBalanceAsset;
		writer.writeHeader();
	}

	public void lunchTrade() throws Exception {
		TradeField info = new TradeField();
		KrakenTicker ticker = kraken.getTicker();
		MyBigDecimal currentPrice = new MyBigDecimal(ticker.getClose().getPrice());
		MyBigDecimal volume=new MyBigDecimal(ticker.getClose().getVolume());
		info.setTime(new Date());
		info.setLastAction(lastAction);
		info.setAssetPrice(currentPrice);
		info.setAssetBalance(balanceAsset);
		info.setCurrencyBalance(balanceCurrency);
		info.setVolume(volume);		
		info.setGainTotalBuy(gainTotalBuy);
		info.setGainTotalSell(gainTotalSell);
		calculer(currentPrice,info);
		trade(currentPrice,info);
		writer.writeWithCsvBeanWriter(info);
	}

	
	private void calculer(MyBigDecimal lastPrice, TradeField info ) {
		LOG.info("calculer");
		if(lastOpPrice == null || lastOpPrice.compareTo(new MyBigDecimal(0))==0){
			lastOpPrice=lastPrice;
		}
		MyBigDecimal diffPrice=lastPrice.subtract(lastOpPrice);
		MyBigDecimal diffPercent=percent.subtract(lastOpPrice.multiply(percent).divide(lastPrice));
		info.setPriceChange(diffPrice);
		info.setPriceChangePercent(diffPercent);
		if(lastAction=="buy"){
			MyBigDecimal gainOp=balanceAsset.multiply(lastPrice).subtract(balanceAsset.multiply(lastOpPrice));
			info.setGainOperationBuy(gainOp);
		}else if(lastAction=="sell"){
			MyBigDecimal gainOp=balanceCurrency.divide(lastPrice).subtract(balanceCurrency.divide(lastOpPrice));
			info.setGainOperationSell(gainOp);
		}
		if (trust_distrust_highest == null) {
			trust_distrust_highest = lastPrice;
		}
		if (trust_distrust_lowest == null) {
			trust_distrust_lowest = lastPrice;
		}
		if (lastPrice.longValue() > trust_distrust_highest.longValue()) {
			trust_distrust_highest = lastPrice;
		}
		if (trust_distrust_lowest.longValue() > lastPrice.longValue()) {
			trust_distrust_lowest = lastPrice;
		}

	}
	private MyBigDecimal diffPrice(MyBigDecimal price,MyBigDecimal diffPercentage){
		MyBigDecimal diff = price.multiply(diffPercentage).divide(percent);			
		return diff;
	}

	/**
	 * trade
	 * @param ticker
	 * @param info
	 */
	private void trade(MyBigDecimal currentPrice, TradeField info ) {
		LOG.info("trade");
		MyBigDecimal diffPrice=diffPrice(lastOpPrice,sell_min);
		MyBigDecimal sellThresholdTrade = lastOpPrice.add(diffPrice);
		diffPrice=diffPrice(lastOpPrice,buy_threshold);
		MyBigDecimal buyThresholdTrade= lastOpPrice.subtract(diffPrice);
		info.setSellThreshold(sellThresholdTrade);
		info.setBuyThreshold(buyThresholdTrade);
		LOG.info("sell compare value : " + sellThresholdTrade.toString());
		LOG.info("buy compare value : " + buyThresholdTrade);
		if (lastAction != "sell") {
			if (currentPrice.compareTo(sellThresholdTrade) == 1) {
				LOG.info("last price :"+currentPrice+" > sellThreshold: "+sellThresholdTrade);
				sellAction(currentPrice,info);
				return;
			}
			// panic sell
			//the price droped a below the sell_threshold_max
			if (sell_threshold_max != null && sell_threshold_max.longValue() > 0) {
				diffPrice=diffPrice(trust_distrust_highest,sell_threshold_max);
				MyBigDecimal tocompareValue = trust_distrust_highest.subtract(diffPrice);
				if (currentPrice.compareTo(tocompareValue) < 0) {
					LOG.info("panic sell");
					sellAction(currentPrice,info);
					return;
				}
			}
		} else if (lastAction != "buy") {
			
			if (currentPrice.compareTo(lastOpPrice) < 0 && currentPrice.compareTo(buyThresholdTrade) < 0) {
				LOG.info("last price :"+currentPrice+" < buyThreshold: "+buyThresholdTrade);
				buyAction(currentPrice,info);				
				return;
			}
		}
		info.setAction("watch");
		LOG.info("fin trade");
	}
	/**
	 * sell action
	 * @param lastPrice
	 * @param info
	 * @throws IOException 
	 */
	private void buyAction(MyBigDecimal lastPrice,TradeField info){
		
		LOG.info("buying");
		// TO-DO add sell threshold
		try {
			if(kraken.checkOpenOrder()){
				LOG.info("open order");
				return;
			}
			kraken.placeBuyOrder(balanceCurrency.divide(lastPrice),lastPrice);
			MyBigDecimal gainOp=balanceAsset.multiply(lastPrice).subtract(balanceAsset.multiply(lastOpPrice));
			LOG.info("gain operation buy: "+gainOp);
			gainTotalBuy=gainTotalBuy.add(gainOp);
			LOG.info("gain total operation buy: "+gainTotalBuy);
			info.setGainTotalBuy(gainTotalBuy);
			balanceAsset=balanceCurrency.divide(lastPrice);
			balanceCurrency=new MyBigDecimal(0);
			// sell action
			lastAction = "buy";
			trust_distrust_highest = lastPrice;
			trust_distrust_lowest = lastPrice;
			info.setAction("buy");
			lastOpPrice=lastPrice;
			
			return;
		} catch (Exception e) {
			LOG.log(Level.SEVERE,e.getMessage(),e);
		}
	}
	/**
	 * buy action
	 * @param lastPrice
	 * @param info
	 */
	private void sellAction(MyBigDecimal lastPrice,TradeField info){
		try {
			LOG.info("selling");
			if(kraken.checkOpenOrder()){
				LOG.info("open order");
				return;
			}
			kraken.placeSellOrder(balanceAsset,lastPrice);
			MyBigDecimal gainOp=balanceCurrency.divide(lastPrice).subtract(balanceCurrency.divide(lastOpPrice));
			LOG.info("gain operation sell: "+gainOp);
			gainTotalSell=gainTotalSell.add(gainOp);
			LOG.info("gain operation total: "+gainOp);
			info.setGainTotalSell(gainTotalSell);
			//check if order is fulfiled
			balanceCurrency=balanceAsset.multiply(lastPrice);
			balanceAsset=new MyBigDecimal(0);
			// TO-DO add sell threshold
			lastAction = "sell";
			trust_distrust_highest = lastPrice;
			trust_distrust_lowest = lastPrice;
			info.setAction("sell");
			lastOpPrice=lastPrice;
			return;
		} catch (Exception e) {
			LOG.log(Level.SEVERE,e.getMessage(),e);
		}
	}
	
}

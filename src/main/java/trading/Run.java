package trading;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.time.DateUtils;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.examples.kraken.KrakenExampleUtils;
import org.knowm.xchange.kraken.dto.marketdata.KrakenTicker;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;


public class Run {
	private final static Logger LOG = Logger.getLogger(Run.class.getName());
	
	public static void main(String[] args) throws IOException {
		
		// creating instance
		
		Trade trade = new Trade(new OutputWriter());
	
		Runnable task = () -> {
			
			LOG.log(Level.INFO, "trigger "+new Date());
			
			try {
				trade.lunchTrade();
			} catch (Exception e) {
				LOG.log(Level.SEVERE, e.getMessage(),e);
			}
			
		};
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		exec.scheduleAtFixedRate(task , 0, 60, TimeUnit.SECONDS);
	}



}

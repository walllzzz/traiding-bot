package trading;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;

public class TradeField {
	private Date time;
	private String lastAction;
	private BigDecimal assetPrice;
	private BigDecimal assetBalance;
	private BigDecimal currencyBalance;
	private BigDecimal priceChangePercent;
	private BigDecimal priceChange;
	private BigDecimal volume;
	private String action;
	private BigDecimal sellThreshold;
	private BigDecimal buyThreshold;
	private BigDecimal gainOperationBuy;
	private BigDecimal gainOperationSell;
	private BigDecimal gainTotalBuy;
	private BigDecimal gainTotalSell;
	private Date upSince;
	
	public TradeField(){
		
	}
	

	public String getTime() {
		if(time!=null)
			return time.toGMTString();
		return null;
	}
	public void setTime(Date time) {
		this.time = time;
	}


	public String getLastAction() {
		return lastAction;
	}


	public void setLastAction(String lastAction) {
		this.lastAction = lastAction;
	}


	public BigDecimal getAssetPrice() {
		return assetPrice;
	}


	public void setAssetPrice(BigDecimal assetPrice) {
		this.assetPrice = assetPrice;
	}


	public BigDecimal getAssetBalance() {
		return assetBalance;
	}


	public void setAssetBalance(BigDecimal assetBalance) {
		this.assetBalance = assetBalance;
	}


	public BigDecimal getCurrencyBalance() {
		return currencyBalance;
	}


	public void setCurrencyBalance(BigDecimal currencyBalance) {
		this.currencyBalance = currencyBalance;
	}


	public BigDecimal getPriceChangePercent() {
		return priceChangePercent;
	}


	public void setPriceChangePercent(BigDecimal priceChangePercent) {
		this.priceChangePercent = priceChangePercent;
	}


	public BigDecimal getPriceChange() {
		return priceChange;
	}


	public void setPriceChange(BigDecimal priceChange) {
		this.priceChange = priceChange;
	}


	public BigDecimal getVolume() {
		return volume;
	}


	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}


	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}


	public BigDecimal getSellThreshold() {
		return sellThreshold;
	}


	public void setSellThreshold(BigDecimal sellThreshold) {
		this.sellThreshold = sellThreshold;
	}


	public BigDecimal getBuyThreshold() {
		return buyThreshold;
	}


	public void setBuyThreshold(BigDecimal buyThreshold) {
		this.buyThreshold = buyThreshold;
	}


	public Date getUpSince() {
		return upSince;
	}


	public void setUpSince(Date upSince) {
		this.upSince = upSince;
	}


	public BigDecimal getGainOperationBuy() {
		return gainOperationBuy;
	}


	public void setGainOperationBuy(BigDecimal gainOperationBuy) {
		this.gainOperationBuy = gainOperationBuy;
	}


	public BigDecimal getGainOperationSell() {
		return gainOperationSell;
	}


	public void setGainOperationSell(BigDecimal gainOperationSell) {
		this.gainOperationSell = gainOperationSell;
	}


	public BigDecimal getGainTotalBuy() {
		return gainTotalBuy;
	}


	public void setGainTotalBuy(BigDecimal gainTotalBuy) {
		this.gainTotalBuy = gainTotalBuy;
	}


	public BigDecimal getGainTotalSell() {
		return gainTotalSell;
	}


	public void setGainTotalSell(BigDecimal gainTotalSell) {
		this.gainTotalSell = gainTotalSell;
	}
	
	}

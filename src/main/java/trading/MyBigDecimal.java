package trading;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MyBigDecimal extends BigDecimal {
	DecimalFormat currencyFormat = new DecimalFormat();
	DecimalFormat assetFormat = new DecimalFormat();

	public MyBigDecimal(BigDecimal val){
		super(val.toString());
		initFormat();
	}
	public MyBigDecimal(int val){
		super(val);
		initFormat();
	}
	private void initFormat(){
		currencyFormat.setMaximumFractionDigits(5);
		currencyFormat.setMinimumFractionDigits(5);
		currencyFormat.setGroupingUsed(false);
		assetFormat.setMaximumFractionDigits(5);
		assetFormat.setMinimumFractionDigits(5);
		assetFormat.setGroupingUsed(false);
		this.setScale(5, BigDecimal.ROUND_HALF_UP);
	}
	public MyBigDecimal(String str) {
		super(str);
		initFormat();
	}
	public MyBigDecimal add(MyBigDecimal num){
		return new MyBigDecimal(super.add(num.toBigDecimal()));
	}
	public MyBigDecimal divide(MyBigDecimal num){
		return new MyBigDecimal(super.divide(num.toBigDecimal(),5,BigDecimal.ROUND_HALF_UP));
	}
	public MyBigDecimal subtract(MyBigDecimal num){
		return new MyBigDecimal(super.subtract(num.toBigDecimal()));
	}
	public MyBigDecimal multiply(MyBigDecimal num){
		return new MyBigDecimal(super.multiply(num.toBigDecimal()));
	}
	public BigDecimal toBigDecimal(){
		return new BigDecimal(this.toPlainString());
	}
	public String toString() {
		return currencyFormat.format(super.doubleValue());
	}
	public String toStringAsset() {
		return assetFormat.format(super.doubleValue());
	}

}

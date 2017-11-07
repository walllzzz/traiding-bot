package trading;

import java.io.FileWriter;
import java.io.IOException;

import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class OutputWriter {

	private final static String FILENAME = "target/writeWithCsvBeanWriter.csv";
	private final static String[] HEADER = new String[] { "time", "action", "lastAction", "assetPrice", "volume", "assetBalance","currencyBalance",
			"priceChangePercent", "priceChange", "sellThreshold", "buyThreshold","gainOperationBuy","gainOperationSell", "gainTotalBuy","gainTotalSell"
			,"upSince" };
	private ICsvBeanWriter beanWriter = null;

	public OutputWriter() throws IOException {
		beanWriter = new CsvBeanWriter(new FileWriter(FILENAME), CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
	}

	public void writeHeader() throws IOException {
		// write the header
		// the header elements are used to map the bean values to each
		// column (names must match)
		beanWriter.writeHeader(HEADER);
		beanWriter.flush();
	}

	public void writeWithCsvBeanWriter(TradeField info) throws Exception {

		// create the customer beans
		final CellProcessor[] processors = getProcessors();
		// write the beans
		beanWriter.write(info, HEADER, processors);
		beanWriter.flush();
	}

	public void close() throws IOException {
		if (beanWriter != null) {
			beanWriter.close();
		}
	}

	private static CellProcessor[] getProcessors() {

		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), // customerNo
																						// (must
																						// be
																						// unique)
				new Optional(), // lastName
				new Optional(), // lastName
				new Optional(), // birthDate
				new Optional(), // birthDate
				new Optional(), // mailingAddress
				new Optional(), // married
				new Optional(), // numberOfKids
				new Optional(), // favouriteQuote
				new Optional(), // email
				new Optional(), // email
				new Optional(), // email
				new Optional(), // email
				new Optional(), // email
				new Optional(), // email
				new Optional() // loyaltyPoints
		};

		return processors;
	}
}

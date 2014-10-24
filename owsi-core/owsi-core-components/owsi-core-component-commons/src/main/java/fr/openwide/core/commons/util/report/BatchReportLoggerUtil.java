package fr.openwide.core.commons.util.report;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.MDC;

public final class BatchReportLoggerUtil {

	public static final String MDC_CONTEXT_KEY = "BatchReportContext";

	public static void log(Logger logger, BatchReport batchReport) {
		Map<String, List<BatchReportItem>> items = batchReport.getItems();
		Set<Entry<String, List<BatchReportItem>>> entries = items.entrySet();

		for (Entry<String, List<BatchReportItem>> entry : entries) {
			logger.info(entry.getKey());
			try {
				MDC.put(MDC_CONTEXT_KEY, entry.getKey());
				for (BatchReportItem reportItem : entry.getValue()) {
					if (BatchReportItemSeverity.INFO.equals(reportItem.getSeverity())) {
						logger.info(reportItem.getMessage());
					} else if (BatchReportItemSeverity.DEBUG.equals(reportItem.getSeverity())) {
						logger.debug(reportItem.getMessage());
					} else if (BatchReportItemSeverity.TRACE.equals(reportItem.getSeverity())) {
						logger.trace(reportItem.getMessage());
					} else if (BatchReportItemSeverity.WARN.equals(reportItem.getSeverity())) {
						logger.warn(reportItem.getMessage());
					} else if (BatchReportItemSeverity.ERROR.equals(reportItem.getSeverity())) {
						if (reportItem.getException() != null) {
							logger.error(reportItem.getMessage(), reportItem.getException());
						} else {
							logger.error(reportItem.getMessage());
						}
					}
				}
			} finally {
				MDC.remove(MDC_CONTEXT_KEY);
			}
		}
	}

	private BatchReportLoggerUtil() {
	}

}

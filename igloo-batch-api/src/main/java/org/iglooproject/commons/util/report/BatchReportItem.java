package org.iglooproject.commons.util.report;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bindgen.Bindable;
import org.iglooproject.commons.util.CloneUtils;

@Bindable
public class BatchReportItem implements Serializable {

  private static final long serialVersionUID = -8250079955023459814L;

  private String message;

  private BatchReportItemSeverity severity;

  private Exception exception;

  private Date date;

  protected BatchReportItem() {}

  public BatchReportItem(BatchReportItemSeverity severity, String message) {
    setSeverity(severity);
    setMessage(message);
    setDate(new Date());
  }

  public BatchReportItem(BatchReportItemSeverity severity, String message, Exception e) {
    this(severity, message);
    setException(e);
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public BatchReportItemSeverity getSeverity() {
    return severity;
  }

  public void setSeverity(BatchReportItemSeverity severity) {
    this.severity = severity;
  }

  public void setException(Exception exception) {
    this.exception = exception;
  }

  public Exception getException() {
    return exception;
  }

  public void setDate(Date date) {
    this.date = CloneUtils.clone(date);
  }

  public Date getDate() {
    return CloneUtils.clone(date);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("severity", severity.name())
        .append("date", date)
        .append("message", message)
        .build();
  }
}

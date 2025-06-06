package org.iglooproject.test.commons.util.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.iglooproject.commons.util.report.BatchReportItem;
import org.junit.jupiter.api.Test;

public class TestJsonRetrocompatibiliteIgloo5 {
  @Test
  void testRetrocompatibility() {
    String jsonBad =
        """
        {
            "bad" : 1743966600013,
            "message" : "Début du traitement de synchronisation des Habilitations depuis l'API ATEE...",
            "severity" : "INFO"
        }
        """;
    String jsonDate =
        """
        {
            "date" : 1743966600013,
            "message" : "Début du traitement de synchronisation des Habilitations depuis l'API ATEE...",
            "severity" : "INFO"
        }
        """;

    String jsonInstant =
        """
          {
              "instant" : 1743966600013,
              "message" : "Début du traitement de synchronisation des Habilitations depuis l'API ATEE...",
              "severity" : "INFO"
          }
          """;

    Assertions.assertThatThrownBy(
            () ->
                new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
                    .readerFor(BatchReportItem.class)
                    .readValue(jsonBad))
        .isInstanceOf(UnrecognizedPropertyException.class);

    Assertions.assertThatCode(
            () ->
                new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
                    .readerFor(BatchReportItem.class)
                    .readValue(jsonDate))
        .doesNotThrowAnyException();

    Assertions.assertThatCode(
            () ->
                new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
                    .readerFor(BatchReportItem.class)
                    .readValue(jsonInstant))
        .doesNotThrowAnyException();
  }
}

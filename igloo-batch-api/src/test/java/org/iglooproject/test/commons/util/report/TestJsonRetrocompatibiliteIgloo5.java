package org.iglooproject.test.commons.util.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.assertj.core.api.Assertions;
import org.iglooproject.commons.util.report.BatchReportItem;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.exc.UnrecognizedPropertyException;
import tools.jackson.databind.json.JsonMapper;

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
                JsonMapper.builder()
                    .configureForJackson2()
                    .changeDefaultPropertyInclusion(
                        incl -> incl.withValueInclusion(JsonInclude.Include.NON_EMPTY))
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
                    .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .build()
                    .readerFor(BatchReportItem.class)
                    .readValue(jsonBad))
        .isInstanceOf(UnrecognizedPropertyException.class);

    Assertions.assertThatCode(
            () ->
                JsonMapper.builder()
                    .configureForJackson2()
                    .changeDefaultPropertyInclusion(
                        incl -> incl.withValueInclusion(JsonInclude.Include.NON_EMPTY))
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
                    .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .build()
                    .readerFor(BatchReportItem.class)
                    .readValue(jsonDate))
        .doesNotThrowAnyException();

    Assertions.assertThatCode(
            () ->
                JsonMapper.builder()
                    .configureForJackson2()
                    .changeDefaultPropertyInclusion(
                        incl -> incl.withValueInclusion(JsonInclude.Include.NON_EMPTY))
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
                    .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .build()
                    .readerFor(BatchReportItem.class)
                    .readValue(jsonInstant))
        .doesNotThrowAnyException();
  }
}

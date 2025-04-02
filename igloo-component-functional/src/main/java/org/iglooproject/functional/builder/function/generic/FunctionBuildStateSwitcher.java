package org.iglooproject.functional.builder.function.generic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import org.iglooproject.functional.Function2;
import org.iglooproject.functional.builder.function.BigDecimalFunctionBuildState;
import org.iglooproject.functional.builder.function.BooleanFunctionBuildState;
import org.iglooproject.functional.builder.function.DateFunctionBuildState;
import org.iglooproject.functional.builder.function.DoubleFunctionBuildState;
import org.iglooproject.functional.builder.function.IntegerFunctionBuildState;
import org.iglooproject.functional.builder.function.LocalDateFunctionBuildState;
import org.iglooproject.functional.builder.function.LocalDateTimeFunctionBuildState;
import org.iglooproject.functional.builder.function.LongFunctionBuildState;
import org.iglooproject.functional.builder.function.StringFunctionBuildState;

public interface FunctionBuildStateSwitcher<
    TBuildResult,
    TCurrentType,
    TBooleanState extends
        BooleanFunctionBuildState<
                ?,
                TBooleanState,
                TDateState,
                TLocalDateState,
                TLocalDateTimeState,
                TIntegerState,
                TLongState,
                TDoubleState,
                TBigDecimalState,
                TStringState>,
    TDateState extends
        DateFunctionBuildState<
                ?,
                TBooleanState,
                TDateState,
                TLocalDateState,
                TLocalDateTimeState,
                TIntegerState,
                TLongState,
                TDoubleState,
                TBigDecimalState,
                TStringState>,
    TLocalDateState extends
        LocalDateFunctionBuildState<
                ?,
                TBooleanState,
                TDateState,
                TLocalDateState,
                TLocalDateTimeState,
                TIntegerState,
                TLongState,
                TDoubleState,
                TBigDecimalState,
                TStringState>,
    TLocalDateTimeState extends
        LocalDateTimeFunctionBuildState<
                ?,
                TBooleanState,
                TDateState,
                TLocalDateState,
                TLocalDateTimeState,
                TIntegerState,
                TLongState,
                TDoubleState,
                TBigDecimalState,
                TStringState>,
    TIntegerState extends
        IntegerFunctionBuildState<
                ?,
                TBooleanState,
                TDateState,
                TLocalDateState,
                TLocalDateTimeState,
                TIntegerState,
                TLongState,
                TDoubleState,
                TBigDecimalState,
                TStringState>,
    TLongState extends
        LongFunctionBuildState<
                ?,
                TBooleanState,
                TDateState,
                TLocalDateState,
                TLocalDateTimeState,
                TIntegerState,
                TLongState,
                TDoubleState,
                TBigDecimalState,
                TStringState>,
    TDoubleState extends
        DoubleFunctionBuildState<
                ?,
                TBooleanState,
                TDateState,
                TLocalDateState,
                TLocalDateTimeState,
                TIntegerState,
                TLongState,
                TDoubleState,
                TBigDecimalState,
                TStringState>,
    TBigDecimalState extends
        BigDecimalFunctionBuildState<
                ?,
                TBooleanState,
                TDateState,
                TLocalDateState,
                TLocalDateTimeState,
                TIntegerState,
                TLongState,
                TDoubleState,
                TBigDecimalState,
                TStringState>,
    TStringState extends
        StringFunctionBuildState<
                ?,
                TBooleanState,
                TDateState,
                TLocalDateState,
                TLocalDateTimeState,
                TIntegerState,
                TLongState,
                TDoubleState,
                TBigDecimalState,
                TStringState>> {

  TStringState toString(Function2<? super TCurrentType, String> function);

  TIntegerState toInteger(Function2<? super TCurrentType, Integer> function);

  TLongState toLong(Function2<? super TCurrentType, Long> function);

  TDoubleState toDouble(Function2<? super TCurrentType, Double> function);

  TBigDecimalState toBigDecimal(Function2<? super TCurrentType, BigDecimal> function);

  TDateState toDate(Function2<? super TCurrentType, ? extends Date> function);

  TLocalDateState toLocalDate(Function2<? super TCurrentType, LocalDate> function);

  TLocalDateTimeState toLocalDateTime(Function2<? super TCurrentType, LocalDateTime> function);

  TBooleanState toBoolean(Function2<? super TCurrentType, Boolean> function);

  TBuildResult build();
}

package org.iglooproject.functional.builder.function.generic;

import org.iglooproject.functional.Functions2;
import org.iglooproject.functional.builder.function.BigDecimalFunctionBuildState;
import org.iglooproject.functional.builder.function.BooleanFunctionBuildState;
import org.iglooproject.functional.builder.function.DateFunctionBuildState;
import org.iglooproject.functional.builder.function.DoubleFunctionBuildState;
import org.iglooproject.functional.builder.function.IntegerFunctionBuildState;
import org.iglooproject.functional.builder.function.LocalDateFunctionBuildState;
import org.iglooproject.functional.builder.function.LocalDateTimeFunctionBuildState;
import org.iglooproject.functional.builder.function.LongFunctionBuildState;
import org.iglooproject.functional.builder.function.StringFunctionBuildState;

public abstract class GenericDoubleFunctionBuildStateImpl<
        TBuildResult,
        TStateSwitcher extends
            FunctionBuildStateSwitcher<
                    TBuildResult,
                    Double,
                    TBooleanState,
                    TDateState,
                    TLocalDateState,
                    TLocalDateTimeState,
                    TIntegerState,
                    TLongState,
                    TDoubleState,
                    TBigDecimalState,
                    TStringState>,
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
                    TBuildResult,
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
                    TStringState>>
    extends GenericNumberFunctionBuildStateImpl<
        TBuildResult,
        Double,
        TStateSwitcher,
        TBooleanState,
        TDateState,
        TLocalDateState,
        TLocalDateTimeState,
        TIntegerState,
        TLongState,
        TDoubleState,
        TBigDecimalState,
        TStringState>
    implements DoubleFunctionBuildState<
        TBuildResult,
        TBooleanState,
        TDateState,
        TLocalDateState,
        TLocalDateTimeState,
        TIntegerState,
        TLongState,
        TDoubleState,
        TBigDecimalState,
        TStringState> {

  @Override
  public TBuildResult withDefault(final Double defaultValue) {
    return toDouble(Functions2.defaultValue(defaultValue)).build();
  }
}

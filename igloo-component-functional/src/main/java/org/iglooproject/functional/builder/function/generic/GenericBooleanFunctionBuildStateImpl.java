package org.iglooproject.functional.builder.function.generic;

import org.iglooproject.functional.Functions2;
import org.iglooproject.functional.builder.function.BigDecimalFunctionBuildState;
import org.iglooproject.functional.builder.function.BooleanFunctionBuildState;
import org.iglooproject.functional.builder.function.DateFunctionBuildState;
import org.iglooproject.functional.builder.function.DoubleFunctionBuildState;
import org.iglooproject.functional.builder.function.IntegerFunctionBuildState;
import org.iglooproject.functional.builder.function.LongFunctionBuildState;
import org.iglooproject.functional.builder.function.StringFunctionBuildState;

public abstract class GenericBooleanFunctionBuildStateImpl<
        TBuildResult,
        TStateSwitcher extends
            FunctionBuildStateSwitcher<
                    TBuildResult,
                    Boolean,
                    TBooleanState,
                    TDateState,
                    TIntegerState,
                    TLongState,
                    TDoubleState,
                    TBigDecimalState,
                    TStringState>,
        TBooleanState extends
            BooleanFunctionBuildState<
                    TBuildResult,
                    TBooleanState,
                    TDateState,
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
                    TIntegerState,
                    TLongState,
                    TDoubleState,
                    TBigDecimalState,
                    TStringState>>
    extends GenericFunctionBuildStateImpl<
        TBuildResult,
        Boolean,
        TStateSwitcher,
        TBooleanState,
        TDateState,
        TIntegerState,
        TLongState,
        TDoubleState,
        TBigDecimalState,
        TStringState>
    implements BooleanFunctionBuildState<
        TBuildResult,
        TBooleanState,
        TDateState,
        TIntegerState,
        TLongState,
        TDoubleState,
        TBigDecimalState,
        TStringState> {

  @Override
  public TBooleanState not() {
    return toBoolean(input -> input != null ? !input : null);
  }

  @Override
  public TBuildResult withDefault(final Boolean defaultValue) {
    return toBoolean(Functions2.defaultValue(defaultValue)).build();
  }
}

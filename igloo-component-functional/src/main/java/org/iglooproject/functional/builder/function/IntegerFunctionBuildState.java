package org.iglooproject.functional.builder.function;

public interface IntegerFunctionBuildState
		<
		TBuildResult,
		TBooleanState extends BooleanFunctionBuildState<?, TBooleanState, TDateState, TLocalDateState, TLocalDateTimeState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>,
		TDateState extends DateFunctionBuildState<?, TBooleanState, TDateState, TLocalDateState, TLocalDateTimeState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>,
		TLocalDateState extends LocalDateFunctionBuildState<?, TBooleanState, TDateState, TLocalDateState, TLocalDateTimeState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>,
		TLocalDateTimeState extends LocalDateTimeFunctionBuildState<?, TBooleanState, TDateState, TLocalDateState, TLocalDateTimeState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>, 
		TIntegerState extends IntegerFunctionBuildState<?, TBooleanState, TDateState, TLocalDateState, TLocalDateTimeState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>,
		TLongState extends LongFunctionBuildState<?, TBooleanState, TDateState, TLocalDateState, TLocalDateTimeState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>,
		TDoubleState extends DoubleFunctionBuildState<?, TBooleanState, TDateState, TLocalDateState, TLocalDateTimeState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>,
		TBigDecimalState extends BigDecimalFunctionBuildState<?, TBooleanState, TDateState, TLocalDateState, TLocalDateTimeState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>,
		TStringState extends StringFunctionBuildState<?, TBooleanState, TDateState, TLocalDateState, TLocalDateTimeState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>
		>
		extends NumberFunctionBuildState<TBuildResult, Integer, TBooleanState, TDateState, TLocalDateState, TLocalDateTimeState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState> {
	
}

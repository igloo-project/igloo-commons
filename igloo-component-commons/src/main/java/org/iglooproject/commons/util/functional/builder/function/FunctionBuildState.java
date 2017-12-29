package org.iglooproject.commons.util.functional.builder.function;

import java.math.BigDecimal;
import java.util.Date;

import com.google.common.base.Function;

public interface FunctionBuildState
		<
		TBuildResult,
		TCurrentType,
		TBooleanState extends BooleanFunctionBuildState<?, TBooleanState, TDateState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>,
		TDateState extends DateFunctionBuildState<?, TBooleanState, TDateState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>, 
		TIntegerState extends IntegerFunctionBuildState<?, TBooleanState, TDateState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>,
		TLongState extends LongFunctionBuildState<?, TBooleanState, TDateState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>,
		TDoubleState extends DoubleFunctionBuildState<?, TBooleanState, TDateState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>,
		TBigDecimalState extends BigDecimalFunctionBuildState<?, TBooleanState, TDateState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>,
		TStringState extends StringFunctionBuildState<?, TBooleanState, TDateState, TIntegerState, TLongState, TDoubleState, TBigDecimalState, TStringState>
		> {
	
	TStringState toString(Function<? super TCurrentType, String> function);
	
	TIntegerState toInteger(Function<? super TCurrentType, Integer> function);
	
	TLongState toLong(Function<? super TCurrentType, Long> function);
	
	TDoubleState toDouble(Function<? super TCurrentType, Double> function);
	
	TBigDecimalState toBigDecimal(Function<? super TCurrentType, BigDecimal> function);
	
	TDateState toDate(Function<? super TCurrentType, ? extends Date> function);
	
	TBooleanState toBoolean(Function<? super TCurrentType, Boolean> function);
	
	TBuildResult build();
	
	TBuildResult withDefault(TCurrentType defaultValue);

}

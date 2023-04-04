package cn.esign;

import org.apache.flink.table.annotation.DataTypeHint;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.catalog.DataTypeFactory;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.table.types.inference.TypeInference;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectWsUDF extends ScalarFunction {

    public String eval(@DataTypeHint("MAP<STRING, INT>") Map<String, Integer> multiset) {
        return multiset.entrySet().stream()
                .map(entry -> entry.getKey() + "(" + entry.getValue()+")")
                .collect(Collectors.joining(","));
    }

    @Override
    public TypeInference getTypeInference(DataTypeFactory typeFactory) {
        return TypeInference.newBuilder().outputTypeStrategy(callContext->Optional.of(DataTypes.STRING())).build();
    }

}

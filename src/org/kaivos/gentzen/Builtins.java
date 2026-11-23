package org.kaivos.gentzen;

import org.kaivos.gentzen.value.NaturalValue;
import org.kaivos.gentzen.value.TupleValue;
import org.kaivos.gentzen.value.Value;

public class Builtins {
    /**
    * left/and : ($1(?x), *a |- *b) / ($1(?x /\ ?_), *a |- *b);
    */
    public static<T extends Value, U extends Value> void leftAnd(TupleValue<T, U> input1) {

    }

    public static ContinuationCall<NaturalValue> succ(NaturalValue input1, Continuation<NaturalValue> output1) {
        return new ContinuationCall<>(output1, new NaturalValue(input1.value() + 1));
    }
}

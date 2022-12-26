package com.study.aop.trace.hellotrace;

import com.study.aop.trace.TraceStatus;
import org.junit.jupiter.api.Test;

public class HelloTraceV1Test {

    @Test
    public void begin_end() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

    @Test
    public void begin_exception() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException());
    }

}

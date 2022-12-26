package com.study.aop.trace;

import lombok.Getter;

@Getter
public class TraceStatus {

    private TraceId traceId;

    private Long startTImeMs;

    private String message;

    public TraceStatus(TraceId traceId, Long startTImeMs, String message) {
        this.traceId = traceId;
        this.startTImeMs = startTImeMs;
        this.message = message;
    }

}

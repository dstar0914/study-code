package com.study.aop.app.v2;

import com.study.aop.trace.TraceId;
import com.study.aop.trace.TraceStatus;
import com.study.aop.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;

    private final HelloTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId) {
        TraceStatus status = null;

        try {
            status = trace.beginSync(traceId, "OrderService.orderItem()");
            orderRepository.save(traceId, itemId);
            trace.end(status);

        } catch (Exception e) {
            trace.exception(status, e);

            throw e;
        }
    }

}

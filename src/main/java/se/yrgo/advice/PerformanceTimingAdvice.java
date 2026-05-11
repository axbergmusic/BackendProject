package se.yrgo.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceTimingAdvice {

    @Around("execution(* se.yrgo.services.*.*(..))")
    public Object performTimingMeasurement(ProceedingJoinPoint method) throws Throwable {

        // before
        long startTime = System.nanoTime();

        try {
            // proceed to target
            Object value = method.proceed();
            return value;

        } finally {
            // after
            long endTime = System.nanoTime();
            long timeTaken = endTime - startTime;
            System.out.println("The method '" + method.getSignature().getName()
                    + "' took " + timeTaken / 1000000 + " ms");
        }
    }
}
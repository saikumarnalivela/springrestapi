package com.vehicle.assessment.aspect;

import com.vehicle.assessment.domain.Vehicle;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private static Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    private ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("[yyyy-mm-dd hh:mm:ss:SSS]");
        }
    };

    @Pointcut("within(com.vehicle..*) && execution(* com.vehicle.assessment.repository.VehicleRepository.*(..))")
    public void repositoryMethods() {
    }

    @Pointcut("within(com.vehicle..*) && @annotation(com.vehicle.assessment.annotations.Loggable)")
    public void loggableMethods() {
    }



    @Before("repositoryMethods()")
    public void logMethodCall(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        logger.info(sdf.get().format(new Date()) + methodName);
    }

    @Before("loggableMethods()")
    public void logMethod(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        logger.info("Executing method: " + methodName);
    }

    @Around("repositoryMethods()")
    public Object methodExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.nanoTime();
        Object retval = proceedingJoinPoint.proceed();
        long end = System.nanoTime();
        String methodName = proceedingJoinPoint.getSignature().getName();
        logger.info("Execution of " + methodName + " took " + TimeUnit.NANOSECONDS.toMillis(end - start) + " ms");
        return retval;
    }



    /*@AfterThrowing(value = "execution(* com.vehicle.assessment.repository.VehicleRepository.*(..))", throwing = "ex")
    public void logMethodAfterThrowAnError(Exception ex){
        logger.info("Error message: " + ex.getMessage());
    }*/

    @AfterReturning("execution(* com.vehicle.assessment.repository.VehicleRepository.save*(..))")
    public void logMethodAfterReturn(JoinPoint jp){
        String methodName = jp.getSignature().getName();
        Object[] lArgs = jp.getArgs();
        Vehicle vehicle = (Vehicle) lArgs[0];
        ThreadContext.push("Id", vehicle.getVin());
        logger.info("New Vehicle added successfully with id: " +vehicle);

    }

}

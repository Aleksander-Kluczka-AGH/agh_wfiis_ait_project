package zti.lichess_stats.aspect;

import org.springframework.stereotype.Component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect
{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Pointcut that matches all methods found in controllers.
     */
    @Pointcut("execution(* zti.lichess_stats.controller.*.*(..))")
    public void controllerMethods()
    { }

    /**
     * Pointcut that matches all methods found in services.
     */
    @Pointcut("execution(* zti.lichess_stats.service.*.*(..))")
    public void serviceMethods()
    { }

    /**
     * Pointcut that matches all methods found in repositories.
     */
    @Pointcut("execution(* zti.lichess_stats.repository.*.*(..))")
    public void repositoryMethods()
    { }

    /**
     * Logs before entering any controller method.
     * @param joinPoint JoinPoint used to intercept the method call.
     */
    @Before("controllerMethods()")
    public void logBeforeControllers(JoinPoint joinPoint)
    {
        log.info("Entering controller: " + joinPoint.getSignature().getDeclaringType().getName()
                 + "." + joinPoint.getSignature().getName() + "()");
    }

    /**
     * Logs before entering any repository method.
     * @param joinPoint JoinPoint used to intercept the method call.
     */
    @Before("serviceMethods()")
    public void logBeforeServices(JoinPoint joinPoint)
    {
        log.info("Entering service: " + joinPoint.getSignature().getDeclaringType().getName() + "."
                 + joinPoint.getSignature().getName() + "()");
    }

    /**
     * Logs after throwing an exception in any controller, service or repository method.
     * @param joinPoint JoinPoint used to intercept the method call.
     * @param e Exception thrown.
     */
    @AfterThrowing(pointcut = "controllerMethods() || serviceMethods() || repositoryMethods()",
        throwing = "e")
    public void
        logAfterThrowing(JoinPoint joinPoint, Throwable e)
    {
        log.error("Exception in " + joinPoint.getSignature().getDeclaringType().getName() + "."
                  + joinPoint.getSignature().getName() + "() with cause = '" + e.getCause()
                  + "' and exception = '" + e.getMessage() + "'");
    }
}

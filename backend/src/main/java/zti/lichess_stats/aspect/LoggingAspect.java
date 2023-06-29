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

    @Pointcut("execution(* zti.lichess_stats.controller.*.*(..))")
    public void controllerMethods()
    { }

    @Pointcut("execution(* zti.lichess_stats.service.*.*(..))")
    public void serviceMethods()
    { }

    @Pointcut("execution(* zti.lichess_stats.repository.*.*(..))")
    public void repositoryMethods()
    { }

    @Before("controllerMethods()")
    public void logBeforeControllers(JoinPoint joinPoint)
    {
        log.info("Entering controller: " + joinPoint.getSignature().getDeclaringType().getName()
                 + "." + joinPoint.getSignature().getName() + "()");
    }

    @Before("serviceMethods()")
    public void logBeforeServices(JoinPoint joinPoint)
    {
        log.info("Entering service: " + joinPoint.getSignature().getDeclaringType().getName() + "."
                 + joinPoint.getSignature().getName() + "()");
    }

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

package org.example.kihelp_back.global.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * org.example.kihelp_back.teacher.controller.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.subject.controller.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.task.controller.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.argument.controller.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.user.controller.*.*(..)) || " +
            "execution(public * org.example.kihelp_back.wallet.controller.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.transaction.controller.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.history.controller.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.support.controller.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.discount.controller.*.*(..)) || " +
            "execution(public * org.example.kihelp_back.invite.controller.*.*(..))")
    public void controllerLog(){}

    @Pointcut("execution(public * org.example.kihelp_back.teacher.service.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.subject.service.*.*(..)) || " +
            "execution(public * org.example.kihelp_back.task.service.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.argument.service.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.user.service.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.wallet.service.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.transaction.service.*.*(..)) || " +
            "execution(public * org.example.kihelp_back.history.service.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.discount.service.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.invite.service.*.*(..))")
    public void serviceLog(){}

    @Pointcut("execution(public * org.example.kihelp_back.teacher.usecase.impl.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.subject.usecase.impl.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.task.usecase.impl.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.argument.usecase.impl.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.user.usecase.impl.*.*(..)) || " +
            "execution(public * org.example.kihelp_back.wallet.usecase.impl.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.transaction.usecase.impl.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.history.usecase.impl.*.*(..)) || " +
            "execution(public * org.example.kihelp_back.support.usecase.impl.*.*(..)) ||" +
            "execution(public * org.example.kihelp_back.discount.usecase.impl.*.*(..)) || " +
            "execution(public * org.example.kihelp_back.invite.usecase.impl.*.*(..))")
    public void useCaseLog(){}

    @Before("controllerLog()")
    public void doBeforeController(JoinPoint jp){
        var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;

        if(attributes != null){
            request = attributes.getRequest();
        }

        if(request != null){
            log.info("Request: IP: {}, URL: {}, HTTP_METHOD: {}, CONTROLLER_METHOD: {}.{}",
                    request.getRemoteAddr(),
                    request.getRequestURL().toString(),
                    request.getMethod(),
                    jp.getSignature().getDeclaringType(),
                    jp.getSignature().getName()
            );
        }
    }

    @Around("controllerLog()")
    public Object doAroundController(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = jp.proceed();
        long executionTime = System.currentTimeMillis() - start;

        log.info("Execution method: {}.{}. Execution time: {}ms",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(),
                executionTime
        );

        return proceed;
    }

    @Before("serviceLog()")
    public void doServiceLog(JoinPoint jp){
        var className = jp.getSignature().getDeclaringTypeName();
        var methodName = jp.getSignature().getName();

        Object[] args = jp.getArgs();
        var argsString = args.length > 0 ? Arrays.toString(args) : "Method has no arguments";

        log.info("Run service: Service_method: {}.{}. Method arguments: {}",
                className, methodName, argsString
        );
    }

    @After("serviceLog()")
    public void logAfterMethodService(JoinPoint joinPoint) {
        var className = joinPoint.getSignature().getDeclaringTypeName();
        var methodName = joinPoint.getSignature().getName();

        log.info("Finished service: Service_method: {}.{}.",
                className,
                methodName
        );
    }

    @Before("useCaseLog()")
    public void doUseCaseLog(JoinPoint jp){
        var className = jp.getSignature().getDeclaringTypeName();
        var methodName = jp.getSignature().getName();

        Object[] args = jp.getArgs();
        var argsString = args.length > 0 ? Arrays.toString(args) : "Method has no arguments";

        log.info("Run usecase: UseCase_method: {}.{}. Method arguments: {}",
                className, methodName, argsString
        );
    }

    @After("useCaseLog()")
    public void logAfterMethodUseCase(JoinPoint joinPoint) {
        var className = joinPoint.getSignature().getDeclaringTypeName();
        var methodName = joinPoint.getSignature().getName();

        log.info("Finished usecase: UseCase_method: {}.{}.",
                className,
                methodName
        );
    }
}

// package com.backtoback.business.common.config;
//
// import java.lang.annotation.ElementType;
// import java.lang.annotation.Retention;
// import java.lang.annotation.RetentionPolicy;
// import java.lang.annotation.Target;
//
// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.springframework.stereotype.Component;
//
// @Component
// @Aspect
// public class AspectConfig {
//
// 	@Around("@annotation(PerCrawling)")
// 	public Object logPerf(ProceedingJoinPoint joinPoint) throws Throwable {
// 		long begin = System.currentTimeMillis();
//
// 		Object retVal = joinPoint.proceed(); // 메서드 호출 자체를 감쌈
//
// 		System.out.println(System.currentTimeMillis() - begin);
// 		return retVal;
// 	}
//
// 	@Target({ElementType.METHOD})
// 	@Retention(RetentionPolicy.RUNTIME)
// 	public @interface PerCrawling {
// 	}
//
// }

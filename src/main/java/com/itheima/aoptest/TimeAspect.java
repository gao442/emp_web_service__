package com.itheima.aoptest;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class TimeAspect {
    /**
     * 切入点定义（方法体为空，仅作为表达式的"名字"供通知注解引用）
     * 切点表达式: @annotation(com.itheima.aop.MyLog)
     *   - @annotation 按"注解"匹配：只拦截标注了 @MyLog 注解的方法
     *   - 括号内必须写注解的全限定类名（包名.类名）
     *   - 与 execution 按方法签名匹配不同，这里由注解在调用点"主动声明"要拦截哪些方法，
     *     粒度精确到单个方法，且与包结构无关（MyLog 需 @Retention(RUNTIME) 才能被 AOP 在运行期识别）
     */
    /**
     * * 记录方法执行耗时的环绕通知
     * * 引用切入点: pt()，即只应用于标注了 @MyLog 注解的方法
     * * （如需要也可组合写法："pt() && execution(* com.itheima.service..*.*(..))"）
     * *
     * * @param proceedingJoinPoint 连接点对象，可以执行目标方法
     * * @return 目标方法的返回值
     * * @throws Throwable 目标方法抛出的异常
     */



    //根据注解进行匹配

    @Pointcut("@annotation(com.itheima.aoptest.MyLog)")
    private void pt(){};//只能在当前切面类来引用

    @Around("pt()")
    public Object recordTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 记录方法开始执行的时间戳
        long begin = System.currentTimeMillis();

//        //拿到目标方法的全类名
//        proceedingJoinPoint.getTarget().getClass().getName();
//        //拿到目标方法的参数
//        proceedingJoinPoint.getArgs();
//        //拿到目标方法的方法名
//        proceedingJoinPoint.getSignature().getName();

        // 执行目标方法（JoinPoint是连接点是所有可能被aop控制的方法）
        Object proceed = proceedingJoinPoint.proceed();
    
        // 记录方法执行结束的时间戳
        long end = System.currentTimeMillis();
            
        // 打印方法签名和执行耗时（单位：毫秒）
        log.info("{} 执行耗时: {}ms", proceedingJoinPoint.getSignature(), end - begin);
            
        // 返回目标方法的执行结果
        //切面方法自身的 return proceed ——指把目标方法的结果原样传回去


        return proceed;
            
    }

}
/*
连接点：可以被aop控制的方法
通知：抽取出来的重复的逻辑（recordTime）
切入点：通知只在切入点方法执行时被应用（实质上就是目标方法）（通过切入点表达式来表达）
切面：通知加切入点Aspect
目标对象：通知应用的对象（实质上是目标方法所在的对象类）


使用aop以后spring注入的对象实际上是代理对象
 */
/*
通知类型：
@Before
方法执行前
❌不能访问返回值
❌不能能否阻止执行（除非抛异常）
@After
方法执行后（总会执行）
❌不能访问返回值
❌不能能否阻止执行
@AfterReturning
方法成功返回后（必须是成功返回以后才能）
✅能访问返回值，可以修改返回值（可以数据转换日志记录）
❌不能阻止执行
@AfterThrowing
方法抛出异常后
✅能访问返回值（访问抛出的异常对象）
❌不能组织运行
@Around
方法执行前后
✅能访问返回值
✅能阻止运行（可以控制是否执行目标方法，需要自己调用原始方法）
（返回值必须是object，来收集原始方法的返回值）



┌─────────────────────────────────────┐
│         @Around 开始                │
│  ┌───────────────────────────────┐  │
│  │       @Before 执行            │  │
│  └───────────────────────────────┘  │
│           ↓                         │
│  ┌───────────────────────────────┐  │
│  │     目标方法执行              │  │
│  └───────────────────────────────┘  │
│           ↓                         │
│  ┌───────────────────────────────┐  │
│  │  成功 → @AfterReturning 执行  │  │
│  │  失败 → @AfterThrowing 执行   │  │
│  └───────────────────────────────┘  │
│           ↓                         │
│  ┌───────────────────────────────┐  │
│  │       @After 执行             │  │
│  └───────────────────────────────┘  │
│         @Around 结束                │
└─────────────────────────────────────┘
 */
/*
通过point Cut可以提取切入点表达是进行注解的自动注入
所有引用 @Pointcut 的地方都要加双引号
，因为注解属性值必须是常量字符串。
 */


/*
不同切面类匹配到同一个目标方法（切入点），
按照切面类的字母顺序来作为执行顺序
如果加上order注解，数字小的先执行，数字大的后执行


 */
/*
 连接点 (JoinPoint) 是所有可以被拦截的方法（理论上的全集）三层所有可能的方法都是连接点
 切入点 (Pointcut)  是实际被拦截的方法（子集），只有 service 包下的方法
 */

/*
切入点表达式：
execution（执行）(.....里面方法的签名来匹配)
execution( 修饰符? 返回值 包名.类名 方法名(参数) 异常? )
① 修饰符
*
任意返回值类型（省略了修饰符，public 可写可不写）
② 包名+子包
com.itheima..*
.. 匹配 com.itheima 下任意层级子包（service、service.imp、controller…）

③ 类名
*
任意类名
④ 方法名
*
任意方法名

⑤ 参数
(..)
任意参数（个数、类型不限）
切面表达式中的返回值指的是被拦截方法的返回值
（也就是目标方法的返回值）（拦截接口就会自动拦截接口的实现类）

@annotation（....）:根据注解匹配
 */
/*
..代表任意个任意类型，  *代表一个任意类型的
 */
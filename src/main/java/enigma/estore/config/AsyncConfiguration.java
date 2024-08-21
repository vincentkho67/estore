package enigma.estore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfiguration {
    // few example for async config
//    @Bean(name = "taskExecutor")
//    public Executor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(2);
//        executor.setMaxPoolSize(2);
//        executor.setQueueCapacity(500);
//        executor.setThreadNamePrefix("Async-");
//        executor.initialize();
//        return executor;
//    }
//
//    @Bean(name = "emailExecutor")
//    public Executor emailExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(5);
//        executor.setMaxPoolSize(10);
//        executor.setQueueCapacity(100);
//        executor.setThreadNamePrefix("EmailThread-");
//        executor.initialize();
//        return executor;
//    }
}

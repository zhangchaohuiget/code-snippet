## spring定时任务

执行模式有：cron、fixedDelay、fixedRate

- cron：按照cron表达式代表的执行计划来执行；  
  cron表达式举栗：（需要什么，网上有生成器）  
  0 0/1 * * * ? 每分钟执行一次  
  0 0 1 * * ? 每天1点执行一次
- fixedDelay 固定延迟执行，在上一次执行完成后，延迟x时间再执行下一次
- fixedRate 固定速率，以一定的速率执行，这个需要认真理解一下，网上的博客很多都认为这个和fixedDelay一样，实际上是不一样的
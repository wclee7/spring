package crise.studio.facade;

//import crise.studio.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
//import crise.studio.configuration.SchedulerConditional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
//@Conditional(SchedulerConditional.class)
public class SchedulerFacade {

    public SchedulerFacade() {
    }

    private final Logger logger = LoggerFactory.getLogger(SchedulerFacade.class);
    
//    @Autowired
//    private SchedulerService schedulerService;

    private static int MIDNIGHT_HOUR = 0;

    /**
     * 서비스 런칭 통계
     */
    @Scheduled(cron = "0 0 */1 * * *") //1시간 마다
    public void execCollectServiceLaunchingStat() {
        LocalDate now = LocalDate.now();
        logger.info(" {} :: Service Launching Batch Start", LocalDateTime.now());

        // 자정이면 전날 로그를 한번더 종합해 입력한다.
        if (LocalTime.now().getHour() == MIDNIGHT_HOUR) {
            now = now.minusDays(1);
        }
//        schedulerService.execCollectServiceLaunchingStatByLocalDate(now);
    }

    /*
    영상 이용 통계
    인기 이용 통계
     */
    @Scheduled(cron = "0 30 5 * * ?")
    public void execOnceInDayStat() {

        logger.info("{} :: execOnceInDayStat Batch Start ", LocalDateTime.now());
        LocalDateTime closeDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
//        schedulerService.execVodPlayStatByLocalDateTime(closeDate);
//        schedulerService.execVodPlayRankStatByLocalDateTime(closeDate);
    }

}

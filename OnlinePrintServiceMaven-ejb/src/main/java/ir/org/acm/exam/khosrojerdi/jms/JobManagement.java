/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.org.acm.exam.khosrojerdi.jms;

import ir.org.acm.exam.khosrojerdi.jms.local.JobManagementInf;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author behzad
 */
@Singleton
@Startup
@Local(JobManagementInf.class)
public class JobManagement implements JobManagementInf {

    private static final Logger LOGGER = Logger.getLogger(JobManagement.class.getName());


    private ScheduledExecutorService execService = Executors.newScheduledThreadPool(4);

//    @Resource(mappedName = "java:jboss/ee/concurrency/scheduler/default")
//    private ManagedScheduledExecutorService scheduledExecutor;

//    @Resource(mappedName = "java:jboss/ee/concurrency/factory/default")
//    private ManagedThreadFactory managedThreadFactory;
//
//    private ScheduledFuture<?> webserviceCheckerJobFutureResult;


    @PostConstruct
    public void startJobs() {
        WebserviceCheckerJob webserviceCheckerJob = new WebserviceCheckerJob();
        QueueConsumer queueConsumer = new QueueConsumer();
        execService.scheduleWithFixedDelay(webserviceCheckerJob, 20, 1, TimeUnit.SECONDS);
        execService.scheduleAtFixedRate(queueConsumer, 20, 10, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void stopJobs() {
        LOGGER.log(Level.INFO, "preDestroy is called.");

//        webserviceCheckerJobFutureResult.cancel(true);
//        scheduledExecutor.shutdownNow();
        execService.shutdown();
    }

}

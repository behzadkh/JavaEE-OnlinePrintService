package ir.org.acm.exam.khosrojerdi.jms.local;

import javax.ejb.Local;

/**
 * Created by behzad on 4/1/17.
 */
@Local
public interface JobManagementInf {

    public static final String JNDI = "java:global/OnlinePrintServiceMaven-ear-1.0-SNAPSHOT/OnlinePrintServiceMaven-ejb-1.0-SNAPSHOT/JobManagement!ir.org.acm.exam.khosrojerdi.jms.local.JobManagementInf";
}
   /*
    java:global/OnlinePrintServiceMaven-ear-1.0-SNAPSHOT/OnlinePrintServiceMaven-ejb-1.0-SNAPSHOT/JobManagement!ir.org.acm.exam.khosrojerdi.jms.local.JobManagementInf
	java:app/OnlinePrintServiceMaven-ejb-1.0-SNAPSHOT/JobManagement!ir.org.acm.exam.khosrojerdi.jms.local.JobManagementInf
	java:module/JobManagement!ir.org.acm.exam.khosrojerdi.jms.local.JobManagementInf
	java:global/OnlinePrintServiceMaven-ear-1.0-SNAPSHOT/OnlinePrintServiceMaven-ejb-1.0-SNAPSHOT/JobManagement
	java:app/OnlinePrintServiceMaven-ejb-1.0-SNAPSHOT/JobManagement
	java:module/JobManagement
     */
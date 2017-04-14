package ir.org.acm.exam.khosrojerdi.util;

import javax.enterprise.context.Dependent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by reza on 7/2/15.
 */
@Dependent
public class Lookup {

    private static final Logger LOGGER = Logger.getLogger(Lookup.class.getName());

    private InitialContext ctx = null;

    public <T> T get(String jndiName, String ip, String port, String username, String password) throws NamingException {

        Properties properties = new Properties();

        try {
            properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            properties.put("remote.connections", "1");
            properties.put("remote.connection.1.host", ip);
            properties.put("remote.connection.1.port", port);
            properties.put("remote.connection.1.username", username);
            properties.put("remote.connection.1.password", password);
            properties.put("org.jboss.ejb.client.scoped.context", "true");

            // The environment property jboss.naming.client.ejb.context indicates that the InitialContext implementation
            // of the remote naming project will also create an internal EJB client context via the EJB client library.
            // This allows the invocation of EJB components with the remote naming project.
            ctx = new InitialContext(properties);

            return (T) ctx.lookup(jndiName);
        } catch (NamingException e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }

        // All proxies become invalid if .close() for the related javax.naming.InitalContext is invoked,
        // or the InitialContext is not longer referenced and gets garbage-collected.

        throw new NamingException("Service not available on specified servers " + ip);
    }

    public void close() {

        if (ctx != null) {
            try {
                ((Context) ctx.lookup("ejb:")).close();
                ctx.close();
            } catch (NamingException e) {
                LOGGER.log(Level.SEVERE,e.getMessage());
            }
        }
    }

    public Object get(String jndi) {
        Object obj = null;
        Context jndiContext = null;
        try {
            final Hashtable jndiProperties = new Hashtable();
            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            jndiProperties.put("jboss.naming.client.ejb.context", true);
            jndiContext = new InitialContext(jndiProperties);
            obj = jndiContext.lookup(jndi);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
        return obj;
    }

    public Object get(String jndi, String ip, String port) {
        Object obj = null;
        Context jndiContext = null;
        try {
            final Hashtable jndiProperties = new Hashtable();
//            jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
//            jndiProperties.put("jboss.naming.client.ejb.context", true);
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            jndiProperties.put(Context.PROVIDER_URL, "http-remoting://" + ip + ":" + port + "");
            jndiProperties.put(Context.SECURITY_PRINCIPAL, "art-client");
            jndiProperties.put(Context.SECURITY_CREDENTIALS, "123321Q");
            jndiContext = new InitialContext(jndiProperties);
            obj = jndiContext.lookup(jndi);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
        return obj;
    }
}

package com.yxtwang.engine.vaadin;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.ui.UI;

/**
 * 
 * com.vaadin.server.VaadinServlet进行扩展
 * 
 * spring 和vaadin的servlet结合
 * 
 * @author yanxt
 */
public class SpringVaadinServlet extends VaadinServlet {

	private static final long serialVersionUID = -919109610062102691L;
    private static Logger logger = LoggerFactory.getLogger(SpringVaadinServlet.class);
    private WebApplicationContext applicationContext;
    private static final String BEAN_NAME_PARAMETER = "beanName";
    private String vaadinBeanName = "ui";
    
	/**
	 * springProvider--对spring服务监听
	 * @author yanxt
	 *
	 */
    private class SpringProvider extends UIProvider {
		private static final long serialVersionUID = -1556955415575593860L;

		@Override
        public UI createInstance(UICreateEvent event) {
            return (UI) applicationContext.getBean(vaadinBeanName);
        }

        @SuppressWarnings("unchecked")
		@Override
        public Class<? extends UI> getUIClass(UIClassSelectionEvent uiClassSelectionEvent) {
            return (Class<? extends UI>) applicationContext.getType(vaadinBeanName);
        }
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
    	
    	logger.info("spring vaadion 启动");
    	
        super.init(servletConfig);

        applicationContext = 
        		WebApplicationContextUtils.getWebApplicationContext(servletConfig.getServletContext());
        if (servletConfig.getInitParameter(BEAN_NAME_PARAMETER) != null) {
            vaadinBeanName = servletConfig.getInitParameter(BEAN_NAME_PARAMETER);
        }
    }

    @Override
    protected VaadinServletService createServletService(
    		DeploymentConfiguration deploymentConfiguration) {
    	
        final VaadinServletService service = super.createServletService(deploymentConfiguration);
        service.addSessionInitListener(new SessionInitListener() {
			private static final long serialVersionUID = -2144249933108749204L;

			@Override
            public void sessionInit(SessionInitEvent event) throws ServiceException {
                service.addUIProvider(event.getSession(), new SpringProvider());
            }
        });

        return service;
    }
}

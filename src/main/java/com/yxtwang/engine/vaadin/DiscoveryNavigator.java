package com.yxtwang.engine.vaadin;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.UI;

/**
 * 
 * 分布导航---
 * 
 * @author yanxt
 */
public class DiscoveryNavigator extends Navigator {

	private static final long serialVersionUID = 3654482391251371981L;
	private static Logger logger = LoggerFactory.getLogger(DiscoveryNavigator.class);
    private WebApplicationContext applicationContext;
    //所有的视图类
    private static final List<DiscoveryClass> views = new CopyOnWriteArrayList<DiscoveryClass>();
    
	/**
	 * 分布处理类
	 * @author yanxt
	 *
	 */
    private static class DiscoveryClass {
        private String viewName;
        private Class<? extends View> clazz;

        private DiscoveryClass(String viewName, Class<? extends View> clazz) {
            this.viewName = viewName;
            this.clazz = clazz;
        }

        public String getViewName() {
            return viewName;
        }

        public void setViewName(String viewName) {
            this.viewName = viewName;
        }

        public Class<? extends View> getClazz() {
            return clazz;
        }

        public void setClazz(Class<? extends View> clazz) {
            this.clazz = clazz;
        }

        @Override
        public String toString() {
            return "DiscoveryClass{" +
                    "viewName='" + this.getViewName() + '\'' +
                    ", clazz=" + this.getClazz() +
                    '}';
        }
    }


    static {
        logger.debug("discovery views:");
        try {
        	//扫描视图类
            ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
            
            //视图过滤
            scanner.addIncludeFilter(new AnnotationTypeFilter(VaadinView.class));
            
            //找到候选的components
            Set<BeanDefinition> beans = scanner.findCandidateComponents("");
            for (BeanDefinition bean : beans) {
                Class<? extends View> clazz =
                		(Class<? extends View>) Class.forName(bean.getBeanClassName());
                VaadinView vaadinView = (VaadinView) clazz.getAnnotation(VaadinView.class);

                DiscoveryClass discoveryClass = new DiscoveryClass(vaadinView.value(), clazz);
                views.add(discoveryClass);
                logger.debug("found \"{}\" for \"{}\"", new Object[]{vaadinView.value(), bean.getBeanClassName()});
            }
        } catch (ClassNotFoundException e) {
            logger.error("Error loading: {}", e);
        }
    }

    public DiscoveryNavigator(
    		WebApplicationContext applicationContext, 
    		UI ui,
    		ViewDisplay display) {
        this(applicationContext, ui, display, true);
    }

    public DiscoveryNavigator(
    		WebApplicationContext applicationContext,
    		UI ui,
    		ViewDisplay display,
    		boolean discoveryViews) {
        
    	super(ui, display);
        this.applicationContext = applicationContext;

        if (discoveryViews) {
            discoveryViews("");
        }
    }

    /**
     * 分配视图
     * @param basePackage
     * @param excludePackages
     */
    public void discoveryViews(String basePackage, String[] excludePackages) {
        for (DiscoveryClass discoveryClass : views) {
            String viewName = discoveryClass.viewName;
            Class<? extends View> clazz = discoveryClass.getClazz();
            
            String packageName = clazz.getPackage().getName();

            if (packageName.startsWith(basePackage)) {
                boolean exclude = false;
                
                for (String excludePackage : excludePackages) {
                    if (packageName.startsWith(excludePackage)) {
                        exclude = true;
                        break;
                    }
                }

                if (!exclude) {
                    addBeanView(viewName, clazz);
                }
            }
        }
    }
    
    /**
     * 分配视图
     * @param basePackage
     */
    public void discoveryViews(String basePackage) {
        discoveryViews(basePackage, new String[]{});
    }

    public void addBeanView(String viewName, Class<? extends View> viewClass) {
        // Check parameters
        if (viewName == null || viewClass == null) {
            throw new IllegalArgumentException("view and viewClass must be non-null");
        }

        removeView(viewName);
        addProvider(new SpringViewProvider(applicationContext, viewName, viewClass));
    }

    @Override
    public void navigateTo(String navigationState) {
        // fix Vaadin
        if (navigationState.startsWith("!")) {
            super.navigateTo(navigationState.substring(1));
        } else {
            super.navigateTo(navigationState);
        }
    }
}

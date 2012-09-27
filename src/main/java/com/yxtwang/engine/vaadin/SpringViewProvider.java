package com.yxtwang.engine.vaadin;

import org.springframework.web.context.WebApplicationContext;

import com.vaadin.navigator.Navigator.ClassBasedViewProvider;
import com.vaadin.navigator.View;

/**
 * spring、vaadin的view支持
 * 
 * @author yanxt
 */
public class SpringViewProvider extends ClassBasedViewProvider {
	private static final long serialVersionUID = 6872397524316019079L;
	private transient WebApplicationContext applicationContext;

    /**
     * Create a new view provider which creates new view instances based on
     * a view class.
     *
     * @param viewName  name of the views to create (not null)
     * @param viewClass class to instantiate when a view is requested (not null)
     */
    public SpringViewProvider(WebApplicationContext applicationContext, 
    		String viewName, Class<? extends View> viewClass) {
        super(viewName, viewClass);
        this.applicationContext = applicationContext;
    }

    @Override
    public View getView(String viewName) {
        if (getViewName().equals(viewName)) {
            return applicationContext.getBean(getViewClass());
        }
        return null;
    }
}

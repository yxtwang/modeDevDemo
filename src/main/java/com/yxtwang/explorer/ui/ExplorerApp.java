package com.yxtwang.explorer.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator.SimpleViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.UI.CleanupListener;
import com.yxtwang.engine.vaadin.DiscoveryNavigator;
import com.yxtwang.explorer.ui.sample.beans.ApplicationCounter;
import com.yxtwang.explorer.ui.sample.beans.SessionCounter;

/**
 * servlet 入口
 * @author yanxt
 */
@Component
@Scope("request")
//@PreserveOnRefresh
@Theme("yxtwang")
public class ExplorerApp extends UI implements CleanupListener {
	private static final long serialVersionUID = -5996738067811290211L;

	@Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private SessionCounter sessionCounter;

    @Autowired
    private ApplicationCounter applicationCounter;
    
    @Override
    protected void init(final VaadinRequest request) {
    	
        //Create ViewDisplay, and set as UI content
        SimpleViewDisplay display = new SimpleViewDisplay();
        setContent(display);
        //setSizeFull();
        
        //reate Navigator, make it control the ViewDisplay
        //Add some Views
        DiscoveryNavigator navigator = new DiscoveryNavigator(applicationContext, UI.getCurrent(), display);
        
        //Navigate to view
        String currentFramgment = UI.getCurrent().getPage().getFragment();
        navigator.navigateTo(currentFramgment);
        
        Notification.show(String.format("Session counter: %d, application counter: %d", sessionCounter.getCount(), applicationCounter.getCount()));
        System.out.print("" + this);
        
  
    }

	@Override
	public void cleanup(CleanupEvent event) {
		// TODO Auto-generated method stub
		
	}
    
}

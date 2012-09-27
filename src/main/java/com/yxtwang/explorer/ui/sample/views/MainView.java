package com.yxtwang.explorer.ui.sample.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.yxtwang.engine.vaadin.VaadinView;
import com.yxtwang.explorer.ui.login.LoginPage;
import com.yxtwang.explorer.ui.sample.components.SimpleForm;

/**
 * @author yanxt
 */
@Component
@Scope("request")
@VaadinView(MainView.NAME)
public class MainView extends Panel implements View {
    public static final String NAME = "";

    @Autowired
    private SimpleForm form;

    @PostConstruct
    public void PostConstruct() {
        setSizeFull();
        
        //addComponent(form);
        addComponent(new LoginPage());
        
        addComponent(new Link("Go to the Label View111", new ExternalResource("#!" + LabelView.NAME)));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {
    }
}

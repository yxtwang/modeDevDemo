package com.yxtwang.explorer.ui.sample.views;

import javax.annotation.PostConstruct;

import org.springframework.asm.Label;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.yxtwang.engine.vaadin.VaadinView;

@Component
@Scope("request")
@VaadinView(TestView.NAME)
public class TestView extends Panel implements View {
	   public static final String NAME = "test";
	@PostConstruct
    public void PostConstruct() {
        setSizeFull();
        
        //addComponent(form);
//        addComponent(new Link("1111111111111111"));
        
        addComponent(new Link("Go to the Label Viewdsfsfsffd----------------f", new ExternalResource("#!" + LabelView.NAME)));
    }
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}

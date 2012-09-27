package com.yxtwang.explorer.ui.sample.views;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.yxtwang.engine.vaadin.VaadinView;

/**
 * @author xpoft
 */
@Component
@Scope("request")
@VaadinView(LabelView.NAME)
public class LabelView extends Panel implements View
{
    public static final String NAME = "label";

    @PostConstruct
    public void PostConstruct() {
        setSizeFull();

        addComponent(new Label("It's a label!"));
        addComponent(new Button("Go back", new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                Page.getCurrent().setFragment("!" + MainView.NAME);
            }
        }));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {
    }
}

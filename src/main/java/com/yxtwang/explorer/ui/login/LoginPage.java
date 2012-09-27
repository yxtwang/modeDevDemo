package com.yxtwang.explorer.ui.login;

import java.io.IOException;
import java.io.InputStream;

import com.vaadin.ui.CustomLayout;
import com.yxtwang.explorer.ui.mainlayout.ExplorerLayout;


/**
 * @author yanxt
 */
public class LoginPage extends CustomLayout {
  
  private static final long serialVersionUID = 1L;

  public LoginPage() {
    super();  
    
    // Check if the login HTML is available on the classpath. If present, the activiti-theme files are
    // inside a jar and should be loaded from here to be added as resource in UIDL, since the layout html 
    // is not present in a webapp-folder. If not found, just use the default way of defining the template, by name.
    InputStream loginHtmlStream = getClass().getResourceAsStream("/VAADIN/themes/" + ExplorerLayout.THEME + "/layouts/" 
            + ExplorerLayout.CUSTOM_LAYOUT_LOGIN + ".html");
    if(loginHtmlStream != null) {
      try {
        initTemplateContentsFromInputStream(loginHtmlStream);
      } catch (IOException e) {
        throw new RuntimeException("Error while loading login page template from classpath resource", e);
      }
    } else {
      setTemplateName(ExplorerLayout.CUSTOM_LAYOUT_LOGIN);
    }
    
    addStyleName(ExplorerLayout.STYLE_LOGIN_PAGE);
    initUi();
  }
  
  protected void initUi() {
    // Login form is an a-typical Vaadin component, since we want browsers to fill the password fields
    // which is not the case for ajax-generated UI components
    ExplorerLoginForm loginForm = new ExplorerLoginForm();
    addComponent(loginForm, ExplorerLayout.LOCATION_LOGIN);
    
    // Login listener
//    loginForm.addListener(new ActivitiLoginListener());
  }
  
  protected void refreshUi() {
    // Quick and dirty 'refresh'
    removeAllComponents();
    initUi();
  }
}

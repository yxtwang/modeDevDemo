package com.yxtwang.explorer.ui.sample.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author xpoft
 */
@Component
public class ApplicationCounter
{
    private int count = 0;

    public int getCount()
    {
        return ++count;
    }
}

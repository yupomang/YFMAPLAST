package com.yondervision.yfmap.controller;

import org.springframework.ui.ModelMap;
import com.yondervision.yfmap.form.AppApi50001Form;
/**
 * 创建Result类，作为Callable接口实现call()方法的返回值类型
 *
 * Created by hadoop on 2016/11/2.
 */
public class Result {
    private String name;
    private String value;
	private AppApi50001Form form;
	private ModelMap modelMap;
	
    public Result(String name, String value,AppApi50001Form form,ModelMap modelMap) {
        this.name = name;
        this.value = value;
        this.form = form;
        this.modelMap = modelMap;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public AppApi50001Form getForm() {
        return form;
    }

    public ModelMap getModelMap() {
        return modelMap;
    }
}

package com.yondervision.yfmap.result.zunyi;

import java.util.List;

import com.yondervision.yfmap.result.TitleInfoBean;

/** 
* @ClassName: AppApi00801Result 
* @Description: TODO
* @author Caozhongyan
* @date Sep 27, 2013 2:56:13 PM   
* 
*/ 
public class AppApi00802Result {
	private AppApi00801Result01 map = null;
	
	private List<TitleInfoBean> list = null;
	
	private List<TitleInfoBean> content = null;	

	public AppApi00801Result01 getMap() {
		return map;
	}

	public void setMap(AppApi00801Result01 map) {
		this.map = map;
	}

	public List<TitleInfoBean> getList() {
		return list;
	}

	public void setList(List<TitleInfoBean> list) {
		this.list = list;
	}

	public List<TitleInfoBean> getContent() {
		return content;
	}

	public void setContent(List<TitleInfoBean> content) {
		this.content = content;
	}
	
	
}



package com.yondervision.yfmap.dao.impl;

import java.util.List;

import com.yondervision.yfmap.dao.CMi008DAO;
import com.yondervision.yfmap.dao.impl.Mi008DAOImpl;
import com.yondervision.yfmap.dto.Mi008;
import com.yondervision.yfmap.dto.Mi008Example;


public class CMi008DAOImpl extends Mi008DAOImpl implements CMi008DAO {

	/**
	 * 查询加行级锁
	 * @param example
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Mi008> selectByExampleForUpdate(Mi008Example example){
        List<Mi008> list = getSqlMapClientTemplate().queryForList("CMI008.abatorgenerated_selectByExample", example);
        return list;
	}

}

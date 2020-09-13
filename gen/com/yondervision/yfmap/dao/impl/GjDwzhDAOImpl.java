package com.yondervision.yfmap.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.yondervision.yfmap.dao.GjDwzhDAO;
import com.yondervision.yfmap.dto.GjDwzh;
import com.yondervision.yfmap.dto.GjDwzhExample;

public class GjDwzhDAOImpl extends SqlMapClientDaoSupport implements GjDwzhDAO {

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwzh
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public GjDwzhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwzh
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public void insert(GjDwzh record) {
    	getSqlMapClientTemplate().insert("gj_dwzh.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwzh
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public List selectByExample(GjDwzhExample example) {
        List list = getSqlMapClientTemplate().queryForList("gj_dwzh.abatorgenerated_selectByExample", example);
        return list;
    }
    
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwzh
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public int deleteByExample(GjDwzhExample example) {
        int rows = getSqlMapClientTemplate().delete("gj_dwzh.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwzh
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public int countByExample(GjDwzhExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("gj_dwzh.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwzh
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public int updateByExampleSelective(GjDwzh record, GjDwzhExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("gj_dwzh.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwzh
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public int updateByExample(GjDwzh record, GjDwzhExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("gj_dwzh.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table gj_dwzh
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    private static class UpdateByExampleParms extends GjDwzhExample {
        private Object record;

        public UpdateByExampleParms(Object record, GjDwzhExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
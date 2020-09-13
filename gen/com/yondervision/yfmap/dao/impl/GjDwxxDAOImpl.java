package com.yondervision.yfmap.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.yondervision.yfmap.dao.GjDwxxDAO;
import com.yondervision.yfmap.dto.GjDwxx;
import com.yondervision.yfmap.dto.GjDwxxExample;

public class GjDwxxDAOImpl extends SqlMapClientDaoSupport implements GjDwxxDAO {

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwxx
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public GjDwxxDAOImpl() {
        super();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwxx
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public void insert(GjDwxx record) {
    	getSqlMapClientTemplate().insert("gj_dwxx.abatorgenerated_insert", record);
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwxx
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public List selectByExample(GjDwxxExample example) {
        List list = getSqlMapClientTemplate().queryForList("gj_dwxx.abatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwxx
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public int deleteByExample(GjDwxxExample example) {
        int rows = getSqlMapClientTemplate().delete("gj_dwxx.abatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwxx
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public int countByExample(GjDwxxExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("gj_dwxx.abatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwxx
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public int updateByExampleSelective(GjDwxx record, GjDwxxExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("gj_dwxx.abatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table gj_dwxx
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    public int updateByExample(GjDwxx record, GjDwxxExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("gj_dwxx.abatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by Abator for iBATIS.
     * This class corresponds to the database table gj_dwxx
     *
     * @abatorgenerated Thu Sep 25 15:08:44 CST 2014
     */
    private static class UpdateByExampleParms extends GjDwxxExample {
        private Object record;

        public UpdateByExampleParms(Object record, GjDwxxExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
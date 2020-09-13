package com.yondervision.yfmap.dao;
import java.util.List;

import com.yondervision.yfmap.dto.Mi126;
import com.yondervision.yfmap.dto.Mi126Example;

public interface Mi126DAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI126
     *
     * @abatorgenerated Tue Jun 09 16:38:20 CST 2015
     */
    void insert(Mi126 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI126
     *
     * @abatorgenerated Tue Jun 09 16:38:20 CST 2015
     */
    int updateByPrimaryKey(Mi126 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI126
     *
     * @abatorgenerated Tue Jun 09 16:38:20 CST 2015
     */
    int updateByPrimaryKeySelective(Mi126 record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI126
     *
     * @abatorgenerated Tue Jun 09 16:38:20 CST 2015
     */
    List selectByExample(Mi126Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI126
     *
     * @abatorgenerated Tue Jun 09 16:38:20 CST 2015
     */
    Mi126 selectByPrimaryKey(Integer seqno);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI126
     *
     * @abatorgenerated Tue Jun 09 16:38:20 CST 2015
     */
    int deleteByExample(Mi126Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI126
     *
     * @abatorgenerated Tue Jun 09 16:38:20 CST 2015
     */
    int deleteByPrimaryKey(Integer seqno);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI126
     *
     * @abatorgenerated Tue Jun 09 16:38:20 CST 2015
     */
    int countByExample(Mi126Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI126
     *
     * @abatorgenerated Tue Jun 09 16:38:20 CST 2015
     */
    int updateByExampleSelective(Mi126 record, Mi126Example example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table MI126
     *
     * @abatorgenerated Tue Jun 09 16:38:20 CST 2015
     */
    int updateByExample(Mi126 record, Mi126Example example);
}
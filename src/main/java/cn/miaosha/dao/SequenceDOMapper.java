package cn.miaosha.dao;

import cn.miaosha.dataobject.SequenceDO;

public interface SequenceDOMapper {
    //mask-fish
    SequenceDO getSequenceByName(String name);

    //generator
    int deleteByPrimaryKey(String name);

    int insert(SequenceDO record);

    int insertSelective(SequenceDO record);

    SequenceDO selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(SequenceDO record);

    int updateByPrimaryKey(SequenceDO record);
}
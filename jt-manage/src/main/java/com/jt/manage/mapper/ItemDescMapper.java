package com.jt.manage.mapper;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.ItemDesc;

public interface ItemDescMapper extends SysMapper<ItemDesc>{
	void deleteByIds(@Param("ids") Long[] ids);
	
	void updateById(ItemDesc itemDesc);
}

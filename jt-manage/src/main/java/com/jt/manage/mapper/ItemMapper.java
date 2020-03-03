package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.Item;

public interface ItemMapper extends SysMapper<Item>{
	
	/**
	 * 获取商品分页信息
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	List<Item> findItemPage(@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);
	
	/**
	 * 获取商品记录数
	 * @return
	 */
	Integer getItemCount();
	
	/**
	 * 查询所有的商品记录
	 * @return
	 */
	List<Item> findAll();
	
	Integer updateStatus(@Param("ids") Long[] ids,@Param("status") Integer status);
}

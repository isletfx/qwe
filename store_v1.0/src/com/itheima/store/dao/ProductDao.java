package com.itheima.store.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.domain.Product;

public interface ProductDao {

	List<Product> findByNew() throws SQLException;

	List<Product> findByHot() throws SQLException;

	Integer findCountByCid(String cid) throws SQLException;

	List<Product> findPageByCid(String cid, int begin, Integer pageSize) throws SQLException;

	Product findByPid(String pid) throws SQLException;

	Integer findCount() throws SQLException;

	List<Product> findByPage(Integer begin, Integer pageSize) throws SQLException;

	void save(Product product) throws SQLException;

	void update(Product product) throws SQLException;

	List<Product> findByPushDown() throws SQLException;

}

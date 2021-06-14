package com.itheima.store.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	
	private JDBCUtils() {}
	
	private static final ComboPooledDataSource  DATA_SOURCE = new ComboPooledDataSource();
	private static Connection con;
	
	static {
		/*try {
			con = DATA_SOURCE.getConnection();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}*/
	}
	
	public static Connection getConnection() {
		try {
			con = DATA_SOURCE.getConnection();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return con;
	}
	
	public static DataSource getDataSource() {
		return DATA_SOURCE;
	}
}

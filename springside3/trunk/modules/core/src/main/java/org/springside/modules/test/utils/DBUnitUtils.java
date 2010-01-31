package org.springside.modules.test.utils;

import java.io.InputStream;

import javax.sql.DataSource;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.operation.DatabaseOperation;

public class DBUnitUtils {
	


	//-- DBUnit 初始化数据函数 --//
	public static void loadDbUnitData(DataSource dataSource,InputStream dataStream) throws Exception {
		IDatabaseConnection connection = new H2Connection(dataSource.getConnection(), "");
		IDataSet dataSet = new FlatXmlDataSetBuilder().setColumnSensing(true).build(dataStream);
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
}

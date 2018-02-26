package org.apache.hadoop.hive.ql;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.api.FieldSchema;
import org.apache.hadoop.hive.metastore.api.Schema;
import org.apache.hadoop.hive.ql.processors.CommandProcessorResponse;
import org.apache.hadoop.hive.ql.session.SessionState;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDriver {

    @Test
    public void TestGetSchemaFieldsHasNullComments() throws CommandNeedRetryException, IOException {
        HiveConf conf = new HiveConf();
        conf.set("fs.default.name", "file:///");
        conf.setBoolVar(HiveConf.ConfVars.HIVE_SUPPORT_CONCURRENCY, false);
        conf.setVar(HiveConf.ConfVars.HIVEMAPREDMODE, "nonstrict");
        conf.setVar(HiveConf.ConfVars.HIVE_AUTHORIZATION_MANAGER,
                "org.apache.hadoop.hive.ql.security.authorization.plugin.sqlstd.SQLStdHiveAuthorizerFactory");
        SessionState.start(conf);

        Driver driver = new Driver();
        CommandProcessorResponse response;
        final String externalTable = "Test_Dup_Partions_" + System.currentTimeMillis();

        driver.init();
        response = driver.run("drop table if exists " + externalTable);
        Assert.assertEquals(0, response.getResponseCode());

        driver.init();
        response = driver.run("create external table IF NOT EXISTS " + externalTable +
                " (EmployeeID Int,FirstName String,Designation String,Salary Int,Department String)" +
                " row format delimited fields terminated by \",\"\n");
        Assert.assertEquals(0, response.getResponseCode());

        driver.init();
        response = driver.run("load data local inpath '../data/files/empl_dupl.txt' into table "  + externalTable);
        Assert.assertEquals(0, response.getResponseCode());

        driver.init();
        response = driver.run("select * from " + externalTable);
        Assert.assertEquals(0, response.getResponseCode());

        List<Object> externalTableItemsList = new ArrayList<>();
        driver.getResults(externalTableItemsList);
        Assert.assertEquals(20, externalTableItemsList.size());

        driver.init();
        response = driver.run("drop table IF EXISTS Test_Parti");
        Assert.assertEquals(0, response.getResponseCode());

        driver.init();
        response = driver.run("create  table Test_Parti \n" +
                "(EmployeeID Int COMMENT '---------------THIS IS A COMMENT---------------',\n" +
                "FirstName String COMMENT '---------------THIS IS A COMMENT---------------',\n" +
                "Designation  String COMMENT '---------------THIS IS A COMMENT---------------',\n" +
                "Salary Int COMMENT '---------------THIS IS A COMMENT---------------') \n" +
                "PARTITIONED BY (Department String) row format delimited fields terminated by \",\"");
        Assert.assertEquals(0, response.getResponseCode());

        driver.init();
        response = driver.run("INSERT INTO TABLE Test_Parti PARTITION(department='A') \n" +
                "SELECT EmployeeID, FirstName,Designation,Salary FROM " + externalTable + " WHERE department='A'");
        Assert.assertEquals(0, response.getResponseCode());

        driver.init();
        response = driver.run("INSERT INTO TABLE Test_Parti PARTITION (department='B') \n" +
                "SELECT EmployeeID, FirstName,Designation,Salary FROM " + externalTable + " WHERE department='B'");
        Assert.assertEquals(0, response.getResponseCode());

        driver.init();
        response = driver.run("INSERT INTO TABLE Test_Parti PARTITION (department='C') \n" +
                "SELECT EmployeeID, FirstName,Designation,Salary FROM " + externalTable + " WHERE department='C'");
        Assert.assertEquals(0, response.getResponseCode());

        driver.init();
        response = driver.run("SELECT * FROM test_parti where employeeid < 10 AND department = 'A' order by employeeid");
        Assert.assertEquals(0, response.getResponseCode());

        List<Object> departmentAEmployees = new ArrayList<>();
        driver.getResults(departmentAEmployees);
        Assert.assertEquals(4, departmentAEmployees.size());

        Schema schema = driver.getSchema();
        Assert.assertNotNull(schema);
        Assert.assertEquals(5, schema.getFieldSchemas().size());
        for (FieldSchema field : schema.getFieldSchemas()) {
            Assert.assertNull(field.getComment());
        }
        driver.close();
    }
}

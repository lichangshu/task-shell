var ioc = {
    dataSource : {
        type : "org.apache.commons.dbcp.BasicDataSource",
        fields : {
            driverClassName : "com.mysql.jdbc.Driver",
            url : "jdbc:mysql://localhost:3306/task-shell?autoReconnect=true&characterEncoding=UTF-8",
            username : "root",
            password : "",
            testOnBorrow: false,
            testWhileIdle:true,
            validationQuery: "select 1",
            timeBetweenEvictionRunsMillis:3600000,
            maxWait: 10000 // 若不配置此项,如果数据库未启动,druid会一直等可用连接,卡住启动过程
        }
    },
    spiderDataSource : {
        type : "org.apache.commons.dbcp.BasicDataSource",
        fields : {
            driverClassName : "com.mysql.jdbc.Driver",
            url : "jdbc:mysql://192.168.1.3:3306/tv_app_spider?autoReconnect=true&characterEncoding=UTF-8",
            username : "root",
            password : "",
            testOnBorrow: false,
            testWhileIdle:true,
            validationQuery: "select 1",
            timeBetweenEvictionRunsMillis:3600000,
            maxWait: 10000 // 若不配置此项,如果数据库未启动,druid会一直等可用连接,卡住启动过程
        }
    }
}
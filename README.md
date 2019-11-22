# 这是一个代码补充

> 补全 `easymodbus4j` 部分依赖代码

* 原github 仓库地址 ： https://github.com/zengfr/easymodbus4j

* 部分依赖对应关系：


```$xslt
<dependency>
    <artifactId>easymodbus4j-client</artifactId>
    <groupId>com.github.zengfr</groupId>
    <version>0.0.5</version>
</dependency>

--> modbus-client
------------------------------
<dependency>
    <artifactId>easymodbus4j-server</artifactId>
    <groupId>com.github.zengfr</groupId>
    <version>0.0.5</version>
</dependency>
--> modbus-server
------------------------------
<dependency>
    <artifactId>easymodbus4j-extension</artifactId>
    <groupId>com.github.zengfr</groupId>
    <version>0.0.5</version>
</dependency>
--> modbus-extension

```
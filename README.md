# ssm_case

---

---

# SSM整合案例

## 1. 环境准备

### 1.1 数据库与表结构

#### 1.1.1 创建用户与授权

项目使用Oracle数据库

Oracle 为每个项目创建单独的user, Oracle数据表存放在表空间下, 每个用户有独立表空间

**创建用户及密码:**

```sql
create user 用户名 identified by 口令;
```

**用户授权:**

```sql
grant connect, resource to 用户名;
```

**实例代码:**

```sql
--创建用户, 分配表空间
create user C##SSM
    identified by Hhn004460
    default tablespace USERS
    temporary tablespace TEMP
    profile DEFAULT;

--授予权限
grant connect to C##SSM;
grant connect to C##SSM;
grant create table to C##SSM;

--表空间大小设置
grant unlimited tablespace to C##SSM;
```

---

#### 1.1.2 创建产品表

`product`

| 序号 |   字段名称    |   字段类型    |       字段描述        |
| :--: | :-----------: | :-----------: | :-------------------: |
|  1   |      id       | varchar2(32)  |   无意义,主键`uuid`   |
|  2   |  productNum   | varchar2(50)  | 产品编号,唯一,不为空  |
|  3   |  productName  | varchar2(50)  |  产品名称(线路名称)   |
|  4   |   cityName    | varchar2(50)  |       出发城市        |
|  5   | DepartureTime |   timestamp   |       出发时间        |
|  6   | productPrice  |    number     |       产品价格        |
|  7   |  productDesc  | varchar2(500) |       产品描述        |
|  8   | productStatus |      int      | 状态(0 关闭 / 1 开启) |

**创建产品表sql:**

```sql
create table product
(
    id            varchar2(32) default sys_guid() primary key,
    productNum    varchar2(50) not null,
    productName   varchar2(50),
    cityName      varchar2(50),
    DepartureTime timestamp,
    productPrice  number,
    productDesc   varchar2(500),
    productStatus int,
    constraint product unique (id, productNum)
);
```

插入测试数据sql:

```sql
--插入数据
insert into PRODUCT (id, productnum, productname, cityname, departuretime, productprice,
                     productdesc, productstatus)
values ('676C5BD1D35E429A8C2E114939C5685A', 'itcast-002', '北京三日游', '北京', to_timestamp('10-
10-2018 10:10:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1200, '不错的旅行', 1);
insert into PRODUCT (id, productnum, productname, cityname, departuretime, productprice,
                     productdesc, productstatus)
values ('12B7ABF2A4C544568B0A7C69F36BF8B7', 'itcast-003', '上海五日游', '上海', to_timestamp('25-
04-2018 14:30:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1800, '魔都我来了', 0);
insert into PRODUCT (id, productnum, productname, cityname, departuretime, productprice,
                     productdesc, productstatus)
values ('9F71F01CB448476DAFB309AA6DF9497F', 'itcast-001', '北京三日游', '北京', to_timestamp('10-
10-2018 10:10:00.000000', 'dd-mm-yyyy hh24:mi:ss.ff'), 1200, '不错的旅行', 1);
commit ;
```

---

#### 1.1.3 创建会员表

`member`

| 序号 | 字段名称 |   字段类型   |     字段描述     |
| :--: | :------: | :----------: | :--------------: |
|  1   |    id    | varchar2(32) | 无意义, 主键uuid |
|  2   |   name   | varchar2(20) |       姓名       |
|  3   | nikeName | varchar2(20) |       昵称       |
|  4   | phoneNum | varchar2(20) |     电话号码     |
|  5   |  email   | varchar2(50) |       邮箱       |

**创建表sql:**

```sql
--会员表创建
CREATE TABLE member
(
    id       varchar2(32) default SYS_GUID() PRIMARY KEY,
    NAME     VARCHAR2(20),
    nickname VARCHAR2(20),
    phoneNum VARCHAR2(20),
    email    VARCHAR2(20)
);
```

插入测试数据sql:

```sql
--插入数据
insert into MEMBER (id, name, nickname, phonenum, email)
values ('E61D65F673D54F68B0861025C69773DB', '张三', '小三', '18888888888', 'zs@163.com');
commit ;
```

---

#### 1.1.4 创建订单表

`orders`

| 序号 |  字段名称   |   字段类型    |              字段描述               |
| :--: | :---------: | :-----------: | :---------------------------------: |
|  1   |     id      | varchar2(32)  |           无意义,主键uuid           |
|  2   |  orderNum   | varchar2(50)  |       订单编号, 不为空, 唯一        |
|  3   |  orderTime  |   timestamp   |              下单时间               |
|  4   | perpleCount |      int      |              出行人数               |
|  5   |  orderDesc  | varchar2(500) |         订单描述(其他信息)          |
|  6   |   payType   |      int      | 支付方式(0 支付宝/ 1 微信 / 2 其他) |
|  7   | orderStatus |      int      |    订单状态(0 未支付/ 1 已支付)     |
|  8   |  productld  |      int      |             产品id外键              |
|  9   |  memberid   |      int      |        会员(联系人) id 外键         |

**创建表sql:**

```sql
--创建订单表
CREATE TABLE orders
(
    id          varchar2(32) default SYS_GUID() PRIMARY KEY,
    orderNum    VARCHAR2(20) NOT NULL UNIQUE,
    orderTime   timestamp,
    peopleCount INT,
    orderDesc   VARCHAR2(500),
    payType     INT,
    orderStatus INT,
    productId   varchar2(32),
    memberId    varchar2(32),
    FOREIGN KEY (productId) REFERENCES product (id),
    FOREIGN KEY (memberId) REFERENCES member (id)
);
```

插入测试数据:

```sql
--插入测试数据
insert into ORDERS (id, ordernum, ordertime, peoplecount, orderdesc, paytype, orderstatus,
productid, memberid)
values ('0E7231DC797C486290E8713CA3C6ECCC', '12345', to_timestamp('02-03-2018 12:00:00.000000','dd-mm-yyyy hh24:mi:ss.ff'), 2, '没什么', 0, 1, '676C5BD1D35E429A8C2E114939C5685A',
'E61D65F673D54F68B0861025C69773DB');
insert into ORDERS (id, ordernum, ordertime, peoplecount, orderdesc, paytype, orderstatus,
productid, memberid)
values ('5DC6A48DD4E94592AE904930EA866AFA', '54321', to_timestamp('02-03-2018 12:00:00.000000',
'dd-mm-yyyy hh24:mi:ss.ff'), 2, '没什么', 0, 1, '676C5BD1D35E429A8C2E114939C5685A',
'E61D65F673D54F68B0861025C69773DB');
insert into ORDERS (id, ordernum, ordertime, peoplecount, orderdesc, paytype, orderstatus,
productid, memberid)
values ('2FF351C4AC744E2092DCF08CFD314420', '67890', to_timestamp('02-03-2018 12:00:00.000000',
'dd-mm-yyyy hh24:mi:ss.ff'), 2, '没什么', 0, 1, '12B7ABF2A4C544568B0A7C69F36BF8B7',
'E61D65F673D54F68B0861025C69773DB');
insert into ORDERS (id, ordernum, ordertime, peoplecount, orderdesc, paytype, orderstatus,
productid, memberid)
values ('A0657832D93E4B10AE88A2D4B70B1A28', '98765', to_timestamp('02-03-2018 12:00:00.000000',
'dd-mm-yyyy hh24:mi:ss.ff'), 2, '没什么', 0, 1, '12B7ABF2A4C544568B0A7C69F36BF8B7',
'E61D65F673D54F68B0861025C69773DB');
insert into ORDERS (id, ordernum, ordertime, peoplecount, orderdesc, paytype, orderstatus,
productid, memberid)
values ('E4DD4C45EED84870ABA83574A801083E', '11111', to_timestamp('02-03-2018 12:00:00.000000',
'dd-mm-yyyy hh24:mi:ss.ff'), 2, '没什么', 0, 1, '12B7ABF2A4C544568B0A7C69F36BF8B7',
'E61D65F673D54F68B0861025C69773DB');
insert into ORDERS (id, ordernum, ordertime, peoplecount, orderdesc, paytype, orderstatus,
productid, memberid)
values ('96CC8BD43C734CC2ACBFF09501B4DD5D', '22222', to_timestamp('02-03-2018 12:00:00.000000',
'dd-mm-yyyy hh24:mi:ss.ff'), 2, '没什么', 0, 1, '12B7ABF2A4C544568B0A7C69F36BF8B7',
'E61D65F673D54F68B0861025C69773DB');
insert into ORDERS (id, ordernum, ordertime, peoplecount, orderdesc, paytype, orderstatus,
productid, memberid)
values ('55F9AF582D5A4DB28FB4EC3199385762', '33333', to_timestamp('02-03-2018 12:00:00.000000',
'dd-mm-yyyy hh24:mi:ss.ff'), 2, '没什么', 0, 1, '9F71F01CB448476DAFB309AA6DF9497F',
'E61D65F673D54F68B0861025C69773DB');
insert into ORDERS (id, ordernum, ordertime, peoplecount, orderdesc, paytype, orderstatus,
productid, memberid)
values ('CA005CF1BE3C4EF68F88ABC7DF30E976', '44444', to_timestamp('02-03-2018 12:00:00.000000',
'dd-mm-yyyy hh24:mi:ss.ff'), 2, '没什么', 0, 1, '9F71F01CB448476DAFB309AA6DF9497F',
'E61D65F673D54F68B0861025C69773DB');
insert into ORDERS (id, ordernum, ordertime, peoplecount, orderdesc, paytype, orderstatus,
productid, memberid)
values ('3081770BC3984EF092D9E99760FDABDE', '55555', to_timestamp('02-03-2018 12:00:00.000000',
'dd-mm-yyyy hh24:mi:ss.ff'), 2, '没什么', 0, 1, '9F71F01CB448476DAFB309AA6DF9497F',
'E61D65F673D54F68B0861025C69773DB');
commit ;
```

---

#### 1.1.5 创建旅客表

`traveller`

| 序号 |    字段名称     |   字段类型    |              字段描述              |
| :--: | :-------------: | :-----------: | :--------------------------------: |
|  1   |       id        | varhcar2(32)  |          无意义, 主键uuid          |
|  2   |      name       | varchar2(20)  |                姓名                |
|  3   |       sex       | varhchar2(20) |                姓名                |
|  4   |    phoneNum     | varhchar2(20) |              电话号码              |
|  5   | credentialsType |      int      | 证件类型(0 身份证/ 1 护照/ 2 其他) |
|  6   | credentialsNum  | varchar2(50)  |              证件号码              |
|  7   |  travellerType  |      int      |   旅客类型(人群) 0 成人/ 1 儿童    |

**创建表sql:**

```sql
--创建旅客表
CREATE TABLE traveller
(
    id              varchar2(32) default SYS_GUID() PRIMARY KEY,
    NAME            VARCHAR2(20),
    sex             VARCHAR2(20),
    phoneNum        VARCHAR2(20),
    credentialsType INT,
    credentialsNum  VARCHAR2(50),
    travellerType   INT
);
```

插入测试数据:

```sql
--插入测试数据
insert into TRAVELLER (id, name, sex, phonenum, credentialstype, credentialsnum, travellertype)
values ('3FE27DF2A4E44A6DBC5D0FE4651D3D3E', '张龙', '男', '13333333333', 0,
        '123456789009876543', 0);
insert into TRAVELLER (id, name, sex, phonenum, credentialstype, credentialsnum, travellertype)
values ('EE7A71FB6945483FBF91543DBE851960', '张小龙', '男', '15555555555', 0,
        '987654321123456789', 1);
commit;
```

---

#### 1.1.6 旅客与订单中间表

`order_traveller`

| 序号 |  字段名称   |   字段类型   | 字段描述 |
| :--: | :---------: | :----------: | :------: |
|  1   |   orderId   | varchar2(32) |  订单id  |
|  2   | travellerId | varchar2(32) |  旅客id  |

**创建表sql:**

```sql
--旅客与订单中间表
CREATE TABLE order_traveller
(
    orderId     varchar2(32),
    travellerId varchar2(32),
    PRIMARY KEY (orderId, travellerId),
    FOREIGN KEY (orderId) REFERENCES orders (id),
    FOREIGN KEY (travellerId) REFERENCES traveller (id)
);
```

插入测试数据:

```sql
--插入测试数据
insert into ORDER_TRAVELLER (orderid, travellerid)
values ('0E7231DC797C486290E8713CA3C6ECCC', '3FE27DF2A4E44A6DBC5D0FE4651D3D3E');
insert into ORDER_TRAVELLER (orderid, travellerid)
values ('2FF351C4AC744E2092DCF08CFD314420', '3FE27DF2A4E44A6DBC5D0FE4651D3D3E');
insert into ORDER_TRAVELLER (orderid, travellerid)
values ('3081770BC3984EF092D9E99760FDABDE', 'EE7A71FB6945483FBF91543DBE851960');
insert into ORDER_TRAVELLER (orderid, travellerid)
values ('55F9AF582D5A4DB28FB4EC3199385762', 'EE7A71FB6945483FBF91543DBE851960');
insert into ORDER_TRAVELLER (orderid, travellerid)
values ('5DC6A48DD4E94592AE904930EA866AFA', '3FE27DF2A4E44A6DBC5D0FE4651D3D3E');
insert into ORDER_TRAVELLER (orderid, travellerid)
values ('96CC8BD43C734CC2ACBFF09501B4DD5D', 'EE7A71FB6945483FBF91543DBE851960');
insert into ORDER_TRAVELLER (orderid, travellerid)
values ('A0657832D93E4B10AE88A2D4B70B1A28', '3FE27DF2A4E44A6DBC5D0FE4651D3D3E');
insert into ORDER_TRAVELLER (orderid, travellerid)
values ('CA005CF1BE3C4EF68F88ABC7DF30E976', 'EE7A71FB6945483FBF91543DBE851960');
insert into ORDER_TRAVELLER (orderid, travellerid)
values ('E4DD4C45EED84870ABA83574A801083E', 'EE7A71FB6945483FBF91543DBE851960');
commit ;
```

---

### 1.2 权限操作表

#### 1.2.1 用户表

`users`

| 序号 | 字段名称 | 字段类型 |        字段描述        |
| :--: | :------: | :------: | :--------------------: |
|  1   |    id    | varchar2 |    无意义, 主键uuid    |
|  2   |  email   | varchar2 |       非空, 唯一       |
|  3   | username | varchar2 |         用户名         |
|  4   | password | varchar2 |       密码(加密)       |
|  5   | phoneNum | varcahr2 |          电话          |
|  7   |  status  |   int    | 状态( 0未开启 / 1开启) |

**创建表sql:**

```sql
-- 用户表
CREATE TABLE users
(
    id       varchar2(32) default SYS_GUID() PRIMARY KEY,
    email    VARCHAR2(50) UNIQUE NOT NULL,
    username VARCHAR2(50),
    PASSWORD VARCHAR2(50),
    phoneNum VARCHAR2(20),
    STATUS   INT
);
```

---

#### 1.2.2 角色表

`role`

| 序号 | 字段名称 | 字段类型 |     字段描述     |
| :--: | :------: | :------: | :--------------: |
|  1   |    id    | varchar2 | 无意义, 主键uuid |
|  2   | roleName | varchar2 |      角色名      |
|  3   | roleDesc | varchar2 |     角色描述     |

**创建表sql:**

```sql
-- 角色表
CREATE TABLE role
(
    id       varchar2(32) default SYS_GUID() PRIMARY KEY,
    roleName VARCHAR2(50),
    roleDesc VARCHAR2(50)
);
```

---

#### 1.2.3 用户角色关联表

`users_role`

创建表sql:

```sql
-- 用户角色关联表
CREATE TABLE users_role
(
    userId varchar2(32),
    roleId varchar2(32),
    PRIMARY KEY (userId, roleId),
    FOREIGN KEY (userId) REFERENCES users (id),
    FOREIGN KEY (roleId) REFERENCES role (id)
);
```

---

#### 1.2.4 资源权限表

`permission`

**权限表sql:**

```sql
-- 资源权限表
CREATE TABLE permission
(
    id             varchar2(32) default SYS_GUID() PRIMARY KEY,
    permissionName VARCHAR2(50),
    url            VARCHAR2(50)
);
```

---

#### 1.2.5 角色权限关联表

`role_permission`

**创建表sql:**

```sql
-- 角色权限关联表
CREATE TABLE role_permission
(
    permissionId varchar2(32),
    roleId       varchar2(32),
    PRIMARY KEY (permissionId, roleId),
    FOREIGN KEY (permissionId) REFERENCES permission (id),
    FOREIGN KEY (roleId) REFERENCES role (id)
);
```


---

---

### 1.3 创建工程

#### 1.3.1 创建Maven工程

**项目目录结构:**

```
ssm_case
├─src
│  ├─main
│  │  ├─java
│  │  └─resources
│  └─test
│      └─java
├─ssm_case_dao
├─ssm_case_domain
├─ssm_case_service
├─ssm_case_utils
└─ssm_case_web(web-app骨架)
```

#### 1.3.2 父工程导入坐标

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.ssm</groupId>
    <artifactId>ssm_case</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>ssm_case_dao</module>
        <module>ssm_case_service</module>
        <module>ssm_case_domain</module>
        <module>ssm_case_utils</module>
        <module>ssm_case_web</module>
    </modules>

    <properties>
        <spring.version>5.0.2.RELEASE</spring.version>
        <slf4j.version>1.6.6</slf4j.version>
        <log4j.version>1.2.12</log4j.version>
        <oracle.version>19.7.0.0</oracle.version>
        <mybatis.version>3.4.5</mybatis.version>
        <spring.security.version>5.0.1.RELEASE</spring.security.version>
    </properties>

    <dependencies>

        <!-- spring -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.6.8</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.0</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        <!-- spring end-->


        <!-- log start -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>${log4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <!-- log end -->

        <!-- mybatis start -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>${mybatis.version}</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>1.3.0</version>
        </dependency>
        <!-- mybatis end -->

        <!-- dataSource start-->
        <dependency>
            <groupId>c3p0</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.1.2</version>
            <type>jar</type>
            <scope>compile</scope>
        </dependency>
        <!-- dataSource end -->

        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>5.1.2</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-web</artifactId>
            <version>${spring.security.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-config</artifactId>
            <version>${spring.security.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-core</artifactId>
            <version>${spring.security.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-taglibs</artifactId>
            <version>${spring.security.version}</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.oracle.database.jdbc/ojdbc8 -->
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc8</artifactId>
            <version>${oracle.version}</version>
        </dependency>



        <dependency>
            <groupId>javax.annotation</groupId>
            <artifactId>jsr250-api</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>


    <build>
        <pluginManagement>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.2</version>
                    <configuration>
                        <source>1.8</source>
                        <target>1.8</target>
                        <encoding>UTF-8</encoding>
                        <showWarnings>true</showWarnings>
                    </configuration>
                </plugin>
            </plugins>
        </pluginManagement>
    </build>

</project>
```

## 2. 编码

### 2.1 domain层编写

#### 2.1.1 `Product`

```java
package com.ssm.domain;

import java.util.Date;

/**
 * 产品信息实体类
 * @Author Harlan
 * @Date 2020/9/20
 */
public class Product implement Serializable{
    private String id;
    private String productNum;
    private String productName;
    private String cityName;
    private Date departureTime;
    private String departureTimeStr;
    private double productPrice;
    private String productDesc;
    private Integer productStatus;
    private String productStatusStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureTimeStr() {
        return departureTimeStr;
    }

    public void setDepartureTimeStr(String departureTimeStr) {
        this.departureTimeStr = departureTimeStr;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductStatusStr() {
        return productStatusStr;
    }

    public void setProductStatusStr(String productStatusStr) {
        this.productStatusStr = productStatusStr;
    }
}
```

---

### 2.2 dao层编写

####  2.2.1 `IProductDao`

```java
package com.ssm.dao;

import com.ssm.domain.Product;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/20
 */
public interface IProductDao {

    /**
     * 查询所有产品信息
     * @return list
     * @throws Exception 异常
     */
    @Select("select * from product")
    List<Product> findAll() throws Exception;
}
```

---

### 2.3 service层编写

#### 2.3.1 `IProductService`

```java
package com.ssm.service;

import com.ssm.domain.Product;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/20
 */
public interface IProductService {

    /**
     * 查询所有产品
     * @return list
     * @throws Exception 异常
     */
    List<Product> findAll() throws Exception;
}
```

**实现类:**

```java
package com.ssm.service.impl;

import com.ssm.dao.IProductDao;
import com.ssm.domain.Product;
import com.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/20
 */
@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao dao;

    @Override
    public List<Product> findAll() throws Exception {
        return dao.findAll();
    }
}
```

---

### 2.4 web(Controller)层编写

#### 2.4.1 `ProductController`

```java
package com.ssm.controller;

import com.ssm.domain.Product;
import com.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author Harlan
 * @Date 2020/9/20
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService service;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv = new ModelAndView();
        List<Product> products = service.findAll();
        mv.addObject("productList",products);
        mv.setViewName("product-list");
        return mv;
    }
}
```

---

## 3. 配置文件

#### 3.1 `applicationContext.xml`

**Spring配置文件(整合MyBatis)**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!-- 注解扫描 -->
    <context:component-scan base-package="com.ssm.dao"/>
    <context:component-scan base-package="com.ssm.service.impl"/>

    <!-- 加载连接配置信息 -->
    <context:property-placeholder location="classpath:db.properties"/>

    <!-- 数据库连接池 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.user}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

    <!-- sqlSessionFactory 交给IoC容器管理 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 扫描dao接口 -->
    <bean id="mapperScannerConfigurer" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.ssm.dao"/>
    </bean>

    <!-- 配置Spring的声明事务管理 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <tx:annotation-driven transaction-manager="transactionManager"/>
</beans>
```

#### 3.2 `springmvc.xml`

SpringMVC配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd
       http://www.springframework.org/schema/aop
       https://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 扫描controller注解 -->
    <context:component-scan base-package="com.ssm.controller"/>

    <!-- 配置视图解析器 -->
    <bean id="internalResourceViewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/pages/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!-- 静态资源不过滤 -->
    <mvc:default-servlet-handler/>

    <!-- 开启MVC注解 -->
    <mvc:annotation-driven/>

    <!-- AOP注解支持 -->
    <aop:aspectj-autoproxy proxy-target-class="true"/>
</beans>
```

#### 3.3 `web.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">

  <display-name>Archetype Created Web Application</display-name>

  <!-- 配置加载类路径的配置文件 -->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath*:applicationContext.xml</param-value>
  </context-param>

  <!-- 配置监听器 -->
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  
  <!-- 配置监听器, 监听request域对象的创建和销毁 -->
  <listener>
    <listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
  </listener>
  
  <!-- 配置前端控制器 -->
  <servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>*.do</url-pattern>
  </servlet-mapping>

  <!-- 解决中文乱码问题 -->
  <filter>
    <filter-name>characterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>characterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

  <!-- 指定默认加载页面 -->
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
</web-app>
```

#### 3.4 `db.propreties`

数据库连接信息

```properties
jdbc.driver=oracle.jdbc.OracleDriver
jdbc.url=jdbc:oracle:thin:@192.168.154.141:1521/orcldb
jdbc.user=c##ssm
jdbc.password=Hhn004460
```


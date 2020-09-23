# MyBatis分页插件PageHelper
> PageHelper 是国内非常优秀的一款开源的mybatis分页插件, 它支持基本主流与常用的数据库, 例如mysql, oracle, mariaDB, DB2, SQLite, Hsqldb等
***

GitHub项目地址: [GitHub](https://github.com/pagehelper/Mybatis-PageHelper)
GitOSC项目地址: [GitOSC](http://git.oschina.net/free/Mybatis_PageHelper)
***

## PageHelper使用

### 1. 导入插件方式(两种)

#### 1.1 引入jar包

从下列地址下载最新的jar包:

* [https://oss.sonatype.org/content/repositories/releases/com/github/pagehelper/pagehelper/](https://oss.sonatype.org/content/repositories/releases/com/github/pagehelper/pagehelper/)

* [http://repo1.maven.org/maven2/com/github/pagehelper/pagehelper/](http://repo1.maven.org/maven2/com/github/pagehelper/pagehelper/)

由于使用了sql解析工具, 还需要下载`**jsqlparser.jar`**

* [http://repo1.maven.org/maven2/com/github/jsqlparser/jsqlparser/0.9.5/](http://repo1.maven.org/maven2/com/github/jsqlparser/jsqlparser/0.9.5/)
***

#### 1.2 使用Maven
导入依赖坐标:
```xml
<!-- mybatis分页工具 -->
<dependency>    
    <groupId>com.github.pagehelper</groupId>    
    <artifactId>pagehelper</artifactId>    
    <version>5.1.2</version>
</dependency>
```
***
***
### 2. 配置
在Spring配置文件中配置拦截器插件
```xml
    <!-- sqlSessionFactory 交给IoC容器管理 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!-- 传入PageHelper插件 -->
        <property name="plugins">
            <array>
                <!-- 传入插件对象 -->
                <bean id="pageHelper" class="com.github.pagehelper.PageInterceptor">
                    <property name="properties">
                        <props>
                            <prop key="helperDialect">oracle</prop>
                            <prop key="reasonable">true</prop>
                        </props>
                    </property>
                </bean>
            </array>
        </property>
    </bean>
```
---

**可在 `prop`属性中配置其他信息**

1. `helperDialect` ：分页插件会自动检测当前的数据库链接，自动选择合适的分页方式。 你可以配置 `helperDialect` 属性来指定分页插件使用哪种方言。配置时，可以使用下面的缩写值： `oracle` , `mysql` , `mariadb` , `sqlite` , `hsqldb` , `postgresql `, `db2`,` sqlserver `,` informix `,` h2 `,` sqlserver2012` , `derby`

    **特别注意**：使用 `SqlServer2012` 数据库时，需要手动指定为 `sqlserver2012` ，否则会使用 `SqlServer2005` 的 方式进行分页。 你也可以实现 `AbstractHelperDialect` ，然后配置该属性为实现类的全限定名称即可使用自定义的实现方法。 

2. `offsetAsPageNum` ：默认值为 `false` ，该参数对使用 `RowBounds` 作为分页参数时有效。 当该参数设置为 `true` 时，会将 `RowBounds` 中的 `offset` 参数当成 `pageNum` 使用，可以用页码和页面大小两个参数进行分 页。 

3.  `rowBoundsWithCount` ：默认值为 `false` ，该参数对使用 `RowBounds` 作为分页参数时有效。 当该参数设置 为 `true` 时，使用 `RowBounds` 分页会进行 `count` 查询。 

4. `pageSizeZero` ：默认值为 `false` ，当该参数设置为 `true` 时，如果 `pageSize=0` 或者 `RowBounds.limit = 0` 就会查询出全部的结果（相当于没有执行分页查询，但是返回结果仍然是 `Page` 类型）。 

5. `reasonable` ：分页合理化参数，默认值为 `false` 。当该参数设置为` true` 时，` pageNum<=0` 时会查询第一 页， `pageNum>pages` （超过总数时），会查询最后一页。默认 `false` 时，直接根据参数进行查询。 

6. `params` ：为了支持 `startPage(Object params)` 方法，增加了该参数来配置参数映射，用于从对象中根据属 性名取值， 可以配置 `pageNum`,`pageSize`,`count`,`pageSizeZero`,`reasonable `，不配置映射的用默认值， 默认 值为 `pageNum=pageNum`;`pageSize=pageSize`;`count=countSql`;`reasonable=reasonable`;`pageSizeZero=pageSizeZero `。 

7. `supportMethodsArguments `：支持通过 `Mapper` 接口参数来传递分页参数，默认值 `false` ，分页插件会从查 询方法的参数值中，自动根据上面 `params` 配置的字段中取值，查找到合适的值时就会自动分页。 使用方法 可以参考测试代码中的 `com.github.pagehelper.test.basic` 包下的 `ArgumentsMapTest` 和 `ArgumentsObjTest` 。

8. `autoRuntimeDialect` ：默认值为 `false` 。设置为 `true` 时，允许在运行时根据多数据源自动识别对应方言 的分页 （不支持自动选择 `sqlserver2012` ，只能使用 `sqlserver` ），用法和注意事项参考下面的场景五。 

9. `closeConn` ：默认值为 `true` 。当使用运行时动态数据源或没有设置 `helperDialect` 属性自动获取数据库类 型时，会自动获取一个数据库连接， 通过该属性来设置是否关闭获取的这个连接，默认 `true` 关闭，设置为 `false` 后，不会关闭获取的连接，这个参数的设置要根据自己选择的数据源来决定。

    ---

    ---

### 3. 正式使用

<font color=red>**在真正执行`sql`前, 使用PageHelper来完成分页.**</font>

```java
@Service
@Transactional
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private IOrdersDao dao;

    @Override
    public List<Orders> findAll() throws Exception {
        
        //分页查询 pageNum: 页码值, pageSize: 每页条数
        PageHelper.startPage(1, 5);
        
        return dao.findAll();
    }

}
```

<font color=red>**PageInfo相当于一个PageBean, 在返回视图时需要传入要返回的数据到PageInfo中, 然后直接返回PageInfo**</font>

```java
@RequestMapping("/findAll.do")
public ModelAndView findAll(@RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "size",defaultValue = "12") int size)  throws Exception {
    ModelAndView mv = new ModelAndView();
    //查询出的数据
    List<Orders> ordersList = service.findAll(page, size);
    
    //PageInfo 就是一个分页Bean, 传入需要返回的数据
    PageInfo<Orders> pageInfo = new PageInfo<Orders>(ordersList);
    
    //添加PageInfo到ModelAndView中
    mv.addObject("pageInfo", pageInfo);
    mv.setViewName("orders-list");
    return mv;
}
```
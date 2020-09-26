# Spring Security

Spring Security 是Spring 项目中用来提供安全认证服务的框架.

* 认证: 是为用户建立一个他所声明的主体, 主体一般指用户, 设备或可以在你系统中执行动作的其他系统.
* 授权: 指的是一个用户能否在你的应用中执行某个操作, 在到达授权判断之前, 身份的主体已经由身份验证过程建立了.

这些概念是通用的, 不是Spring Security特有的, 在身份验证层面, Spring Security广泛支持各种身份验证模式.

---

## 入门

### 导入依赖坐标

```xml
<!-- spring-security -->
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
```

---

### web.xml配置

```xml
<!DOCTYPE web-app PUBLIC
 "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN"
 "http://java.sun.com/dtd/web-app_2_3.dtd" >

<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
                             http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         version="2.5">
  <display-name>Archetype Created Web Application</display-name>

  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:spring-security.xml</param-value>
  </context-param>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <filter>
    <filter-name>springSecurityFilterChain</filter-name>
    <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>springSecurityFilterChain</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
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

---

### Spring-Security.xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:security="http://www.springframework.org/schema/security"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd
          http://www.springframework.org/schema/security
          http://www.springframework.org/schema/security/spring-security.xsd">

    <security:http auto-config="true" use-expressions="false">
        <!--
        intercept-url 定义一个过滤规则 pattern表示对哪些url进行权限控制. access熟悉表示在请求对于的yrl时需要什么权限
        默认配置时它应该是一个已逗号分隔的角色列表, 请求的用户只需拥有其中的一个角色就能成功访问对应的URL
        -->
        <security:intercept-url pattern="/**" access="ROLE_USER"/>

        <!--
        auto-config 配置后, 不需要在配置下面信息 <security:from-login/>定义登录表单信息
        <security:http-basic/> <security:logout/>
        -->
    </security:http>

    <security:authentication-manager>
        <security:authentication-provider>
            <security:user-service>
                <security:user name="user" password="{noop}user" authorities="ROLE_USER"/>
                <security:user name="admin" password="{noop}admin" authorities="ROLE_ADMIN"/>
            </security:user-service>
        </security:authentication-provider>
    </security:authentication-manager>

</beans>
```

---

### 指定资源不过滤

```xml
<!-- 配置不过滤的资源（静态资源及登录相关） -->
<security:http security="none" pattern="/login.html" />
<security:http security="none" pattern="/failer.html" />
```

---

### 指定登录页面(成功, 失败, 登出)

```xml
<security:http auto-config="true" use-expressions="false" >

    <!-- 配置资料连接，表示任意路径都需要ROLE_USER权限 -->
    <security:intercept-url pattern="/**" access="ROLE_USER" />

    <!--
    自定义登陆页面，login-page 自定义登陆页面 authentication-failure-url 用户权限校验失败之后才会跳转到这个页面，如果数据库中没有这个用户则不会跳转到这个页面。
    default-target-url 登陆成功后跳转的页面。 注：登陆页面用户名固定 username，密码 password，action:login
    -->
    <security:form-login login-page="/login.html" login-processing-url="/login" username-parameter="username" password-parameter="password" authentication-failure-url="/failer.html" default-target-url="/success.html" authentication-success-forward-url="/success.html"/>
    <!--
    登出， invalidate-session 是否删除session logout-url：登出处理链接 logout-success-url：登出成功页面
    注：登出操作 只需要链接到 logout即可登出当前用户
    -->
    <security:logout invalidate-session="true" logout-url="/logout" logout-success-url="/login.jsp" />

    <!-- 关闭CSRF,默认是开启的 -->
    <security:csrf disabled="true" />
</security:http>
```

---

## `UserDetails`

`UserDetails`是一个接口, 对认证主体进行了规范, 通常使用实现类`User`进行使用

其中`Collection<? extends GrantedAuthority>`代表了角色拥有的权限集合, 其集合继承了`GrantedAuthority`

```java
public interface UserDetails extends AuthenticatedPrincipal, Serializable {
    Collection<? extends GrantedAuthority> getAuthorities();

    String getPassword();

    String getUsername();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();

    boolean isEnabled();

    default String getName() {
        return this.getUsername();
    }
}
```

---

## `UserDetallService`

`IUserService`需要继承`UserDetailsService`类

```
public interface IUserService extends UserDetailsService {
}
```

`UserServiceImpl`需要实现`loadUserByUsername`方法, 返回值为`UserDetails`, 使用实现类`User`返回

```java
@Override
public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    UserInfo userInfo = null;
    try {
        userInfo = dao.findByUsername(s);
    } catch (Exception e) {
        e.printStackTrace();
    }
    assert userInfo != null;
    //处理自己的用户对象封装成UserDetails
    return new User(userInfo.getUsername(), "{noop}" + userInfo.getPassword(), userInfo.getStatus() == 1, true, true, true, getAuthority(userInfo.getRoles()));
}
```

构造`User`实体需要传入角色的权限集合, 即`Collection<? extends GrantedAuthority>`, 所以提供了一个私有方法来获取权限的集合, 集合的类型为`SimpleGrantedAuthority`其继承了`GrantedAuthority`.

```java
/**
 * 返回一个list集合, 集合中装入的是角色描述
 * @return list集合
 */
private List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
    List<SimpleGrantedAuthority> list = new ArrayList<>();
    for (Role role : roles) {
        list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
    }
    return list;
}
```

完整代码实现:

```java
/**
 * @Author Harlan
 * @Date 2020/9/25
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao dao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserInfo userInfo = null;
        try {
            userInfo = dao.findByUsername(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert userInfo != null;
        //处理自己的用户对象封装成UserDetails
        return new User(userInfo.getUsername(), "{noop}" + userInfo.getPassword(), userInfo.getStatus() == 1, true, true, true, getAuthority(userInfo.getRoles()));
    }

    /**
     * 返回一个list集合, 集合中装入的是角色描述
     * @return list集合
     */
    private List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }
        return list;
    }

}
```

---


# Spring Security权限控制

## 服务器端方法级别权限控制

> 在服务器端,我们可以通过Spring security提供的注解对方法来进行权限控制. Spring security在方法的权限控制上支持三种类型的注解, `JSR-250`注解 / `@Secured`注解 / 支持表达式的注解, 者三种注解默认都是没有启用的, 需要单独通过`global-method-security`元素的对应属性进行启用



## `JSR-250`使用

`pom.xm`l导入依赖坐标

```xml
<dependency>
    <groupId>javax.annotation</groupId>
    <artifactId>jsr250-api</artifactId>
    <version>1.0</version>
</dependency>
```

`Spring Security`配置文件配置开启注解

```xml
<security:global-method-security jsr250-annotations="enabled"/>
```

---

### `@RoleAllowed`注解

在指定方法上使用, 表示访问对应方法时所应该具有的角色, 其参数为一个`String`的数组

```java
@RolesAllowed("ADMIN")
@RequestMapping("/findAll.do")
public void findAll(){
 ...   
}
```

以上代码表示只有`ADMIN`角色能够访问该方法

---

### `@PermitAll`注解

表示允许所有的角色进行访问, 也就是不进行权限控制

---

### `@DenyAll`注解

和`@PermitAll`相反, 表示无论什么角色都不能访问

---

---

## `@Secured`使用

`Spring securtiy`配置文件开启 `secured`注解

```xml
<security:global-method-security secured-annotations="enabled"/>
```

`@Secured`注解标注的方法进行权限控制的支持, 其默认为`disabled`

---

在指定方法上使用, 表示访问对应方法时所应该具有的角色, 其参数为一个`String`的数组

在使用`@Secured`注解时, 需要添加角色的前缀`ROLE_`才能正常使用

```java
@Secured("ROLE_ADMIN")
@RequestMapping("/findAll.do")
public ModelAndView findAll()  throws Exception {
    ...
}
```

以上代码表示只有`ROLE_ADMIN`角色能够访问该方法

---

---

## 支持表达式的注解

`spring securtiy`配置文件中开启注解表达式支持

```xml
<security:global-method-security pre-post-annotations="enabled" jsr250-annotations="enabled" secured-annotations="enabled"/>
```

---

### `Spring EL`表达式

`spring security`运行我们在定义`URL`访问或方法访问所应用的权限时使用`Spring EL`表达式, 在定义所需的访问权限时, 如果对应的表达式返回结果为`true`, 则表示拥有对应的权限, 反之则无. `Spring Security`可用表达式对象的基类时`SecurityExpressionRoot`, 其为我们提供了如下在使用`Spring EL` 表达式对`URL`或方法进行权限控制时的内置表达式

|              表达式               |                             描述                             |
| :-------------------------------: | :----------------------------------------------------------: |
|         `hasRole([role])`         |                     当前用户是否拥有角色                     |
|   `hasAnyRole([role1, role2])`    | 多个角色是一个以逗号进行分隔的字符串。如果当前用户拥有指定角色中的任意一个则返回`true` |
|      `hasAuthority([auth])`       |                      等同于`hasRole()`                       |
| `hasAnyAuthority([auth1, auth2])` |                     等同于`hasAntRole()`                     |
|            `Principle`            |                代表当前用户的`principle`对象                 |
|         `authentication`          |    直接从`SecurityContext`获取的当前`Authentication`对象     |
|            `permitAll`            |                 总是返回`true`, 表示允许所有                 |
|             `denyAll`             |                总是返回`false`, 表示拒绝所有                 |
|          `isAnonymous()`          |                  当前用户是否是一个匿名用户                  |
|         `isRememberMe()`          |          表示当前用户是否通过Remember-Me自动登录的           |
|         `isAuthenticated`         |              表示当前用户是否已经登录认证成功了              |
|     `isFullyAuthenticated()`      | 如果当前用户既不是一个匿名用户, 同时又不是通过Remember-Me自动登录的, 则返回`true` |



---

### `@PerAuthorize`注解

在方法调用之前, 基于表达式的计算结果来对限制对方法的访问

---

### `@PostAuthorize`注解

允许方法调用, 但是如果表达式计算结果为`false`, 将抛出一个安全异常

---

### `@PostFilter`注解

允许方法调用, 但必须按照表达式来过滤方法的结果

---

### `@PreFilter`注解

运行方法调用, 但必须在进入方法之前过滤输入值
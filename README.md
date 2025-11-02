## CourseDesign 25

### resourses/mybatis.mapper/userInfoMapper.xml

**MyBatis 的 XML 映射文件（Mapper）**，用于将 Java 方法和 SQL 操作**绑定起来**。这是在使用 MyBatis（尤其是结合 MyBatis Generator 自动生成代码）时常见的结构。

将 Java 对象（如 userInfo）与数据库表（如 user）进行**双向映射**，实现 **增删改查** 操作的 SQL 绑定。

 

### resourses/mybatis.mapper/UserMapper.xml

这个 UserMapper.xml 文件是你项目中使用 MyBatis 框架进行数据库操作的**XML 映射文件**，它的作用是：

**将 Java 接口方法映射到对应的 SQL 语句**，用来操作数据库中的 User 表，实现对该表的增、删、改、查。

此处的Java接口在：`mapper/UserMapper.java`中定义：

```java
public interface UserMapper {
    //查询所有用户
    List<PointObject> getPointObject();
    //根据ID查询用户
    PointObject getPointObjectByID(int id);
    //insert一个用户,增删改需要提交事务
    int addPointObject(PointObject pointObject);
    //更新数据
    int updatePointObject(PointObject pointObject);
    //删除用户
    int deletePointObject(int id);
}

```

可以把它看作一个“**数据库操作说明书**”\

- Spring Boot 在运行时会根据这个 XML 文件
- 自动把你写的 Java 接口方法（如 getPointObject）对应到这里的 SQL
- 然后执行这些 SQL，返回结果给 Java 对象（如 PointObject）

| **方法 ID（**<id=...>**）** | **SQL 操作** | **功能说明**                                                 |
| --------------------------- | ------------ | ------------------------------------------------------------ |
| getPointObject              | SELECT       | 查询所有 User 表中的积分记录（结果映射为 PointObject 对象列表） |
| getPointObjectByID          | SELECT       | 根据主键 ID 查询某条记录                                     |
| addPointObject              | INSERT       | 插入新的积分记录（从 Java PointObject 对象中提取字段）       |
| updatePointObject           | UPDATE       | 根据 ID 更新积分记录（不更新 ID 本身）                       |
| deletePointObject           | DELETE       | 根据 ID 删除积分记录                                         |







```
+-------------+       +-----------------+       +--------------------------+
| 前端页面    | <---> | Controller 层    | <---> | Service 层（可选）        |
+-------------+       +-----------------+       +--------------------------+
                                                 ↓
                                   +--------------------------+
                                   | Mapper 接口（UserMapper）|
                                   +--------------------------+
                                                 ↓
                                +----------------------------------+
                                | UserMapper.xml（写 SQL 的地方） |
                                +----------------------------------+
                                                 ↑
                          +--------------------------------+
                          | POJO（如 PointObject.java）    |
                          +--------------------------------+
```

| **对比项** | pojo**（数据结构）**   | mapper**（数据库接口）**     |
| ---------- | ---------------------- | ---------------------------- |
| 类的作用   | 表示数据库一行数据结构 | 定义对数据库表的增删改查操作 |
| 是否写 SQL | ❌ 不写                 | ✅ 与 XML 中 SQL 一一绑定     |
| 是否含注解 | 通常无注解             | 通常含 @Mapper / @Repository |
| 典型命名   | User.java / Point.java | UserMapper.java              |
| 调用方式   | 通过接口参数传递       | 被 Service / Controller 调用 |

## com.course/mapper：数据访问层

- 定义对某个表（比如 User 表）进行的操作：查询、新增、修改、删除
- 通常每个表对应一个 Mapper 接口（如 UserMapper.java）



## com.course/pojo：数据模型层

定义的是**实体类**（如 PointObject.java），用来封装数据库中的一行数据。

```java
public class PointObject {
    private Integer id;
    private Integer growScore;
    private Integer exchangeScore;
    private Integer scoreTotal;
    // getter/setter ...
```

- 表示一个具体的数据结构
- 用于接收前端传来的 JSON，或从数据库中读取一条记录
- 可以和多个层（Controller, Service, Mapper）传递数据时复用
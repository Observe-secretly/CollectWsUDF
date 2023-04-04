# 问题
在使用flink的过程中，需要使用到类似Hive的collect_set分组聚合函数。flink与之对应的是collect函数。但是返回值是`multiset`。不方便直接对此类型的数据进行存储。故：需要转换成string类型sink到其它存储系统中

# 解决办法：UDF
自定义UDF把multiset类型序列化成字符串即可。multiset类型类似一个hashtable，key是分组的值，value是值出现的次数。

# 使用方式
- 第一步：打包
`mvn clean package`
- 第二步：上传jar包
把打包好的jar包上传到 `flink/lib`下
- 第三部：引用

```
# 引入自定义函数(FLINK SQL)
CREATE FUNCTION collect_ws AS 'cn.esign.CollectWsUDF';

# 使用 (FLINK SQL)
select id,collect_ws(COLLECT(DISTINCT name )) name from ......  group by name
```
- 效果如下

|id| name|
| -- | -- |
|1748760898899545504 |        菱纱(1)|
|1324714784301518289 |  竹柒(1),玖兮(1)|
|1699696445986180687 |        夙娜(1)|
|1914297635259810770 |        南下(1)|
|1981052646567970637 |  剔透(1),沙棠(1),逍遥(1)|



# 使用 jaxb2 自动从 vast.xsd 文件生成 Java Model


## 运行Generator

### 使用前先clean。如果自上次生成之后 xsd 文件未修改，将会跳过。

 mvn clean

### 运行

 mvn -f vast-pom.xml jaxb2:xjc


## 参考文档

 http://www.mojohaus.org/jaxb2-maven-plugin/Documentation/v2.2/xjc-mojo.html
# Protocol Buffer

### 官网地址：
> https://developers.google.com/protocol-buffers/

### 工具下载
> https://github.com/google/protobuf/releases

## Adxing

### seller .proto文件生成 .java文件：
```
protoc --proto_path=./lib/protobuf --java_out=./src/main/java ./lib/protobuf/adxing-for-seller.proto

```

### dsp .proto文件生成 .java文件：
```
protoc --proto_path=./lib/protobuf --java_out=./src/main/java ./lib/protobuf/adxing-for-dsp.proto
```


## iqiyi


### dsp .proto文件生成 .java文件：
```
protoc --proto_path=./lib/protobuf --java_out=./src/main/java ./lib/protobuf/iqiyi-for-dsp.proto
```
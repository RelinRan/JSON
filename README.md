# JsonParser
Android Json解析工具
[JsonParser.jar](https://github.com/RelinRan/JsonParser/blob/master/JsonParser.jar)
## 方法一  ARR依赖
[JsonParser.arr](https://github.com/RelinRan/JsonParser/blob/master/JsonParser.aar)
```
android {
    ....
    repositories {
        flatDir {
            dirs 'libs'
        }
    }
}

dependencies {
    implementation(name: 'JsonParser', ext: 'aar')
}

```

## 方法二   JitPack依赖
### A.项目/build.grade
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
### B.项目/app/build.grade
```
	dependencies {
	        implementation 'com.github.RelinRan:JsonParser:1.0.2'
	}
```
## 使用
### 对象转JSON字符串
```
String json = JsonParser.parseObject(user);
```
### List对象转JSON字符串
```
String json = JsonParser.parseObject(list);
```
### Json字符串转实体
```
User user = JsonParser.parseJSONObject(User.class,json);
```
### Json字符串转List实体
```
List<User> user = JsonParser.parseJSONArray(User.class,json);
List<String> user = JsonParser.parseJSONArray(String.class,json);
```
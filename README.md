# JSON
Android Json工具  
如果是java项目使用，请先下载org.json.jar依赖在使用JSON  
## ARR
|名称|操作|
|-|-|
|json_2023.9.1.1.aar|[下载](https://github.com/RelinRan/JSON/blob/master/json_2023.9.1.1.aar) |
|org.json.jar|[下载](https://github.com/RelinRan/JSON/blob/master/org.json.aar) |
## JitPack
/build.grade
```
allprojects {
    repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
/app/build.grade
```
dependencies {
	 implementation 'com.github.RelinRan:JSON:2023.9.1.1'
}
```
#### Debug
```
JSON json = new JSON();
//设置调试模式，调试错误使用
json.setDebug(true);
```
#### Object

```
//Object -> JSON
String json = json.toJson(object);
//JSON -> Object
User user = json.toObject(json,User.class);
```
#### Array
```
String[] array = json.toArray(json,String.class);
```
#### List
```
List<User> list = json.toList(json,User.class);
List<String> list = json.toList(json,String.class);
```
#### Collection
```
ArrayList<User> list = json.toCollection(json,ArrayList.class,User.class);
Stack<User> list = json.toCollection(json,Stack.class,User.class);
Vector<User> list = json.toCollection(json,Vector.class,User.class);
Collection<String> decode = json.toCollection(json,Collection.class,String.class);
```
#### T
```
//多个泛型
Map<String,Class<?>> variable = new HashMap<>();
variable.put("data",Data.class);
User<Data> user = json.toObject(json,user,variable);
//单个泛型
User<Data> user = json.toObject(json,User.class,"data",Data.class);
```
#### Map
```
Map<String,Object> map = json.toMap(json);
```
#### List Map
```
List<Map<String, Object>> listMap =  json.toMapCollection(json);
```
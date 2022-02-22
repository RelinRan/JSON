# JSON
Android Json工具
## ARR
[JSON.arr](https://github.com/RelinRan/JSON/blob/master/JSON.aar)

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
	 implementation 'com.github.RelinRan:JSON:2022.2.22.1'
}
```
## Object
对象转JSON字符串
```
User user = new User();
user.setName("JSON");
user.setAge(25);
String json = JSON.toJson(user);
```
JSON字符串转对象
```
User user = JSON.toObject(json,User.class);
```
## Map
Map转JSON字符串
```
Map<String,Object> map = new HashMap<>();
map.put("name","JSON");
map.put("age",25);
String json = JSON.toJson(map);
```
JSON字符串转Map
```
Map<String,Object> map = JSON.toMap(json);
```
## List
List转JSON字符串
```
List<User> list = new ArrayList<>();
String json = JSON.toJson(list);
```
JSON字符串转List
```
List<Map<String,Object>> list = List<Map<>>();
String json = JSON.toJson(list);
```
JSON转List<Map<String,Object>>
```
List<Map<String,Object>> list = JSON.toMapCollection(json);
```
List<Map<String,Object>>转JSON
```
List<User> list = JSON.toCollection(json,User.class);
```

## Array
Array转JSON
```
int[] array = new int[5];
for(int i=0;i<5;i++){
    array[i] = i;
}
String json = JSON.toJson(array);
```
JSON转Array
```
List<Integer> list = JSON.toCollection(json,Integer.class);
int[] array = list.toArray(new int[list.size()]);
```
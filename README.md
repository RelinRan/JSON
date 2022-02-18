# JSON
Android Json工具
## JAR
[JSON.jar](https://github.com/RelinRan/JsonParser/blob/master/JSON.jar)
## ARR
[JSON.arr](https://github.com/RelinRan/JsonParser/blob/master/JSON.aar)

## JitPack implementation
### /build.grade
```
allprojects {
    repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
### /app/build.grade
```
dependencies {
	 implementation 'com.github.RelinRan:JSON:2022.2.18.1'
}
```
## object转json
```
User user = new User();
user.setName("JSON");
user.setAge(25);
String json = JSON.toJson(user);
```
## json转object
```
User user = JSON.toObject(json,User.class);
```
## map转json
```
Map<String,Object> map = new HashMap<>();
map.put("name","JSON");
map.put("age",25);
String json = JSON.toJson(map);
```
## json转map
```
Map<String,Object> map = JSON.toMap(json);
```
## list转json
```
List<User> list = new ArrayList<>();
String json = JSON.toJson(list);
```
## json转map collection
```
List<Map<String,Object>> list = List<Map<>>();
String json = JSON.toJson(list);
```
## map collection转json
```
Map<String,Object> map = JSON.toMapCollection(json);
```
## json转list
```
List<User> list = JSON.toCollection(json,User.class);
```
## array转json
```
int[] array = new int[5];
for(int i=0;i<5;i++){
    array[i] = i;
}
String json = JSON.toJson(array);
```
## json转array
```
List<Integer> list = JSON.toCollection(json,Integer.class);
int[] array = list.toArray(new int[list.size()]);
```
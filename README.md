# JsonParser
Android Json解析工具

## 方法一  ARR依赖
[AndroidKit.arr](https://github.com/RelinRan/AndroidKit/blob/master/AndrodKit.aar)
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

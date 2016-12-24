# Unplag java-sdk

### Installation
Deploy jar to your local maven repo:
```
$ gradlew install
```


To run integration tests:
```
$ gradlew itest -Dkey=<apikey> -Dsecret=<apisecret>
```

### Usage

Dependency: 
```
<dependency>
    <groupId>com.unplag</groupId>
    <artifactId>unplag-java-sdk</artifactId>
    <version>1.0</version>
</dependency>
```

Get APIKey & APISecret https://unplag.com/profile/apisettings

```java
public class Main {
    
	public static void main(String[] args) throws InterruptedException{
        Unplag unplag = new Unplag("xxx", "yyy");
        UFile uFile = unplag.uploadFile(file, format, name);
        UCheck uCheck = unplag.createCheck(uFile.getId(), UType.WEB, null, null, null);
        
        System.out.println(uCheck);
    }
    
}
```



# JAXB Fix for Java 11+

## Problem
The SOAP module failed to compile with errors like:
```
package javax.xml.bind.annotation does not exist
```

This happened because:
1. Java 11+ removed JAXB from the JDK
2. The old `maven-jaxb2-plugin` (v0.15.3) generated code using `javax.xml.bind.annotation` instead of `jakarta.xml.bind.annotation`
3. There was a conflict between a manually created `User` class and the JAXB-generated `User` class

## Solution

### 1. Updated JAXB Plugin
Changed from the old plugin to the newer one that supports Jakarta APIs:

**Before:**
```xml
<plugin>
    <groupId>org.jvnet.jaxb2.maven2</groupId>
    <artifactId>maven-jaxb2-plugin</artifactId>
    <version>0.15.3</version>
    ...
</plugin>
```

**After:**
```xml
<plugin>
    <groupId>org.jvnet.jaxb</groupId>
    <artifactId>jaxb-maven-plugin</artifactId>
    <version>4.0.8</version>
    ...
</plugin>
```

### 2. Added JAXB Dependencies
Added the necessary JAXB runtime dependencies for Java 11+:

```xml
<!-- JAXB API for Java 11+ -->
<dependency>
    <groupId>jakarta.xml.bind</groupId>
    <artifactId>jakarta.xml.bind-api</artifactId>
</dependency>

<!-- JAXB Runtime -->
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
</dependency>

<!-- Jakarta Activation -->
<dependency>
    <groupId>jakarta.activation</groupId>
    <artifactId>jakarta.activation-api</artifactId>
</dependency>
```

### 3. Removed Duplicate User Class
Deleted the manually created `User.java` in the `endpoint` package since the JAXB plugin generates it from the XSD schema.

## Result
✅ The SOAP API module now compiles successfully
✅ The application starts on port 8087
✅ WSDL is available at http://localhost:8087/ws/users.wsdl
✅ SOAP endpoints are registered correctly

## Key Takeaways
- When using JAXB with Java 11+, use `jaxb-maven-plugin` version 4.x (not the old `maven-jaxb2-plugin`)
- Always include JAXB runtime dependencies explicitly
- Let JAXB generate classes from XSD - don't create duplicate manual classes
- The newer plugin automatically uses Jakarta APIs (`jakarta.xml.bind.*`) instead of the old Java EE APIs (`javax.xml.bind.*`)

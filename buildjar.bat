javac -source 1.3 -target 1.3 -bootclasspath C:\WTK2.5.2_01\lib\cldcapi11.jar -classpath "lib\*;C:\WTK2.5.2_01\lib\midpapi20.jar" -d outputs src\*.java
cd outputs
jar xf ..\lib\luaj-jme-3.0.2.jar
cd ..
jar cfm Love2D.jar MANIFEST.MF -C outputs .
preverify -classpath Love2D.jar;C:\WTK2.5.2_01\lib\cldcapi11.jar;C:\WTK2.5.2_01\lib\midpapi20.jar -d . Love2D.jar
rem ./Love2D.jar
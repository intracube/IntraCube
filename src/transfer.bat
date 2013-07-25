@Echo off
TITLE IntraCube Transfer
SET path2="C:\Users\Dana\Documents\IntraCube Scripts\Scripts\Sources"

SET path1=%~dp0*.java
xcopy /y %path1% %path2%

SET path2=%path2:~0,-16%
call %path2%Compile.bat
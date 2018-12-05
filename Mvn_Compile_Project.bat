@echo off

@rem 这里是注释，进入项目目录，然后依次做命令执行

d: && cd D:\JavaWorkSpace\Yun_Self_New\Yun_JavaTechnicalSummary
cd Design_Pattern  && call mvn clean install && cd ..
cd Rd_Standard  && call mvn clean install && cd ..
@rem 还有其他项目需要编译打包，依次执行

pause

cd ..
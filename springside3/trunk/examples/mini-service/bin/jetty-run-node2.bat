@echo off
echo [INFO] ʹ��maven jetty plugin ��8081�˿���node2�ڵ�����������Ŀ.

cd ..
call mvn jetty:run-war -Pnotest -Djetty.port=8081 -Dcluster.nodename=node2

pause
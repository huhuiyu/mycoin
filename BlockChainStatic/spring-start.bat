rem =====================path=====================
call spring-path.bat
rem =====================spring=====================
cd ../BlockChainService
call gradle clean
call gradle build
cd build/libs
java -jar BlockChainService-1.0.0.0.jar
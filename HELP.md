
## 说明
   
    
### 1.jar打包与启动
    mvn clean package 
    #### 2 启动方式1
    java -jar ci.jar --spring.profiles.active=${profile.active}
    #### 1 启动方式2
    nohup java -jar CI.jar --spring.profiles.active=${profile.active} &


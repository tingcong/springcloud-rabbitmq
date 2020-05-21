#zipkin server
以mysql作为存储介质，rabbitmq作为传输介质
> java -jar zipkin-server-2.21.1-exec.jar --zipkin.collector.rabbitmq.addresses=localhost:5672 --zipkin.collector.rabbitmq.password=guest --zipkin.collector.rabbitmq.username=guest --zipkin.storage.type=mysql --zipkin.storage.mysql.username=root --zipkin.storage.mysql.password=hutingcong --zipkin.storage.mysql.port=23306 --zipkin.storage.mysql.host=106.13.160.249

rabbitmq作为传输介质
> java -jar zipkin-server-2.21.1-exec.jar --zipkin.collector.rabbitmq.addresses=localhost:5672 --zipkin.collector.rabbitmq.password=guest --zipkin.collector.rabbitmq.username=guest 


##rabbitmq
--zipkin.collector.rabbitmq.addresses=localhost:5672 
--zipkin.collector.rabbitmq.password=guest 
--zipkin.collector.rabbitmq.username=guest

##mysql
--zipkin.storage.type=mysql 
--zipkin.storage.mysql.host=106.13.160.249
--zipkin.storage.mysql.port=23306
--zipkin.storage.mysql.username=root 
--zipkin.storage.mysql.password=hutingcong 
--zipkin.storage.mysql.db=23306 

--zipkin.collector.kafka.zookeeper=10.4.120.77:2181

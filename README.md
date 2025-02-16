# Kafka-product-producer

# have a DB to store message before produce to kafka broker and apply JpaTransactionManager
Step 1: Run docker-compose.yaml this will start up MySql in container <br />
$ docker-compose up <br />
Step 2: Access your DB by<br />
docker exec -it kafka-demo-database bash<br />
mysql -u root -p kafka-demo-database<br />
password:dummypassword<br />
show databases; # lists all databases<br />
use kafka-demo-database; # connect to the mysql schema<br />
show tables;<br />
#docker-compose to run kafka in containers
environment.env file contains variable used in docker-compose.yaml file<br />
#run the docker-compose with env file
docker-compose --evn-file environment.env up <br/>
#docker into container <br />
docker exec -it kafka-broker-server-1 bash<br />
$ cd /opt/bitnami/kafka <br />

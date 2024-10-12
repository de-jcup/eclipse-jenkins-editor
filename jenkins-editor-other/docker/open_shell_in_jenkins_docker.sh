#!/bin/bash

container_id=$(sudo -S docker ps -q  --filter ancestor=jenkins/jenkins:lts)
echo container_id=$container_id
sudo docker exec -it $container_id /bin/bash 
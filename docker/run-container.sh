#!/bin/bash
# run the container in the backgroud corresponding to the image mockmcokserver
docker run -d -p 2525:2525 -p 8282:8282 mockmockserver 

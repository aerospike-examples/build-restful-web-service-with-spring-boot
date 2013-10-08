Building a RESTful Web Service with Spring Boot to access data in an Aerospike cluster
==========================================
Spring Boot is a powerful jump start into Spring. It allows you to build powerful applications with production grade services with little effort on your part. 

Aerospike is a high available, low latency NoSQL data base that scales linearly. It is an in-memory database optimized to use both DRAM and native Flash. Aerospike boasts latencies of 1 to 3 ms consistently across throughput loads on a correctly sized cluster. Aerospike also has high reliability and is ACID compliant.  Their oldest customer has many terabytes of data and has never been offline, even during Hurricane Sandy in New York City.

What you will build
This guide will take you through creating a simple RESTful web service with Spring Boot. You will build a service that accepts an HTTP GET request:

http://localhost:8080/as/test/flights/getAll/1234

It responds with the following JSON:

{"expiration":121023390,"bins":{"DISTANCE":2446,"DEST_CITY_NAME":"New York","DEST":"JFK","YEAR":2012,"ORI_AIRPORT_ID":"14679","DEP_TIME":"802","DAY_OF_MONTH":12,"DEST_STATE_ABR":"NY","ORIGIN":"SAN","FL_NUM":160,"CARRIER":"AA","ORI_STATE_ABR":"CA","FL_DATE":"2012/01/12","AIR_TIME":291,"ORI_CITY_NAME":"San Diego","ELAPSED_TIME":321,"ARR_TIME":"1623","AIRLINE_ID":19805},"generation":1}


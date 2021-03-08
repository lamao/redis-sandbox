# Redis Sandbox

## How to run application

1. Run redis server. Use default port '6379' 
   
   1.1. For Linux [this guideline](https://redis.io/topics/quickstart)  may 
   be used
   
   1.2. For windows Redis server may be downloaded [here](https://github.com/dmajkic/redis/downloads) 
2. Run application by typing `./gradlew clean build bootRun` from the 
   project root
3. Postman may be used to test API. Check `Redis Sandbox.postman_collection.json` 
   from the project root
4. [This](https://www.epochconverter.com/) service may be used to play with 
   timestamp

## Explanations

Explanations of some decisions
* Didn't create convertors for model object because it's not necessary yet.
  Project is very simple and will not be developed much. I would definetely 
  introduce 'Convertor' layer in real project.  
* Streams are used to convert object. They may be slow for such tasks (faced
  with this at current project). If API is going to be heavy loaded I would 
  avoid them and used simple if/for operators.
* Used only integration tests for the controller. This is a very small test project.
  Decided not to waste time for duplicated united tests. In real project
  separate unit tests may be useful because they don't launch Spring and
  are much faster.
* Used gradle as a build tool because of personal preferences.
* Decided not clarify details about API specification, data format, error handling etc. 
  I thought the purpose of the task is to check technical level, and it's not
  so important what shape has response (pure date or data wrapped in response
  object)

Some points were considered but haven't been implemented. Mostly because of lack of time
* low-level implementation for getByTime. I have no experience with Redis. So,
  haven't figured out how to write complex 'queries'. I realize current 
  implementation is slow.
* getLast may be buggy. Didn't investigate deeply. Sometimes it returns not the
  very last message
* repository test is disabled because I failed to configure embedded Redis
  server. It still can be run with external server.
* didn't create docker images, because of time. I need to refresh my 
  knowledge about docker/docker-compose
  



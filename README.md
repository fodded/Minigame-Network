## PROJECT IDEA

This project was inspired by Hypixel. The general idea was to create a solid code base for a minigame network, to scale it horizontally with no pain. It tends to load new server instances on the fly in order to make it easier to handle many hunders of spigot/bungeecord instances with no need to touch config files.

## PROJECT LIBRARIES

![alt text](https://avatars.githubusercontent.com/u/45120?s=20&v=4) Mongo Database
  - To save statistics in a persistent storage

![alt text](https://avatars.githubusercontent.com/u/1529926?s=20&v=1) Redis
  - To cache retrieved statistics from persistent storage to access it faster, whenever it's needed.
  - Redis is put in use as a way of inter communication inside of the network. As an example it synchronizes all players among different proxies to make it look like one big server, basically it's an implementation of RedisBungee

![alt text](https://avatars.githubusercontent.com/u/5713248?s=20&v=1) Influx DB **AND** ![alt text](https://avatars.githubusercontent.com/u/7195757?s=20&v=1) Grafana
  - To save metrics and display them in a nice way. Example of metrics collected by the project is provided below

As obvious as it's, the project also uses quite of a few smaller libraries to simplify the coding process. Such as Caffeine, Gson, Lombok and etc...

## SCREENSHOTS
![image](https://github.com/fodded/Minigame-Network/assets/47217090/33ecc4c1-d12a-446c-96ca-b3bf6d34dc81)

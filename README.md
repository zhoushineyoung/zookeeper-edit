zookeeper-edit
==============

Zookeeper Edit UI, A OpenSource Web Face Based JavaEE Tech 4 Zookeeper Management.

Requirements
==============
Requires Java7 or newer to run.

Setup
==============
1. mvn clean install
2. Copy the config.properties into the folder with the jar file. Modify it to point to the zookeeper instance/cluster. 
3. Multiple zookeeper instances(namely zookeeper cluster members) are comma separated.  eg: server1:2181,server2:2181.
4. Run the jar. (type "java -jar zkui-2.0-SNAPSHOT-jar-with-dependencies.jar &")
5. <a href="http://localhost:9090">http://localhost:9090</a> 

Login Info
==============
username: admin, password: manager (Admin privileges, CRUD operations supported)
username: zkedit, pwd: zkedit (Readonly privileges, Read operations supported only)

And you can change this in the config.properties

Technology Stack
==============
1. Embedded Jetty Server.
2. Freemarker template.
3. H2 DB.
4. Active JDBC.
5. JSON.
6. SLF4J.
7. Zookeeper.
8. Apache Commons File upload.
9. Bootstrap.
10. Jquery.
11. Flyway DB migration.

Features
==============
1. CRUD operation on zookeeper properties.
2. Export properties.
3. Import properties via call back url.
4. Import properties via file upload.
5. History of changes + Path specific history of changes.
6. Search feature.
7. Rest API for accessing Zookeeper properties.
8. Basic Role based authentication.
9. LDAP authentication supported.
10. Root node /zookeeper hidden for safety.

Limitations
==============
1. ACLs are not yet fully supported

License & Contribution
==============
ZKEditUI(zookeeper-edit) is released under the MIT license. Comments, bugs, pull requests, and other contributions are all welcomed!

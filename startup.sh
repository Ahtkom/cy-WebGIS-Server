# Copy webgis/ to webapps/
rm -rf /opt/tomcat/latest/webapps/webgis/
cp -r /home/Ahtkom/pmk/project/cy-WebGIS-Server/webgis/ /opt/tomcat/latest/webapps/

# Compile java files
javac -cp "/opt/tomcat/apache-tomcat-9.0.58/lib/servlet-api.jar:/opt/tomcat/apache-tomcat-9.0.58/lib/postgresql-42.3.0.jar:/home/Ahtkom/pmk/project/cy-WebGIS-Server/webgis/WEB-INF/lib/gson-2.8.2.jar" -d /opt/tomcat/latest/webapps/webgis/WEB-INF/classes/ /home/Ahtkom/pmk/project/cy-WebGIS-Server/src/com/servlet/*.java


# javac -cp "/opt/tomcat/apache-tomcat-9.0.58/lib/servlet-api.jar:/home/Ahtkom/pmk/project/cy-WebGIS-Server/webgis/WEB-INF/lib/gson-2.8.2.jar:/opt/tomcat/latest/webapps/webgis/WEB-INF/lib/postgresql-42.2.19.jar" -d /opt/tomcat/latest/webapps/webgis/WEB-INF/classes/ /home/Ahtkom/pmk/project/cy-WebGIS-Server/src/com/servlet/*.java


# javac -cp /opt/tomcat/apache-tomcat-9.0.58/lib/servlet-api.jar -cp /home/Ahtkom/pmk/project/cy-WebGIS-Server/webgis/WEB-INF/lib/gson-2.8.2.jar -d /opt/tomcat/latest/webapps/webgis/WEB-INF/classes/ /home/Ahtkom/pmk/project/cy-WebGIS-Server/src/com/servlet/*.java


# javac /home/Ahtkom/pmk/project/cy-WebGIS-Server/src/com/servlet/*.java -cp /opt/tomcat/apache-tomcat-9.0.58/lib/servlet-api.jar;/home/Ahtkom/pmk/project/cy-WebGIS-Server/webgis/WEB-INF/lib/gson-2.8.2.jar;/usr/share/postgresql/13/postgresql-42.2.19.jar -d /opt/tomcat/latest/webapps/webgis/WEB-INF/classes/ 

# Restart tomcat service
systemctl restart tomcat.service
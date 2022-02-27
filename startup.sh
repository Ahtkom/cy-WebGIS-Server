# Copy webgis/ to webapps/
rm -rf /opt/tomcat/latest/webapps/webgis/
cp -r webgis/ /opt/tomcat/latest/webapps/

# Compile java files
javac -cp /opt/tomcat/apache-tomcat-9.0.58/lib/servlet-api.jar -d /opt/tomcat/latest/webapps/webgis/WEB-INF/classes/ /home/Ahtkom/pmk/project/cy-WebGIS-Server/src/com/servlet/*.java

# Restart tomcat service
systemctl restart tomcat.service
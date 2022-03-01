# cd /home/Ahtkom/pmk/project/cy-WebGIS-Server/webgis/map/
# npm run build

# cd /home/Ahtkom/pmk/project/cy-WebGIS-Server/webgis/
rm -rf /opt/tomcat/latest/webapps/webgis/map
rm -f /opt/tomcat/latest/webapps/webgis/index.html

cp -r /home/Ahtkom/pmk/project/cy-WebGIS-Server/webgis/map/ /opt/tomcat/latest/webapps/webgis/
cp /home/Ahtkom/pmk/project/cy-WebGIS-Server/webgis/index.html /opt/tomcat/latest/webapps/webgis/

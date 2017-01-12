#FROM e-identification-java-base-image
FROM dev-docker-registry.kapa.ware.fi/e-identification-base-java-service
COPY License.info /License.info
COPY target/site /site

# Define JAVA_HOME
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# Deploy project
RUN mkdir -p /opt/e-identification/vtj-client/
ADD target/e-identification-vtj-client.jar /opt/e-identification/vtj-client/

WORKDIR /opt/e-identification/vtj-client/

COPY service.properties.template /data00/templates/store/
COPY logback.xml.template /data00/templates/store/
COPY conf/ansible /data00/templates/store/ansible

RUN mkdir -p /opt/vtj-client-properties
RUN ln -s /data00/deploy/vtj-client.properties /opt/vtj-client-properties/vtj-client.properties
RUN ln -s /data00/deploy/logback.xml /logback.xml
RUN ln -sf /data00/deploy/tomcat_keystore /opt/e-identification/vtj-client/tomcat_keystore


EXPOSE 8443
ENTRYPOINT ["sh", "/data00/deploy/cmd.sh"]


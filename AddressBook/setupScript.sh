APP_HOME="/home/likewise-open/ANT/kashifu/Desktop/tomcat7/webapps/AddressBook"
TOMCAT_HOME="/home/likewise-open/ANT/kashifu/Desktop/tomcat7"
echo "setting up classpaths"
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/antlr-2.7.7.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/commons-collections-3.2.1.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/commons-lang3-3.3.2.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/commons-lang3-3.3.2-javadoc.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/commons-logging-1.1.3.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/commons-logging-1.1.3.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/hibernate-commons-annotations-4.0.1.Final.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/hibernate-core-4.0.1.Final.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/hibernate-jpa-2.0-api-1.0.1.Final.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/jackson-all-1.9.11.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/jasypt-1.9.2.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/javassist-3.15.0-GA.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/jboss-logging-3.1.0.CR2.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/jboss-transaction-api_1.1_spec-1.0.0.Final.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/jopenid-1.07.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/junit-4.10.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/mockito-all-1.9.5.jar
export CLASSPATH=$CLASSPATH:$APP_HOME/WEB-INF/lib/mysql-connector-java-5.1.31-bin
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/annotations-api.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/catalina.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/catalina-ant.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/catalina-ha.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/catalina-tribes.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/ecj-4.2.1.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/el-api.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/jasper.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/jasper-el.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/jsp-api.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/servlet-api.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/tomcat-api.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/tomcat-coyote.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/tomcat-dbcp.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/tomcat-i18n-es.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/tomcat-i18n-fr.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/tomcat-i18n-ja.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/tomcat-jdbc.jar
export CLASSPATH=$CLASSPATH:$TOMCAT_HOME/lib/tomcat-util.jar
echo "setting up binaries"
rm -rf $APP_HOME/WEB-INF/classes/*
cp $APP_HOME/src/hibernate.cfg.xml $APP_HOME/WEB-INF/classes/hibernate.cfg.xml
javac $APP_HOME/src/com/addressbook/businessobjects/*.java -d $APP_HOME/WEB-INF/classes
javac $APP_HOME/src/com/addressbook/servlets/beans/*.java -d $APP_HOME/WEB-INF/classes
cd $APP_HOME/src
javac $APP_HOME/src/com/addressbook/util/*.java -d $APP_HOME/WEB-INF/classes
javac $APP_HOME/src/com/addressbook/dao/util/*.java -d $APP_HOME/WEB-INF/classes
javac $APP_HOME/src/com/addressbook/dao/*.java -d $APP_HOME/WEB-INF/classes
javac $APP_HOME/src/com/addressbook/businesslogic/*.java -d $APP_HOME/WEB-INF/classes
javac $APP_HOME/src/com/addressbook/servlets/*.java -d $APP_HOME/WEB-INF/classes
echo "done"



pwd;
mvn clean install sonar:sonar   -Dsonar.projectKey=mypos_backend   -Dsonar.host.url=http://localhost:9000   -Dsonar.login=26384b77e647f0b4cf393e58bf3480008fa354f3 -Dsonar.junit.reportPaths=target/surefire-reports

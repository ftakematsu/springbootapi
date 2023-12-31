# Projeto Java Microservices

## Ferramentas para download
- Java 8, 9 ou 11: [https://www.java.com/pt-BR/download/ie_manual.jsp?locale=pt_BR](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)
  - Configurar as variáveis de ambiente
    - JAVA_HOME: para raiz / da JDK
    - CLASSPATH: para a pasta .;%JAVA_HOME%/lib
    - PATH: adicionar na lista %JAVA_HOME%/bin
- Spring Tools Suite (Eclipse): https://spring.io/tools
  - Será baixado um arquivo JAR. Basta executá-lo que a pasta do Spring Tools Suite será descompactado. Em seguida, basta ir na pasta extraída e executar o arquivo SpringToolSuite4.
- Laragon (para poder executar o MySQL): [https://www.postgresql.org/download/windows/](https://laragon.org/download/index.html)
  - Inicialmente, o acesso ao banco de dados é root e sem senha.
  - Alterar a senha do root: ALTER USER 'root'@'localhost' IDENTIFIED BY 'MyN3wP4ssw0rd'; flush privileges;
- Apache Maven (Windows): https://dlcdn.apache.org/maven/maven-3/3.9.2/binaries/apache-maven-3.9.2-bin.zip
  - Configura as variáveis MAVEN_PATH (raiz) e PATH (/bin).
  - Na pasta raiz do projeto, execute mvn package
  - Vá ao diretório /target do projeto e execute java -jar *.jar
- Postman: https://www.postman.com/



## Fazendo deploy no Heroku

- Crie uma conta no Heroku ou faça login caso já tenha conta (https://id.heroku.com/login)
	- Confirme sua conta no seu e-mail.
- Instale o Heroku CLI https://devcenter.heroku.com/articles/heroku-cli



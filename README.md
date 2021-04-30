# RES-labo-SMTP

### Description du projet 

Cett

### Instructions pour mettre en place le serveur Mock SMTP

Mise en place du serveur Mock: 

- cloner le projet https://github.com/tweakers/MockMock

- modifier le `pom.xml`: 
  
  -  modifier la ligne:
  
  ```xml
   <groupId>org.dstovall</groupId> par <groupId>com.jolira</groupId>
  ```
  
  -  supprimer les lignes:

```xml
 <pluginRepositories>
        <pluginRepository>
            <id>onejar-maven-plugin.googlecode.com</id>
            <url>http://onejar-maven-plugin.googlecode.com/svn/mavenrepo</url>
        </pluginRepository>
</pluginRepositories>
```

- dans le dossier MockMock: `mvn clean install`
- dans le dossier target: `java -jar MockMock-1.4.0.one-jar.jar -p 2525`
- vérification sur le navigateur web: `localhost:8282`



#### TO DO DOCKER



### Instructions pour la configuration



### Description de l'implémentation

 





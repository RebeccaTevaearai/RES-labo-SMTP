# RES-labo-SMTP

Gamboni Fiona, Tevaearai Rébecca

### Description du projet 

Cette application client permet d'envoyer des blagues par mails à des groupes de victimes réparties dans des groupes. Ces groupes sont composés d'un expéditeur et d'au moins deux destinataires. L'utilisateur est en mesure d'établir la liste des victimes et le nombre de groupe à piéger. L'utilisateur peut également définir les blagues envoyées. 

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

- Cloner le repo 

- Modifier à sa guise le dossier config en **respectant la syntaxe**:
  - config.properties où la configutation doit être définie:

  ```properties
  smtpServerAddress=localhost
  smtpServerPort=2525
  numberOfGroups=5
  witnessesToCC=fiona.gamboni@heig-vd.ch,rebecca.tevaearai@heig-vd.ch
  ```

  - messages.utf8 où la liste de blagues doit être définie:

  ```
  Subject: Confession
  
  Hi everyone, I have a big announcement to make:
  I hate you all
  
  ***
  Subject: Missing cat
  
  ...
  ```

  - victims.utf8 où la liste des victimes doit être définie:

  ```
  alec.berney@heig-vd.ch
  david.gonzalezleon@heig-vd.ch
  quentin.forestier@heig-vd.ch
  ...
  ```

- Lancer le programme

### Description de l'implémentation

Pour l'implémentation, nous nous sommes inspirées de la structure proposée dans les webcasts. 

Ainsi notre projet est découpé selon ces classes:



![UML](./images/UML.png)



La classe `ConfigManager` permet de récupérer les informations (configuration, victimes, blagues) contenues dans le dossier de config. 

La classe `Prank` définit qu'une blague est constituée d'un expéditeur, d'une liste de victimes et d'observateurs et d'une blague. 

La classe PrankGenerator permet de créer une ` Prank` pour chaque groupe qu'elle aura constitué.

Ainsi pour chaque `Prank ` un `Message` est envoyé par `SmtpClient`. 
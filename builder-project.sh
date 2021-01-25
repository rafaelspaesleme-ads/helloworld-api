#!/bin/sh

echo "Deseja executar esse projeto local via docker? (S ou N)"
# shellcheck disable=SC2162
read HAS_RUN

if [ $HAS_RUN = "S" ]; then

    echo "Escolha qual perfil seu backend irá rodar: (test ou dev)"
    # shellcheck disable=SC2162
    read PROFILE

    sed -e "s|PROFILEXY|$PROFILE|" -i src/main/resources/application.properties

    zip -r docker_file_bkp.zip Dockerfile

    if [ $PROFILE = "test" ]; then
        sed -e "s|########==||" -i src/main/resources/application-test.properties

        sed -e "s|SPRINGDATABASEURL||" -i Dockerfile
        sed -e "s|SPRINGDATABASEUSERNAME||" -i Dockerfile
        sed -e "s|SPRINGDATABASEPASSWORD||" -i Dockerfile
        sed -e "s|SPRINGDATABASEDDL||" -i Dockerfile
        sed -e "s|SPRINGTRANSLATEENDPOINT||" -i Dockerfile
        sed -e "s|SPRINGTRANSLATETOKENVALUE||" -i Dockerfile
        sed -e "s|SPRINGTRANSLATETOKENKEY||" -i Dockerfile
        sed -e "s|SPRINGGEONAMEAPIWORLDID||" -i Dockerfile
        sed -e "s|SPRINGGEONAMEAPIPLANET||" -i Dockerfile
        sed -e "s|SPRINGGEONAMEAPIUSERNAME||" -i Dockerfile
        sed -e "s|COPYTESTE||" -i Dockerfile

        sed -e "s|EXPOSEPORT|COPY target/world-0.0.1-beta.jar /app|" -i Dockerfile

        sed -e "s|RUNMVNCLEANINSTALL||" -i Dockerfile
        sed -e 's|CMDEXECDOCKER|CMD ["java","-jar","world-0.0.1-beta.jar"]|' -i Dockerfile

        mvn clean install
        docker build -t hello_world_api_img .
        docker run -it -d --name hello_world_api_cont -p 8080:8080 hello_world_api_img
    else
        sed -e "s|SPRINGDATABASEURL|ENV SPRING_DATASOURCE_URL='jdbc:postgresql://helloworlddb:5832/hello_world_db'|" -i Dockerfile
        sed -e "s|SPRINGDATABASEUSERNAME|ENV SPRING_DATASOURCE_USERNAME='postgres'|" -i Dockerfile
        sed -e "s|SPRINGDATABASEPASSWORD|ENV SPRING_DATASOURCE_PASSWORD='banco@1004'|" -i Dockerfile
        sed -e "s|SPRINGDATABASEDDL|ENV SPRING_JPA_HIBERNATE_DDL_AUTO='create'|" -i Dockerfile
        sed -e "s|SPRINGTRANSLATEENDPOINT|ENV SPRING_TRANSLATE_ENDPOINT='https://i18ns.com/api/v1/search'|" -i Dockerfile
        sed -e "s|SPRINGTRANSLATETOKENVALUE|ENV SPRING_TRANSLATE_TOKEN_VALUE='bda3e80aeab08f15f1a340e13b9ae9a04e4d12aa'|" -i Dockerfile
        sed -e "s|SPRINGTRANSLATETOKENKEY|ENV SPRING_TRANSLATE_TOKEN_KEY='x-access-token'|" -i Dockerfile
        sed -e "s|SPRINGGEONAMEAPIWORLDID|ENV SPRING_GEONAME_API_WORLD_ID='6295630'|" -i Dockerfile
        sed -e "s|SPRINGGEONAMEAPIPLANET|ENV SPRING_GEONAME_API_PLANET='Terra'|" -i Dockerfile
        sed -e "s|SPRINGGEONAMEAPIUSERNAME|ENV SPRING_GEONAME_API_USERNAME='rafaelspaesleme_ads'|" -i Dockerfile
        sed -e "s|COPYTESTE|EXPOSE 8080:8080|" -i Dockerfile

        sed -e "s|RUNMVNCLEANINSTALL|RUN mvn clean install|" -i Dockerfile
        sed -e "s|COPYTESTE|ADD . /app|" -i Dockerfile

        sed -e 's|CMDEXECDOCKER|CMD ["java","-jar","target/world-0.0.1-beta.jar"]|' -i Dockerfile


        docker-compose up -d
    fi

    docker ps

else
    echo "Deseja executar o projeto local ou acessa-lo em produção? (LOCAL ou PROD)"
    read TYPE

    if [ $TYPE = "LOCAL" ]; then
        sed -e "s|PROFILEXY|test|" -i src/main/resources/application.properties
        echo "spring.h2.console.settings.web-allow-others=true" >> src/main/resources/application-test.properties
        mvn clean install
        mvn spring-boot:run
    else
        google-chrome https://rpl-helloworld-api.herokuapp.com/world
    fi
fi
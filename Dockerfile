#imagem do pacote de execucao da apicacao em questao
FROM openjdk:11.0.9.1-slim
FROM maven:3.6.3-openjdk-11-slim

#rotulo do projeto
LABEL com.hello.world.version="0.0.1-beta"
LABEL com.hello.world.name="Projeto Hello World API"
LABEL com.hello.world.release-date="11-01-2021"

#Variaveis de ambiente das properties
SPRINGDATABASEURL
SPRINGDATABASEUSERNAME
SPRINGDATABASEPASSWORD
SPRINGDATABASEDDL
SPRINGTRANSLATEENDPOINT
SPRINGTRANSLATETOKENVALUE
SPRINGTRANSLATETOKENKEY
SPRINGGEONAMEAPIWORLDID
SPRINGGEONAMEAPIPLANET
SPRINGGEONAMEAPIUSERNAME

#Criando diretorio da aplicação
RUN mkdir /app

#Criando diretorio para o microservice (aplicacao)
WORKDIR /app

#Copie o .jar no diretório de trabalho
COPYTESTE

EXPOSEPORT

#buildando projeto
RUNMVNCLEANINSTALL

#Comando que será executado assim que executarmos o contêiner
CMDEXECDOCKER
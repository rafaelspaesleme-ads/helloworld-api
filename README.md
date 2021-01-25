<h1>Hello World API</h1>
<p>Uma API para acesso a todos os países, estados, regiões, cidades do mundo. Bem como também, acesso a todos os códigos postais de todo mundo.</p>
<strong>Tradução em cinco idiomas: Portguẽs, Inglês, Francês, Espanhol e Alemão</strong>
<br/>
<a href="https://rpl-helloworld-api.herokuapp.com/world">Acesse a API</a>
<hr/>

**Instruções para executar o projeto Hello World API**




Execução via Docker


 -  Execute o arquivo builder-project.sh com o seguinte comando abaixo (Windos através do WSL):

```bash

bash builder-project.sh
ou
./builder-project.sh

```

-   Ao executar o comando acima, o console do terminal lhe retornar com a seguinte pergunta:

```bash

Deseja executar esse projeto local via docker? (S ou N)

```
Obs.: Você deve escolher conforme as opções apresentadas, (em caixa alta).
<br/>


- Caso deseja executar o projeto via docker, deve-se escolher o perfil de configuração dasd properties do Spring Boot que 
será instanciado junto ao container Docker. Você pode escolher entre o perfil de Teste `test` ou de Desenvolvimento `dev`. 
Veja a seguir:


```bash

Escolha qual perfil seu backend irá rodar: (test ou dev)

```
Obs: Caso escolhe `test`, será levantado um container através do Dockerfile, com o banco de dados H2 embarcado.
Caso escolha `dev`, o script irá rodar o docker-compose afim de levantar o container da aplicação, mais o container do banco de dados PostgreSQL 
(Lembrado que para este caso, sempre que o container for reiniciado, o banco de dados será zerado).
<br/>


- Bom, retornando a estaca zero, vamos novamente para a primeira pergunta do script.

```bash

Deseja executar esse projeto local via docker? (S ou N)

```

- Caso a escolha seja por não executar o projeto via docker, aparecerá uma nova pergunta. A seguinte:

```bash

Deseja executar o projeto local ou acessa-lo em produção? (LOCAL ou PROD)

```

- Caso a escolha seja, `LOCAL`, o script executará o seguinte comando:

```bash

mvn spring-boot:run

```
Obs.: O comando executará o projeto a partir do Maven.

- Caso escolha por `PROD`, e você tenha o Google Chrome instalado em seu computador, o script abrirá a pagina oficial da api 
(caso não execute, ele apresentará a URL de acesso a API, Esta URL você também encontrará neste documento).
<br/>
<br/>
<hr/>
<br/>
<br/>
Pré requisitos para uso local da API:

- Sistema Operacional: Linux, WIndows (WSL ativado) ou Mac
    - SE o WSL não estiver ativado, ou o Windows não for o Windows 10, o melhor a se fazer é não executar 
    o script `builder-project.sh` e sim executar direto o comando `mvn spring-boot:run` após configurar o JDK e maven.
- Versão do JDK 11
- Versão do Maven 3.6
- Docker instalado de preferencia, a partir da versão 19.03.13
- Ter o Google Chrome instalado
- Ter um aplicativo de teste de requisição (Ex.: Postman)
- Ter conexão com a internet
<br/>
<br/>
<hr/>
<br/>
<br/>
Como utilizar a API?

- BaseURL:

```json
https://rpl-helloworld-api.herokuapp.com
```

- Endpoint World:
    - Method: `GET`
    - Content-Type: `application/json`
    - paths: `findAll: /world`, `findByPlanet: /world/search`
    - params: `findByPlanet: planet`
    - response: `findAll: all planet` `findByPlanet: planet select`


```json
findAll: https://rpl-helloworld-api.herokuapp.com/world
findByPlanet: https://rpl-helloworld-api.herokuapp.com/world?search=Terra
```

- Endpoint Continents:
    - Method: `GET`
    - Content-Type: `application/json`
    - paths: `findAll: /world/continents`
    - params: `--`
    - response: `findAll: all continents`


```json
findAll: https://rpl-helloworld-api.herokuapp.com/world/continents
```

- Endpoint Nations:
    - Method: `GET`
    - Content-Type: `application/json`
    - paths: `findAll: /world/continents/nations`, `findByContinent: /world/continents/nations/`
    - params: `findByContinent: searchPerContinent and nativeLang`
    - response: `findAll: all nations`, `findByContinent: nations by selected continent`


```json
findAll: https://rpl-helloworld-api.herokuapp.com/world/continents/nations
findByContinent: https://rpl-helloworld-api.herokuapp.com/world/continents/nations/?searchPerContinent=Afrika&nativeLang=deutsche
```
- Obs: _O parametro `nativeLang` é o parametro de tradução. Seria o identificador da lingua nativa de quem esta a usar este endpoint.
Se você é francês e digitou em `searchPerContinent` sua lingua nativa, escolha por `français`, português por `português`, inglês por `english`, alemão por `deutsche`, espanhol por `español`. 
Só são permitidas essas cinco linguas nesta versão._ 

- Endpoint States:
    - Method: `GET`
    - Content-Type: `application/json`
    - paths: `findAllByNation: /world/continents/nations/federative-units/`
    - params: `findAllByNation: searchPerNation and nativeLang`
    - response: `findAllByNation: states by selected nation`


```json
findByContinent: https://rpl-helloworld-api.herokuapp.com/world/continents/nations/federative-units/?searchPerNation=Trinidad%20and%20Tobago&nativeLang=english
```
- Obs: _O parametro `nativeLang` é o parametro de tradução. Seria o identificador da lingua nativa de quem esta a usar este endpoint.
Se você é francês e digitou em `searchPerNation` sua lingua nativa, escolha por `français`, português por `português`, inglês por `english`, alemão por `deutsche`, espanhol por `español`. 
Só são permitidas essas cinco linguas nesta versão._ 

- Endpoint Cities:
    - Method: `GET`
    - Content-Type: `application/json`
    - paths: `findAllByStates: /world/continents/nations/federative-units/cities/`, `findAllByZipCode: /world/continents/nations/federative-units/cities/zip-code`
    - params: `findAllByStates: searchPerNation, nativeLangNation, and searchPerFederativeUnits`, `findAllByZipCode: searchZipCode and searchShortNameNation`
    - response: `findAllByStates: cities by selected state`, `findAllByZipCode: cities or city by selected zip code`


```json
findAllByStates: https://rpl-helloworld-api.herokuapp.com/world/continents/nations/federative-units/cities/?searchPerNation=Brasil&nativeLangNation=português&searchPerFederativeUnits=Bahia
findAllByZipCode: https://rpl-helloworld-api.herokuapp.com/world/continents/nations/federative-units/cities/zip-code?searchZipCode=25800-000&searchShortNameNation=BR
```
- Obs: _O parametro `nativeLangNation` é o parametro de tradução. Seria o identificador da lingua nativa de quem esta a usar este endpoint.
Se você é francês e digitou em `searchPerNation` sua lingua nativa, escolha por `français`, português por `português`, inglês por `english`, alemão por `deutsche`, espanhol por `español`. 
Só são permitidas essas cinco linguas nesta versão._ 

<br/>
<hr/>
<br/>
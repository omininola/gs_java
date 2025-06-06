# Sprint 1 Java

## Integrantes do Grupo

| Nome                   |   RM   |
| :--------------------- | :----: |
| Otavio Miklos Nogueira | 554513 |
| Luciayla Yumi Kawakami | 557987 |

---

## Descrição do Projeto

Este projeto é uma API REST que se comunica com o Banco da Oracle e
permite por meio de endipoints realizar operações CRUD padrão em motos e
filiais, e além disso, permite também a busca por parâmetros como nome da
filial e paginação dos resultados

---

## Configuração do Projeto

### 1. Requisitos

- Java Development Kit (JDK) 17 ou superior
- Maven
- Banco de dados Oracle acessível
- IDE (IntelliJ IDEA, Eclipse, etc.)

### 2. Configuração do Banco de Dados

Dentro da pasta `resources`, atualize o arquivo `application.properties` com suas credenciais:

```properties
spring.application.name=api_sprint

spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl
spring.datasource.username=RMXXXXXX
spring.datasource.password=CALMAAAA
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 4. Dependências Maven `pom.xml`

```xml
<dependencies>

    <!-- Spring -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- Cache -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>

    <!-- Driver JDBC Oracle -->
    <dependency>
        <groupId>com.oracle.database.jdbc</groupId>
        <artifactId>ojdbc11</artifactId>
        <scope>runtime</scope>
    </dependency>

    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

### 5. Build Maven `pom.xml`

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <annotationProcessorPaths>
                    <path>
                        <groupId>org.projectlombok</groupId>
                        <artifactId>lombok</artifactId>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>

        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <excludes>
                    <exclude>
                        <groupId>org.projectlombok</groupId>
                        <artifactId>lombok</artifactId>
                    </exclude>
                </excludes>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### 6. Importação do Projeto

- Importe o projeto para sua IDE.
- Certifique-se de que as dependências do Maven estão resolvidas corretamente.

---

## Rotas HTTP

URL Base -> **localhost:8080**

### Filiais

| Método | Rota | Parametros | Descrição |
| ------ | ---- | :--------: | --------- |
| POST   | [/filiais](localhost:8080/filiais) | x | Cria uma nova instância de filial |
| GET    | [/filiais](localhost:8080/filiais?page=0&size=10&sort=nome) | page: number & size: number & sort: string | Retorna todas as filiais com controle de página e ordenação |
| GET    | [/filiais/search](localhost:8080/filiais/search?nome=Filial1) | nome: string | Retorna todas as filiais que tem o nome passado no parâmetro |
| GET    | [/filiais/{id}](localhost:8080/filiais/1) | x | Retorna a filial com o id fornecido |
| PUT    | [/filiais/{id}](localhost:8080/filiais/1) | x | Atualiza a filial com o id fornecido |
| DELETE | [/filiais/{id}](localhost:8080/filiais/1) | x | Delete a filial com o id fornecido |

#### JSON de uma filial

```javascript
{
    "nome": string,
    "endereco": string
}
```

### Áreas

| Método | Rota | Parametros | Descrição |
| ------ | ---- | :--------: | --------- |
| POST   | [/areas](localhost:8080/areas) | x | Cria uma nova instância de area |
| GET    | [/areas](localhost:8080/areas?page=0&size=10&sort=status) | page: number & size: number & sort: string | Retorna todas as areas com controle de página e ordenação |
| GET    | [/areas/search](localhost:8080/areas/search?filial=Filial1) | filial: string | Retorna todas as areas que pertencem à filial passada no parâmetro `filial` |
| GET    | [/areas/{id}](localhost:8080/areas/1) | x | Retorna a area com o id fornecido |
| PUT    | [/areas/{id}](localhost:8080/areas/1) | x | Atualiza a area com o id fornecido |
| DELETE | [/areas/{id}](localhost:8080/areas/1) | x | Delete a area com o id fornecido |

#### JSON de uma area

```javascript
{
    "status": string,
    "filialId": number
}
```

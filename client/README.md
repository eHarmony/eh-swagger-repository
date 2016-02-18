eh-swagger-repository-client
============================

Add this client to your swagger enabled service to publish your swagger docs to the eh-swagger-repository


## Configuration

1. Add this to your pom:   
    ```xml   
            <!-- Swagger -->
            <dependency>
                <groupId>com.eharmony.services</groupId>
                <artifactId>eh-swagger</artifactId>
                <version>${eh-swagger-version}</version>
            </dependency> 
            <dependency>
                <groupId>com.eharmony.services</groupId>
                <artifactId>eh-swagger-repository-client</artifactId>
                <version>${eh-swagger-repository-client-version}</version>
            </dependency> 
    ```     
2. Configure your Swaggerizer with the following properties
    1. Add these properties to your swagger bean:
        * enableRepositoryPublish - set to true to turn on publishing
        * environment - coming from your Chef properties: lt, np, prod, int
        * category - General grouping for your service. Eg. matching, singles, shared
        * repositoryHost - host of the central repository
        * apiHost - host vip for your service in the specified environment
    ```xml
        <bean class="com.eharmony.services.swagger.Swaggerizer">
            <property name="apiHost" value="${swagger.ui.host}" />
            <property name="resourcePackage" value="com.eharmony.packages.with.your.resources"/>
            <property name="enableRepositoryPublish" value="true"/>
            <property name="environment" value="${environment}"/>
            <property name="category" value="Matching"/>
            <property name="repositoryHost" value="${swagger.repository.service}"/>
            <property name="apiHost" value="${swagger.ui.host}" />
        </bean>
    ```     
3. Update your Chef properties to include new properties per environment:
    ```yaml
        environment: <np,lt,prod, int>
        swagger_repository_service: <The host of the central swagger repository>
        swagger_ui_host: <Your environments VIP host>
    ```  

## Usage

Once configured, your service will automatically POST what is configured to the central documentation repository. If the
environment is null or local, the POST will be ignored. If the post fails, your application will still continue to start
as usual, but you will see and exception with the reason it failed.
# Blobber

This project is a very simple implementation of a blob store http service that is backed by MySQL

It leverages [Spring Auto-Reconfiguration](https://github.com/cloudfoundry/java-buildpack-auto-reconfiguration) to know how to run "in the cloud". That is, it expects a bound database service to work correctly.

No considerations have been made for performance.

## Deployment on Cloud Foundry

### Security

**Note** I in no way recommend deploying this service to the public internet as it is currently designed. This service is designed as a workaround for when you are unwilling or unable to leverage a cloud file store (e.g. AWS S3).

As written, it is intended to be deployed on **Cloud Foundry** instances that exist behind a firewall.

Aside from the API Key mentioned here, no real considerations have been made to secure the application.

Ensure that `BLOBBER_API_KEY` is set, clients of this store should provide the same key as a header (`X-API-KEY`) when posting files.

```
cf set-env [application-name] BLOBBER_API_KEY [inscrutable-key]
```

### Database

Initialize your database with the following schema

 ```
 CREATE TABLE files 
   ( 
      id            VARCHAR(255), 
      content_bytes LONGBLOB, 
      content_type  VARCHAR(255) 
   ); 
 ```
 
I use the this cf cli plugin: [andreasf/cf-mysql-plugin](https://github.com/andreasf/cf-mysql-plugin) to easily connect to my bound database.

### Push It

```
./gradlew build
cf push [application-name] -p build/libs/blobber-0.0.1-SNAPSHOT.jar
```

## Usage
Upload a file like so

```bash
curl -X POST \
  http://[application-name].cfapps.io/files \
  -H 'content-type: multipart/form-data' \
  -H 'x-api-key: [inscrutable-key]' \
  -F file=@[path-to-your-file].png
```

### Expected Response
Currently the service will return a UUID which you can use to construct a public URL

```json
{
  "uuid": "some-fancy-uuid" 
}
```

becomes:

<http://[application-name].cfapps.io/files/some-fancy-uuid>

## Testing

```bash
./gradlew test
```

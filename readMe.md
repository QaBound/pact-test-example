# PACT Test Example Repo

Repo consists of two services and dockerised PACT broker:
- Consumer Service - Sends downstream request to Provider service
- Provider Service
- Docker Compose (Pact broker & postgres) to run the PACT dependencies on local

## Consumer Service
To Publish the consumer pact test to the broker run 
```mvn clean test pact:publish```

Running the above will run the Test in the class `src/test/java/contract/ConsumerTest.java`
and publish the generated pacts to the broker

## Provider

To run the provider test `mvn clean test`

Currently, the provider test is designed to fail so interested parties can pull and play around.

```
"description": "Expected '€115.00' but received '€114.99999999999999'",
```

The consumer expects €115.00 to be returned but the test fails due to the decimal points.
So clearly the PACT test shows a gap in understanding between the two services, as usually when dealing with currency only 2 decimal points are considered.

## Broker 

Before Running the tests ensure the docker compose is running by navigating to `docker` directory and running 
```
docker-compose up
```

To view the broker enter the url `localhost:9292` into your browser.
After publishing and verifying the changes can be observed.
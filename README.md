## Crypto Investment API Documentation

This Spring Boot application provides an API for retrieving cryptocurrency information.

### Installation

To run this application, you need to have Java installed on your machine.

Clone the repository from GitHub:

```bash
git clone https://github.com/santanu26/recommendation-system.git
```

### Build the application:

```bash
mvn clean install
```

### Run the application:

```bash
java -jar target/recommendation-system.jar
```

The application will be running on port 8080.

### Endpoints

Request:
Return a descending sorted list of all the cryptos,
comparing the normalized range (i.e. (max-min)/min)

```bash
GET /sorted
```

Response:

```json
[
  "ETH",
  "XRP",
  "DOGE",
  "LTC",
  "BTC"
]
Status code: 200 OK
```

Request:
Calculates oldest/newest/min/max for each crypto for the whole month

```bash
GET /stats
```

Response

```json
{
  "BTC": {
    "oldest": {
      "timestamp": "2022-01-01T05:00:00",
      "symbol": "BTC",
      "price": 46813.21
    },
    "newest": {
      "timestamp": "2022-01-31T21:00:00",
      "symbol": "BTC",
      "price": 38415.79
    },
    "min": {
      "timestamp": "2022-01-24T12:00:00",
      "symbol": "BTC",
      "price": 33276.59
    },
    "max": {
      "timestamp": "2022-01-02T01:00:00",
      "symbol": "BTC",
      "price": 47722.66
    }
  },
  "XRP": {
    "oldest": {
      "timestamp": "2022-01-01T01:00:00",
      "symbol": "XRP",
      "price": 0.8298
    },
    "newest": {
      "timestamp": "2022-01-31T02:00:00",
      "symbol": "XRP",
      "price": 0.5867
    },
    "min": {
      "timestamp": "2022-01-24T12:00:00",
      "symbol": "XRP",
      "price": 0.5616
    },
    "max": {
      "timestamp": "2022-01-01T22:00:00",
      "symbol": "XRP",
      "price": 0.8458
    }
  },
  "ETH": {
    "oldest": {
      "timestamp": "2022-01-01T09:00:00",
      "symbol": "ETH",
      "price": 3715.32
    },
    "newest": {
      "timestamp": "2022-01-31T21:00:00",
      "symbol": "ETH",
      "price": 2672.5
    },
    "min": {
      "timestamp": "2022-01-24T11:00:00",
      "symbol": "ETH",
      "price": 2336.52
    },
    "max": {
      "timestamp": "2022-01-03T01:00:00",
      "symbol": "ETH",
      "price": 3828.11
    }
  },
  "DOGE": {
    "oldest": {
      "timestamp": "2022-01-01T06:00:00",
      "symbol": "DOGE",
      "price": 0.1702
    },
    "newest": {
      "timestamp": "2022-01-31T20:00:00",
      "symbol": "DOGE",
      "price": 0.1415
    },
    "min": {
      "timestamp": "2022-01-22T12:00:00",
      "symbol": "DOGE",
      "price": 0.129
    },
    "max": {
      "timestamp": "2022-01-14T17:00:00",
      "symbol": "DOGE",
      "price": 0.1941
    }
  },
  "LTC": {
    "oldest": {
      "timestamp": "2022-01-01T07:00:00",
      "symbol": "LTC",
      "price": 148.1
    },
    "newest": {
      "timestamp": "2022-01-31T18:00:00",
      "symbol": "LTC",
      "price": 109.6
    },
    "min": {
      "timestamp": "2022-01-24T11:00:00",
      "symbol": "LTC",
      "price": 103.4
    },
    "max": {
      "timestamp": "2022-01-17T13:00:00",
      "symbol": "LTC",
      "price": 151.5
    }
  }
}
Status code: 200 OK
```

Request:
Return the oldest/newest/min/max values for a requested crypto

```bash
GET /{cryptoName}/stats
example: http://localhost:8080/ETH/stats
```

Response:

```json
{
  "oldest": {
    "timestamp": 1641024000000,
    "symbol": "ETH",
    "price": 3715.32
  },
  "newest": {
    "timestamp": 1643659200000,
    "symbol": "ETH",
    "price": 2672.5
  },
  "min": {
    "timestamp": 1643018400000,
    "symbol": "ETH",
    "price": 2336.52
  },
  "max": {
    "timestamp": 1641168000000,
    "symbol": "ETH",
    "price": 3828.11
  }
}
Status code: 200 OK
```

Request:
Return the crypto with the highest normalized range for a specific day

```bash
GET /highest/{timestamp}
example: http://localhost:8080/highest/1641009600000
```

Response:

```json
{
  "timestamp": 1641009600000,
  "symbol": "ETH",
  "normalizedRange": 0.6383810110763016
}
Status code: 200 OK
```

### Rate Limiting

* Malicious users will always exist, so it will be really beneficial if at least we can rate limit
  them (based on IP).The API has a rate limiter implemented to prevent abuse. The maximum number of requests per second
  is set to 10.
  If a client exceeds this limit, a 429 Too Many Requests response will be returned.

* Initially the cryptos are only five, but we can include more.

* Run everything on Kubernetes, so containerizing the recommendation service.<br>

```docker
docker pull santanubarua/recommendation-system
```

### Useful Docker and K8s command:

```bash
docker build -t recommendation-system .
docker tag recommendation-system:latest santanubarua/recommendation-system:latest
docker push santanubarua/recommendation-system:latest

Then, run the deployment.yaml and service.yaml
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
```
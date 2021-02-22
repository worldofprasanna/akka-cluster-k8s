# akka-cluster-k8s

[![standard-readme compliant](https://img.shields.io/badge/standard--readme-OK-green.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

POC to run Akka cluster, which is running in multiple JVM s to run inside Kubernetes

## Table of Contents

- [Install](#install)
- [Usage](#usage)
- [Maintainers](#maintainers)
- [Contributing](#contributing)
- [License](#license)

## Install

```
# Needs Docker & Kubernetes setup properly
```

## Usage

Commands used:
```
# To build the docker image
docker build -t akka-in-k8s .

# Exec and run the app
docker run -it --shm-size=1gb -v$(pwd):/opt/app akka-in-k8s bash

# To run the application
HOSTNAME=localhost sbt run

# To deploy it to the local k8s
cd k8s
kubectl create -f .

```

## Maintainers

[@worldofprasanna](https://github.com/worldofprasanna)

## Contributing

PRs accepted.

Small note: If editing the README, please conform to the [standard-readme](https://github.com/RichardLitt/standard-readme) specification.

## License

MIT © 2021 Prasanna

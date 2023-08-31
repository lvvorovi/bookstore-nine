Reason for architecture:
=

- chosen microservice. Separate Auth/Resource servers. Admin FE separate service. Implementation includes only resource
  server.
- separate cache server for keeping the load mentioned in the task, allowing horizontal scaling. Only used by admin part
  of service
- MySQL as persistence layer. No specific requirements in the task, one of those easier to work with.
- containerizing with docker, allowing for automated CI/CD.
- GitHub - VCS solution.
- GitHub Actions - automated build, containerizing, pushing to docker hub.
- Spring Boot main framework
- Spring Security and Spring OAuth2 Resource Server authentication/authorization management
- Spring Cloud properties refresh (Cloud Config server not implemented)

Risks:
=

- no logging or monitoring setup (no requirements)
- security config is for demo
- db volume is not externalized from container intentionally
- if project will grow admin part might be separated from client-facing part to another microservice
- `@RefreshScope` not activated due to lack of Cloud Config server

Source-code:
=

- https://github.com/lvvorovi/bookstore-nine

Pre-compiled package:
=

- containerized https://hub.docker.com/r/lvvorovi/bookstore/tags - 9.
- package https://github.com/lvvorovi/bookstore-nine/blob/master/nine-2.0.jar

Set-up guide:
=

- able to run, but unable to use. All endpoints secured, except `/swagger-ui/index.html` and `/actuator/refresh`.
- use `docker compose -f compose9.yaml up` with https://github.com/lvvorovi/bookstore-nine/blob/master/compose9.yaml
  
Note:
  =
  considered unfinished due to lack of time.





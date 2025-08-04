# SnowCoach

Snowcoach is a remote ski and snowboard coaching app. It allows athletes to submit posts which can include media. These posts can be claimed by coaches which they can respond to and attach media of their own if they wish. Posts can be marked as complete where they will still be visible to athletes in case they wish to review past conversations/lessons. When posts are created they are marked with an ability level and designated sport where coaches are only allowed to claim posts that they are qualified for.

## Stack
The frontend is React with Typescript, the backend is Java SpringBoot with a PostgreSQL database which can be launched via Dockercompose. Follows RESTful design with using HTTP only cookies with JWTs to authorize and authenticate users.

## Future Ideas/Goals
* Possibly allow coaches to create profiles visible to athletes to allow athletes to find coaches they wish to work with or work with the same coach consitently.
* Implement payment processing system.
* Update UI

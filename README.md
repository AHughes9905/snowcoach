# SnowCoach
Snowcoach is a remote ski and snowboard coaching app that allows athletes to create posts. These posts can be claimed by coaches which they can respond to and attach media of their own if they wish. Posts can be marked as complete where they will still be visible to athletes in case they wish to review past conversations/lessons. When posts are created they are marked with an ability level and designated sport where coaches are only allowed to claim posts that they are qualified for.

## Frontend
The frontend is React with TypeScript. The frontend accesses the backend with RESTful API calls. Media is retrieved using a media URL to simulate the use of a media server.

## Backend
The backend is Java SpringBoot with an MVC style architecture. Layers are kept separate in order to improve organization and maintainability. Currently the backend is serving media directly.

## Security
Endpoints are protected by a security filter chain that extracts a JWT that authenticates and authorizes users and their actions. The JWT is stored in HTTP only cookies to mitigate risk of client side manipulation. Passwords are also hashed using BCrypt to ensure privacy. 

## Future Ideas/Goals
* Possibly allow coaches to create profiles visible to athletes to allow athletes to find coaches they wish to work with or work with the same coach consistently.
* Implement payment processing system.
* Update UI

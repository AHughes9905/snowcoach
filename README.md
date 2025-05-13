# snowcoach
Webservice coaching app


## DB Setup
First run docker run --name my-postgres -e POSTGRES_USER=aaron -e POSTGRES_PASSWORD=pass -p 5432:5432 -d postgres

Then run docker exec -it my-postgres psql -U aaron  inorder to get inside of postgres

Then inside postgres rnun docker exec -u aaron my-postgres psql -U aaron -c "CREATE DATABASE snowcoachdb;" to create db

When running progam for first time on db uncomment everything in InitDbService

## Run react dev server
go into frontend directory then run npm run dev
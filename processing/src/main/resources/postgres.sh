#!/bin/bash

docker run --name pg-13.3 -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=masterkey -v /srv/db/postgres:/var/lib/postgersql/dara:rw -d postgres:13.3
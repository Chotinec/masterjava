#!/usr/bin/env bash
liquibase --driver=org.postgresql.Driver
     --classpath=/home/art/tools/liquidbase/lib
     --changeLogFile=/home/art/workRepo/masterjava/config_templates/sql/databaseChangeLog.sql
     --url="jdbc:postgresql://localhost:5432/masterjava"
     --username=postgres
     --password=password
     migrate
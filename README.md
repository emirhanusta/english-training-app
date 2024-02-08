# English Training App

This project is a Spring Boot application that demonstrates user authentication, authorization, and JWT token generation for an English training platform.

## Introduction

The English Training application is designed to facilitate English learning for users. It provides features such as user registration, login, diary management, word tracking, and word list creation.

## Technologies Used

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Elasticsearch
- React
- Bootstrap

## Project Structure

The project is organized into several packages:

- `controller`: Contains controllers for handling authentication, user management, and word-related operations.
- `config`: Includes configuration classes such as password encoder configuration.
- `exception`: Holds custom exception classes.
- `dto`: Contains Data Transfer Objects (DTOs) for data transfer between layers.
- `model`: Defines entities such as User, Role, Diary, Word, and WordList.
- `repository`: Contains repository interfaces for database operations.
- `security`: Includes security-related classes such as JWT filter, authentication entry point, access denied handler, and Spring Security configuration.
- `service`: Implements services for user management, authentication, word operations, and diary management.
- `utils`: Includes utility classes related to Elasticsearch.

## AuthController

The `AuthController` manages authentication and user registration operations.

- `POST /api/v1/auth/login`: Used for user login.
- `POST /api/v1/auth/signup`: Used for user registration.

## DiaryController

The `DiaryController` handles operations related to diaries.

- `GET /api/v1/diary/getAll/{userId}`: Retrieves all diaries of a user.
- `GET /api/v1/diary/get/{id}`: Retrieves a specific diary.
- `POST /api/v1/diary/save`: Creates a new diary entry.
- `PUT /api/v1/diary/update/{id}`: Updates a diary entry.
- `DELETE /api/v1/diary/delete/{id}`: Deletes a diary entry.

## UserController

The `UserController` manages user-related operations.

- `GET /api/v1/user/me`: Retrieves information of the currently authenticated user.
- `PUT /api/v1/user/update/{id}`: Updates user information.
- `DELETE /api/v1/user/delete/{id}`: Deletes a user.

## WordController

The `WordController` handles operations related to words.

- `POST /api/v1/word/save`: Creates a new word.
- `GET /api/v1/word/get/{id}`: Retrieves a specific word.
- `GET /api/v1/word/searchWithName/{name}`: Searches for words with a given name.
- `GET /api/v1/word/getAllWithFilter`: Retrieves all filtered words.
- `PUT /api/v1/word/update/{id}`: Updates a word.
- `DELETE /api/v1/word/delete/{id}`: Deletes a word.

## WordListController

The `WordListController` manages operations related to word lists.

- `POST /api/v1/word-list/save`: Creates a new word list.
- `GET /api/v1/word-list/getAll/{userId}`: Retrieves all word lists of a user.
- `GET /api/v1/word-list/get/{id}`: Retrieves a specific word list.
- `PUT /api/v1/word-list/update/{id}`: Updates a word list.
- `PUT /api/v1/word-list/addWord/{name}/{wordId}`: Adds a word to a word list.
- `PUT /api/v1/word-list/removeWord/{id}/{wordId}`: Removes a word from a word list.
- `DELETE /api/v1/word-list/delete/{id}`: Deletes a word list.


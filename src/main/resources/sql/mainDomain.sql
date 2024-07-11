CREATE TABLE users (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        profile_img VARCHAR(255),
                        is_ban BOOLEAN DEFAULT FALSE,
                        registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE roles (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE posts (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       context TEXT NOT NULL,
                       is_private BOOLEAN DEFAULT FALSE,
                       creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       thumbnail_url VARCHAR(255),
                       author_id BIGINT,
                       CONSTRAINT fk_posts_author FOREIGN KEY (author_id) REFERENCES users(id)
);


CREATE TABLE tags (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE series (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE images (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        url VARCHAR(255) NOT NULL
);

CREATE TABLE about_me (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL UNIQUE,
                        context TEXT NOT NULL,
                        CONSTRAINT fk_about_me_user FOREIGN KEY (user_id) REFERENCES users(id)
);
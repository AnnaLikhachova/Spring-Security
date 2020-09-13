create table user (
   id SERIAL PRIMARY KEY,
   username CHAR(100) NOT NULL UNIQUE,
   password CHAR(100) NOT NULL,
   email CHAR(100) NOT NULL,
   green bit(1) NOT NULL
);

create table role(
   id SERIAL PRIMARY KEY,
   type CHAR(30) NOT NULL UNIQUE
);

INSERT INTO role(name)
VALUES ('USER');

CREATE TABLE user_role (
    user_id SERIAL NOT NULL,
    role_id SERIAL NOT NULL,
    PRIMARY KEY (user_id, user_profile_id),
    CONSTRAINT FK_APP_USER FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT FK_USER_PROFILE FOREIGN KEY (role_id) REFERENCES role (id)
);

create table token (
   id SERIAL PRIMARY KEY,
   token CHAR(100) NOT NULL,
   expirydate CHAR(100) NOT NULL,
   user_id INTEGER NOT NULL,
   FOREIGN KEY (user_id) REFERENCES user(id)
);

create table passwordresettoken (
   id SERIAL PRIMARY KEY,
   token CHAR(100) NOT NULL,
   expirydate CHAR(100) NOT NULL,
   user_id INTEGER NOT NULL,
   FOREIGN KEY (user_id) REFERENCES user(id)
);




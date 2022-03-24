CREATE TABLE IF NOT EXISTS USERFILES
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_Id VARCHAR(60),
    file_name   VARCHAR(60),
    folder_Name   VARCHAR(60),
    created_on Long NOT NULL,
    text VARCHAR NOT NULL
--     PRIMARY KEY(user_Id, file_name, folder_Name)
);

--INSERT INTO USERFILES (userId, fileName, folderName, createdOn, text)
--VALUES ("12ABC", "file name", "folder name", 1230, "text");
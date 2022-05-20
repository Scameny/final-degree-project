
CREATE TABLE IF NOT EXISTS Rol (
    id BIGINT NOT NULL AUTO_INCREMENT, 
    nombre VARCHAR(60),
    CONSTRAINT RolPK PRIMARY KEY (id),
    CONSTRAINT nombreRolUniqueKey UNIQUE (nombre)
)ENGINE=InnoDB;

insert into Rol (id, nombre) values (1, "USER_ROLE");
insert into Rol (id, nombre) values (2, "ADMIN_ROLE");


CREATE TABLE IF NOT EXISTS Usuario (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    apellido1 VARCHAR(30) NOT NULL,
    apellido2 VARCHAR(30),
    nombreUsuario VARCHAR(60) NOT NULL,
    direccionCorreo VARCHAR(60) NOT NULL,
    password VARCHAR(60) NOT NULL,
    idRol BIGINT NOT NULL,
    CONSTRAINT UserPK PRIMARY KEY (id),
    CONSTRAINT UserNameUniqueKey UNIQUE (nombreUsuario),
    CONSTRAINT RoleFK1 FOREIGN KEY (idRol)
        REFERENCES Rol (id)
)ENGINE=InnoDB;

	
CREATE TABLE IF NOT EXISTS Conjunto (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(60) NOT NULL,
    descripcion VARCHAR(400),
    fechaCreacion DATE,
    idAutor BIGINT NOT NULL,
    CONSTRAINT DataSetPK PRIMARY KEY (id),
    CONSTRAINT nameUniqueKey UNIQUE (nombre),
    CONSTRAINT DataSetFK1 FOREIGN KEY (idAutor)
        REFERENCES Usuario (id)
)ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS Cuenta (
    nombre VARCHAR(50),
    nombreUsuario VARCHAR(60) NOT NULL,
    biografia VARCHAR(160),
    ubicacion VARCHAR(30),
    sitioWeb VARCHAR(100),
    fotoPerfil VARCHAR(100),
    fotoEncabezado VARCHAR(100),
    fechaNacimiento DATE,
    fechaCreacion DATE,
    CONSTRAINT AccountPK PRIMARY KEY (nombreUsuario)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ConjuntoCuentas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombreUsuario VARCHAR(60) NOT NULL,
    idConjunto BIGINT NOT NULL,
    CONSTRAINT DataSetPK PRIMARY KEY (id),
    CONSTRAINT DataSetAccountFK1 FOREIGN KEY (nombreUsuario)
        REFERENCES Cuenta (nombreUsuario),
    CONSTRAINT DataSetAccountFK2 FOREIGN KEY (idConjunto)
        REFERENCES Conjunto (id)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Tweet (
    id BIGINT NOT NULL AUTO_INCREMENT,
     nombreUsuario VARCHAR(60) NOT NULL,
    idTweetOrigen BIGINT,
    idTweetMencionado BIGINT,
    enlace VARCHAR(100),
    texto VARCHAR(600),
    fechaHoraTweet DATETIME NOT NULL ,
    multimedia VARCHAR(200),
    plataformaUsada VARCHAR(60),
    CONSTRAINT TweetPK PRIMARY KEY (id),
    CONSTRAINT TweetFK1 FOREIGN KEY (NombreUsuario)
        REFERENCES Cuenta (nombreUsuario),
    CONSTRAINT TweetFK2 FOREIGN KEY (idTweetOrigen)
        REFERENCES Tweet (id),
    CONSTRAINT TweetFK3 FOREIGN KEY (idTweetMencionado)
        REFERENCES Tweet (id)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Retweet (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombreUsuario VARCHAR(60) NOT NULL,
    idTweet BIGINT NOT NULL,
    CONSTRAINT RetweetPK PRIMARY KEY (id),
    CONSTRAINT retweetFK1 FOREIGN KEY (nombreUsuario)
        REFERENCES Cuenta (nombreUsuario),
    CONSTRAINT RetweetFK2 FOREIGN KEY (idTweet)
        REFERENCES Tweet (id)
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS MeGusta (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombreUsuario VARCHAR(60) NOT NULL,
    idTweet BIGINT NOT NULL,
    CONSTRAINT LikePK PRIMARY KEY (id),
    CONSTRAINT LikeFK1 FOREIGN KEY (nombreUsuario)
        REFERENCES Cuenta (nombreUsuario),
    CONSTRAINT LikeFK2 FOREIGN KEY (idTweet)
        REFERENCES Tweet (id)
)ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS Seguimiento (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombreUsuarioSeguidor VARCHAR(60) NOT NULL,
    nombreUsuarioSeguido VARCHAR(60) NOT NULL,
    CONSTRAINT FollowPK PRIMARY KEY (id),
    CONSTRAINT FollowFK1 FOREIGN KEY (nombreUsuarioSeguidor)
        REFERENCES Cuenta (nombreUsuario),
    CONSTRAINT FollowFK2 FOREIGN KEY (nombreUsuarioSeguido)
        REFERENCES Cuenta (nombreUsuario)
)ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS ResultadoClasificador (
    id BIGINT NOT NULL AUTO_INCREMENT,
    idConjunto BIGINT NOT NULL,
    nombreUsuario VARCHAR(60),
    porcentajeBot DECIMAL(5,2),
    CONSTRAINT PercentageBotValueCheck CHECK (porcentajeBot between 0 AND 100),
    porcentajeHumano DECIMAL(5,2),
    CONSTRAINT PercentageHumanValueCheck CHECK (porcentajeHumano between 0 and 100),
    CONSTRAINT ResultPK PRIMARY KEY (id),
    CONSTRAINT ResultFK1 FOREIGN KEY (nombreUsuario)
        REFERENCES Cuenta (nombreUsuario),
    CONSTRAINT ResultFK2 FOREIGN KEY (idConjunto)
        REFERENCES Conjunto (id)
)ENGINE=InnoDB; 

CREATE TABLE IF NOT EXISTS NotasExperto (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombreUsuario VARCHAR(60) NOT NULL,
    idUsuario BIGINT NOT NULL,
    notas VARCHAR(600),
    CONSTRAINT NotesPK PRIMARY KEY (id),
    CONSTRAINT NotesFK1 FOREIGN KEY (nombreUsuario)
        REFERENCES Cuenta (nombreUsuario),
    CONSTRAINT NotesFK2 FOREIGN KEY (idUsuario)
        REFERENCES Usuario (id)
)ENGINE=InnoDB;




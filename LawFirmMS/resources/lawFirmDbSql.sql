Drop database if exists lawfirmms;
Create database if not exists LawFirmMS;
use LawFirmMS;
-- ------------------------- Table Creation -------------------------------

-- User, Admin, Lawyer, Client Tables 

CREATE TABLE Users (
    userID INT PRIMARY KEY,
    CNIC VARCHAR(16) UNIQUE,
    name VARCHAR(255) UNIQUE,
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(255),
    password VARCHAR(50)
);

INSERT INTO Users (userID, CNIC, name, email, phone, address,password) VALUES
(1, '12345-1234567-1', 'John Doe', 'johndoe@example.com', '111-222-3333', '123 Main St, City ABC','abc'),
(2, '23456-2345678-2', 'Jane Smith', 'janesmith@example.com', '222-333-4444', '456 Elm St, City XYZ','abc'),
(3, '34567-3456789-3', 'Alice Johnson', 'alicejohnson@example.com', '333-444-5555', '789 Oak St, City DEF','abc');


CREATE TABLE Lawyers (
    lawyerId INT PRIMARY KEY,
    userId INT,
    specialization VARCHAR(100),
    role VARCHAR(100),
    FOREIGN KEY (userId) REFERENCES Users(userId)
);

INSERT INTO Lawyers (lawyerId, userId, specialization,role) VALUES
(1, 1, 'Criminal Law','Lawyer');

-- delete from Lawyers where lawyerId = 3
CREATE TABLE Admins (
    adminId INT PRIMARY KEY,
    userId INT,
    role VARCHAR(100),
    FOREIGN KEY (userId) REFERENCES Users(userId)
);

INSERT INTO Admins (adminId, userId,role) VALUES
(1, 2, 'Admin'),
(2, 3, 'Admin');

CREATE TABLE Clients (
    clientId INT PRIMARY KEY,
    userId INT,
    caseHistory TEXT,
    FOREIGN KEY (userId) REFERENCES Users(userId)
);

--  Case relevant tables 

CREATE TABLE Cases (
    caseId INT PRIMARY KEY,
    clientId INT,
    caseType VARCHAR(100),
    startDate DATE,
    status VARCHAR(50),
    details TEXT,
    FOREIGN KEY (clientId) REFERENCES Clients(clientId)
);

CREATE TABLE CaseDocuments (
    documentId INT PRIMARY KEY,
    userId INT,
    caseId INT,
    documentName VARCHAR(255),
    documentType VARCHAR(20),
    documentData BLOB,
    uploadedDate DATE,
    FOREIGN KEY (caseId) REFERENCES Cases(caseId),
    FOREIGN KEY (userId) REFERENCES Users(userId)
);

CREATE TABLE HearingDates (
    hearingId INT PRIMARY KEY,
    caseId INT,
    lawyerId INT,
    hearingDateTime DATETIME,
    status ENUM('assigned', 'pending', 'resolved'),
    notes TEXT,
    FOREIGN KEY (caseId) REFERENCES Cases(caseId),
    FOREIGN KEY (lawyerId) REFERENCES Lawyers(lawyerId)
);

-- Performance Table
CREATE TABLE LawyerPerformance (
    performanceId INT PRIMARY KEY,
    lawyerId INT,
    casesWon INT DEFAULT 0,
    hearingsResolved INT DEFAULT 0,
    FOREIGN KEY (lawyerId) REFERENCES Lawyers(lawyerId)
);

select * from users u Inner join lawyers l on u.userId = l.userId  where u.name = "john doe" AND u.password = "abc";
select * from users u Inner join admins a on u.userId = a.userId  where u.name = "jane smith";



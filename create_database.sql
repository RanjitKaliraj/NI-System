<Query Kind="SQL">
  <Connection>
    <ID>76d1e00e-c12b-4a98-92b2-0edd4b79347f</ID>
    <Persist>true</Persist>
    <Server>RONZEYT-PC\SQLEXPRESS</Server>
    <Database>national_ins_db</Database>
  </Connection>
</Query>

CREATE table ItemDefination(
	SID int identity(1,1) NOT NULL PRIMARY KEY,
	UID varchar(255) NOT NULL,
	ItemName varchar(255) NOT NULL,
	Type varchar(255) NOT NULL
	);	
		
CREATE table FormulaSteps(
	SID int NOT NULL FOREIGN KEY REFERENCES ItemDefination(sid),
	Steps int NOT NULL,
	ReturnItem varchar NOT NULL,
	Formula varchar(255) NOT NULL
	);
	
CREATE table FormulaStatus(
	FID int IDENTITY(1,1) NOT NULL PRIMARY KEY,
	SID int NOT NULL FOREIGN KEY REFERENCES ItemDefination(sid),
	UID varchar(255) NOT NULL,
	Status tinyint NOT NULL
	);
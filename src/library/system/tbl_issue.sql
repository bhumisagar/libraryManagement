CREATE TABLE tbl_isuue(
		bookID VARCHAR,
		memberID VARCHAR,
        
	issueTime timestamp default CURRENT_TIMESTAMP,
    renew_count INTEGER default 0,
    
		FOREIGN KEY (bookID) REFERENCES  tbl_addbook(id),
		FOREIGN KEY  (memberID)  REFERENCES tbl_addmembers(id)
        )
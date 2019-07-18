CREATE OR REPLACE function increasePricesFunction(thePublisherID integer, theCount integer) RETURNS integer AS
$$

DECLARE
    currrentCounter integer := 0;
    currentBookID char(6);

DECLARE cursor_i CURSOR FOR
  SELECT b.BookID 
  FROM Books b
  WHERE b.PublisherID = thePublisherID
  ORDER BY b.PublisherID  DESC;

BEGIN
	OPEN cursor_i;
	FETCH FROM cursor_i INTO currentBookID;
    WHILE currrentCounter < theCount AND Found 
	LOOP
		UPDATE Books SET price = price + 1.50 WHERE bookID = currentBookID AND category = 'N';
		UPDATE Books SET price = price + 2.50 WHERE bookID = currentBookID AND category = 'F';
		currrentCounter := currrentCounter + 1;
	END LOOP;
	RETURN currrentCounter;
	CLOSE cursor_i;
END;
$$
LANGUAGE plpgsql



create definer = root@localhost trigger TriggerOnCartInsertDigital
	after insert
	on digitalcontaining
	for each row
	update Cart
        set numberOfProduct = numberOfProduct + new.quantity,
            totalPrice = totalPrice + new.quantity*(
                select price from DigitalProduct D where new.digitalProduct=D.id
            )
        where new.cart=Cart.user;


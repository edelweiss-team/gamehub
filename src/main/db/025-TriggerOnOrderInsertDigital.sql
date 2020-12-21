create definer = root@localhost trigger TriggerOnOrderInsertDigital
	after insert
	on digitalpurchasing
	for each row
	update `Order`
    set numberOfProduct = numberOfProduct + new.quantity,
        totalPrice = totalPrice + new.quantity*(
            select price from DigitalProduct D where new.digitalProduct=D.id
        )
    where new.`order`=`Order`.id;


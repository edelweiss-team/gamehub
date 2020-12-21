create definer = root@localhost trigger TriggerOnOrderQuantityUpdateDigital
	after update
	on digitalpurchasing
	for each row
	update `Order`
    set numberOfProduct = numberOfProduct - old.quantity + new.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from DigitalProduct D where old.digitalProduct=D.id
        )  + new.quantity*(
            select price from DigitalProduct D where old.digitalProduct=D.id
        )
    where old.`order`=`Order`.id;


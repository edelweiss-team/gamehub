create definer = root@localhost trigger TriggerOnOrderRemoveDigital
	before delete
	on digitalpurchasing
	for each row
	update `Order`
    set numberOfProduct=numberOfProduct - old.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from DigitalProduct D where old.digitalProduct=D.id
        )
    where old.`order`=`Order`.id;


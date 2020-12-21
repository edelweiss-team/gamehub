create definer = root@localhost trigger TriggerOnOrderQuantityUpdatePhysical
	after update
	on physicalpurchasing
	for each row
	update `Order`
    set numberOfProduct = numberOfProduct - old.quantity + new.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from PhysicalProduct D where old.physicalProduct=D.id
        )  + new.quantity*(
            select price from PhysicalProduct D where old.physicalProduct=D.id
        )
    where old.`order`=`Order`.id;


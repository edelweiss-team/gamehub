create definer = root@localhost trigger TriggerOnOrderRemovePhysical
	before delete
	on physicalpurchasing
	for each row
	update `Order`
    set numberOfProduct=numberOfProduct - old.quantity,
        totalPrice = totalPrice - old.quantity*(
            select price from PhysicalProduct D where old.physicalProduct=D.id
        )
    where old.`order`=`Order`.id;


SELECT
	pe.RequiredEquipmentTypeCount AS EquipmentRequired,
    e.Count AS EquipmentAvailable,
    (e.Count - pe.RequiredEquipmentTypeCount) AS Leftover
FROM 
	Product AS p
	INNER JOIN ProductEquipment AS pe ON p.ID = pe.ProductID
    INNER JOIN EquipmentType AS et ON pe.EquipmentTypeID = et.ID
    INNER JOIN Equipment AS e ON et.ID = e.TypeID
WHERE 
	pe.EquipmentTypeID = 0;
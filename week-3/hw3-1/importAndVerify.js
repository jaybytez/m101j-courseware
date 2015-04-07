conn = new Mongo();
//Attach to the students collection
db = conn.getDB("school");

//This is the function to loop through a remove the lowest homework score in the array
cursor = db.students.aggregate(
	[{ "$unwind": "$scores" }, 
	 { "$match": { "scores.type": "homework"}},
	 { "$group": {'_id': '$_id', 'minitem': {
	                '$min': "$scores.score"
	            }
	        }
	    }
	]
);

cursor.forEach(
function(coll) {
    db.students.update({
        '_id': coll._id
    }, {
        '$pull': {
            'scores': {
                'score': coll.minitem
            }
        }
    })
})

//Print out the count of documents
print("Count: " + db.students.count());

//Run the aggregate and then print the json response
cursor = db.students.find( { _id : 137 } ).pretty( ) ;
while ( cursor.hasNext() ) {
	printjson( cursor.next() );
}

//Run the aggregate and then print the json response
cursor = db.students.aggregate( { '$unwind' : '$scores' } , { '$group' : { '_id' : '$_id' , 'average' : { $avg : '$scores.score' } } } , { '$sort' : { 'average' : -1 } } , { '$limit' : 1 } );
while ( cursor.hasNext() ) {
	printjson( cursor.next() );
}
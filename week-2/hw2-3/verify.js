conn = new Mongo();
//Attach to the students collection
db = conn.getDB("students");

//Print out the count of documents
print("Count: " + db.grades.count());

//Run the aggregate and then print who holds the 101st grade
cursor = db.grades.find().sort({'score':-1}).skip(100).limit(1);
while ( cursor.hasNext() ) {
	printjson( cursor.next() );
}

//Run the aggregate and then print who holds the highest avg
cursor = db.grades.aggregate({'$group':{'_id':'$student_id', 'average':{$avg:'$score'}}}, {'$sort':{'average':-1}}, {'$limit':1});
while ( cursor.hasNext() ) {
	printjson( cursor.next() );
}
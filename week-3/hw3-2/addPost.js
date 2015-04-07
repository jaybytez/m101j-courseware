conn = new Mongo();
//Attach to the students collection
db = conn.getDB("blog");

//This is the function to loop through a remove the lowest homework score in the array
cursor = db.posts.insert({
    "title" : "Martians to use MongoDB",
    "author" : "andrew",
    "body" : "Representatives from the planet Mars announced today that the planet would adopt MongoDB as a planetary standard. Head Martian Flipblip said that MongoDB was the perfect tool to store the diversity of life that exists on Mars.",
    "permalink" : "martians_to_use_mongodb",
    "tags" : [
        "martians",
        "seti",
        "nosql",
        "worlddomination"
    ],
    "comments" : [ ],
    "date" : ISODate("2013-03-11T01:54:53.692Z")
});

//Print out the count of documents
print("Count: " + db.posts.count());
/*
 * Copyright 2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package course.homework;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;



import freemarker.template.Configuration;
import freemarker.template.Template;



import org.bson.Document;
import org.bson.conversions.Bson;



import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;



import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;



import static java.util.Arrays.asList;

public class Homework2_3 {
    public static void main(String[] args)  {

        MongoClient client = new MongoClient();

        MongoDatabase database = client.getDatabase("students");
        final MongoCollection<Document> collection = database.getCollection("grades");

        Bson filter = Filters.eq("type", "homework");

        Bson sort = Sorts.descending("student_id","score");

        Set<Object> keys = new HashSet<>();
        collection.find(filter).sort(sort).into(new ArrayList<Document>()).stream().forEach(doc -> {
        	if(keys.contains(doc.get("student_id"))) {
        		System.out.println("Already exists " + doc.get("student_id") + " = " + doc.get("score"));
        		collection.deleteOne(Filters.eq("_id", doc.getObjectId("_id")));
        	} else {
        		System.out.println("Does not exist " + doc.get("student_id") + " = " + doc.get("score"));
        		keys.add(doc.get("student_id"));
        	}	
        });
    }
}

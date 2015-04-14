package course;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlogPostDAO {
    MongoCollection<Document> postsCollection;

    public BlogPostDAO(final MongoDatabase blogDatabase) {
        postsCollection = blogDatabase.getCollection("posts");
    }

    // Return a single post corresponding to a permalink
    public Document findByPermalink(String permalink) {
    	return postsCollection.find(Filters.eq("permalink", permalink)).limit(1).first();
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<Document> findByDateDescending(int limit) {
        List<Document> posts = new ArrayList();
        postsCollection.find().sort(new BasicDBObject("date",-1)).limit(limit).iterator().forEachRemaining(doc -> {
        	posts.add(doc);
        });
        return posts;
    }


    public String addPost(String title, String body, List tags, String username) {

        System.out.println("inserting blog entry " + title + " " + body);

        String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();

        // Build the post object and insert it
		Document post = new Document();
		post.append("author", username).append("title", title)
				.append("body", body).append("permalink", permalink)
				.append("tags", tags).append("date", new Date())
				.append("comments", new ArrayList<Object>());
		postsCollection.insertOne(post);

        return permalink;
    }

    // White space to protect the innocent

    // Append a comment to a blog post
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {

		BasicDBObject comment = new BasicDBObject().append("author", name)
				.append("body", body);
		if (email != null) {
			comment.append("email", email);
		}
		postsCollection.updateOne(new BasicDBObject("permalink", permalink),
				new BasicDBObject("$push", new BasicDBObject("comments",
						comment)));
    }
}

package cl.usach.spring.backend.lucene;

import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import cl.usach.spring.backend.database.MongoConection;



public class Index {
	
	public void IndexarTweets(){
		
		try{
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			Directory directory = FSDirectory.open(Paths.get("/tmp/testindex"));
			//Directory directory = new RAMDirectory();
			
			IndexWriter iwriter = new IndexWriter(directory, config);
			
			MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db = mongoClient.getDB("myTwitter");
            DBCollection coll = db.getCollection("cTwitter");
			//MongoConection mc = new MongoConection();
			//MongoCollection<Document> collection = mc.ConectarMongo();
			
			//Getting the iterable object 
		     DBCursor cursor = coll.find(); 

		      //Getting the iterator 
		      //Iterator it = iterDoc.iterator(); 
		      String text;
		      while (cursor.hasNext()) {
		    	  org.apache.lucene.document.Document lDocument = new org.apache.lucene.document.Document();
		    	  DBObject o = (DBObject) cursor.next();
		    	  text = (String) o.get("text");
		         System.out.println(text);
		    	  lDocument.add(new Field("fieldname", text, TextField.TYPE_STORED));
		    	  lDocument.add(new StringField("text", text, Field.Store.YES));
		    	  
		    	  iwriter.addDocument(lDocument);
		      }
		      
		iwriter.close();
		}catch (Exception ex){
			System.out.println(ex);
		}

	}
}
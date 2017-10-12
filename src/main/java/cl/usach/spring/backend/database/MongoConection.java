package cl.usach.spring.backend.database;

import com.mongodb.client.MongoDatabase; 
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import java.util.Iterator;

import org.bson.Document;



public class MongoConection {
	
	
	public MongoCollection<Document> ConectarMongo(){
	    // Creating a Mongo client 
	    MongoClient mongo = new MongoClient( "localhost" , 27017 );
	 
	        
	    //Accessing the database 
	    MongoDatabase database = mongo.getDatabase("myTwitter");
	    //database.getCollection("sampleCollection"); 
	    System.out.println("Collection created successfully"); 
	     
	     //Retrieving a collection
	     MongoCollection<Document> collection = database.getCollection("cTwitter");
	     //collection.drop();
	     System.out.println("Collection sampleCollection selected successfully");
	     return collection;
	      	    
	}
	
	public void agregarDocumento(MongoCollection<Document> collection, long id, String text){
	   
		 //Ejemplo para agregar datos
	      Document document = new Document("id", id) 
	      .append("text", text);
	      collection.insertOne(document); 	   
		
	}
	
	public void mostrarColeccion(MongoCollection<Document> collection){
		 //Getting the iterable object 
	      FindIterable<Document> iterDoc = collection.find(); 

	      //Getting the iterator 
	      Iterator it = iterDoc.iterator(); 
	    
	      while (it.hasNext()) {  
	         System.out.println(it.next());  
	      }
	}

}

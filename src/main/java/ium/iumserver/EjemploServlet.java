package ium.iumserver;

import ium.iumserver.db.MongoDBConnector;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

@WebServlet("/EjemploServlet")
public class EjemploServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        MongoDatabase database = MongoDBConnector.getDatabase();
        MongoCollection<Document> collection = database.getCollection("clubs");

        Document query = new Document("name", "Veria NPS");

        Document result = collection.find(query).first();

        System.out.println(result);

        MongoDBConnector.close();
    }
}

package ium.iumserver;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import ium.iumserver.db.MongoDBConnector;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "SearchPlayers", urlPatterns = "/SearchPlayers")
public class Search extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String[] servletPath = request.getServletPath().split("/");
        Map<String, String[]> pars = request.getParameterMap();

        if (!pars.isEmpty() && pars.containsKey("player_name")) {
            String playerName = pars.get("player_name")[0];
            MongoDatabase database = MongoDBConnector.getDatabase();
            MongoCollection<Document> collection = database.getCollection("players");

            Document query = new Document("first_name", playerName);
            Bson select = Projections.fields(Projections.include("first_name", "current_club_name", "foot"));
            MongoCursor<Document> cursor = collection.find(query).projection(select).cursor();

            ArrayList<String> result = new ArrayList<>();
            
            while (cursor.hasNext()){
                result.add(String.valueOf(cursor.next().toJson()));
            }
            
            System.out.println(result);
            out.println(result);
        }

    }

    public void destroy() {
        MongoDBConnector.close();
    }

}

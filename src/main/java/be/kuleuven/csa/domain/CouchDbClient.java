package be.kuleuven.csa.domain;

import com.google.gson.JsonObject;
import org.lightcouch.Document;
import org.lightcouch.Page;
import org.lightcouch.Response;

import java.util.ArrayList;
import java.util.List;

public class CouchDbClient {
    private org.lightcouch.CouchDbClient client;

    public static void main(String[] args) {
        CouchDbClient couch = new CouchDbClient();
        couch.clearDb();
    }

    public CouchDbClient() {
        client = new org.lightcouch.CouchDbClient();
    }

    public void saveProductTip(ProductTips tips) {
        Response response = client.save(tips);
        System.out.println(response);
        client.shutdown();
    }

    public ProductTips getProductTips(int product_id) {
        String id = ""+product_id;
        ProductTips tips = client.find(ProductTips.class,id);
        return tips;
    }
    public void updateProductTip(ProductTips tips) {
        client.update(tips);
        client.shutdown();
    }
    public void removeProductTip(ProductTips tips) {
        client. remove(tips);
        client.shutdown();
    }

    public ArrayList<ProductTips> getAllProductTips(){
        List<Document> allDocs = client.view("_all_docs").includeDocs(true).query(Document.class);
        ArrayList<ProductTips> result = new ArrayList<>();
        for(Document d: allDocs){
            ProductTips pt = getProductTips(Integer.parseInt(d.getId()));
            result.add(pt);
        }
        return result;
    }

    public void clearDb(){
       for(ProductTips pt : getAllProductTips()){
           removeProductTip(pt);
           this.client = new org.lightcouch.CouchDbClient();
       }
    }

    public org.lightcouch.CouchDbClient getClient() {
        return client;
    }
}

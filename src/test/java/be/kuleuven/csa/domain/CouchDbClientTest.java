package be.kuleuven.csa.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.lightcouch.Document;
import org.lightcouch.DocumentConflictException;
import org.lightcouch.NoDocumentException;

import java.util.ArrayList;
import java.util.List;
//TODO: Make sure that before u run te tests in "persistence.xml" value of "javax.persistence.schema-generation.database.action" is "drop-and-create"
public class CouchDbClientTest {
    @Before
    public void setUp() {
        new CouchDbClient().clearDb();
    }
    @After
    public void tearDown() {
        new CouchDbClient().clearDb();
    }

    @Test
    public void getProductTip_prodId1_Broccoli(){
        Model model = new Model();
        model.initialise();
        ProductTips pt = new CouchDbClient().getProductTips(1);
        Assert.assertNotNull("result should not be null", pt);
        Assert.assertTrue("Name should be Broccoli", pt.getName().equalsIgnoreCase("Broccoli"));
    }

    @Test(expected = NoDocumentException.class)
    public void getProductTip_unknownId_NoDocumentException() {
        ProductTips pt = new CouchDbClient().getProductTips(0);
    }

    @Test
    public void saveProductTip_prodId333_true(){
        ProductTips ptAdd = new ProductTips("ptAddName","ptAddSoort",333);
        new CouchDbClient().saveProductTip(ptAdd);
        ProductTips ptGot = new CouchDbClient().getProductTips(333);
        Assert.assertNotNull("result should not be null", ptGot);
        Assert.assertTrue("Object should have the same properties", ptAdd.equals(ptGot));
    }
    @Test(expected = DocumentConflictException.class)
    public void saveProductTip_prodIdAlreadyInUse_DocumentConflictException(){
        ProductTips pt1 = new ProductTips("pt1Name","pt1Soort",333);
        ProductTips pt2 = new ProductTips("pt2Name","pt2Soort",333);
        new CouchDbClient().saveProductTip(pt1);
        new CouchDbClient().saveProductTip(pt2);
    }

    @Test
    public void updateProductTip_newName_newName(){
        String newName = "newName";
        ProductTips pt = new ProductTips("ptName","ptSoort",333);
        new CouchDbClient().saveProductTip(pt);
        ProductTips ptGot = new CouchDbClient().getProductTips(pt.getProduct_id());
        ptGot.setName(newName);
        new CouchDbClient().updateProductTip(ptGot);
        ProductTips ptCheck = new CouchDbClient().getProductTips(pt.getProduct_id());
        Assert.assertNotNull("result should not be null", ptCheck);
        Assert.assertTrue("new Name should be newName", ptCheck.getName().equalsIgnoreCase(newName));
    }

    @Test(expected = NoDocumentException.class)
    public void removeProductTip_(){
        ProductTips pt = new ProductTips("ptName","ptSoort",333);
        new CouchDbClient().saveProductTip(pt);
        ProductTips ptGot = new CouchDbClient().getProductTips(pt.getProduct_id());
        new CouchDbClient().removeProductTip(ptGot);
        ProductTips ptCheck = new CouchDbClient().getProductTips(pt.getProduct_id());
    }

    @Test
    public void getAllProductTips_size_9(){
        Model model = new Model();
        model.initialise();
        ArrayList<ProductTips> tips = new CouchDbClient().getAllProductTips();
        Assert.assertNotNull("result should not be null", tips);
        Assert.assertTrue("Size should be 9", tips.size()==9);
    }

    @Test
    public void clearDb_size_0(){
        CouchDbClient couch = new CouchDbClient();
        var client = couch.getClient();
        couch.clearDb();
        List<Document> allDocs = client.view("_all_docs").includeDocs(true).query(Document.class);
        Assert.assertNotNull("result should not be null", allDocs);
        Assert.assertTrue("Size should be 0", allDocs.size()==0);
    }
}

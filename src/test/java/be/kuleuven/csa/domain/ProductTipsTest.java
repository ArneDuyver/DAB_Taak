package be.kuleuven.csa.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ProductTipsTest {

    @Test
    public void equals_true(){
        ProductTips pt1 = new ProductTips("ptName","ptSoort",1);
        ProductTips pt2 = new ProductTips("ptName","ptSoort",1);
        Assert.assertTrue("Objects attributes should be equal", pt1.equals(pt2));
    }

    @Test
    public void equals_false(){
        ProductTips pt1 = new ProductTips("ptName","ptSoort",1);
        ProductTips pt2 = new ProductTips("ptName","ptSoort",1);
        pt2.setTips(new ArrayList<>());
        pt2.getTips().add("pt2Tip");
        System.out.println(pt2.getTips().get(0));
        Assert.assertTrue("Objects attributes should not be equal", pt1.equals(pt2));
    }
}
